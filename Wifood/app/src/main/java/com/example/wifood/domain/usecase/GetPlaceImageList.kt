package com.example.wifood.domain.usecase

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.wifood.domain.repository.WifoodRepository
import javax.inject.Inject

class GetPlaceImageList @Inject constructor(
    private val repository: WifoodRepository
) {
    operator fun invoke(groupId: Int, placeId: Int): LiveData<MutableList<Uri>> {
        val imageList = MutableLiveData<MutableList<Uri>>()
        repository.getPlaceImageList(groupId, placeId).observeForever {
            imageList.value = it
            Log.d(
                "usecase",
                "get image url list from Firebase Storage : ${imageList.value.toString()}"
            )
        }
        return imageList
    }
}