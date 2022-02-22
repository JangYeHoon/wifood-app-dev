package com.example.wifood.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Place(val id:Int = 0, val name:String = "Name", val address:String = "None",
                val latitude:Double = 0.0, val longitude:Double = 0.0,
                var tasteGrade:Double = 0.0, var cleanGrade:Double = 0.0,
                var kindnessGrade:Double = 0.0, var personCount:Int = 0)  : Parcelable