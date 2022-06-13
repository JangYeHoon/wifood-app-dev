package com.example.wifood.presentation.view.component

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.wifood.presentation.util.Route

@Composable
fun ListTopAppBar(
    navController: NavController
) {
    TopAppBar(
        title = {
            Text(text = "맛집 리스트")
        },
        navigationIcon = {
            IconButton(onClick = {}) {
                Icon(Icons.Filled.ArrowBack, "backIcon")
            }
        },
        actions = {
            IconButton(onClick = {
                navController.navigate(Route.GroupAdd.route)
            }) {
                Icon(Icons.Filled.Menu, "menu")
            }
        }
    )
}