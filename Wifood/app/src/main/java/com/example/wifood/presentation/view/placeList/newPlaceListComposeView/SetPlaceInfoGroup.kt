package com.example.wifood.presentation.view.placeList.newPlaceListComposeView

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wifood.presentation.view.component.DialogCenterDivider
import com.example.wifood.presentation.view.component.MainButton
import com.example.wifood.presentation.view.login.component.Picker
import com.example.wifood.ui.theme.mainFont
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.recyclerview.widget.ItemTouchHelper.UP
import com.example.wifood.ui.theme.mainFont
import com.example.wifood.view.ui.theme.*
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.roundToInt

@Preview(showBackground = true)
@Composable
fun SetPlaceInfoGroup(

){
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .padding(horizontal = sidePaddingValue.dp)
    ) {
        Spacer(Modifier.height(8.dp))
        DialogCenterDivider(
            width = 54,
            thickness = 4
        )
        Spacer(Modifier.height(94.dp))
        YOGOLargeText(
            text = "맛집 그룹을\n선택해주세요."
        )
        Spacer(Modifier.height(42.dp))
        SelectGroupView()
        Spacer(Modifier.height(42.dp))
        MainButton(
            text = "그룹 선택하기",
            onClick = { /*TODO*/ }
        )
        Spacer(Modifier.height(buttonBottomValue.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun SelectGroupView(

){
    val interactionSource = remember {
        MutableInteractionSource()
    }
    val scrollState = rememberScrollState()
    val myGroupList = arrayOf<String>(
        "강남맛집",
        "핫플",
        "갬성카페",
        "가보고싶은 맛집",
        "회사 근처 점심",
        "단골 맛집",
        "와인바&칵테일바"
    )
    var selectedGroup by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp)
            .verticalScroll(scrollState)
    ){
        for(group in myGroupList){
            Text(
                text = group,
                fontFamily = mainFont,
                fontWeight = if (selectedGroup != group) FontWeight.Normal else FontWeight.Bold,
                fontSize = 21.sp,
                color = if (selectedGroup != group) EnableColor else MainColor,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .clickable(
                        indication = null,
                        interactionSource = interactionSource
                    ){
                        selectedGroup = group
                    }
            )
            Spacer(Modifier.height((11.5).dp))
        }
    }

}
