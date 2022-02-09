package com.example.wifood.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Food(val id:Int = 0, val name:String = "Name",
                var memo:String = "None", val address:String = "None",
                val latitude:Double = 0.0, val longitude:Double = 0.0,
                val myTasteGrade:Double = 0.0, val myCleanGrade:Double = 0.0,
                val myKindnessGrade:Double = 0.0)  : Parcelable
