package com.example.wifood.dto

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.wifood.dao.GroupDao
import com.example.wifood.entity.Group

// Connect DAO and ViewModel
// Class to prevent direct DB access from view
class GroupDto(groupType: String) {
    private val groupDao = GroupDao(groupType)

    // 디비에서 받아온 정보를 이용해 group list 업데이트
    fun getGroupList(): LiveData<MutableList<Group>> {
        val mutableFoodGroup = MutableLiveData<MutableList<Group>>()
        groupDao.getGroupList().observeForever { it ->
            val orderGroupList = it.sortedBy { it.order }
            mutableFoodGroup.value = orderGroupList as MutableList<Group>?
        }
        return mutableFoodGroup
    }

    // 디비에 추가할 group 정보를 dao에게 넘겨줌
    fun insertGroup(group: Group) {
        groupDao.insertGroup(group)
    }

    // 디비에서 삭제할 id 정보를 dao에 넘겨줌
    fun deleteGroup(groupIdList: ArrayList<Int>) {
        for (i in groupIdList)
            groupDao.deleteGroup(i)
    }

    fun updateGroup(group: Group) {
        groupDao.updateGroup(group)
    }
}