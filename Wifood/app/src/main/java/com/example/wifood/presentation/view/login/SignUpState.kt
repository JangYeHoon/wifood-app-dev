package com.example.wifood.presentation.view.login

data class SignUpState(
    val phoneNumber: String = "",
    val phoneNumberError: String? = null,
    val certNumber: String = "",
    val certError: String? = null,
    val address: String = "",
    val birthday: String = "",
    val birthdayError: String? = null,
    val gender: Boolean = true
)
