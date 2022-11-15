package com.yogo.wifood.domain.usecase

import com.yogo.wifood.domain.repository.WifoodRepository
import com.yogo.wifood.domain.usecase.util.ValidationResult
import javax.inject.Inject

class ValidateNickname @Inject constructor(
    private val repository: WifoodRepository
) {

    operator fun invoke(nickname: String): ValidationResult {
        // empty check
        // 중복 체크
        // 특수문자 X
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