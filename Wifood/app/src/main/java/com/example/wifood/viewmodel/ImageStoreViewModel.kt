package com.example.wifood.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.wifood.dto.ImageStoreDto

class ImageStoreViewModel() : ViewModel() {
    private val imageStoreDto: ImageStoreDto = ImageStoreDto()
    fun insertFoodImage(uri: ArrayList<Uri>, foodId: Int) {
        imageStoreDto.insertFoodImage(uri, foodId)
    }
}