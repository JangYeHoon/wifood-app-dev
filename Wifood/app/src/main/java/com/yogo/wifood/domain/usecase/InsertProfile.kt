package com.yogo.wifood.domain.usecase

import android.net.Uri
import com.yogo.wifood.domain.repository.WifoodRepository
import com.google.firebase.storage.UploadTask
import javax.inject.Inject

class InsertProfile @Inject constructor(
    private val repository: WifoodRepository
) {
    operator fun invoke(image: Uri, id: String): UploadTask {
        return repository.insertProfile(image, id)
    }
}