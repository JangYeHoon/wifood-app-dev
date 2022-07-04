package com.example.wifood.presentation.view.placeList

import android.graphics.Bitmap
import android.net.Uri
import com.example.wifood.domain.model.Group
import com.example.wifood.domain.model.MenuGrade
import com.google.android.gms.maps.model.LatLng

data class PlaceInfoWriteFormState(
    val groups: List<Group> = emptyList(),
    val groupName: String = "그룹 선택",
    val placeName: String = "맛집 선택",
    val menu: String = "",
    val visited: Boolean = false,
    val score: Float = 0.0f,
    val tasteChk: Boolean = false,
    val cleanChk: Boolean = false,
    val kindChk: Boolean = false,
    val vibeChk: Boolean = false,
    val review: String = "",
    val menuName: String = "",
    val menuPrice: String = "",
    val menuMemo: String = "",
    val placeImages: ArrayList<Uri> = ArrayList(0),
    val menuGrades: ArrayList<MenuGrade> = ArrayList(0),
    val currentPhotoPath: String = "",
    val placeEditChk: Boolean = false,
    val latLng: LatLng = LatLng(0.0, 0.0),
    val address: String = "",
    val isLoading: Boolean = false
)