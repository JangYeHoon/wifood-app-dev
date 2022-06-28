package com.example.wifood.presentation.view.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.wifood.WifoodApp
import com.example.wifood.presentation.util.Route
import com.example.wifood.presentation.view.main.MainViewModel
import com.example.wifood.view.ui.theme.Main
import com.google.android.gms.location.FusedLocationProviderClient

@Composable
fun MapTopAppBar(
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel()
) {
    TopAppBar(
        title = {
            TextField(
                /*
                For sharedPreference test
                */
                value = WifoodApp.pref.getString("user_id", "No user data"),
                onValueChange = {
                    navController.navigate(Route.Search.route)
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
        },
        modifier = Modifier.height(48.dp),
        navigationIcon = {
            IconButton(
                onClick = {
                    navController.navigate(Route.Search.route)
                }
            ) {
                Icon(
                    Icons.Filled.LocationOn,
                    contentDescription = "Pin",
                    tint = Main,
                    modifier = Modifier
                        .padding(8.dp)
                        .size(30.dp)
                )
            }
        },
        backgroundColor = Color.White,
        actions = {
            Row(modifier = Modifier.width(50.dp)) {
                IconButton(onClick = { navController.navigate(Route.Search.route) }) {
                    Icon(
                        Icons.Filled.Search,
                        contentDescription = "Search",
                        modifier = Modifier
                            .padding(8.dp)
                            .size(30.dp)
                    )
                }
            }
        }
    )
}