package com.example.wifood.dto

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.wifood.dao.FoodListDao
import com.example.wifood.entity.Food

class FoodListDto(groupId : Int) {
    private val foodListDao = FoodListDao(groupId)

    fun getFoodList() : LiveData<MutableList<Food>> {
        val mutableFoodList = MutableLiveData<MutableList<Food>>()
        foodListDao.getFoodList().observeForever {
            mutableFoodList.value = it
        }
        return mutableFoodList
    }

    fun insertFoodList(food: Food) {
        foodListDao.insertFoodList(food)
    }

    fun deleteFoodList(foodIdList: ArrayList<Int>) {
        for (i in foodIdList)
            foodListDao.deleteFoodList(i)
    }
}