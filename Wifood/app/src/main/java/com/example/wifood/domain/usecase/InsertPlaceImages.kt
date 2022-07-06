package com.example.wifood.domain.usecase

import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.MutableLiveData
import com.example.wifood.domain.repository.WifoodRepository
import com.example.wifood.util.Resource
import com.google.firebase.storage.UploadTask
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class InsertPlaceImages @Inject constructor(
    private val repository: WifoodRepository
) {
    operator fun invoke(groupId: Int, placeId: Int, images: ArrayList<Uri>): UploadTask {
        return repository.insertPlaceImages(groupId, placeId, images)
    }
}