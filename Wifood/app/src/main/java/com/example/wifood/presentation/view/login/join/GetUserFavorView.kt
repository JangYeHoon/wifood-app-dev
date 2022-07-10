package com.example.wifood.presentation.view.login.join

import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.wifood.ui.theme.mainFont
import com.example.wifood.R
import com.example.wifood.presentation.view.component.MainButton
import com.example.wifood.view.ui.theme.DividerColor
import com.example.wifood.view.ui.theme.Gray01Color
import com.example.wifood.view.ui.theme.buttonBottomValue

@ExperimentalComposeUiApi
@Composable
fun GetUserFavorView(
    showDialog:MutableState<Boolean>
) {
    Dialog(
        onDismissRequest = {
            showDialog.value = false
        },
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        ),
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = 85.dp),
            shape = RoundedCornerShape(
                topStart = 30.dp,
                topEnd = 30.dp
            ),
            color = Color(0xFFFFFFFF)
        ) {
            GetUserFavorContent(showDialog)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GetUserFavorContent(
    showDialog:MutableState<Boolean> = mutableStateOf(false)
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .padding(horizontal = 35.dp)
            .fillMaxWidth()
            .verticalScroll(scrollState)
    ) {
        Spacer(Modifier.height(85.dp))
        Text(
            text = "조금만 더\n알려주세요!",
            fontFamily = mainFont,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 28.sp,
            color = Color.Black
        )
        Spacer(Modifier.height(12.dp))
        Text(
            text = "등록한 정보는 추후 개인화 추천에 이용됩니다",
            fontFamily = mainFont,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            color = Color(0xFF565656)
        )
        Spacer(Modifier.height(40.dp))
        UserFavorRadioGroup()
        Text(
            text = "좋아하면 선택해주세요",
            fontFamily = mainFont,
            fontWeight = FontWeight.SemiBold,
            fontSize = 13.sp,
            color = Color.Black
        )
        Spacer(Modifier.height(16.dp))
        UserFavorButtonGroup()
        Spacer(Modifier.height(59.dp))
        MainButton(
            text = "등록하기",
            onClick = {
                // TODO("put infos")
                showDialog.value = false
            }
        )
        Spacer(Modifier.height(buttonBottomValue.dp))
    }
}

@Composable
fun UserFavorRadioGroup() {
    val favorSpacerValue = 25
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        YOGORadioGroup(titleText = "매운맛")
        Spacer(Modifier.height(favorSpacerValue.dp))
        YOGORadioGroup(titleText = "단맛")
        Spacer(Modifier.height(favorSpacerValue.dp))
        YOGORadioGroup(titleText = "짠맛")
        Spacer(Modifier.height(favorSpacerValue.dp))
        YOGORadioGroup(titleText = "신맛")
        Spacer(Modifier.height(favorSpacerValue.dp))
        YOGORadioGroup(titleText = "쓴맛")
        Spacer(Modifier.height(favorSpacerValue.dp))
    }
}

@Composable
fun YOGORadioGroup(
    titleText: String = "테스트"
) {
    var selectedArray = remember { mutableStateListOf<Int>(1, 0, 0, 0, 0) }
    //semibold 13
    Column() {
        Text(
            text = titleText,
            fontFamily = mainFont,
            fontWeight = FontWeight.SemiBold,
            fontSize = 13.sp,
            color = Color.Black
        )
        Spacer(Modifier.height(14.dp))
        Box() {
            Divider(
                color = Color(0xFFE0E0E0),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .align(Alignment.Center)
            )
            Row(
                //verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                YOGORadioButton(selectedArray, 0)
                YOGORadioButton(selectedArray, 1)
                YOGORadioButton(selectedArray, 2)
                YOGORadioButton(selectedArray, 3)
                YOGORadioButton(selectedArray, 4)
            }
        }
        Spacer(Modifier.height(7.dp))
        Row(
            //verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 3.dp)

        ) {
            Text(
                text = "싫음",
                fontWeight = FontWeight.Normal,
                fontFamily = mainFont,
                fontSize = 10.sp,
                color = Gray01Color
            )
            Text(
                text = "조금 싫음",
                fontWeight = FontWeight.Normal,
                fontFamily = mainFont,
                fontSize = 10.sp,
                color = Gray01Color
            )
            Text(
                text = "보통",
                fontWeight = FontWeight.Normal,
                fontFamily = mainFont,
                fontSize = 10.sp,
                color = Gray01Color
            )
            Text(
                text = "조금 좋음",
                fontWeight = FontWeight.Normal,
                fontFamily = mainFont,
                fontSize = 10.sp,
                color = Gray01Color
            )
            Text(
                text = "좋음",
                fontWeight = FontWeight.Normal,
                fontFamily = mainFont,
                fontSize = 10.sp,
                color = Gray01Color
            )
        }

    }
}

@Composable
fun YOGORadioButton(
    selectedArray: MutableList<Int>,
    selectedValue: Int = 0
) {
    IconButton(
        onClick = {
            for (i: Int in 0..4) {
                if (i == selectedValue) {
                    selectedArray[i] = 1
                    continue
                }
                selectedArray[i] = 0
            }
        },
        modifier = Modifier
            .size(24.dp)
    ) {
        Icon(
            ImageVector.vectorResource(id = if (selectedArray[selectedValue] == 1) R.drawable.ic_selected_radiobutton else R.drawable.ic_unselected_radiobutton),
            contentDescription = "",
            modifier = Modifier.wrapContentSize(),
            tint = Color.Unspecified
        )
    }
}

@Composable
fun UserFavorButtonGroup() {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    )
    {
        var cucumberClicked = remember{ mutableStateOf(false)}
        var corianderClicked = remember{ mutableStateOf(false)}
        var mintChokoClicked = remember{ mutableStateOf(false)}
        var eggplantClicked = remember{ mutableStateOf(false)}

        FavorComponent(
            favorText = "오이",
            favorUnClickedId = R.drawable.ic_favor_cucumber,
            favorClickedId = R.drawable.ic_favor_cucumber_clicked,
            isClicked = cucumberClicked
        )
        FavorComponent(
            favorText = "고수",
            favorUnClickedId = R.drawable.ic_favor_coriander,
            favorClickedId = R.drawable.ic_favor_coriander_clicked,
            isClicked = corianderClicked
        )
        FavorComponent(
            favorText = "민트초코",
            favorUnClickedId = R.drawable.ic_favor_mint,
            favorClickedId = R.drawable.ic_favor_mint_clicked,
            isClicked = mintChokoClicked
        )
        FavorComponent(
            favorText = "가지",
            favorUnClickedId = R.drawable.ic_favor_eggplant,
            favorClickedId = R.drawable.ic_favor_eggplant_clicked,
            isClicked = eggplantClicked
        )

    }
}

@Composable
fun FavorComponent(
    favorText:String = "오이",
    favorUnClickedId:Int = R.drawable.ic_favor_cucumber,
    favorClickedId:Int = R.drawable.ic_favor_cucumber_clicked,
    isClicked:MutableState<Boolean>
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            onClick = {
                isClicked.value = !isClicked.value
            },
            modifier = Modifier.size(56.dp)
        ){
            Icon(
                ImageVector.vectorResource(id = if (isClicked.value) favorClickedId else favorUnClickedId ),
                contentDescription = "",
                modifier = Modifier.fillMaxSize(),
                tint = Color.Unspecified
            )
        }
        Spacer(Modifier.height(10.dp))
        Text(
            text = favorText,
            fontFamily = mainFont,
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp,
            color = Gray01Color
        )
    }
}
