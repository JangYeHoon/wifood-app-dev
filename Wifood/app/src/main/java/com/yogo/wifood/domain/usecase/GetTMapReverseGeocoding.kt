package com.yogo.wifood.domain.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yogo.wifood.domain.repository.WifoodRepository
import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject

class GetTMapReverseGeocoding @Inject constructor(
    private val repository: WifoodRepository
) {
    operator fun invoke(latLng: LatLng): LiveData<String> {
        val tMapAddress = MutableLiveData<String>()
        repository.getTMapReverseGeocoding(latLng).observeForever {
            tMapAddress.value = it
        }
        return tMapAddress
    }
}