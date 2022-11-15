package com.yogo.wifood.presentation.view.login.new_compose_views

import android.annotation.SuppressLint
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.yogo.wifood.presentation.view.login.SignUpEvent
import com.yogo.wifood.presentation.view.login.SignUpViewModel
import com.yogo.wifood.presentation.view.login.contents.SearchUserAddressContent
import com.yogo.wifood.presentation.view.mypage.MyPageEvent
import com.yogo.wifood.presentation.view.mypage.MyPageViewModel
import com.yogo.wifood.util.composableActivityViewModel

@OptIn(ExperimentalComposeUiApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SearchUserAddressView(
    navController: NavController,
    navBackStackEntry: NavBackStackEntry,
    viewModel: SignUpViewModel = composableActivityViewModel(),
    viewModel2: MyPageViewModel = hiltViewModel()
) {
    var view by remember {
        mutableStateOf("")
    }
    view =
        if (navBackStackEntry.arguments?.getString("viewModel")!! == "modify") "modify" else "signup"
    val state = viewModel.state.value
    val state2 = viewModel2.state.value
    val keyboardController = LocalSoftwareKeyboardController.current

    SearchUserAddressContent(
        addressText = if (view == "signup") state.address else state2.address,
        onAddressChanged = {
            if (view == "signup") viewModel.onEvent(SignUpEvent.AddressChanged(it)) else viewModel2.onEvent(
                MyPageEvent.AddressChanged(it)
            )
        },
        onBackButtonClicked = {
            navController.navigateUp()
        },
        onDeleteButtonClicked  = {
            if (view == "signup") viewModel.onEvent(SignUpEvent.AddressChanged("")) else viewModel2.onEvent(
                MyPageEvent.AddressChanged("")
            )
        },
        onSearchButtonClicked = {
            if (view == "signup") viewModel.onEvent(SignUpEvent.ButtonClicked) else viewModel2.onEvent(
                MyPageEvent.ButtonClicked
            )
        },
        keyboardActions = KeyboardActions(
            onSearch = {
                if (view == "signup") viewModel.onEvent(SignUpEvent.ButtonClicked) else viewModel2.onEvent(
                    MyPageEvent.ButtonClicked
                )
                keyboardController?.hide()
            }
        ),
        searchList = if (view == "signup") state.searchResults else state2.searchResults,
        onSearchedCardClicked = {
            if (view == "signup") {
                viewModel.onEvent(SignUpEvent.AddressClicked(it))
                navController.navigateUp()
            } else {
                viewModel2.onEvent(MyPageEvent.AddressClicked(it))
                navController.navigateUp()
            }
        }
    )
}