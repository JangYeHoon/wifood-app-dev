package com.example.wifood.presentation.view.login

import com.example.wifood.domain.model.Group
import com.example.wifood.domain.model.Place
import com.example.wifood.domain.model.User

data class LoginState(
    val user: User? = null,
    val groups: List<Group> = emptyList(),
    val places: List<Place> = emptyList(),
    val isLoading: Boolean = false,
    val googleEmail: String? = null,
    val email: String? = null
)
