package com.yogo.wifood.domain.usecase

import com.yogo.wifood.domain.repository.WifoodRepository
import com.yogo.wifood.domain.usecase.util.ValidationResult
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