package com.example.wifood.dto

import android.net.Uri
import com.example.wifood.dao.ImageStoreDao

class ImageStoreDto {
    private val imageStoreDto = ImageStoreDao()
    fun insertFoodImage(uriList: ArrayList<Uri>, foodId: Int) {
        for (i in 0 until uriList.size)
            imageStoreDto.insertFoodImage(uriList[i], i + 1, foodId)
    }
}