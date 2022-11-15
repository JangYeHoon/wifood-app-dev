package com.yogo.wifood.domain.usecase

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yogo.wifood.domain.repository.WifoodRepository
import javax.inject.Inject

class GetPlaceImageUris @Inject constructor(
    private val repository: WifoodRepository
) {
    operator fun invoke(groupId: Int, placeId: Int): LiveData<MutableList<Uri>> {
        val imageUris = MutableLiveData<MutableList<Uri>>()
        repository.getPlaceImageUris(groupId, placeId).observeForever {
            imageUris.value = it
        }
        return imageUris
    }
}