package com.example.wifood.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.wifood.domain.model.Group
import com.example.wifood.presentation.util.Route
import com.example.wifood.presentation.util.createParcelableNavType
import com.example.wifood.presentation.view.*
import com.example.wifood.presentation.view.login.LoginView
import com.example.wifood.presentation.view.login.MobileAuthenticationView
import com.example.wifood.presentation.view.login.join.JoininView
import com.example.wifood.presentation.view.main.MainView
import com.example.wifood.presentation.view.map.MapView
import com.example.wifood.presentation.view.placeList.PlaceInfoComposeView
import com.example.wifood.presentation.view.placeList.group.GroupAddView
import com.example.wifood.presentation.view.start.SplashView
import com.example.wifood.presentation.view.start.WorkThroughView1
import com.example.wifood.presentation.view.start.WorkThroughView2
import com.example.wifood.presentation.view.start.WorkThroughView3
import com.google.accompanist.permissions.ExperimentalPermissionsApi

@ExperimentalPermissionsApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Route.Login.route
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
        composable(Route.PlaceInfo.route) {
            PlaceInfoComposeView()
        }
        composable(Route.Splash.route) {
            SplashView(navController = (navController))
        }
        composable(Route.WorkThrough1.route) {
            WorkThroughView1(navController = (navController))
        }
        composable(Route.WorkThrough1.route) {
            WorkThroughView2(navController = (navController))
        }
        composable(Route.WorkThrough1.route) {
            WorkThroughView3(navController = (navController))
        }
        composable(Route.MobileAuthentication.route) {
            MobileAuthenticationView(navController = (navController))
        }
        composable(Route.Joinin.route) {
            JoininView(navController)
        }
        composable(Route.EditPlace.route) {
            EditPlaceComposeView(navController)
        }
        composable(Route.Search.route) {
            SearchPlaceComposeView(navController)
        }
        composable(Route.EditProfile.route) {
            EditProfileComposeView(navController)
        }
        composable(
            route = "${Route.GroupAdd.route}/{group}",
            arguments = listOf(navArgument("group") { type = createParcelableNavType<Group>() })
        ) {
            GroupAddView(navController)
        }
    }
}