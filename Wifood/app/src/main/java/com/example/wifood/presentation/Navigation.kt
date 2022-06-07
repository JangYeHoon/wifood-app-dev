package com.example.wifood.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.wifood.presentation.util.Route
import com.example.wifood.presentation.view.*
import com.example.wifood.presentation.view.login.LoginView
import com.example.wifood.presentation.view.login.join.JoininView
import com.example.wifood.presentation.view.main.MainView
import com.example.wifood.presentation.view.map.MapView
import com.example.wifood.presentation.view.placeList.PlaceInfoComposeView
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
    }
}