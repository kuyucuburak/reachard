package com.kuyucuburak.reachard.namifier.namifier

import com.kuyucuburak.reachard.namifier.namifier.base.NamifierBase

internal object SentenceCaseNamifier : NamifierBase() {

    override val replacingSeparator: String = " "

    override fun convertWord(totalWordCount: Int, wordIndex: Int, word: String): String {
        return when (wordIndex) {
            0 -> {
                word
                    .lowercase()
                    .replaceFirstChar { it.uppercase() }
            }

            else -> word.lowercase()
        }
    }
}
