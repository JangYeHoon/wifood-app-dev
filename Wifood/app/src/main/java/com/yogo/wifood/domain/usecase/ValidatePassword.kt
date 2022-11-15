package com.yogo.wifood.domain.usecase

import com.yogo.wifood.domain.repository.WifoodRepository
import com.yogo.wifood.domain.usecase.util.ValidationResult
import javax.inject.Inject

class ValidatePassword @Inject constructor(
    private val repository: WifoodRepository
) {

    operator fun invoke(password: String): ValidationResult {
        if (password.length < 8) {
            return ValidationResult(
                successful = false,
                errorMessage = "비밀번호는 최소 8자리 이상 입력해주세요."
            )
        }
        val containsLettersAndDigits =
            password.any { it.isDigit() } && password.any { it.isLetter() }
        if (!containsLettersAndDigits) {
            return ValidationResult(
                successful = false,
                errorMessage = "비밀번호는 최소 한 자리 이상의 숫자 혹은 영어를 입력해주세요."
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}