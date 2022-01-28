package com.example.wifood.dto

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.wifood.dao.FoodGroupDao
import com.example.wifood.entity.Group

// Connect DAO and ViewModel
// Class to prevent direct DB access from view
class FoodGroupDto {
    private val foodDao = FoodGroupDao()

    fun getGroupList() : LiveData<MutableList<Group>> {
        val mutableFoodGroup = MutableLiveData<MutableList<Group>>()
        foodDao.getGroupList().observeForever {
            mutableFoodGroup.value = it
        }
        return mutableFoodGroup
    }

    fun groupInsert(group: Group) {
        foodDao.foodGroupInsert(group)
    }

    fun groupDelete(groupIdList: ArrayList<Int>) {
        for (i in groupIdList)
            foodDao.foodGroupDelete(i)
    }
}