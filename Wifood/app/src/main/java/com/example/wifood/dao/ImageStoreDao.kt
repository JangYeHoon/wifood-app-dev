package com.example.wifood.dao

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask

class ImageStoreDao {
    var storage = FirebaseStorage.getInstance().reference
    fun insertPlaceImage(uri: Uri, idx: Int, placeId: Int): UploadTask {
        return storage.child("$placeId/").child("$idx.png").putFile(uri)
    }

    fun getPlaceImage(idx: Int, placeId: Int): Task<Uri> {
        return storage.child("$placeId/$idx.png").downloadUrl
    }
}