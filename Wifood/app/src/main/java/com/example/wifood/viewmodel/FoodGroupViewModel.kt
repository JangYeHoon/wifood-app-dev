package com.example.wifood.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.wifood.dto.FoodGroupDto
import com.example.wifood.entity.Group

// Independent of the lifecycle of the activity.
// Data retention and sharing
class FoodGroupViewModel :ViewModel() {
    var foodGroupList : LiveData<MutableList<Group>>
    private val foodGroupDto : FoodGroupDto = FoodGroupDto()

    init {
        foodGroupList = foodGroupDto.getGroupList()
    }

    fun groupInsert(group : Group) {
        foodGroupDto.groupInsert(group)
    }
}