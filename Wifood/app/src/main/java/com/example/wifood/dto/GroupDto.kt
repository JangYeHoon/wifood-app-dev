package com.example.wifood.dto

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.wifood.dao.GroupDao
import com.example.wifood.entity.Group

// Connect DAO and ViewModel
// Class to prevent direct DB access from view
class GroupDto(groupType: String) {
    private val groupDao = GroupDao(groupType)

    fun getGroupList() : LiveData<MutableList<Group>> {
        val mutableFoodGroup = MutableLiveData<MutableList<Group>>()
        groupDao.getGroupList().observeForever {
            mutableFoodGroup.value = it
        }
        return mutableFoodGroup
    }

    fun groupInsert(group: Group) {
        groupDao.foodGroupInsert(group)
    }

    fun groupDelete(groupIdList: ArrayList<Int>) {
        for (i in groupIdList)
            groupDao.foodGroupDelete(i)
    }
}