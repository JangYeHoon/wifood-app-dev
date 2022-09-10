package com.example.wifood.presentation.view.placeList.newPlaceInfo

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wifood.R
import com.example.wifood.presentation.view.component.MainButton
import com.example.wifood.presentation.view.component.YOGOLargeText
import com.example.wifood.presentation.view.component.YOGOTextPM15
import com.example.wifood.presentation.view.placeList.newPlaceListComposeView.PlaceInputTopAppBar
import com.example.wifood.ui.theme.mainFont
import com.example.wifood.view.ui.theme.*


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SetPlaceInfoMenuInfo() {
    val scrollState = rememberScrollState()
    val scaffoldState = rememberScaffoldState()
    val interactionSource = remember {
        MutableInteractionSource()
    }

    Scaffold(
        topBar = {
            PlaceInputTopAppBar(
                leftButtonClicked = {

                },
                rightButtonOn = false
            )
        }
    ) {
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
                    ImageVector.vectorResource(id = R.drawable.ic_3by4),
                    contentDescription = "",
                    modifier = Modifier.wrapContentSize(),
                    tint = Color.Unspecified
                )
                Spacer(Modifier.height(6.dp))
                YOGOLargeText(
                    text = "맛집 정보를 등록해주세요."
                )
                Spacer(Modifier.height(24.dp))
                YOGOSubTextField(
                    titleText = "메뉴명",
                    inputText = "",
                    placeholder = "메뉴명을 입력해주세요",
                    onValueChange = {}
                )
                Spacer(Modifier.height(24.dp))
                YOGOSubTextField(
                    titleText = "가격",
                    inputText = "",
                    placeholder = "가격을 입력해주세요",
                    onValueChange = {}
                )
                Spacer(Modifier.height(24.dp))
                YOGOSubTextField(
                    titleText = "메뉴 리뷰",
                    inputText = "",
                    placeholder = "메뉴 리뷰를 입력해주세요",
                    onValueChange = {}
                )
                Spacer(Modifier.height(24.dp))
                Icon(
                    ImageVector.vectorResource(id = R.drawable.ic_add_menu_eval_button),
                    contentDescription = "left button of top app bar",
                    modifier = Modifier
                        .wrapContentSize()
                        .clickable(
                            indication = null,
                            interactionSource = interactionSource
                        ) {
                            //TODO
                        },
                    tint = Color.Unspecified
                )
                Spacer(Modifier.height(24.dp))
                Spacer(Modifier.weight(1f))
                MainButton(
                    text = "맛집 등록하기",
                    onClick = { /*TODO*/ }
                )
                Spacer(Modifier.height(buttonBottomValue.dp))
            }
        }
    }
}

@Composable
fun YOGOSubTextField(
    titleText: String = "메뉴명",
    inputText: String = "햄버거",
    placeholder: String = "메뉴명을 입력해주세요",
    onValueChange: (String) -> Unit = {},
    transformEnable: Boolean = false
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        YOGOTextPM15(
            titleText
        )
        if (transformEnable) {
            YOGOTransformTextField(
                text = inputText,
                onValueChange = onValueChange,
                placeholderText = placeholder
            )
        } else {
            YOGOBaseTextField(
                text = inputText,
                onValueChange = onValueChange,
                placeholderText = placeholder
            )
        }
    }
}


@Composable
fun YOGOSubTextFieldWithButton_SB(
    titleText: String,
    inputText: String = "",
    placeholder: String = "맛집 그룹을 입력해주세요",
    onValueChange: (String) -> Unit = {},
    onTextFieldClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        YOGOTextPM15(
            buildText = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = MainColor
                    ),
                ) {
                    append("* ")
                }
                append(titleText)
            }
        )
        Box(
            modifier = Modifier
                .wrapContentSize()
                .clickable() {
                    onTextFieldClick()
                }
        ){
            YOGOBaseTextField(
                text = inputText,
                onValueChange = onValueChange,
                placeholderText = placeholder,
                selectable = true,
                selectFunction = onTextFieldClick
            )
        }
    }
}