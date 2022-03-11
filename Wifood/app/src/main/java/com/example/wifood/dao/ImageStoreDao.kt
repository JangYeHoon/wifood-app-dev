package com.example.wifood.dao

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage

class ImageStoreDao {
    var storage = FirebaseStorage.getInstance().reference
    fun insertFoodImage(uri: Uri, idx: Int, foodId: Int) {
        storage.child("$foodId/").child("$idx.png").putFile(uri)
    }
}