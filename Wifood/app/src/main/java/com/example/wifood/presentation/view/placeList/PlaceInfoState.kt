package com.example.wifood.presentation.view.placeList

import com.example.wifood.domain.model.Place

// State data class : ViewModel이 동작할 때 필요한 데이터를 들고 있는 느낌?
// 그래서 생각해보고 필요한 것을 여기다 넣으면 됨
// 아래는 내가 예시로 대충 넣어놓은 것, 필요에 따라 수정하셈
data class PlaceInfoState(
    val placeInfo: Place? = null,
    val isLoading: Boolean = false,
    val error: String = ""
)
