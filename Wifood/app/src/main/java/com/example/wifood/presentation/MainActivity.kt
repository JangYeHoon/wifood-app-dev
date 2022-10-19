package com.example.wifood.presentation

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import coil.annotation.ExperimentalCoilApi
import com.example.wifood.BuildConfig
import com.example.wifood.WifoodApp
import com.example.wifood.presentation.view.main.MainEvent
import com.example.wifood.presentation.view.main.util.MainData
import com.example.wifood.presentation.view.map.util.DefaultLocationClient
import com.example.wifood.view.ui.theme.WifoodTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalPagerApi
@ExperimentalPermissionsApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@ExperimentalCoilApi
@ExperimentalSerializationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val apikey = BuildConfig.TMAP_KEY
        val locationClient = DefaultLocationClient(
            this,
            LocationServices.getFusedLocationProviderClient(this)
        )

        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
            ),
            0
        )

        locationClient
            .getLocationUpdates(3000L)
            .catch { e -> e.printStackTrace() }
            .onEach { location ->
                MainData.location = location
            }
            .launchIn(WifoodApp.serviceScope)

        setContent {
            WifoodTheme {
                Surface(
                    color = MaterialTheme.colors.background,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Navigation()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        WifoodApp.serviceScope.cancel()
    }
}