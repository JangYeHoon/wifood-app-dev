package com.yogo.wifood.data.remote.dto

import com.google.gson.annotations.SerializedName

data class Request(
    @SerializedName("phoneNumber") val phoneNumber: String
)
