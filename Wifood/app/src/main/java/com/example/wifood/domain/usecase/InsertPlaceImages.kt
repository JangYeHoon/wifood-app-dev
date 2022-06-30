package com.example.wifood.domain.usecase

import android.graphics.Bitmap
import com.example.wifood.domain.repository.WifoodRepository
import javax.inject.Inject

class InsertPlaceImages @Inject constructor(
    private val repository: WifoodRepository
) {
    operator fun invoke(groupId: Int, placeId: Int, images: ArrayList<Bitmap>) {
        repository.insertPlaceImages(groupId, placeId, images)
    }
}