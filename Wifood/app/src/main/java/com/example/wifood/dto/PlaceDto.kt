package com.example.wifood.dto

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.wifood.dao.PlaceDao
import com.example.wifood.entity.Place

class PlaceDto() {
    private val placeDao = PlaceDao()

    // 디비에서 받아온 정보를 이용해 foodlist 업데이트
    fun getFoodList() : LiveData<MutableList<Place>> {
        val mutableFoodList = MutableLiveData<MutableList<Place>>()
        placeDao.getFoodList().observeForever {
            mutableFoodList.value = it
        }
        return mutableFoodList
    }

    // 디비에 저장하기 위해 dao에 food를 넘겨줌
    fun insertFoodList(place: Place) {
        placeDao.insertFoodList(place)
    }

    // 디비에서 삭제할 id 정보를 dao에 넘겨줌
    fun deleteFoodList(deleteId: Int) {
        placeDao.deleteFoodList(deleteId)
    }
}