package com.example.wifood.dto

import android.net.Uri
import android.os.Environment
import com.example.wifood.dao.ImageStoreDao
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.UploadTask
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ImageStoreDto {
    private val imageStoreDto = ImageStoreDao()
    fun insertPlaceImage(uriList: ArrayList<Uri>, placeId: Int): UploadTask {
        return imageStoreDto.insertPlaceImage(uriList[0], 1, placeId)
    }

    fun getPlaceImage(idx: Int, placeId: Int): Task<Uri> {
        return imageStoreDto.getPlaceImage(idx, placeId)
    }
}