package com.yogo.wifood.presentation.view.login.util

sealed class ValidationEvent {
    object Success : ValidationEvent()
    object SignUp : ValidationEvent()
    object Loading : ValidationEvent()
    data class Error(val message: String) : ValidationEvent()
}
