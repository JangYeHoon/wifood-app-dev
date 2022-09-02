package com.example.wifood.presentation.view.placeList.placeListComposeView

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.util.TableInfo
import com.example.wifood.R
import com.example.wifood.presentation.view.placeList.componentGroup.DoubleButton
import com.example.wifood.ui.theme.mainFont
import com.example.wifood.view.ui.theme.*


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Preview(showBackground = true)
@Composable
fun PlaceInputImagesAndMenuEvaluationContent() {
    val scrollState = rememberScrollState()
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        topBar = {
            PlaceInputTopAppBar(
                leftButtonClicked = {

                },
                rightButtonClicked = {

                }
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
                YOGOBasicText(
                    largeText = "맛집 리뷰를 등록해주세요.",
                    explainText = "맛집 리뷰와 사진을 등록해주세요."
                )
                Spacer(Modifier.height(21.dp))
                ReviewTextField()
                Spacer(Modifier.height(24.dp))
                PhotoListUp(
                    listOf(
                        R.drawable.place_image,
                        R.drawable.place_image,
                        R.drawable.place_image
                    )
                )
                Spacer(Modifier.height(24.dp))
                Spacer(Modifier.weight(1f))
                DoubleButton(
                    leftButtonText = "건너뛰기",
                    leftButtonClicked = {},
                    rightButtonText = "맛집 평가",
                    rightButtonClicked = {}
                )
                Spacer(Modifier.height(buttonBottomValue.dp))
            }
        }
    }
}

@Composable
fun YOGOBasicText(
    largeText: String = "",
    explainText: String = ""
) {
    YOGOLargeText(text = largeText)
    if (explainText.isNotEmpty()) {
        Spacer(Modifier.height(12.dp))
        YOGOExplainText(text = explainText)
    }
}

@Composable
fun YOGOLargeText(
    text: String = "맛집 리뷰를 등록해주세요"
) {
    Text(
        text = text,
        fontFamily = mainFont,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        color = Black2Color
    )
}

@Composable
fun YOGOExplainText(
    text: String = "맛집리뷰와 사진을 등록해주세요"
) {
    Text(
        text = text,
        fontFamily = mainFont,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        color = Gray03Color
    )
}

@Composable
fun ReviewTextField(
    text: String = "",
    onValueChange: (String) -> Unit = {},
    placeholder: String = "맛집 리뷰",
    modifier: Modifier = Modifier
        .border(1.dp, Color(0xFFF1F1F1))
        .height(120.dp)
        .fillMaxWidth()
) {
    Box(

    ) {
        TextField(
            value = text,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = placeholder,
                    fontFamily = mainFont,
                    fontWeight = FontWeight.Normal,
                    fontSize = 15.sp,
                    color = Color(0xFFC4C4C4)
                )
            },
            modifier = modifier,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
            ),
            textStyle = TextStyle(
                fontFamily = mainFont,
                fontWeight = FontWeight.Normal,
                fontSize = 15.sp,
                color = Gray01Color
            )
        )
        Text(
            text = text.length.toString() + "/500",
            fontFamily = mainFont,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 14.dp, bottom = 10.dp),
            color = Color(0xFFC4C4C4)
        )
    }
}

@Composable
fun PhotoDefaultIcon(
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
        .size(60.dp)
){
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            ImageVector.vectorResource(id = R.drawable.ic_place_info_photo_default),
            contentDescription = "",
            modifier = Modifier.wrapContentSize(),
            tint = Color.Unspecified
        )
    }
}


@Composable
fun PhotoListUp(
    imageList: List<Int> = listOf(R.drawable.place_image, R.drawable.place_image)
){
    val photoSize = 60
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ){
        PhotoDefaultIcon(
            modifier = Modifier.size(photoSize.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))

        LazyRow {
            items(imageList) {
                Image(
                    painter = painterResource(id = it),
                    contentDescription = "",
                    modifier = Modifier
                        .size(photoSize.dp)
                        .clip(
                            RoundedCornerShape(5.dp)
                        ),
                    contentScale = ContentScale.FillBounds
                )
                Spacer(modifier = Modifier.width(6.dp))
            }
        }
    }
}

@Composable
fun PlaceInputTopAppBar(
    leftButtonClicked:() -> Unit = {},
    rightButtonClicked:() -> Unit = {}
){
    val interactionSource = remember {
        MutableInteractionSource()
    }

    TopAppBar(
        title = {
        },
        navigationIcon = {
            IconButton(
                onClick = leftButtonClicked,
                modifier = Modifier.wrapContentSize()
            ) {
                Icon(
                    ImageVector.vectorResource(id = R.drawable.ic_back_arrow),
                    contentDescription = "left button of top app bar",
                    modifier = Modifier.wrapContentSize(),
                    tint = Color.Unspecified
                )
            }
        },
        backgroundColor = Color.White,
        actions = {
            Text(
                text = "등록하기",
                fontFamily = mainFont,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                color = MainColor,
                modifier = Modifier
                    .clickable (
                        indication = null,
                        interactionSource = interactionSource
                    ){
                        rightButtonClicked
                    }
                    .padding(end = 20.dp)
            )
        },
        contentColor = Color(0xFFF1F1F1),
        elevation = 1.dp
    )
}