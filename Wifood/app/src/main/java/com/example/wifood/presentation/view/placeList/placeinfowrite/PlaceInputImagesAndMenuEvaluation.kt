package com.example.wifood.presentation.view.placeList.placeinfowrite

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.wifood.R
import com.example.wifood.presentation.util.Route
import com.example.wifood.presentation.util.ValidationEvent
import com.example.wifood.presentation.view.component.MainButton
import com.example.wifood.presentation.view.component.MainButtonInversed
import com.example.wifood.presentation.view.component.YOGOTopAppBar
import com.example.wifood.presentation.view.login.component.InputTextField
import com.example.wifood.presentation.view.login.component.TitleText
import com.example.wifood.presentation.view.placeList.component.CameraAndAlbumBottomSheetContent
import com.example.wifood.presentation.view.placeList.componentGroup.DoubleButton
import com.example.wifood.ui.theme.mainFont
import com.example.wifood.view.ui.theme.*
import com.google.gson.Gson
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@DelicateCoroutinesApi
@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun PlaceInputImagesAndMenuEvaluation(
    navController: NavController,
    viewModel: PlaceInfoWriteViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()
    val interactionSource = remember {
        MutableInteractionSource()
    }
    val placeInfoMenuImageSize = 60

    val formState = viewModel.formState
    val scope = rememberCoroutineScope()
    val modalBottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current

    val sheetContent: @Composable (() -> Unit) = { Text("NULL") }
    var customSheetContent by remember { mutableStateOf(sheetContent) }

    LaunchedEffect(key1 = context) {
        viewModel.validationEvents.collectLatest { event ->
            when (event) {
                is ValidationEvent.Success -> {
                    if (formState.placeEditChk) {
                        val placeJson = Uri.encode(Gson().toJson(viewModel.state.place))
                        val groupJson = Uri.encode(Gson().toJson(viewModel.state.group))
                        navController.navigate("${Route.PlaceInfo.route}/${placeJson}/${groupJson}")
                    } else
                        navController.navigate(Route.Main.route)
                }
                is ValidationEvent.Error -> {
                    scaffoldState.snackbarHostState.showSnackbar(event.message)
                }
            }
        }
    }

    ModalBottomSheetLayout(
        sheetContent = { customSheetContent() },
        sheetState = modalBottomSheetState,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetBackgroundColor = Color(0xFF222222)
    ) {
        Scaffold(
            topBar = {
                YOGOTopAppBar(
                    text = "맛집 등록",
                    leftButtonClicked = {/*TODO*/ },
                    rightButtonOn = true,
                    rightButtonClicked = {
                        scope.launch {
                            viewModel.onEvent(PlaceInfoWriteFormEvent.PlaceAddBtnClick)
                        }
                    }
                )
            }
        ) {
            Column(
                modifier = Modifier.verticalScroll(scrollState)
            ) {
                Row {
                    IconButton(
                        onClick = {
                            customSheetContent = { CameraAndAlbumBottomSheetContent() }
                            scope.launch {
                                modalBottomSheetState.show()
                            }
                        },
                        modifier = Modifier
                            .size(placeInfoMenuImageSize.dp)
                    ) {
                        Icon(
                            ImageVector.vectorResource(id = R.drawable.ic_place_info_photo_default),
                            contentDescription = "",
                            modifier = Modifier.fillMaxSize(),
                            tint = Color.Unspecified
                        )
                    }
                    Spacer(Modifier.width(6.dp))
                    LazyRow {
                        items(formState.placeImages) { image ->
                            IconButton(
                                onClick = {},
                                modifier = Modifier
                                    .width(placeInfoMenuImageSize.dp)
                                    .height(placeInfoMenuImageSize.dp)
                            ) {
                                Image(
                                    painter = rememberImagePainter(
                                        data = image
                                    ),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .clip(RoundedCornerShape(5.dp)),
                                    contentScale = ContentScale.Crop,
                                )
                            }
                            Spacer(Modifier.width(6.dp))
                        }
                    }
                }
                Spacer(Modifier.height(10.dp))
                Column(
                    modifier = Modifier
                        .padding(top = 18.dp)
                        .padding(horizontal = 32.dp)
                ) {
                    Row() {
                        TitleText("메뉴평가")
                        Spacer(Modifier.weight(1f))
                        IconButton(
                            onClick = {
                                //isClicked.value = !isClicked.value
                            },
                            modifier = Modifier
                                .size(17.dp)
                        ) {
                            Icon(
                                ImageVector.vectorResource(id = R.drawable.ic_add_menu_eval_button),
                                contentDescription = "",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clickable(
                                        indication = null,
                                        interactionSource = interactionSource
                                    ) {
                                        scope.launch {
                                            viewModel.onEvent(PlaceInfoWriteFormEvent.MenuGradeAddBtnClick)
                                        }
                                    },
                                tint = Color.Unspecified
                            )
                        }
                    }
                    Spacer(Modifier.height(12.dp))
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
                    Spacer(Modifier.height(buttonBottomValue.dp))
                }
            }
        }
    }
}