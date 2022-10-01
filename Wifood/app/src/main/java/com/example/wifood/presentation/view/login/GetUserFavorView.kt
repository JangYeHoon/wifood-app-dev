package com.example.wifood.presentation.view.login.join

import android.annotation.SuppressLint
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.example.wifood.WifoodApp
import com.example.wifood.presentation.util.Route
import com.example.wifood.presentation.view.login.SignUpEvent
import com.example.wifood.presentation.view.login.SignUpViewModel
import com.example.wifood.presentation.view.login.contents.GetUserFavorContent
import com.example.wifood.presentation.view.login.util.SignUpData
import com.example.wifood.presentation.view.login.util.ValidationEvent
import com.example.wifood.presentation.view.mypage.MyPageEvent
import com.example.wifood.presentation.view.mypage.MyPageViewModel
import com.example.wifood.util.composableActivityViewModel

@SuppressLint("MutableCollectionMutableState")
@Composable
fun GetUserFavorView(
    navController: NavController,
    viewModel: SignUpViewModel = composableActivityViewModel()
) {
    val scrollState = rememberScrollState()
    val state = viewModel.state.value

    LaunchedEffect(true) {
        viewModel.validationEvents.collect { event ->
            when (event) {
                is ValidationEvent.Success -> {
                    /*
                    must be fixed
                     */
                    WifoodApp.pref.setString("user_id", SignUpData.phoneNumber)
                    WifoodApp.pref.setString("Initial_Flag", "1")
                    navController.navigate(Route.Complete.route)
                }
                is ValidationEvent.Error -> {
                    /*

                     */
                }
            }
        }
    }

    GetUserFavorContent(
        isLoading = state.isLoading,
        onFavorSelected = { select,index ->
            if (WifoodApp.pref.getString("Initial_Flag", "0") == "0") {
                (viewModel as SignUpViewModel).onEvent(
                    SignUpEvent.FavorSelected(
                        select + 1,
                        index
                    )
                )
            } else {
                (viewModel as MyPageViewModel).onEvent(
                    MyPageEvent.FavorSelected(
                        select + 1,
                        index
                    )
                )
            }
        },
        onCucumberClicked = {
            SignUpData.cucumberClicked = it
        },
        onCorianderClicked = {
            SignUpData.corianderClicked = it
        },
        onMintClicked = {
            SignUpData.mintChokoClicked = it
        },
        onEggplantClicked = {
            SignUpData.eggplantClicked = it
        },
        onCompleteButtonClicked = {
            viewModel.onEvent(SignUpEvent.TasteCreated)
        }
    )
}