package com.example.wifood.presentation.view.login

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.wifood.R
import com.example.wifood.presentation.util.Route
import com.example.wifood.presentation.view.component.MainButton
import com.example.wifood.presentation.view.component.ProgressIndicator
import com.example.wifood.presentation.view.login.component.Picker
import com.example.wifood.presentation.view.login.component.TitleText
import com.example.wifood.presentation.view.login.util.SignUpData
import com.example.wifood.presentation.view.login.util.phoneFilter
import com.example.wifood.ui.theme.mainFont
import com.example.wifood.util.composableActivityViewModel
import com.example.wifood.view.ui.theme.Black2Color
import com.example.wifood.view.ui.theme.Gray03Color
import com.example.wifood.view.ui.theme.sidePaddingValue

@Composable
fun SignUpView4(
    navController: NavController,
    viewModel: SignUpViewModel = composableActivityViewModel()
) {
    val state = viewModel.state.value

    val year = remember { mutableStateOf(1995) }
    val month = remember { mutableStateOf(1) }
    val day = remember { mutableStateOf(10) }
    val scrollState = rememberScrollState()
    val interactionSource = remember { MutableInteractionSource() }

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
            Spacer(Modifier.height(10.dp))
            Text(
                text = "생일을 알려주세요",
                fontFamily = mainFont,
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp,
                color = Black2Color
            )
            Spacer(Modifier.height(12.dp))
            Text(
                text = "연령에 맞는 맛집을 추천할 수 있도록\n정확한 생년월일을 알려주세요",
                fontFamily = mainFont,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                color = Gray03Color
            )
            Spacer(Modifier.height(57.dp))
            BasicTextField(
                value = state.birthday,
                onValueChange = {
                    viewModel.onEvent(SignUpEvent.CertChanged(it))
                }
            )
            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .fillMaxWidth()
                    .background(
                        color = Color(0x4DD9D9D9),
                        shape = RoundedCornerShape(8.dp)
                    )
            ){
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Picker(
                        state = year,
                        range = 1950..2022,
                        additional = "년"
                    )
                    Picker(
                        state = month,
                        range = 1..12,
                        additional = "월"
                    )
                    Picker(
                        state = day,
                        range = 1..31,
                        additional = "일"
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            MainButton(
                text = "다음",
                onClick = {
                    SignUpData.birthday = buildString {
                        append(year.value)
                        append(month.value)
                        append(day.value)
                    }
                    navController.navigate(Route.SignUp5.route)
                }
            )
        }
    }
}
