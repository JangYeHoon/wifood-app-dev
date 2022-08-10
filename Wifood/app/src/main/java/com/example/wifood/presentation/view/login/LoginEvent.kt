package com.example.wifood.presentation.view.login

sealed class LoginEvent {
    data class EmailChanged(val email: String) : LoginEvent()
    data class PasswordChanged(val password: String) : LoginEvent()
    object Login : LoginEvent()
    data class CheckUserByEmail(val email: String) : LoginEvent()
}
