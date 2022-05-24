package com.example.wifood.domain.usecase

import android.util.Patterns
import com.example.wifood.domain.repository.WifoodRepository
import com.example.wifood.domain.usecase.util.ValidationResult
import javax.inject.Inject

class ValidateRepeatedPassword @Inject constructor(
    private val repository: WifoodRepository
) {

    operator fun invoke(password: String, repeatedPassword: String): ValidationResult {
        if (password != repeatedPassword) {
            return ValidationResult(
                successful = false,
                errorMessage = "The passwords don't match"
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}