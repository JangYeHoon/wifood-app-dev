package com.yogo.wifood.presentation.view.placeList.placeinfowrite

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import com.yogo.wifood.R
import com.yogo.wifood.presentation.util.Route
import com.yogo.wifood.presentation.util.ValidationEvent
import com.yogo.wifood.presentation.view.component.*
import com.yogo.wifood.view.ui.theme.buttonBottomValue
import com.yogo.wifood.view.ui.theme.sidePaddingValue
import com.google.gson.Gson
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun PlaceInputReviewAndImages(
    navController: NavController,
    viewModel: PlaceInfoWriteViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()

    val formState = viewModel.formState
    val state = viewModel.state
    val scope = rememberCoroutineScope()

    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current

    val takePhotoFromAlbumIntent =
        Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).apply {
            type = "image/*"
            action = Intent.ACTION_GET_CONTENT
            putExtra(
                Intent.EXTRA_MIME_TYPES,
                arrayOf("image/jpeg", "image/png", "image/bmp", "image/webp")
            )
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false)
        }
    val takePhotoFromAlbumLauncher = // 갤러리에서 사진 가져오기
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.data?.let { uri ->
                    scope.launch {
                        viewModel.onEvent(
                            PlaceInfoWriteFormEvent.PlaceImagesAdd(uri)
                        )
                    }
                }
            }
        }

    BackHandler(enabled = true) {
        navController.previousBackStackEntry?.savedStateHandle?.set("placeBack", state.place)
        navController.popBackStack()
    }

    LaunchedEffect(key1 = context) {
        viewModel.validationEvents.collectLatest { event ->
            when (event) {
                is ValidationEvent.Success -> {
                    if (formState.placeEditChk) {
                        val placeJson = Uri.encode(Gson().toJson(viewModel.state.place))
                        val groupJson = Uri.encode(Gson().toJson(viewModel.state.group))
                        navController.navigate("${Route.PlaceInfo.route}/${placeJson}/${groupJson}")
                    } else {
                        navController.navigate(Route.AddNewPlaceComplete.route)
                    }
                }
                is ValidationEvent.Error -> {
                    scaffoldState.snackbarHostState.showSnackbar(event.message)
                }
            }
        }
    }
    Scaffold(
        topBar = {
            PlaceInputTopAppBar(
                leftButtonClicked = {
                    navController.popBackStack()
                }
            )
        }
    ) {
        if (formState.isLoading)
            ProgressIndicator()

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(horizontal = sidePaddingValue.dp)
            ) {
                Spacer(Modifier.weight(1f))
                Icon(
                    ImageVector.vectorResource(id = R.drawable.ic_4by4),
                    contentDescription = "",
                    modifier = Modifier.wrapContentSize(),
                    tint = Color.Unspecified
                )
                Spacer(Modifier.height(6.dp))
                YOGOBasicText(
                    largeText = "맛집 리뷰를 등록해주세요.",
                    explainText = "맛집 리뷰와 사진을 등록해주세요."
                )
                Spacer(Modifier.height(21.dp))
                ReviewTextField(
                    text = formState.review,
                    onValueChange = {
                        scope.launch {
                            viewModel.onEvent(PlaceInfoWriteFormEvent.ReviewChange(it))
                        }
                    }
                )
                Spacer(Modifier.height(24.dp))
                PhotoListUpWithSelection(
                    formState.placeImages,
                    photoAddClick = {
                        scope.launch {
                            takePhotoFromAlbumLauncher.launch(takePhotoFromAlbumIntent)
                        }
                    }
                )
                Spacer(Modifier.height(24.dp))
                Spacer(Modifier.weight(1f))
                MainButton(
                    text = "맛집 등록하기",
                    onClick = {
                        scope.launch {
                            viewModel.onEvent(PlaceInfoWriteFormEvent.PlaceAddBtnClick)
                        }
                    }
                )
                Spacer(Modifier.height(buttonBottomValue.dp))
            }
        }
    }
}