package com.example.wifood.presentation.view.login.util

sealed class ValidationEvent {
    object Success : ValidationEvent()
    object SignUp : ValidationEvent()
    data class Error(val message: String) : ValidationEvent()
}
