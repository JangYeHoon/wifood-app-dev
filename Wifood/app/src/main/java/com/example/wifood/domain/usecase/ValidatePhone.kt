package com.example.wifood.domain.usecase

import androidx.core.text.isDigitsOnly
import com.example.wifood.domain.usecase.util.ValidationResult
import com.example.wifood.util.Constants
import java.util.regex.Pattern

class ValidatePhone {
    operator fun invoke(phoneNumber: String): ValidationResult {
        if (!phoneNumber.isDigitsOnly()) {
            return ValidationResult(
                successful = false,
                errorMessage = "${phoneNumber}"
            )
        }
        if (phoneNumber.length != 11) {
            return ValidationResult(
                successful = false,
                errorMessage = "${phoneNumber}"
            )
        }
        val pattern = Pattern.compile(Constants.PHONE_NUMBER_PATTERN)
        val matcher = pattern.matcher(phoneNumber)
        if (!matcher.matches()) {
            return ValidationResult(
                successful = false,
                errorMessage = "${phoneNumber}"
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}