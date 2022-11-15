package com.yogo.wifood.domain.usecase

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yogo.wifood.domain.model.TMapSearch
import com.yogo.wifood.domain.repository.WifoodRepository
import javax.inject.Inject

class GetTMapSearchPlaceResult @Inject constructor(
    private val repository: WifoodRepository
) {
    operator fun invoke(
        keyword: String,
        currentLocation: Location
    ): LiveData<MutableList<TMapSearch>> {
        val tmapSearchPlace = MutableLiveData<MutableList<TMapSearch>>()
        repository.getTMapSearchPlaceResult(keyword, currentLocation).observeForever {
            tmapSearchPlace.value = it
        }
        return tmapSearchPlace
    }
}