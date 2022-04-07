package com.example.wifood.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.wifood.dto.GroupDto
import com.example.wifood.entity.Group
import java.lang.Integer.max

// Independent of the lifecycle of the activity.
// Data retention and sharing
class GroupListViewModel : ViewModel() {
    var groupList: LiveData<MutableList<Group>>
    private val groupDto: GroupDto = GroupDto()

    init {
        groupList = groupDto.getGroupList()
    }

    fun insertGroup(group: Group) {
        groupDto.insertGroup(group)
    }

    fun deleteGroup(groupIdList: ArrayList<Int>) {
        groupDto.deleteGroup(groupIdList)
    }

    fun updateGroup(group: Group) {
        groupDto.updateGroup(group)
    }

    fun setGroupOrderByGroupList(groupList: ArrayList<Group>) {
        for (i in groupList.indices) {
            groupList[i].order = i + 1
            groupDto.updateGroup(groupList[i])
        }
    }

    fun getGroupNameById(groupId: Int): String {
        if (groupId != -1) {
            for (group in groupList.value!!) {
                if (groupId == group.id)
                    return group.name
            }
        }
        return ""
    }

    fun getGroupMaxId(): Int {
        var maxId = 0
        if (groupList.value != null) {
            for (group in groupList.value!!) {
                maxId = max(maxId, group.id)
            }
        }
        return maxId
    }

    fun getGroupList(): MutableList<Group>? {
        return groupList.value
    }
}