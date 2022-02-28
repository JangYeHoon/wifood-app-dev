package com.example.wifood.dto

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.wifood.dao.FoodListDao
import com.example.wifood.entity.Food

class FoodListDto() {
    private val foodListDao = FoodListDao()

    // 디비에서 받아온 정보를 이용해 foodlist 업데이트
    fun getFoodList() : LiveData<MutableList<Food>> {
        val mutableFoodList = MutableLiveData<MutableList<Food>>()
        foodListDao.getFoodList().observeForever {
            mutableFoodList.value = it
        }
        return mutableFoodList
    }

    // 디비에 저장하기 위해 dao에 food를 넘겨줌
    fun insertFoodList(food: Food) {
        foodListDao.insertFoodList(food)
    }

    // 디비에서 삭제할 id 정보를 dao에 넘겨줌
    fun deleteFoodList(deleteId: Int) {
        foodListDao.deleteFoodList(deleteId)
    }
}