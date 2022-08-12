package com.example.wifood.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.annotation.ExperimentalCoilApi
import com.example.wifood.domain.model.Group
import com.example.wifood.domain.model.Place
import com.example.wifood.presentation.util.Route
import com.example.wifood.presentation.util.createParcelableNavType
import com.example.wifood.presentation.view.*
import com.example.wifood.presentation.view.login.FindPwdView
import com.example.wifood.presentation.view.login.LoginView
import com.example.wifood.presentation.view.login.MobileAuthenticationView
import com.example.wifood.presentation.view.login.join.JoininView
import com.example.wifood.presentation.view.main.MainView
import com.example.wifood.presentation.view.map.MapView
import com.example.wifood.presentation.view.mypage.AppInfoView
import com.example.wifood.presentation.view.placeList.PlaceInfoView
import com.example.wifood.presentation.view.placeList.PlaceInfoWriteView
import com.example.wifood.presentation.view.placeList.group.GroupDescInputView
import com.example.wifood.presentation.view.placeList.group.GroupEditView
import com.example.wifood.presentation.view.placeList.group.GroupNameInputView
import com.example.wifood.presentation.view.placeList.search.SearchPlaceComposeView
import com.example.wifood.presentation.view.splash.*
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalPagerApi
@ExperimentalCoilApi
@ExperimentalSerializationApi
@ExperimentalPermissionsApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Route.Splash.route
    ) {
        composable(Route.Main.route) {
            MainView(navController)
        }
        composable(Route.Login.route) {
            LoginView(navController)
        }
        composable(Route.Map.route) {
            MapView(navController)
        }
        composable(Route.MyPage.route) {
            MyPageComposeView(navController)
        }
        composable(
            route = "${Route.PlaceInfo.route}/{place}/{group}",
            arguments = listOf(
                navArgument("place") { type = createParcelableNavType<Place>() },
                navArgument("group") { type = createParcelableNavType<Group>() },
            )
        ) {
            PlaceInfoView(navController)
        }
        composable(Route.Splash.route) {
            SplashView(navController = (navController))
        }
        composable(Route.Onboarding.route) {
            OnboardingView(navController = (navController))
        }
        composable(Route.MobileAuthentication.route) {
            MobileAuthenticationView(navController = (navController))
        }
        composable(
            route = "${Route.Joinin.route}?email={email}&gender={gender}&phone={phone}&nickname={nickname}&birthday={birthday}",
            arguments = listOf(
                navArgument("email") {
                    nullable = true
                    defaultValue = null
                    type = NavType.StringType
                },
                navArgument("gender") {
                    nullable = true
                    defaultValue = null
                    type = NavType.StringType
                },
                navArgument("phone") {
                    nullable = true
                    defaultValue = null
                    type = NavType.StringType
                },
                navArgument("nickname") {
                    nullable = true
                    defaultValue = null
                    type = NavType.StringType
                },
                navArgument("birthday") {
                    nullable = true
                    defaultValue = null
                    type = NavType.StringType
                }
            )
        ) {
            JoininView(navController, it)
        }
        composable(
            route = "${Route.EditPlace.route}/{place}",
            arguments = listOf(navArgument("place") {
                type = createParcelableNavType<Place>()
            })
        ) {
            PlaceInfoWriteView(navController)
        }
        composable(Route.Search.route) {
            SearchPlaceComposeView(navController)
        }
        composable(Route.EditProfile.route) {
            EditProfileComposeView(navController)
        }
        composable(
            route = "${Route.GroupNameInput.route}/{group}",
            arguments = listOf(navArgument("group") { type = createParcelableNavType<Group>() })
        ) {
            GroupNameInputView(navController)
        }
        composable(
            route = "${Route.GroupDescInput.route}/{group}",
            arguments = listOf(navArgument("group") { type = createParcelableNavType<Group>() })
        ) {
            GroupDescInputView(navController)
        }
        composable(
            route = "${Route.GroupEdit.route}/{group}",
            arguments = listOf(navArgument("group") { type = createParcelableNavType<Group>() })
        ) {
            GroupEditView(navController)
        }
        composable(Route.FindPwd.route) {
            FindPwdView(navController)
        }
        composable(Route.EditMyInfo.route) {
            EditMyInfoComposeView(navController)
        }
        composable(Route.AppInfo.route) {
            AppInfoView(navController)
        }
    }
}