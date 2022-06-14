package com.example.wifood

import android.app.Application
import com.example.wifood.di.TimberLogPrefix
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class WifoodApp : Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(TimberLogPrefix())
    }
}