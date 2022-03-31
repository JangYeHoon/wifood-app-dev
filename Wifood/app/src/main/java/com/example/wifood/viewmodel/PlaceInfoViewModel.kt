package com.example.wifood.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.wifood.dto.GroupDto
import com.example.wifood.entity.Group
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot

class PlaceInfoViewModel(groupId: Int) : ViewModel() {
    lateinit var group: Group
    private val groupDto: GroupDto = GroupDto()

    fun getGroupTaskToFireBase(groupId: Int): Task<DataSnapshot> {
        return groupDto.getGroupById(groupId)
    }

    fun setGroupInstance(group: Group) {
        this.group = group
    }

    fun getGroupPinColor(): String {
        return group.color
    }

    class Factory(private val groupId: Int) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return PlaceInfoViewModel(groupId) as T
        }
    }
}