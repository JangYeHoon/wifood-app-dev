package com.example.wifood.presentation.view.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.wifood.R
import com.example.wifood.presentation.util.Route
import com.example.wifood.presentation.view.component.MainButton
import com.example.wifood.presentation.view.component.ProgressIndicator
import com.example.wifood.presentation.view.component.YOGOBasicText
import com.example.wifood.presentation.view.login.component.TitleText
import com.example.wifood.presentation.view.login.contents.GetUserAddressContent
import com.example.wifood.presentation.view.login.util.SignUpData
import com.example.wifood.ui.theme.mainFont
import com.example.wifood.util.composableActivityViewModel
import com.example.wifood.view.ui.theme.*

@Composable
fun GetUserAddressView(
    navController: NavController,
    viewModel: SignUpViewModel = composableActivityViewModel()
) {
    GetUserAddressContent(
        addressText = SignUpData.address,
        onAddressClicked = {
            navController.navigate("${Route.FindLocation.route}/signup")
        },
        onAddressValueChanged = {
            viewModel.onEvent(SignUpEvent.AddressChanged(it))
        },
        onButtonClicked = {
            navController.navigate(Route.SignUp4.route)
        }
    )
}