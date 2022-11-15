package com.yogo.wifood.presentation.view.placeList.newPlaceListComposeView

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
import com.yogo.wifood.R
import com.yogo.wifood.presentation.view.component.*
import com.yogo.wifood.presentation.view.placeList.componentGroup.DoubleButton
import com.yogo.wifood.ui.theme.mainFont
import com.yogo.wifood.view.ui.theme.*


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PlaceInputImagesAndMenuEvaluationContent(

) {
    val scrollState = rememberScrollState()
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
                ReviewTextField()
                Spacer(Modifier.height(24.dp))
//                PhotoListUpWithSelection(
//                    listOf(
//                        R.drawable.place_image,
//                        R.drawable.place_image,
//                        R.drawable.place_image
//                    )
//                )
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