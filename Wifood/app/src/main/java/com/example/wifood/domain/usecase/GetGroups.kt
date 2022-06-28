package com.example.wifood.domain.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.wifood.domain.model.Group
import com.example.wifood.domain.repository.WifoodRepository
import javax.inject.Inject

class GetGroups @Inject constructor(
    private val repository: WifoodRepository
){
    operator fun invoke(): LiveData<MutableList<Group>> {
        val groups = MutableLiveData<MutableList<Group>>()
        repository.getGroups().observeForever {
            groups.value = it
        }
        return groups
    }
}