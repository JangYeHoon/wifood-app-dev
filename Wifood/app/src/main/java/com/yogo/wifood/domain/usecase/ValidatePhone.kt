package com.yogo.wifood.domain.usecase

import androidx.core.text.isDigitsOnly
import com.yogo.wifood.domain.usecase.util.ValidationResult
import com.yogo.wifood.util.Constants
import java.util.regex.Pattern

class ValidatePhone {
    operator fun invoke(phoneNumber: String): ValidationResult {
        if (!phoneNumber.isDigitsOnly()) {
            return ValidationResult(
                successful = false,
                errorMessage = "$phoneNumber, 숫자만 입력이 가능합니다."
            )
        }
        if (phoneNumber.length != 11) {
            return ValidationResult(
                successful = false,
                errorMessage = "${phoneNumber}, 핸드폰 번호 11자리를 입력해주세요."
            )
        }
        val pattern = Pattern.compile(Constants.PHONE_NUMBER_PATTERN)
        val matcher = pattern.matcher(phoneNumber)
        if (!matcher.matches()) {
            return ValidationResult(
                successful = false,
                errorMessage = "${phoneNumber}, 올바른 핸드폰 번호가 아닙니다."
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}