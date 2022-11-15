package com.yogo.wifood.data.remote.dto

import com.google.gson.annotations.SerializedName

data class Response(
    @SerializedName("message") val message: String
)
