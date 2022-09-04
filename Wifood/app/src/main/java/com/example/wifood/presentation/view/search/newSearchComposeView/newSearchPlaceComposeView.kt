package com.example.wifood.presentation.view.search.newSearchComposeView

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.wifood.R
import com.example.wifood.presentation.view.component.MainButton
import com.example.wifood.presentation.view.component.YOGOLargeText
import com.example.wifood.presentation.view.login.new_compose_views.CustomTextField
import com.example.wifood.presentation.view.login.new_compose_views.SearchPlaceInfoCard
import com.example.wifood.view.ui.theme.buttonBottomValue
import com.example.wifood.view.ui.theme.sidePaddingValue

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NewSearchPlaceComposeView(

) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            CustomTextField(
                address = "강남역",
                onValueChanged = {},
                onDeleteClicked = {},
                onSearchClicked = {},
                onBackClicked = {},
                placeholder = "맛집, 주소 검색"
            )
            Spacer(Modifier.height(12.dp))
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = sidePaddingValue.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    SearchPlaceInfoCard(
                        address = "강남역 양재역[2호선]",
                        name = "서울 강남구 역삼동 858",
                        search = "강남역",
                        onClick = {}
                    )
                }
            }
        }
    }

}

@Composable
fun SearchPlaceEmptyView(

) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){

    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = sidePaddingValue.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.weight(1f))
        Icon(
            ImageVector.vectorResource(R.drawable.ic_search_place_empty),
            contentDescription = "searched place empty image",
            modifier = Modifier
                .wrapContentSize(),
            tint = Color.Unspecified
        )
        Spacer(Modifier.height(39.dp))
        YOGOLargeText(
            text = "등록이 안된 식당입니다.\n직접 입력해주세요!"
        )
        Spacer(Modifier.height(24.dp))
        Spacer(Modifier.weight(1f))
        MainButton(
            text = "직접 입력하기",
            onClick = { /*TODO*/ }
        )
        Spacer(Modifier.height(buttonBottomValue.dp))
    }
}