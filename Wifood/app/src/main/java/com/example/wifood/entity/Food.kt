package com.example.wifood.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Food(val id:Int = 0, val name:String = "Name",
                var memo:String = "None", val address:String = "None",
                val latitude:Double = 0.0, val longitude:Double = 0.0,
                var myTasteGrade:Double = 0.0, var myCleanGrade:Double = 0.0,
                var myKindnessGrade:Double = 0.0, val visited:Int = 0, val placeId:Int = 0,
                val groupId:Int = 0)  : Parcelable
