package com.yogo.wifood.domain.usecase

import android.util.Patterns
import com.yogo.wifood.domain.repository.WifoodRepository
import com.yogo.wifood.domain.usecase.util.ValidationResult
import javax.inject.Inject

class ValidateEmail @Inject constructor(
    private val repository: WifoodRepository
) {

    operator fun invoke(email: String): ValidationResult {
        if (email.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "아이디(이메일)를 입력해주세요."
            )
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return ValidationResult(
                successful = false,
                errorMessage = "아이디(이메일)가 올바른 형식이 아닙니다."
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}