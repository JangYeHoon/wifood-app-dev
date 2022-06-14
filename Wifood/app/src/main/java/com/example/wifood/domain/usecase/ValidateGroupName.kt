package com.example.wifood.domain.usecase

import com.example.wifood.domain.repository.WifoodRepository
import com.example.wifood.domain.usecase.util.ValidationResult
import javax.inject.Inject

class ValidateGroupName @Inject constructor(
    private val repository: WifoodRepository
) {
    operator fun invoke(name: String): ValidationResult {
        if (name.isBlank()) {
            return ValidationResult(successful = false, errorMessage = "그룹명 필수 입력")
        }
        return ValidationResult(successful = true)
    }
}