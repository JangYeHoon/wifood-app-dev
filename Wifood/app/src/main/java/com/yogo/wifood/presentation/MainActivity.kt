package com.yogo.wifood.presentation

import android.Manifest
import android.content.pm.PackageManager
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
import androidx.core.content.ContextCompat
import coil.annotation.ExperimentalCoilApi
import com.yogo.wifood.BuildConfig
import com.yogo.wifood.WifoodApp
import com.yogo.wifood.presentation.view.main.util.MainData
import com.yogo.wifood.presentation.view.map.util.DefaultLocationClient
import com.yogo.wifood.view.ui.theme.WifoodTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.serialization.ExperimentalSerializationApi
import timber.log.Timber

@ExperimentalPagerApi
@ExperimentalPermissionsApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@ExperimentalCoilApi
@ExperimentalSerializationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var locationClient: DefaultLocationClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val apikey = BuildConfig.TMAP_KEY

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            locationClient = DefaultLocationClient(
                this,
                LocationServices.getFusedLocationProviderClient(this)
            )
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                ),
                0
            )
        }

        locationClient
            .getLocationUpdates(3000L)
            .catch { e -> Timber.e(e.printStackTrace().toString()) }
            .onEach { location ->
                MainData.location = location
            }
            .launchIn(com.yogo.wifood.WifoodApp.serviceScope)

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
        com.yogo.wifood.WifoodApp.serviceScope.cancel()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 1) {
            locationClient = DefaultLocationClient(
                this,
                LocationServices.getFusedLocationProviderClient(this)
            )
        }
    }
}