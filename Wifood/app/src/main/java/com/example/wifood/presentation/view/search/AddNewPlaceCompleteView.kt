package com.example.wifood.presentation.view.search

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.wifood.R
import com.example.wifood.data.remote.dto.PlaceDto
import com.example.wifood.presentation.util.Route
import com.example.wifood.presentation.view.component.MainButton
import com.example.wifood.ui.theme.fontTmoney
import com.example.wifood.view.ui.theme.Gray03Color
import com.example.wifood.view.ui.theme.buttonBottomValue
import com.example.wifood.view.ui.theme.sidePaddingValue
import com.google.gson.Gson
import kotlinx.coroutines.delay

@Composable
fun AddNewPlaceCompleteView(
    navController: NavController
) {
    LaunchedEffect(true) {
        delay(1000L)
        val placeJson = Uri.encode(Gson().toJson(PlaceDto().toPlace()))
        navController.navigate("${Route.PlaceInputNameAndVisited.route}/${placeJson}")
    }

    Scaffold(
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = sidePaddingValue.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text= buildAnnotatedString {
                    append("맛집 등록이")
                    append("\n 완료되었습니다.")
                },
                fontFamily = fontTmoney,
                fontWeight = FontWeight.Normal,
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                color = Gray03Color
            )
            Spacer(Modifier.height(56.dp))
            Image(
                painter = painterResource(R.drawable.ic_joinin_complete_cat),
                contentDescription = "joinin complete cate image",
                modifier = Modifier
                    .width(253.dp)
                    .height(294.dp)
            )
            Spacer(Modifier.height(50.dp))
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            )
            MainButton(
                text = "확인",
                onClick = {}
            )
            Spacer(Modifier.height(buttonBottomValue.dp))
        }
    }
}