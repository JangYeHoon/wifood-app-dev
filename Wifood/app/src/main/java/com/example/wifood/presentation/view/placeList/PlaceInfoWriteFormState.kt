package com.example.wifood.presentation.view.placeList

import com.example.wifood.domain.model.Group

data class PlaceInfoWriteFormState(
    val groups: List<Group> = emptyList(),
    val groupName: String = "그룹 선택",
    val placeName: String = "맛집 선택"
)