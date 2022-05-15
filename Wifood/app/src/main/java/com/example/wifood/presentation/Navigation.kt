package com.example.wifood.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.wifood.presentation.util.Route
import com.example.wifood.presentation.view.LoginView
import com.example.wifood.presentation.view.MyPageComposeView
import com.example.wifood.presentation.view.PlaceListComposeView
import com.example.wifood.presentation.view.map.MapView
import com.example.wifood.presentation.view.placeList.PlaceInfoComposeView

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Route.Map.route
    ) {
        composable(Route.Login.route) {
            LoginView()
        }
        composable(Route.PlaceList.route) {
            PlaceListComposeView(navController)
        }
        composable(Route.Map.route) {
            MapView(navController)
        }
        composable(Route.MyPage.route) {
            MyPageComposeView()
        }
        composable(Route.PlaceInfo.route) {
            PlaceInfoComposeView()
        }
    }
}