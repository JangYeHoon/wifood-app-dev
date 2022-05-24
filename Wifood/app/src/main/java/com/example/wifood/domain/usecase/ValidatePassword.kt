package com.example.wifood.domain.usecase

import android.util.Patterns
import com.example.wifood.domain.repository.WifoodRepository
import com.example.wifood.domain.usecase.util.ValidationResult
import javax.inject.Inject

class ValidatePassword @Inject constructor(
    private val repository: WifoodRepository
) {

    operator fun invoke(password: String): ValidationResult {
        if (password.length < 8) {
            return ValidationResult(
                successful = false,
                errorMessage = "The password needs to consist of at least 8 characters"
            )
        }
        val containsLettersAndDigits =
            password.any { it.isDigit() } && password.any { it.isLetter() }
        if (!containsLettersAndDigits) {
            return ValidationResult(
                successful = false,
                errorMessage = "The password needs to contain at least one letter and digit"
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}