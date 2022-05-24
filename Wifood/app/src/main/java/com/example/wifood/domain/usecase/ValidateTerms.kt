package com.example.wifood.domain.usecase

import android.util.Patterns
import com.example.wifood.domain.repository.WifoodRepository
import com.example.wifood.domain.usecase.util.ValidationResult
import javax.inject.Inject

class ValidateTerms @Inject constructor(
    private val repository: WifoodRepository
) {

    operator fun invoke(acceptedTerms: Boolean): ValidationResult {
        if (!acceptedTerms) {
            return ValidationResult(
                successful = false,
                errorMessage = "Please accept the terms"
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}