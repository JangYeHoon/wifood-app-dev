package com.example.wifood.presentation.view.search

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.wifood.R
import com.example.wifood.data.remote.dto.PlaceDto
import com.example.wifood.presentation.util.Route
import com.example.wifood.presentation.view.component.MainButton
import com.example.wifood.presentation.view.component.YOGOLargeText
import com.example.wifood.ui.theme.fontTmoney
import com.example.wifood.view.ui.theme.Gray01Color
import com.example.wifood.view.ui.theme.Gray03Color
import com.example.wifood.view.ui.theme.buttonBottomValue
import com.example.wifood.view.ui.theme.sidePaddingValue
import com.google.gson.Gson
import kotlinx.coroutines.delay

@Composable
fun AddNewPlaceCompleteView(
    navController: NavController,
    viewModel: SearchPlaceViewModel = hiltViewModel()
) {
    LaunchedEffect(true) {
        delay(1000L)
        val placeJson = Uri.encode(Gson().toJson(viewModel.formState.place))
        navController.navigate("${Route.PlaceInputNameAndVisited.route}/${placeJson}")
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                ImageVector.vectorResource(id = R.drawable.ic_complete_icon),
                contentDescription = "left button of top app bar",
                modifier = Modifier.wrapContentSize(),
                tint = Color.Unspecified
            )
            Spacer(Modifier.height(39.dp))
            YOGOLargeText(
                text = "맛집 등록이\n완료되었습니다.",
                color = Gray01Color,
                textAlign = TextAlign.Center
            )
        }
    }
}