package com.example.wifood.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.wifood.dto.GroupDto
import com.example.wifood.entity.Food
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

    fun updateGroup(group: Group) {
        foodGroupDto.updateGroup(group)
    }

    fun setGroupOrder(groupList: ArrayList<Group>) {
        for (i in groupList.indices) {
            groupList[i].order = i + 1
            foodGroupDto.updateGroup(groupList[i])
        }
    }

    fun getGroupName(groupId: Int) : String {
        if (groupId != -1) {
            for (group in foodGroupList.value!!) {
                if (groupId == group.id)
                    return group.name
            }
        }
        return ""
    }

    fun getGroupList(): MutableList<Group>? {
        return foodGroupList.value
    }

    fun getGroup(pos: Int): Group {
        val groupList = foodGroupList.value
        return groupList!![pos]
    }
}