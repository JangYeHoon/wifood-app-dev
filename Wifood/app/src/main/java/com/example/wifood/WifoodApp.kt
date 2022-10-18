package com.example.wifood

import android.app.Application
import com.example.wifood.presentation.view.main.MainEvent
import com.example.wifood.presentation.view.main.util.MainData
import com.example.wifood.presentation.view.map.util.DefaultLocationClient
import com.example.wifood.util.TimberLogPrefix
import com.example.wifood.util.SharedPreference
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

@HiltAndroidApp
class WifoodApp : Application() {
    companion object {
        lateinit var pref: SharedPreference
        val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(TimberLogPrefix())
        pref = SharedPreference(applicationContext)
    }
}