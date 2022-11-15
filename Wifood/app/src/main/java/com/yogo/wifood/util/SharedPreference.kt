package com.yogo.wifood.util

import android.content.Context
import android.content.SharedPreferences

class SharedPreference(context: Context) {
    private val pref: SharedPreferences =
        context.getSharedPreferences("pref", Context.MODE_PRIVATE)

    fun getString(key: String, default: String) = pref.getString(key, default).toString()

    fun setString(key: String, value: String) {
        pref.edit().putString(key, value).apply()
    }

    fun getInt(key: String, default: Int) = pref.getInt(key, default).toInt()

    fun setInt(key: String, value: Int) {
        pref.edit().putInt(key, value).apply()
    }
}