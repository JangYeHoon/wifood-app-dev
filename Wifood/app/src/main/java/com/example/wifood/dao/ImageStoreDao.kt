package com.example.wifood.dao

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage

class ImageStoreDao {
    var storage = FirebaseStorage.getInstance().reference
    fun insertPlaceImage(uri: Uri, idx: Int, placeId: Int) {
        storage.child("$placeId/").child("$idx.png").putFile(uri)
    }
}