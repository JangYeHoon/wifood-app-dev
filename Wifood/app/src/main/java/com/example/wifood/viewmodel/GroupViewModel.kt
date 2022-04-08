package com.example.wifood.viewmodel

import androidx.lifecycle.ViewModel
import com.example.wifood.dto.GroupDto
import com.example.wifood.entity.Group
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot

class GroupViewModel() : ViewModel() {
    lateinit var group: Group
    private val groupDto: GroupDto = GroupDto()

    fun getGroupTaskToFireBase(groupId: Int): Task<DataSnapshot> {
        return groupDto.getGroupById(groupId)
    }

    fun setGroupInstance(group: Group) {
        this.group = group
    }

    fun getGroupName(): String {
        return group.name
    }

    fun setGroupName(name: String) {
        group.name = name
    }

    fun getGroupPinColor(): String {
        return group.color
    }
}