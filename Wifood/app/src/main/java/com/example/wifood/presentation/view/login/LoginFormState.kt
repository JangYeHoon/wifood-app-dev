package com.example.wifood.presentation.view.login

import com.example.wifood.domain.model.Group
import com.example.wifood.domain.model.Place
import com.example.wifood.domain.model.User

data class LoginFormState(
    var email: String = "",
    val emailError: String? = null,
    var password: String = "",
    val passwordError: String? = null
) {
    fun clear() {
        email = ""
        password = ""
    }
}
