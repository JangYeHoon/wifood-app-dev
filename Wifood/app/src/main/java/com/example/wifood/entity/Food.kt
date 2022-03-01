package com.example.wifood.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Food(
    var id:Int = 0, var name:String = "None",
    var memo:String = "None", var address:String = "None",
    var latitude:Double = 0.0, var longitude:Double = 0.0,
    var myTasteGrade:Double = 0.0, var myCleanGrade:Double = 0.0,
    var myKindnessGrade:Double = 0.0, var visited:Int = 0,
    var groupId:Int = -1)  : Parcelable
