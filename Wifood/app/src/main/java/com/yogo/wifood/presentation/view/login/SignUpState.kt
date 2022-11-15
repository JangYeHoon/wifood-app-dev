package com.yogo.wifood.presentation.view.login

import com.yogo.wifood.domain.model.TMapSearch

data class SignUpState(
    val phoneNumber: String = "",
    val certNumber: String = "",
    val certError: String? = null,
    val address: String = "",
    val birthday: String = "",
    val birthdayError: String? = null,
    val gender: Boolean = true,
    val isLoading: Boolean = false,
    val phoneValidation: Boolean = false,
    val reqCertNumber: String? = null,
    val searchResults: List<TMapSearch> = emptyList(),
    val agreement: Boolean = false,
    val taste: ArrayList<Int> = arrayListOf(3, 3, 3, 3, 3)
)
