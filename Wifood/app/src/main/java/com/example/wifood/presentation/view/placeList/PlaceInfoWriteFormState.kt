package com.example.wifood.presentation.view.placeList

import android.graphics.Bitmap
import com.example.wifood.domain.model.Group
import com.example.wifood.domain.model.MenuGrade

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
    val placeImages: ArrayList<Bitmap> = ArrayList(0),
    val menuGrades: ArrayList<MenuGrade> = ArrayList(0),
    val isLoading: Boolean = false
)