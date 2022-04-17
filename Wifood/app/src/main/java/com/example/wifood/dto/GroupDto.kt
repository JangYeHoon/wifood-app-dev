package com.example.wifood.dto

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.wifood.dao.GroupDao
import com.example.wifood.entity.Group
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot

// Connect DAO and ViewModel
// Class to prevent direct DB access from view
class GroupDto {
    private val groupDao = GroupDao()

    // 디비에서 받아온 정보를 이용해 group list 업데이트
    fun getGroupList(): LiveData<MutableList<Group>> {
        val mutableGroup = MutableLiveData<MutableList<Group>>()
        groupDao.getGroupList().observeForever { it ->
            mutableGroup.value =
                if (it != null)
                    it.sortedBy { it.order } as MutableList<Group>?
                else it
        }
        return mutableGroup
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

    fun getGroupById(groupId: Int): Task<DataSnapshot> {
        return groupDao.getGroupById(groupId)
    }
}