 package com.yogo.wifood.presentation.view.login.join

import android.annotation.SuppressLint
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.yogo.wifood.WifoodApp
import com.yogo.wifood.presentation.util.Route
import com.yogo.wifood.presentation.view.login.SignUpEvent
import com.yogo.wifood.presentation.view.login.SignUpViewModel
import com.yogo.wifood.presentation.view.login.contents.GetUserFavorContent
import com.yogo.wifood.presentation.view.login.util.SignUpData
import com.yogo.wifood.presentation.view.login.util.ValidationEvent
import com.yogo.wifood.presentation.view.mypage.MyPageEvent
import com.yogo.wifood.presentation.view.mypage.MyPageViewModel
import com.yogo.wifood.util.composableActivityViewModel

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
                    com.yogo.wifood.WifoodApp.pref.setString("user_id", SignUpData.phoneNumber)
                    com.yogo.wifood.WifoodApp.pref.setString("Initial_Flag", "1")
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
        onFavorSelected = { select, index ->
            viewModel.onEvent(
                SignUpEvent.FavorSelected(
                    select + 1,
                    index
                )
            )
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