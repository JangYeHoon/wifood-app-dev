package com.yogo.wifood.domain.usecase

import android.net.Uri
import com.yogo.wifood.domain.repository.WifoodRepository
import com.google.firebase.storage.UploadTask
import javax.inject.Inject

class InsertPlaceImages @Inject constructor(
    private val repository: WifoodRepository
) {
    operator fun invoke(groupId: Int, placeId: Int, images: ArrayList<Uri>): UploadTask {
        return repository.insertPlaceImages(groupId, placeId, images)
    }
}