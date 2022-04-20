package com.example.wifood.dto

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.wifood.dao.PlaceDao
import com.example.wifood.entity.Place

class PlaceDto() {
    private val placeDao = PlaceDao()

    fun getPlaceList(): LiveData<MutableList<Place>> {
        val mutablePlaceList = MutableLiveData<MutableList<Place>>()
        placeDao.getPlaceList().observeForever {
            mutablePlaceList.value = it
        }
        return mutablePlaceList
    }

    fun insertPlaceList(place: Place) {
        placeDao.insertPlaceList(place)
    }

    fun deletePlaceList(deleteId: Int) {
        placeDao.deletePlaceList(deleteId)
    }
}