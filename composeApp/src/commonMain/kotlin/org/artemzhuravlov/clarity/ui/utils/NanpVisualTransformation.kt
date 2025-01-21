package org.artemzhuravlov.clarity.ui.utils

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class NanpVisualTransformation : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {
        // Ensure we handle only up to 10 digits for a Ukrainian phone number.
        val trimmed = if (text.text.length >= 9) text.text.substring(0..8) else text.text

        // Start building the output with opening parenthesis if text is not empty.
        var out = if (trimmed.isNotEmpty()) "(" else ""

        for (i in trimmed.indices) {
            if (i == 2) out += ") " // Add closing parenthesis and space after first two digits.
            if (i == 5) out += "-" // Add hyphen after fifth digit.
            out += trimmed[i]
        }

        return TransformedText(AnnotatedString(out), phoneNumberOffsetTranslator)
    }

    private val phoneNumberOffsetTranslator = object : OffsetMapping {

        override fun originalToTransformed(offset: Int): Int =
            when (offset) {
                0 -> offset
                // Add 1 for opening parenthesis.
                in 1..2 -> offset + 1
                // Add 3 for both parentheses and a space.
                in 3..5 -> offset + 3
                // Add 4 for both parentheses, space, and hyphen.
                else -> offset + 4
            }

        override fun transformedToOriginal(offset: Int): Int =
            when (offset) {
                0 -> offset
                // Subtract 1 for opening parenthesis.
                in 1..3 -> offset - 1
                // Subtract 3 for both parentheses and a space.
                in 4..8 -> offset - 3
                // Subtract 4 for both parentheses, space, and hyphen.
                else -> offset - 4
            }
    }
}
