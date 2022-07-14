package com.example.wifood.presentation.view.login

import com.example.wifood.domain.model.Group
import com.example.wifood.domain.model.Place
import com.example.wifood.domain.model.User

data class LoginFormState(
    var email: String = "",
    var emailError: String = "",
    var password: String = "",
    var passwordError: String = ""
) {
    fun clear() {
        email = ""
        password = ""
    }

    fun errorReset() {
        emailError = ""
        passwordError = ""
    }
}
