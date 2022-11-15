package com.yogo.wifood.presentation.view.mypage

import android.net.Uri
import com.yogo.wifood.domain.model.TMapSearch
import com.yogo.wifood.domain.model.User

data class MyPageState(
    val currentUser: User,
    val nickname: String,
    val phoneNumber: String = "",
    val address: String = "",
    val taste: ArrayList<Int> = arrayListOf(3, 3, 3, 3, 3),
    val searchResults: List<TMapSearch> = emptyList(),
    val isLoading: Boolean = false,
    val phoneValidation: Boolean = false,
    val image: Uri? = null,
    val placeImagesReCompose: String = "1"
)
