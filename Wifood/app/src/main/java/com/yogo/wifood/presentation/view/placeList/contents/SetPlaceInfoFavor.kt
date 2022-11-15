package com.yogo.wifood.presentation.view.placeList.newPlaceListComposeView

import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yogo.wifood.R
import com.yogo.wifood.presentation.view.component.PlaceInputTopAppBar
import com.yogo.wifood.presentation.view.component.SingleRatingStar
import com.yogo.wifood.presentation.view.component.YOGOLargeText
import com.yogo.wifood.presentation.view.groupComponent.SingleIconWithText
import com.yogo.wifood.presentation.view.placeList.componentGroup.DoubleButton
import com.yogo.wifood.presentation.view.placeList.placeinfowrite.PlaceInfoWriteFormEvent
import com.yogo.wifood.presentation.view.placeList.placeinfowrite.PlaceInfoWriteViewModel
import com.yogo.wifood.ui.theme.mainFont
import com.yogo.wifood.view.ui.theme.EnableColor
import com.yogo.wifood.view.ui.theme.Gray01Color
import com.yogo.wifood.view.ui.theme.buttonBottomValue
import com.yogo.wifood.view.ui.theme.sidePaddingValue
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SetPlaceInfoFavor(
) {
    val scrollState = rememberScrollState()
    Scaffold(
        topBar = {
            PlaceInputTopAppBar(
                leftButtonClicked = {

                },
                rightButtonClicked = {

                },
                rightButtonOn = true
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
                    ImageVector.vectorResource(id = R.drawable.ic_2by4),
                    contentDescription = "",
                    modifier = Modifier.wrapContentSize(),
                    tint = Color.Unspecified
                )
                Spacer(Modifier.height(6.dp))
                YOGOLargeText(
                    text = "맛집을 평가해주세요."
                )
                Spacer(Modifier.height(24.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 1.dp,
                            color = EnableColor,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(horizontal = 27.dp)
                        .padding(vertical = 42.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    SimpleText(
                        "맛의 만족도"
                    )
                    Spacer(Modifier.height(5.dp))
//                    RateFavor()
                    Spacer(Modifier.height(36.dp))
                    SimpleText(
                        "추가적으로 뭐가 더 좋았는지"
                    )
                    Spacer(Modifier.height(20.dp))
//                    SelectAdditionalFavor()
                    // TODO
                }
                Spacer(Modifier.height(24.dp))
                Spacer(Modifier.weight(1f))
                DoubleButton(
                    leftButtonText = "건너뛰기",
                    leftButtonClicked = {

                    },
                    rightButtonText = "리뷰 입력하기",
                    rightButtonClicked = {

                    }
                )
                Spacer(Modifier.height(buttonBottomValue.dp))

            }
        }
    }
}

@Composable
fun SimpleText(
    text: String = "맛의 만족도"
) {
    Text(
        text = text,
        fontFamily = mainFont,
        fontWeight = FontWeight.Bold,
        fontSize = 15.sp,
        color = Gray01Color
    )
}

@Composable
fun RateFavor(starScore: List<Int>, viewModel: PlaceInfoWriteViewModel) {
    val scope = rememberCoroutineScope()
    Row(
    ) {
        SingleRatingStar(
            isClicked = starScore[0] == 1,
            onClick = {
                scope.launch {
                    viewModel.onEvent(PlaceInfoWriteFormEvent.ScoreChange(0))
                }
            },
            starSize = 36
        )
        Spacer(Modifier.width(9.dp))
        SingleRatingStar(
            isClicked = starScore[1] == 1,
            onClick = {
                scope.launch {
                    viewModel.onEvent(PlaceInfoWriteFormEvent.ScoreChange(1))
                }
            },
            starSize = 36
        )
        Spacer(Modifier.width(9.dp))
        SingleRatingStar(
            isClicked = starScore[2] == 1,
            onClick = {
                scope.launch {
                    viewModel.onEvent(PlaceInfoWriteFormEvent.ScoreChange(2))
                }
            },
            starSize = 36
        )
        Spacer(Modifier.width(9.dp))
        SingleRatingStar(
            isClicked = starScore[3] == 1,
            onClick = {
                scope.launch {
                    viewModel.onEvent(PlaceInfoWriteFormEvent.ScoreChange(3))
                }
            },
            starSize = 36
        )
        Spacer(Modifier.width(9.dp))
        SingleRatingStar(
            isClicked = starScore[4] == 1,
            onClick = {
                scope.launch {
                    viewModel.onEvent(PlaceInfoWriteFormEvent.ScoreChange(4))
                }
            },
            starSize = 36
        )
    }
}

@Composable
fun SelectAdditionalFavor(
    viewModel: PlaceInfoWriteViewModel,
    tasteChk: Boolean,
    cleanChk: Boolean,
    kindChk: Boolean,
    vibeChk: Boolean
) {
    val scope = rememberCoroutineScope()
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        SingleIconWithText(
            text = "위생",
            UnClickedSourceId = R.drawable.ic_place_info_clean_unclicked,
            ClickedSourceId = R.drawable.ic_place_info_clean_clicked,
            isClicked = cleanChk,
            onClick = {
                scope.launch {
                    viewModel.onEvent(PlaceInfoWriteFormEvent.CleanCheck(!cleanChk))
                }
            }
        )
        SingleIconWithText(
            text = "친절",
            UnClickedSourceId = R.drawable.ic_place_info_kind_unclicked,
            ClickedSourceId = R.drawable.ic_place_info_kind_clicked,
            isClicked = kindChk,
            onClick = {
                scope.launch {
                    viewModel.onEvent(PlaceInfoWriteFormEvent.KindCheck(!kindChk))
                }
            }
        )
        SingleIconWithText(
            text = "분위기",
            UnClickedSourceId = R.drawable.ic_place_info_mood_unclicked,
            ClickedSourceId = R.drawable.ic_place_info_mood_clicked,
            isClicked = vibeChk,
            onClick = {
                scope.launch {
                    viewModel.onEvent(PlaceInfoWriteFormEvent.VibeCheck(!vibeChk))
                }
            }
        )
        SingleIconWithText(
            text = "주차",
            UnClickedSourceId = R.drawable.ic_place_info_parking_unclicked,
            ClickedSourceId = R.drawable.ic_place_info_parking_clicked,
            isClicked = tasteChk,
            onClick = {
                scope.launch {
                    viewModel.onEvent(PlaceInfoWriteFormEvent.TasteCheck(!tasteChk))
                }
            }
        )
    }
}