package com.example.wifood

import android.app.Application
import com.example.wifood.util.TimberLogPrefix
import com.example.wifood.util.SharedPreference
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class WifoodApp : Application() {
    companion object {
        lateinit var pref: SharedPreference
    }

    override fun onCreate() {
        super.onCreate()
        // Kakao 로그인(API) 사용을 위한 KakaoSdk 초기화
        KakaoSdk.init(this, getString(R.string.kakao_native_app_key))
        Timber.plant(TimberLogPrefix())
        pref = SharedPreference(applicationContext)
    }
}