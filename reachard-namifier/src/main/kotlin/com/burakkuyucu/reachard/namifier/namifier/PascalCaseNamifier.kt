package com.burakkuyucu.reachard.namifier.namifier

import com.burakkuyucu.reachard.namifier.namifier.base.NamifierBase

internal object PascalCaseNamifier : NamifierBase() {

    override val replacingSeparator: String = ""

    override fun convertWord(totalWordCount: Int, wordIndex: Int, word: String): String {
        return word
            .lowercase()
            .replaceFirstChar { it.uppercase() }
    }

    override fun split(word: String): List<String> {
        return Regex("([a-z])?([A-Z])")
            .replace(word, "$1$temporarySeparator$2")
            .split(temporarySeparator)
            .filter { it.isNotEmpty() }
            .map { it.lowercase() }
    }
}
