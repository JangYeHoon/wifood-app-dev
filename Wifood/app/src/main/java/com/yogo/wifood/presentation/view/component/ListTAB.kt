package com.yogo.wifood.presentation.view.component

import android.net.Uri
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.yogo.wifood.domain.model.Group
import com.yogo.wifood.presentation.util.Route
import com.google.gson.Gson

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
                val groupJson = Uri.encode(Gson().toJson(Group()))
                navController.navigate("${Route.GroupNameInput.route}/$groupJson")
            }) {
                Icon(Icons.Filled.Menu, "menu")
            }
        }
    )
}