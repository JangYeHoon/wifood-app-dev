package com.example.wifood.domain.usecase

import com.example.wifood.domain.repository.WifoodRepository
import com.example.wifood.domain.usecase.util.ValidationResult
import com.example.wifood.util.Resource
import javax.inject.Inject

class ValidateNickname @Inject constructor(
    private val repository: WifoodRepository
) {

    operator fun invoke(nickname: String): ValidationResult {
        if (nickname.length < 2 || nickname.length > 15) {
            return ValidationResult(
                successful = false,
                errorMessage = "닉네임은 \'2~15\' 자만 설정할 수 있습니다."
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}