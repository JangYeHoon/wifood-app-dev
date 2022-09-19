package com.example.wifood.presentation.view.mypage

import com.example.wifood.domain.model.TMapSearch
import com.example.wifood.domain.model.User

data class MyPageState(
    val currentUser: User,
    val nickname: String,
    val phoneNumber: String = "",
    val address: String = "",
    val searchResults: List<TMapSearch> = emptyList(),
    val isLoading: Boolean = false,
    val phoneValidation: Int = 0
)
