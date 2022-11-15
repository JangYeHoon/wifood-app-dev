package com.yogo.wifood.domain.usecase.util

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
)
