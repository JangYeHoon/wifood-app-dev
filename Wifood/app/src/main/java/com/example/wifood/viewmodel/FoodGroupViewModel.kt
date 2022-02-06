package com.example.wifood.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.wifood.dto.GroupDto
import com.example.wifood.entity.Group

// Independent of the lifecycle of the activity.
// Data retention and sharing
class FoodGroupViewModel :ViewModel() {
    var foodGroupList : LiveData<MutableList<Group>>
    private val foodGroupDto : GroupDto = GroupDto("food")

    init {
        foodGroupList = foodGroupDto.getGroupList()
    }

    fun groupInsert(group : Group) {
        foodGroupDto.groupInsert(group)
    }

    fun groupDelete(groupIdList: ArrayList<Int>) {
        foodGroupDto.groupDelete(groupIdList)
    }

    fun getGroup(pos: Int): Group {
        val groupList = foodGroupList.value
        return groupList!![pos]
    }
}