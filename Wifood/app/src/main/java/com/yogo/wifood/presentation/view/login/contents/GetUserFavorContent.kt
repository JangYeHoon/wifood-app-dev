package com.yogo.wifood.presentation.view.login.contents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yogo.wifood.R
import com.yogo.wifood.WifoodApp
import com.yogo.wifood.presentation.view.component.MainButton
import com.yogo.wifood.presentation.view.component.ProgressIndicator
import com.yogo.wifood.presentation.view.groupComponent.SingleIconWithText
import com.yogo.wifood.presentation.view.login.util.SignUpData
import com.yogo.wifood.presentation.view.main.util.MainData
import com.yogo.wifood.ui.theme.mainFont
import com.yogo.wifood.view.ui.theme.Black2Color
import com.yogo.wifood.view.ui.theme.Gray01Color
import com.yogo.wifood.view.ui.theme.buttonBottomValue
import com.yogo.wifood.view.ui.theme.sidePaddingValue

//@Preview(showBackground = true)
@Composable
fun GetUserFavorContent(
    isLoading: Boolean = false,
    onFavorSelected: (Int, Int) -> Unit = { select: Int, index: Int -> },
    onCucumberClicked: (Boolean) -> Unit = {},
    onCorianderClicked: (Boolean) -> Unit = {},
    onMintClicked: (Boolean) -> Unit = {},
    onEggplantClicked: (Boolean) -> Unit = {},
    onCompleteButtonClicked: () -> Unit = {}
) {
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            ProgressIndicator()
        }

        Column(
            modifier = Modifier
                .padding(horizontal = sidePaddingValue.dp)
                .verticalScroll(scrollState)
        ) {
            Spacer(Modifier.height(106.dp))
            Icon(
                ImageVector.vectorResource(id = R.drawable.ic_4by4),
                contentDescription = "",
                modifier = Modifier.wrapContentSize(),
                tint = Color.Unspecified
            )
            Spacer(Modifier.height(10.dp))
            Text(
                text = "입맛을 알려주세요",
                fontFamily = mainFont,
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp,
                color = Black2Color
            )
            Column(
                modifier = Modifier
                    .padding(horizontal = 14.dp)
                    .fillMaxWidth()
            ) {
                Spacer(Modifier.height(36.dp))
                UserFavorRadioGroup(onFavorSelected)
                Text(
                    text = "좋아하면 선택해주세요",
                    fontFamily = mainFont,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.sp,
                    color = Color.Black
                )
                Spacer(Modifier.height(16.dp))
                UserFavorButtonGroup(
                    onCucumberClicked = onCucumberClicked,
                    onCorianderClicked = onCorianderClicked,
                    onMintClicked = onMintClicked,
                    onEggplantClicked = onEggplantClicked
                )
            }
            Spacer(Modifier.height(30.dp))
            Spacer(Modifier.weight(1f))
            MainButton(
                text = "완료하기",
                onClick = onCompleteButtonClicked
            )
            Spacer(Modifier.height(buttonBottomValue.dp))
        }
    }
}


@Composable
fun UserFavorRadioGroup(
    onFavorSelected: (Int, Int) -> Unit = { _: Int, _: Int -> }
) {
    val favorSpacerValue = 30
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        YOGORadioGroup(titleText = "매운맛", 0, onFavorSelected)
        Spacer(Modifier.height(favorSpacerValue.dp))
        YOGORadioGroup(titleText = "단맛", 1, onFavorSelected)
        Spacer(Modifier.height(favorSpacerValue.dp))
        YOGORadioGroup(titleText = "짠맛", 2, onFavorSelected)
        Spacer(Modifier.height(favorSpacerValue.dp))
        YOGORadioGroup(titleText = "신맛", 3, onFavorSelected)
        Spacer(Modifier.height(favorSpacerValue.dp))
        YOGORadioGroup(titleText = "쓴맛", 4, onFavorSelected)
        Spacer(Modifier.height(favorSpacerValue.dp))
    }
}

