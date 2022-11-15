package com.yogo.wifood.presentation.util

sealed class ValidationEvent {
    object Success : ValidationEvent()
    data class Error(val message: String) : ValidationEvent()
}
