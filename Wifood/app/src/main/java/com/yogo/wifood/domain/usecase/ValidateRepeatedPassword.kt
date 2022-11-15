package com.yogo.wifood.domain.usecase

import com.yogo.wifood.domain.repository.WifoodRepository
import com.yogo.wifood.domain.usecase.util.ValidationResult
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