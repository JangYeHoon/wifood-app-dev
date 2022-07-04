package com.example.wifood.presentation.view.placeList

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.example.wifood.presentation.view.component.YOGOTopAppBar
import com.example.wifood.presentation.view.login.component.TitleText
import com.example.wifood.presentation.view.placeList.component.ListSelectionButtonWithIcon
import com.example.wifood.presentation.view.placeList.component.ListSelectionButtonWithoutIcon
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.wifood.R
import com.example.wifood.domain.model.MenuGrade
import com.example.wifood.presentation.util.Route
import com.example.wifood.presentation.util.ValidationEvent
import com.example.wifood.presentation.view.component.MainButton
import com.example.wifood.presentation.view.login.component.InputTextField
import com.example.wifood.presentation.view.login.component.SnsIconButton
import com.example.wifood.presentation.view.placeList.component.PlaceWriteGroupsBottomSheetContent
import com.example.wifood.ui.theme.mainFont
import com.example.wifood.view.ui.theme.*
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarConfig
import com.gowtham.ratingbar.RatingBarStyle
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun PlaceInfoWriteView(
    navController: NavController,
    viewModel: PlaceInfoWriteViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()
    val formState = viewModel.formState
    val initialRating = 1f
    val scope = rememberCoroutineScope()
    val modalBottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scaffoldState = rememberScaffoldState()

    val context = LocalContext.current
    val googleSearchPlaceIntent =
        Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, viewModel.field)
            .setCountry("KR")
            .build(context)
    val googleSearchPlaceLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val searchResult = Autocomplete.getPlaceFromIntent(it.data)
                scope.launch {
                    viewModel.onEvent(PlaceInfoWriteFormEvent.PlaceSelected(searchResult))
                }
            }
        }
    val takePhotoFromCameraLauncher = // 카메라로 사진 찍어서 가져오기
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                scope.launch {
                    val file = File(formState.currentPhotoPath)
                    viewModel.onEvent(PlaceInfoWriteFormEvent.PlaceImagesAdd(Uri.fromFile(file)))
                }
            }
        }

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

    LaunchedEffect(key1 = context) {
        viewModel.validationEvents.collectLatest { event ->
            when (event) {
                is ValidationEvent.Success -> {
                    navController.navigate(Route.Main.route)
                }
                is ValidationEvent.Error -> {
                    scaffoldState.snackbarHostState.showSnackbar(event.message)
                }
            }
        }
    }

    ModalBottomSheetLayout(
        sheetContent = { PlaceWriteGroupsBottomSheetContent() },
        sheetState = modalBottomSheetState,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetBackgroundColor = Color(0xFF222222)
    ) {
        Scaffold(
            topBar = {
                YOGOTopAppBar(
                    text = "맛집 등록",
                    onBackButtonClicked = {/*TODO*/ }
                )
            }
        ) {
            Column(
                modifier = Modifier.verticalScroll(scrollState)
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .padding(bottom = 8.dp)
                ) {
                    ListSelectionButtonWithIcon(
                        buttonText = formState.groupName,
                        onClick = {
                            scope.launch { modalBottomSheetState.show() }
                        }
                    )
                    Divider(
                        color = DividerColor,
                        modifier = Modifier.height(1.dp)
                    )
                    ListSelectionButtonWithIcon(
                        buttonText = formState.placeName,
                        onClick = { googleSearchPlaceLauncher.launch(googleSearchPlaceIntent) }
                    )
                    Divider(
                        color = DividerColor2,
                        modifier = Modifier.height(1.dp)
                    )
                    InputTextField(
                        text = formState.menu,
                        placeholder = " 메뉴",
                        onValueChange = {
                            scope.launch {
                                viewModel.onEvent(PlaceInfoWriteFormEvent.MenuChange(it))
                            }
                        },
                        height = 50
                    )
                    Divider(
                        color = DividerColor2,
                        modifier = Modifier.height(1.dp)
                    )
                    Spacer(Modifier.height(10.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(Modifier.width(10.dp))
                        TitleText(text = "방문 여부")
                        Spacer(Modifier.width(14.dp))
                        Switch(
                            checked = formState.visited,
                            onCheckedChange = {
                                scope.launch {
                                    viewModel.onEvent(PlaceInfoWriteFormEvent.VisitedCheck(it))
                                }
                            },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = MainColor
                            )
                        )
                    }
                }
                Divider(
                    color = DividerColor,
                    modifier = Modifier
                        .height(4.dp)
                        .fillMaxWidth()
                )
                Column(
                    modifier = Modifier
                        .padding(horizontal = 41.dp)
                        .padding(top = 33.dp, bottom = 13.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    TitleText("어떤 점이 만족스러웠나요?")
                    Spacer(Modifier.height(15.dp))
                    RatingBar(
                        value = formState.score,
                        config = RatingBarConfig()
                            .style(RatingBarStyle.HighLighted)
                            .hideInactiveStars(false)
                            .inactiveColor(Color.Black)
                            .numStars(5)
                            .size(27.dp)
                            .padding(9.dp),
                        onValueChange = {
                            scope.launch {
                                viewModel.onEvent(PlaceInfoWriteFormEvent.ScoreChange(it))
                            }
                        },
                        onRatingChanged = {
                            scope.launch {
                                viewModel.onEvent(PlaceInfoWriteFormEvent.ScoreChange(it))
                            }
                        }
                    )
                    Spacer(Modifier.height(15.dp))
                    Row()
                    {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            OutlinedButton(
                                onClick = {
                                    scope.launch {
                                        viewModel.onEvent(PlaceInfoWriteFormEvent.TasteCheck(!formState.tasteChk))
                                    }
                                },
                                shape = CircleShape,
                                border = BorderStroke(1.dp, Color.Yellow),
                                modifier = Modifier.size(56.dp)
                            ) {

                            }
                            Spacer(Modifier.height(10.dp))
                            Text(
                                text = "맛",
                                fontFamily = mainFont,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 12.sp,
                                color = Gray01Color
                            )
                        }
                        Spacer(Modifier.width(18.dp))
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            OutlinedButton(
                                onClick = {
                                    scope.launch {
                                        viewModel.onEvent(PlaceInfoWriteFormEvent.CleanCheck(!formState.cleanChk))
                                    }
                                },
                                shape = CircleShape,
                                border = BorderStroke(1.dp, Color.Yellow),
                                modifier = Modifier.size(56.dp)
                            ) {

                            }
                            Spacer(Modifier.height(10.dp))
                            Text(
                                text = "위생",
                                fontFamily = mainFont,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 12.sp,
                                color = Gray01Color
                            )
                        }
                        Spacer(Modifier.width(18.dp))
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            OutlinedButton(
                                onClick = {
                                    scope.launch {
                                        viewModel.onEvent(PlaceInfoWriteFormEvent.KindCheck(!formState.kindChk))
                                    }
                                },
                                shape = CircleShape,
                                border = BorderStroke(1.dp, EnableColor),
                                modifier = Modifier.size(56.dp)
                            ) {

                            }
                            Spacer(Modifier.height(10.dp))
                            Text(
                                text = "친절",
                                fontFamily = mainFont,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 12.sp,
                                color = Gray01Color
                            )
                        }
                        Spacer(Modifier.width(18.dp))
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            OutlinedButton(
                                onClick = {
                                    scope.launch {
                                        viewModel.onEvent(PlaceInfoWriteFormEvent.VibeCheck(!formState.vibeChk))
                                    }
                                },
                                shape = CircleShape,
                                border = BorderStroke(1.dp, Color.Yellow),
                                modifier = Modifier.size(56.dp)
                            ) {
                            }
                            Spacer(Modifier.height(10.dp))
                            Text(
                                text = "분위기",
                                fontFamily = mainFont,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 12.sp,
                                color = Gray01Color
                            )
                        }
                    }
                }
                Divider(
                    color = DividerColor,
                    modifier = Modifier
                        .height(4.dp)
                        .fillMaxWidth()
                )
                Column(
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .padding(top = 15.dp)
                ) {
                    Row {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    takePhotoFromCameraLauncher.launch(
                                        getPictureIntent(context, viewModel)
                                    )
                                }
//                                takePhotoFromAlbumLauncher.launch(takePhotoFromAlbumIntent)
                            },
                            modifier = Modifier
                                .width(60.dp)
                                .height(60.dp)
                        ) {
                            Icon(
                                ImageVector.vectorResource(id = R.drawable.ic_place_info_photo_default),
                                contentDescription = "",
                                modifier = Modifier.fillMaxSize(),
                                tint = Color.Unspecified
                            )
                        }
                        LazyRow {
                            items(formState.placeImages) { image ->
                                Image(
                                    painter = rememberImagePainter(
                                        data = image
                                    ),
                                    contentDescription = "show place image bitmap",
                                    modifier = Modifier
                                        .width(60.dp)
                                        .height(60.dp)
                                )
                            }
                        }
                    }
                    Spacer(Modifier.height(10.dp))
                    OutlinedTextField(
                        value = formState.review,
                        onValueChange = {
                            scope.launch {
                                viewModel.onEvent(PlaceInfoWriteFormEvent.ReviewChange(it))
                            }
                        },
                        placeholder = {
                            Text(
                                text = "맛집 리뷰",
                                style = TextStyle(
                                    color = Color(0xFFC4C4C4)
                                )
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(108.dp),
                        maxLines = 7,
                        shape = RoundedCornerShape(5.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = MainColor,
                            unfocusedBorderColor = Color(0xFFF1F1F1),
                            cursorColor = Color.Transparent,
                            textColor = Gray01Color,
                        )
                    )
                    Spacer(Modifier.height(38.dp))
                    Row() {
                        TitleText("메뉴평가")
                        Spacer(Modifier.weight(1f))
                        SnsIconButton(
                            resourceId = R.drawable.ic_add_menu_eval_button,
                            size = 17,
                            onClick = {
                                scope.launch {
                                    viewModel.onEvent(PlaceInfoWriteFormEvent.MenuGradeAddBtnClick)
                                }
                            }
                        )
                    }
                    Spacer(Modifier.height(19.dp))
                    InputTextField(
                        text = formState.menuName,
                        placeholder = "메뉴명",
                        height = 50,
                        onValueChange = {
                            scope.launch {
                                viewModel.onEvent(PlaceInfoWriteFormEvent.MenuNameChange(it))
                            }
                        }
                    )
                    InputTextField(
                        text = formState.menuPrice,
                        placeholder = "가격",
                        height = 50,
                        onValueChange = {
                            scope.launch {
                                viewModel.onEvent(PlaceInfoWriteFormEvent.MenuPriceChange(it))
                            }
                        }
                    )
                    InputTextField(
                        text = formState.menuMemo,
                        placeholder = "메모",
                        height = 50,
                        onValueChange = {
                            scope.launch {
                                viewModel.onEvent(PlaceInfoWriteFormEvent.MenuMemoChange(it))
                            }
                        }
                    )
                    formState.menuGrades.forEach { menuGrade ->
                        Text(
                            text = menuGrade.name,
                            fontFamily = mainFont,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 12.sp,
                            color = Gray01Color
                        )
                        Text(
                            text = menuGrade.price.toString(),
                            fontFamily = mainFont,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 12.sp,
                            color = Gray01Color
                        )
                        Text(
                            text = menuGrade.memo,
                            fontFamily = mainFont,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 12.sp,
                            color = Gray01Color
                        )
                    }
                    Spacer(Modifier.height(70.dp))
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                    )

                    MainButton(
                        text = "맛집 등록하기",
                        onClick = {
                            scope.launch {
                                viewModel.onEvent(PlaceInfoWriteFormEvent.PlaceAddBtnClick)
                            }
                        }
                    )
                    Spacer(Modifier.height(50.dp))
                }

            }
        }
    }
}

suspend fun getPictureIntent(context: Context, viewModel: PlaceInfoWriteViewModel): Intent {
    val fullSizeCaptureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    val photoFile: File = File.createTempFile(
        "JPEG_${timeStamp}_", /* prefix */
        ".jpg", /* suffix */
        storageDir /* directory */
    ).apply {
        viewModel.onEvent(PlaceInfoWriteFormEvent.ImageNameChange(absolutePath))
    }
    photoFile.also {
        val photoUri =
            FileProvider.getUriForFile(
                context,
                "com.example.wifood.fileprovider",
                it
            )
        fullSizeCaptureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
    }
    return fullSizeCaptureIntent
}