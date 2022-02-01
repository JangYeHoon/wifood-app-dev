package com.example.wifood.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.wifood.dto.GroupDto
import com.example.wifood.entity.Group

class WishGroupViewModel: ViewModel() {
    var wishGroupList: LiveData<MutableList<Group>>
    private val wishGroupDto: GroupDto = GroupDto("wish")

    init {
        wishGroupList = wishGroupDto.getGroupList()
    }

    fun groupInsert(group : Group) {
        wishGroupDto.groupInsert(group)
    }

    fun groupDelete(groupIdList: ArrayList<Int>) {
        wishGroupDto.groupDelete(groupIdList)
    }

    fun getGroup(pos: Int): Group {
        val groupList = wishGroupList.value
        return groupList!![pos]
    }
}