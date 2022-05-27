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
import com.example.wifood.presentation.util.Route
import com.example.wifood.presentation.view.map.MapViewModel
import com.example.wifood.view.ui.theme.Main

@Composable
fun MapTopAppBar(
    navController: NavController,
    viewModel: MapViewModel = hiltViewModel()
) {
    TopAppBar(
        title = {
            TextField(
                value = viewModel.input.value,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = viewModel::inputChanged,
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
                IconButton(onClick = { viewModel.showSnackBar(viewModel.input.value + "fun(검색)") }) {
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