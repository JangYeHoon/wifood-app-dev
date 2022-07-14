package com.example.wifood.domain.usecase.util

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String = ""
)
