package com.example.wifood.presentation.view.placeList.component

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.wifood.presentation.view.component.BottomSheetListItem
import com.example.wifood.presentation.view.component.DialogCenterDivider
import com.example.wifood.presentation.view.component.MainButton
import com.example.wifood.presentation.view.component.YOGOLargeText
import com.example.wifood.presentation.view.placeList.newPlaceListComposeView.SelectGroupView
import com.example.wifood.presentation.view.placeList.placeinfowrite.PlaceInfoWriteFormEvent
import com.example.wifood.presentation.view.placeList.placeinfowrite.PlaceInfoWriteViewModel
import com.example.wifood.ui.theme.mainFont
import com.example.wifood.view.ui.theme.EnableColor
import com.example.wifood.view.ui.theme.MainColor
import com.example.wifood.view.ui.theme.buttonBottomValue
import com.example.wifood.view.ui.theme.sidePaddingValue
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun PlaceWriteGroupsBottomSheetContent(
    viewModel: PlaceInfoWriteViewModel = hiltViewModel()
) {
    val formState = viewModel.formState
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var selectedGroup by remember { mutableStateOf("") }
    val selectGroupScrollState = rememberScrollState()
    val interactionSource = remember {
        MutableInteractionSource()
    }
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
        //SelectGroupView()
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp)
                .verticalScroll(selectGroupScrollState)
        ){
            for(group in formState.groups){
                Text(
                    text = group.name,
                    fontFamily = mainFont,
                    fontWeight = if (selectedGroup != group.name) FontWeight.Normal else FontWeight.Bold,
                    fontSize = 21.sp,
                    color = if (selectedGroup != group.name) EnableColor else MainColor,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .clickable(
                            indication = null,
                            interactionSource = interactionSource
                        ){
                            selectedGroup = group.name
                            scope.launch {
                                viewModel.onEvent(PlaceInfoWriteFormEvent.GroupSelected(group))
                            }
                        }
                )
                Spacer(Modifier.height((11.5).dp))
            }
        }
        Spacer(Modifier.height(42.dp))
        MainButton(
            text = "그룹 선택하기",
            onClick = {

            }
        )
        Spacer(Modifier.height(buttonBottomValue.dp))
    }
}