@Composable
fun YOGORadioGroup(
    titleText: String,
    index: Int,
    onFavorSelected: (Int, Int) -> Unit = { _: Int, _: Int -> }
) {
    var now: Int = 0
    if (com.yogo.wifood.WifoodApp.pref.getString("Initial_Flag", "0") == "0") {
        now = 2
    } else {
        when (index) {
            0 -> {
                now = (MainData.user.taste?.spicy ?: 3) - 1
            }
            1 -> {
                now = (MainData.user.taste?.sweet ?: 3) - 1
            }
            2 -> {
                now = (MainData.user.taste?.salty ?: 3) - 1
            }
            3 -> {
                now = (MainData.user.taste?.sour ?: 3) - 1
            }
            4 -> {
                now = (MainData.user.taste?.acidity ?: 3) - 1
            }
        }
    }
    val selected = remember {
        mutableStateListOf<Int>(
            if (now == 0) 1 else 0,
            if (now == 1) 1 else 0,
            if (now == 2) 1 else 0,
            if (now == 3) 1 else 0,
            if (now == 4) 1 else 0
        )
    }
    //semibold 13
    Column() {
        Text(
            text = titleText,
            fontFamily = mainFont,
            fontWeight = FontWeight.SemiBold,
            fontSize = 15.sp,
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
                YOGORadioButton(selected, 0, index, onFavorSelected = onFavorSelected)
                YOGORadioButton(selected, 1, index, onFavorSelected = onFavorSelected)
                YOGORadioButton(selected, 2, index, onFavorSelected = onFavorSelected)
                YOGORadioButton(selected, 3, index, onFavorSelected = onFavorSelected)
                YOGORadioButton(selected, 4, index, onFavorSelected = onFavorSelected)
            }
        }
        Spacer(Modifier.height(7.dp))
        Row(
            //verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "싫음",
                fontWeight = FontWeight.Normal,
                fontFamily = mainFont,
                fontSize = 12.sp,
                color = Gray01Color
            )
            Text(
                text = "조금 싫음",
                fontWeight = FontWeight.Normal,
                fontFamily = mainFont,
                fontSize = 12.sp,
                color = Gray01Color
            )
            Text(
                text = "보통",
                fontWeight = FontWeight.Normal,
                fontFamily = mainFont,
                fontSize = 12.sp,
                color = Gray01Color
            )
            Text(
                text = "조금 좋음",
                fontWeight = FontWeight.Normal,
                fontFamily = mainFont,
                fontSize = 12.sp,
                color = Gray01Color
            )
            Text(
                text = "좋음",
                fontWeight = FontWeight.Normal,
                fontFamily = mainFont,
                fontSize = 12.sp,
                color = Gray01Color
            )
        }

    }
}


@Composable
fun YOGORadioButton(
    selected: MutableList<Int>,
    selectedValue: Int = 0,
    index: Int = 0,
    onFavorSelected: (Int, Int) -> Unit = { _: Int, _: Int -> }
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }
    IconButton(
        onClick = {},
        modifier = Modifier
            .size(24.dp)
    ) {
        Icon(
            ImageVector.vectorResource(id = if (selected[selectedValue] == 1) R.drawable.ic_selected_radiobutton else R.drawable.ic_unselected_radiobutton),
            contentDescription = "",
            modifier = Modifier
                .wrapContentSize()
                .clickable(
                    indication = null,
                    interactionSource = interactionSource
                ) {
                    for (i: Int in 0..4) {
                        if (i == selectedValue) {
                            onFavorSelected(selectedValue, index)
                            selected[i] = 1
                            continue
                        }
                        selected[i] = 0
                    }
                },
            tint = Color.Unspecified
        )
    }
}


@Composable
fun UserFavorButtonGroup(
    onCucumberClicked: (Boolean) -> Unit = {},
    onCorianderClicked: (Boolean) -> Unit = {},
    onMintClicked: (Boolean) -> Unit = {},
    onEggplantClicked: (Boolean) -> Unit = {},
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    )
    {
        var cucumberClicked = remember { mutableStateOf(false) }
        var corianderClicked = remember { mutableStateOf(false) }
        var mintChokoClicked = remember { mutableStateOf(false) }
        var eggplantClicked = remember { mutableStateOf(false) }
        MainData.user.taste?.let {
            cucumberClicked = remember { mutableStateOf(MainData.user.taste!!.cucumber) }
            corianderClicked = remember { mutableStateOf(MainData.user.taste!!.coriander) }
            mintChokoClicked = remember { mutableStateOf(MainData.user.taste!!.mintChoco) }
            eggplantClicked = remember { mutableStateOf(MainData.user.taste!!.eggplant) }
        }
        SingleIconWithText(
            text = "오이",
            UnClickedSourceId = R.drawable.ic_favor_cucumber,
            ClickedSourceId = R.drawable.ic_favor_cucumber_clicked,
            isClicked = cucumberClicked,
            onClick = onCucumberClicked
        )
        SingleIconWithText(
            text = "고수",
            UnClickedSourceId = R.drawable.ic_favor_coriander,
            ClickedSourceId = R.drawable.ic_favor_coriander_clicked,
            isClicked = corianderClicked,
            onClick = onCorianderClicked
        )
        SingleIconWithText(
            text = "민트초코",
            UnClickedSourceId = R.drawable.ic_favor_mint,
            ClickedSourceId = R.drawable.ic_favor_mint_clicked,
            isClicked = mintChokoClicked,
            onClick = onMintClicked
        )
        SingleIconWithText(
            text = "가지",
            UnClickedSourceId = R.drawable.ic_favor_eggplant,
            ClickedSourceId = R.drawable.ic_favor_eggplant_clicked,
            isClicked = eggplantClicked,
            onClick = onEggplantClicked
        )
    }
}