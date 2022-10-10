package com.example.wifood

import android.app.Application
import com.example.wifood.util.TimberLogPrefix
import com.example.wifood.util.SharedPreference
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class WifoodApp : Application() {
    companion object {
        lateinit var pref: SharedPreference
    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(TimberLogPrefix())
        pref = SharedPreference(applicationContext)
    }
}