package com.sborges.price.presentation.screens.price.utils

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class CepVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val originalText = text.text
        val maskedText = originalText.take(5) + (if (originalText.length > 5) "-" + originalText.substring(5) else "")
        return TransformedText(
            text = AnnotatedString(maskedText),
            offsetMapping = CepOffsetMapping(originalText)
        )
    }
}

class CepOffsetMapping(private val originalText: String) : OffsetMapping {
    override fun originalToTransformed(offset: Int): Int {
        return minOf(offset, originalText.length).let { if (it > 5) it + 1 else it }
    }

    override fun transformedToOriginal(offset: Int): Int {
        return minOf(offset, originalText.length).let { if (it > 6) it - 1 else it }
    }
}