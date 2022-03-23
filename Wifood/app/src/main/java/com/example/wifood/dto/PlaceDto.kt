package com.example.wifood.dto

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.wifood.dao.PlaceDao
import com.example.wifood.entity.Place

class PlaceDto() {
    private val placeDao = PlaceDao()

    // 디비에서 받아온 정보를 이용해 foodlist 업데이트
    fun getPlaceList(): LiveData<MutableList<Place>> {
        val mutablePlaceList = MutableLiveData<MutableList<Place>>()
        placeDao.getPlaceList().observeForever {
            mutablePlaceList.value = it
        }
        return mutablePlaceList
    }

    // 디비에 저장하기 위해 dao에 food를 넘겨줌
    fun insertPlaceList(place: Place) {
        placeDao.insertPlaceList(place)
    }

    // 디비에서 삭제할 id 정보를 dao에 넘겨줌
    fun deletePlaceList(deleteId: Int) {
        placeDao.deletePlaceList(deleteId)
    }
}