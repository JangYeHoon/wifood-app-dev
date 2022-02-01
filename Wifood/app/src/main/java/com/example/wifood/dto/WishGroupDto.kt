package com.example.wifood.dto

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.wifood.dao.WishGroupDao
import com.example.wifood.entity.Group

class WishGroupDto {
    private val wishDao = WishGroupDao()

    fun getGroupList() : LiveData<MutableList<Group>> {
        val mutableFoodGroup = MutableLiveData<MutableList<Group>>()
        wishDao.getGroupList().observeForever {
            mutableFoodGroup.value = it
        }
        return mutableFoodGroup
    }

    fun groupInsert(group: Group) {
        wishDao.wishGroupInsert(group)
    }

    fun groupDelete(groupIdList: ArrayList<Int>) {
        for (i in groupIdList)
            wishDao.wishGroupDelete(i)
    }
}