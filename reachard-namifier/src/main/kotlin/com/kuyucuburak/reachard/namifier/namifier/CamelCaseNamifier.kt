package com.kuyucuburak.reachard.namifier.namifier

import com.kuyucuburak.reachard.namifier.namifier.base.NamifierBase

internal object CamelCaseNamifier : NamifierBase() {

    override val replacingSeparator: String = ""

    override fun convertWord(totalWordCount: Int, wordIndex: Int, word: String): String {
        return when (wordIndex) {
            0 -> word.lowercase()
            else -> {
                word
                    .lowercase()
                    .replaceFirstChar { it.uppercase() }
            }
        }
    }

    override fun split(word: String): List<String> {
        return Regex("([a-z])([A-Z])")
            .replace(word, "$1$TEMPORARY_SEPARATOR$2")
            .split(TEMPORARY_SEPARATOR)
            .map { it.lowercase() }
    }
}
