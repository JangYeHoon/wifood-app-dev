package com.example.wifood.entity

data class Wish(val id:Int = 0, val name:String = "Name",
                val memo:String = "None", val address:String = "None",
                val latitude:Double = 0.0, val longitude:Double = 0.0)