package com.yogo.wifood.domain.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yogo.wifood.domain.repository.WifoodRepository
import javax.inject.Inject

class CheckUser @Inject constructor(
    private val repository: WifoodRepository
) {
    operator fun invoke(id: String): LiveData<Int> {
        val result = MutableLiveData<Int>()
        repository.checkUser(id).observeForever {
            result.value = it
        }
        return result
    }
}