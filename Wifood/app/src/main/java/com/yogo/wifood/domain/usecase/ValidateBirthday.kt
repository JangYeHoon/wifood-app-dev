package com.yogo.wifood.domain.usecase

import com.yogo.wifood.domain.usecase.util.ValidationResult
import java.text.SimpleDateFormat
import java.util.*

class ValidateBirthday {

    operator fun invoke(birthday: String): ValidationResult {
        val isNotDigit = birthday.any { it.isDigit().not() }
        if (isNotDigit) {
            return ValidationResult(
                successful = false,
                errorMessage = "숫자만 입력해"
            )
        }
        try {
            val dateFormat = toDate(birthday)
        } catch (e: Exception) {
            return ValidationResult(
                successful = false,
                errorMessage = "880101 형식으로 입력해라"
            )
        }
        return ValidationResult(
            successful = true
        )
    }

    private fun toDate(birthday: String): String {
        val date = Date(birthday)
        return SimpleDateFormat("yyMMDD")
            .format(date)
    }
}