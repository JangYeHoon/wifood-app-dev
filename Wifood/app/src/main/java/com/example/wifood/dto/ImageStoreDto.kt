package com.example.wifood.dto

import android.net.Uri
import com.example.wifood.dao.ImageStoreDao

class ImageStoreDto {
    private val imageStoreDto = ImageStoreDao()
    fun insertPlaceImage(uriList: ArrayList<Uri>, placeId: Int) {
        for (i in 0 until uriList.size)
            imageStoreDto.insertPlaceImage(uriList[i], i + 1, placeId)
    }
}