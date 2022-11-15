package com.yogo.wifood.presentation.view.placeList.placeinfowrite

import android.location.Location
import android.net.Uri
import com.yogo.wifood.domain.model.Group
import com.yogo.wifood.domain.model.MenuGrade

data class PlaceInfoWriteFormState(
    val groups: List<Group> = emptyList(),
    val groupName: String = "그룹 선택",
    val placeName: String = "맛집 선택",
    val visited: Boolean = false,
    val score: Float = 0.0f,
    val starScore: List<Int> = listOf(0, 0, 0, 0, 0),
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
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val address: String = "",
    val isLoading: Boolean = false,
    val currentLocation: Location? = null,
    val reviewTextLength: String = "0/200",
    val bizName: String = "",
    val imageUploadChk: Boolean = false,
    val placeImagesReCompose: String = "1"
)