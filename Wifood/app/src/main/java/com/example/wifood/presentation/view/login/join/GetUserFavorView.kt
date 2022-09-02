package com.example.wifood.presentation.view.login.join

import android.annotation.SuppressLint
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.wifood.R
import com.example.wifood.presentation.util.Route
import com.example.wifood.presentation.view.component.MainButton
import com.example.wifood.presentation.view.component.ProgressIndicator
import com.example.wifood.presentation.view.groupComponet.SingleIconWithText
import com.example.wifood.presentation.view.login.SignUpEvent
import com.example.wifood.presentation.view.login.SignUpViewModel
import com.example.wifood.presentation.view.login.util.ValidationEvent
import com.example.wifood.ui.theme.mainFont
import com.example.wifood.util.composableActivityViewModel
import com.example.wifood.view.ui.theme.Black2Color
import com.example.wifood.view.ui.theme.Gray01Color
import com.example.wifood.view.ui.theme.buttonBottomValue
import com.example.wifood.view.ui.theme.sidePaddingValue

//@ExperimentalComposeUiApi
//@Composable
//fun GetUserFavorView(
//    showDialog:MutableState<Boolean>
//) {
//    Dialog(
//        onDismissRequest = {
//            showDialog.value = false
//        },
//        properties = DialogProperties(
//            usePlatformDefaultWidth = false
//        ),
//    ) {
//        Surface(
//            modifier = Modifier
//                .fillMaxWidth()
//                .wrapContentHeight()
//                .padding(top = 85.dp),
//            shape = RoundedCornerShape(
//                topStart = 30.dp,
//                topEnd = 30.dp
//            ),
//            color = Color(0xFFFFFFFF)
//        ) {
//            GetUserFavorContent(showDialog)
//        }
//    }
//}

@SuppressLint("MutableCollectionMutableState")
@Composable
fun GetUserFavorContent(
    navController: NavController,
    viewModel: SignUpViewModel = composableActivityViewModel()
) {
    val scrollState = rememberScrollState()
    val state = viewModel.state.value

    LaunchedEffect(true) {
        viewModel.validationEvents.collect { event ->
            when (event) {
                is ValidationEvent.Success -> {
                    navController.navigate(Route.Complete.route)
                }
                is ValidationEvent.Error -> {
                    /*

                     */
                }
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (state.isLoading) {
            ProgressIndicator()
        }

        Column(
            modifier = Modifier
                .padding(horizontal = sidePaddingValue.dp)
                .verticalScroll(scrollState)
        ) {
            Spacer(Modifier.weight(1f))
            Spacer(Modifier.height(30.dp))
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
                UserFavorRadioGroup(viewModel)
                Text(
                    text = "좋아하면 선택해주세요",
                    fontFamily = mainFont,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.sp,
                    color = Color.Black
                )
                Spacer(Modifier.height(16.dp))
                UserFavorButtonGroup()
            }
            Spacer(Modifier.height(30.dp))
            Spacer(Modifier.weight(1f))
            MainButton(
                text = "완료하기",
                onClick = {
                    viewModel.onEvent(SignUpEvent.TasteCreated)
                }
            )
            Spacer(Modifier.height(buttonBottomValue.dp))
        }
    }
}

@Composable
fun UserFavorRadioGroup(
    viewModel: SignUpViewModel
) {
    val favorSpacerValue = 30
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        YOGORadioGroup(titleText = "매운맛", viewModel)
        Spacer(Modifier.height(favorSpacerValue.dp))
        YOGORadioGroup(titleText = "단맛", viewModel)
        Spacer(Modifier.height(favorSpacerValue.dp))
        YOGORadioGroup(titleText = "짠맛", viewModel)
        Spacer(Modifier.height(favorSpacerValue.dp))
        YOGORadioGroup(titleText = "신맛", viewModel)
        Spacer(Modifier.height(favorSpacerValue.dp))
        YOGORadioGroup(titleText = "쓴맛", viewModel)
        Spacer(Modifier.height(favorSpacerValue.dp))
    }
}

@Composable
fun YOGORadioGroup(
    titleText: String,
    viewModel: SignUpViewModel
) {
    val selected = remember { mutableStateListOf<Int>(0, 0, 1, 0, 0) }
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
                YOGORadioButton(selected, 0, 0, viewModel)
                YOGORadioButton(selected, 1, 1, viewModel)
                YOGORadioButton(selected, 2, 2, viewModel)
                YOGORadioButton(selected, 3, 3, viewModel)
                YOGORadioButton(selected, 4, 4, viewModel)
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
fun UserFavorButtonGroup(
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

        SingleIconWithText(
            text = "오이",
            UnClickedSourceId = R.drawable.ic_favor_cucumber,
            ClickedSourceId = R.drawable.ic_favor_cucumber_clicked,
            isClicked = cucumberClicked
        )
        SingleIconWithText(
            text = "고수",
            UnClickedSourceId = R.drawable.ic_favor_coriander,
            ClickedSourceId = R.drawable.ic_favor_coriander_clicked,
            isClicked = corianderClicked
        )
        SingleIconWithText(
            text = "민트초코",
            UnClickedSourceId = R.drawable.ic_favor_mint,
            ClickedSourceId = R.drawable.ic_favor_mint_clicked,
            isClicked = mintChokoClicked
        )
        SingleIconWithText(
            text = "가지",
            UnClickedSourceId = R.drawable.ic_favor_eggplant,
            ClickedSourceId = R.drawable.ic_favor_eggplant_clicked,
            isClicked = eggplantClicked
        )
    }
}

@Composable
fun YOGORadioButton(
    selected: MutableList<Int>,
    selectedValue: Int = 0,
    index: Int,
    viewModel: SignUpViewModel
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }
    IconButton(
        onClick = {
            /*for (i: Int in 0..4) {
                if (i == selectedValue) {
                    selectedArray[i] = 1
                    continue
                }
                selectedArray[i] = 0
            }*/
        },
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
                            viewModel.onEvent(SignUpEvent.FavorSelected(i + 1, index))
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