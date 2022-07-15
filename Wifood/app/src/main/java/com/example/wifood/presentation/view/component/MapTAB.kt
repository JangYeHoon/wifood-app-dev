package com.example.wifood.presentation.view.component

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.wifood.WifoodApp
import com.example.wifood.presentation.util.Route
import com.example.wifood.presentation.view.main.MainEvent
import com.example.wifood.presentation.view.main.MainViewModel
import com.example.wifood.ui.theme.mainFont
import com.example.wifood.view.ui.theme.Gray01Color
import com.example.wifood.view.ui.theme.Main
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.RectangularBounds
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import kotlinx.coroutines.launch

@Composable
fun MapTopAppBar(
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val googleSearchPlaceLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val searchResult = Autocomplete.getPlaceFromIntent(it.data)
                scope.launch {
                    viewModel.onEvent(MainEvent.SearchClicked(searchResult))
                }
            }
        }

    TopAppBar(
        title = {
            Text(
                text = viewModel.state.searchResultName,
                fontFamily = mainFont,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                color = Gray01Color
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
                IconButton(onClick = {
                    val bounds =
                        viewModel.state.currentLocation?.let {
                            RectangularBounds.newInstance(
                                LatLng(
                                    it.latitude,
                                    it.longitude
                                ),
                                LatLng(
                                    it.latitude,
                                    it.longitude
                                )
                            )
                        }

                    val googleSearchPlaceIntent =
                        Autocomplete.IntentBuilder(
                            AutocompleteActivityMode.OVERLAY,
                            viewModel.state.field
                        ).setCountry("KR").setLocationBias(bounds).build(context)

                    googleSearchPlaceLauncher.launch(googleSearchPlaceIntent)
                }) {
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