package com.example.wifood.dto

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.wifood.dao.PlaceDao
import com.example.wifood.entity.Place

class PlaceDto {
    private val placeDao = PlaceDao()

    fun getPlaceList() : LiveData<MutableList<Place>> {
        val mutablePlace = MutableLiveData<MutableList<Place>>()
        placeDao.getPlaceList().observeForever {
            mutablePlace.value = it
        }
        return mutablePlace
    }
}