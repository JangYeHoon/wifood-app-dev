package com.yogo.wifood.domain.usecase

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yogo.wifood.domain.repository.WifoodRepository
import javax.inject.Inject

class GetProfile @Inject constructor(
    private val repository: WifoodRepository
) {
    operator fun invoke(id: String): LiveData<Uri> {
        val imageUri = MutableLiveData<Uri>()
        repository.getProfile(id).observeForever {
            imageUri.value = it
        }
        return imageUri
    }
}