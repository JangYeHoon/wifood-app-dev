package com.example.wifood.presentation.view.login.util

import com.example.wifood.presentation.view.login.LoginViewModel

sealed class ValidationEvent {
    object Success : ValidationEvent()
    data class Error(val message: String) : ValidationEvent()
}
