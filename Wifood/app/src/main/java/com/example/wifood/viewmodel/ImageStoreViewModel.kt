package com.example.wifood.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.wifood.dto.ImageStoreDto

class ImageStoreViewModel() : ViewModel() {
    private val imageStoreDto: ImageStoreDto = ImageStoreDto()
    fun insertPlaceImage(uri: ArrayList<Uri>, groupId: Int) {
        imageStoreDto.insertPlaceImage(uri, groupId)
    }
}