package com.example.wifood.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.wifood.dto.ImageStoreDto
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.UploadTask

class ImageStoreViewModel() : ViewModel() {
    private val imageStoreDto: ImageStoreDto = ImageStoreDto()
    fun insertPlaceImage(uri: ArrayList<Uri>, groupId: Int): UploadTask {
        return imageStoreDto.insertPlaceImage(uri, groupId)
    }

    fun getPlaceImage(idx: Int, placeId: Int): Task<Uri> {
        return imageStoreDto.getPlaceImage(idx, placeId)
    }
}