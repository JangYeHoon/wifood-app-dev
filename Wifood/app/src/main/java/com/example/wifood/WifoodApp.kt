package com.example.wifood

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class WifoodApp : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}