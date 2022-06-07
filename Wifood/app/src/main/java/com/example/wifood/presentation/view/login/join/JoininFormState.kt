package com.example.wifood.presentation.view.login.join

import android.location.Address

data class JoininFormState(
    var email: String = "",
    val emailError: String? = null,
    var password: String = "",
    val passwordError: String? = null,
    var repeatedPassword: String = "",
    val repeatedPasswordError: String? = null,
    var nickname: String = "",
    val nicknameError: String? = null,
    var phoneNumber: String = "",
    val phoneNumberError: String? = null,
    var validNumber: String = "",
    val ValidNumberError: String? = null,
    var address: String = "",
    var detailedAddress: String = "",
    var birthday: String = "",
    val birthdayError: String? = null,
    var terms: Boolean = false,
    val termsError: String? = null,
)
