package com.yogo.wifood.domain.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yogo.wifood.domain.model.TMapSearch
import com.yogo.wifood.domain.repository.WifoodRepository
import javax.inject.Inject

class GetTMapSearchAddressResult @Inject constructor(
    private val repository: WifoodRepository
) {
    operator fun invoke(
        keyword: String
    ): LiveData<MutableList<TMapSearch>> {
        val tmapSearchPlace = MutableLiveData<MutableList<TMapSearch>>()
        repository.getTMapSearchAddressResult(keyword).observeForever {
            tmapSearchPlace.value = it
        }
        return tmapSearchPlace
    }
}