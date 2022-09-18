package com.example.wifood.presentation.view.mypage.contents

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.wifood.R
import com.example.wifood.presentation.view.component.MyPageTopAppBar
import com.example.wifood.presentation.view.mypage.MyPageEvent
import com.example.wifood.presentation.view.mypage.MyPageViewModel
import com.example.wifood.presentation.view.placeList.component.CameraAndAlbumBottomSheetContent
import com.example.wifood.ui.theme.mainFont
import com.example.wifood.view.ui.theme.Black2Color
import com.example.wifood.view.ui.theme.EnableColor
import com.example.wifood.view.ui.theme.MainColor
import com.example.wifood.view.ui.theme.sidePaddingValue
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ModifyUserProfileContent(
    viewModel: MyPageViewModel = hiltViewModel(),
    onBackButtonClicked: () -> Unit = {},
    onCameraButtonClicked: () -> Unit = {},
    onNicknameTextChanged: (String) -> Unit = {},
    onCompleteButtonClicked: () -> Unit = {}
) {
    val scrollState = rememberScrollState()
    val sheetContent: @Composable (() -> Unit) = { Text("NULL") }
    var customSheetContent by remember { mutableStateOf(sheetContent) }
    val scope = rememberCoroutineScope()
    val modalBottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val state = viewModel.state.value
    val interactionSource = MutableInteractionSource()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        MyPageTopAppBar(
            titleText = "프로필 수정",
            leftButtonOn = true,
            leftButtonClicked = {
                onBackButtonClicked()
            },
            rightButtonOn = true,
            rightButtonClicked = {
                onCompleteButtonClicked()
            },
            rightButtonText = "완료"
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            Box(
                modifier = Modifier
                    .size(100.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.profile),
                    contentDescription = "",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(
                            shape = CircleShape
                        ),
                    contentScale = ContentScale.FillBounds
                )
                Icon(
                    painterResource(R.drawable.ic_camera_button),
                    contentDescription = "",
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .wrapContentSize()
                        .align(Alignment.BottomEnd)
                        .clickable(
                            indication = null,
                            interactionSource = interactionSource
                        ) {
                            customSheetContent = { CameraAndAlbumBottomSheetContent() }
                            scope.launch {
                                modalBottomSheetState.show()
                            }
                        }
                )
            }
            Spacer(Modifier.height(24.dp))
        }
        Column(
            modifier = Modifier
                .padding(horizontal = sidePaddingValue.dp)
        ) {
            Text(
                text = "닉네임",
                fontFamily = mainFont,
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp,
                color = Black2Color
            )
            Spacer(Modifier.height(7.dp))
            TextField(
                value = state.nickname,
                onValueChange = {
                    viewModel.onEvent(MyPageEvent.NicknameChanged(it))
                },
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = Color(0xFFCFCFCF),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .wrapContentHeight()
                    .fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    focusedIndicatorColor = Color.Unspecified,
                    unfocusedIndicatorColor = Color.Unspecified,
                    cursorColor = MainColor
                ),
                textStyle = TextStyle(
                    fontFamily = mainFont,
                    fontWeight = FontWeight.Normal,
                    fontSize = 15.sp,
                    color = Black2Color
                ),
                placeholder = {
                    Text(
                        text = "닉네임을 입력해주세요",
                        fontFamily = mainFont,
                        fontWeight = FontWeight.Normal,
                        fontSize = 15.sp,
                        color = EnableColor
                    )
                }
            )
        }
    }
}