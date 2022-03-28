package com.example.wifood.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Menu(val name: String = "None") : Parcelable
