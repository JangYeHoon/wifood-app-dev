package com.example.wifood.domain.usecase

import android.util.Patterns
import com.example.wifood.domain.repository.WifoodRepository
import com.example.wifood.domain.usecase.util.ValidationResult
import javax.inject.Inject

class ValidateEmail @Inject constructor(
    private val repository: WifoodRepository
) {

    operator fun invoke(email: String): ValidationResult {
        if (email.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "The email can't be blank"
            )
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return ValidationResult(
                successful = false,
                errorMessage = "That's not a vaild email"
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}