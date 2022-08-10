package com.example.wifood.presentation.view.login.util

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText

const val phoneMask = "010-1234-5678"

internal fun phoneFilter(text: AnnotatedString): TransformedText {
    val trimmed = if (text.text.length >= 11) text.text.substring(0..10) else text.text

    val annotatedString = AnnotatedString.Builder().run {
        for (i in trimmed.indices) {
            append(trimmed[i])
            if (i == 2 || i == 6) {
                append("-")
            }
        }
        pushStyle(SpanStyle(color = Color.LightGray))
        append(phoneMask.takeLast(phoneMask.length - length))
        toAnnotatedString()
    }

    val offsetTranslator = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            if (offset <= 2) return offset
            if (offset <= 6) return offset + 1
            if (offset <= 11) return offset + 2
            return 13
        }

        override fun transformedToOriginal(offset: Int): Int {
            if (offset <= 2) return offset
            if (offset <= 6) return offset - 1
            if (offset <= 11) return offset - 2
            return 11
        }
    }

    return TransformedText(annotatedString, offsetTranslator)
}