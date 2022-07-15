package com.example.wifood

import android.app.Application
import android.util.Log
import com.example.wifood.di.TimberLogPrefix
import com.example.wifood.util.Constants
import com.example.wifood.util.SharedPreference
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class WifoodApp : Application() {
    companion object {
        lateinit var pref: SharedPreference
    }

    override fun onCreate() {
        pref = SharedPreference(applicationContext)
        super.onCreate()
        KakaoSdk.init(this, Constants.KAKAO_KEY)
        Timber.plant(TimberLogPrefix())
    }
}