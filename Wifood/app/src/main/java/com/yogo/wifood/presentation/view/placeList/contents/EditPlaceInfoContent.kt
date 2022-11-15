package com.yogo.wifood.presentation.view.placeList.newPlaceInfo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.*
import com.yogo.wifood.R
import com.yogo.wifood.presentation.view.component.*
import com.yogo.wifood.presentation.view.placeList.RatedMode
import com.yogo.wifood.presentation.view.placeList.componentGroup.YOGOSubTextFieldWithButton
import com.yogo.wifood.view.ui.theme.*

@Composable
fun EditPlaceInfoContent(

){
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        MyPageTopAppBar(
            titleText = "",
            leftButtonOn = true,
            leftButtonClicked = {
                // TODO
            }
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White)
                .verticalScroll(scrollState)
                .padding(horizontal = sidePaddingValue.dp)
        ){
            Spacer(Modifier.height(58.dp))
            YOGOLargeText("맛집 수정")
            Spacer(Modifier.height(24.dp))
            YOGOSubTextFieldWithButton(
                titleText = "맛집 그룹",
                inputText = "강남맛집",
                onValueChange = {},
                onTextFieldClick = {}
            )
            Spacer(Modifier.height(24.dp))
            YOGOSubTextFieldWithButton(
                titleText = "맛집 이름",
                inputText = "울프강 스테이크 하우스",
                onValueChange = {},
                onTextFieldClick = {}
            )
            Spacer(Modifier.height(24.dp))
            YOGOTextPM15(text = "방문 여부")
            YOGOSwitch(
                checked = false,
                onCheckedChange = {},
                modifier = Modifier
            )
            Spacer(Modifier.height(24.dp))
            YOGOTextPM15(text = "맛집 평가")
            Spacer(Modifier.height(10.dp))
            Row {
                for (i in 1..5) {
                    SingleRatingStar(
                        isClicked = i <= 3,
                        starSize = 27
                    )
                    Spacer(Modifier.width(6.dp))
                }
            }
            Spacer(Modifier.height(10.dp))
            Row(
            ) {
                if (true) {
                    RatedMode(
                        text = "친절함",
                        color = KindRateColor
                    )
                    Spacer(Modifier.width(6.dp))
                }
                if (true) {
                    RatedMode(
                        text = "맛집",
                        color = DeliciousRateColor
                    )
                    Spacer(Modifier.width(6.dp))
                }
                if (true) {
                    RatedMode(
                        text = "분위기",
                        color = MoodRateColor
                    )
                    Spacer(Modifier.width(6.dp))
                }
            }
            Spacer(Modifier.height(34.dp))
            YOGOTextPM15("맛집 리뷰")
            Spacer(Modifier.height(10.dp))
            ReviewTextField(
                text = "3시에서 4시 해피아워 10% 할인됨, 본점이랑 비교하기 미션",
                onValueChange = {},
                modifier = Modifier
                    .height(108.dp),
                fontSize = 15,
                showCount = true
            )
            Spacer(Modifier.height(10.dp))
//            PhotoListUpWithSelection(
//                listOf(
//                    R.drawable.place_image, R.drawable.place_image
//                ),
//                imageSize = 60
//            )
            Spacer(Modifier.height(34.dp))
            YOGOSubTextField(
                titleText = "메뉴명",
                inputText = "W코스",
                placeholder = "메뉴를 입력해주세요",
                onValueChange = {

                }
            )
            Spacer(Modifier.height(24.dp))
            YOGOSubTextField(
                titleText = "가격",
                inputText = "198,000",
                placeholder = "가격을 입력해주세요",
                onValueChange = {

                }
            )
            Spacer(Modifier.height(24.dp))
            YOGOSubTextField(
                titleText = "메뉴 리뷰",
                inputText = "가격값하는 맛이지만 또 먹을지는..",
                placeholder = "리뷰를 입력해주세요",
                onValueChange = {

                }
            )
            Spacer(Modifier.height(59.dp))
            Spacer(Modifier.weight(1f))
            MainButton(
                text = "맛집 수정하기",
                onClick = { /*TODO*/ }
            )
            Spacer(Modifier.height(buttonBottomValue.dp))
        }
    }
}