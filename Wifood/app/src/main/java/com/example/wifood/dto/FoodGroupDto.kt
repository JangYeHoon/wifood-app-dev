package com.example.wifood.dto

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.wifood.dao.FoodGroupDao
import com.example.wifood.entity.Group

// Connect DAO and ViewModel
// Class to prevent direct DB access from view
class FoodGroupDto {
    private val foodDto = FoodGroupDao()

    fun getGroupList() : LiveData<MutableList<Group>> {
        val mutableFoodGroup = MutableLiveData<MutableList<Group>>()
        foodDto.getGroupList().observeForever {
            mutableFoodGroup.value = it
        }
        return mutableFoodGroup
    }
}