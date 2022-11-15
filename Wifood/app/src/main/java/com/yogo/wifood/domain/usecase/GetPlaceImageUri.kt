package com.yogo.wifood.domain.usecase

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yogo.wifood.domain.repository.WifoodRepository
import javax.inject.Inject

class GetPlaceImageUri @Inject constructor(
    private val repository: WifoodRepository
) {
    operator fun invoke(groupId: Int, placeId: Int): LiveData<Uri> {
        val imageUri = MutableLiveData<Uri>()
        repository.getPlaceImageUri(groupId, placeId).observeForever {
            imageUri.value = it
        }
        return imageUri
    }
}