package com.burakkuyucu.reachard.namifier.namifier

import com.burakkuyucu.reachard.namifier.namifier.base.NamifierBase

internal object CamelCaseNamifier : NamifierBase() {

    override val replacingSeparator: String = ""

    override fun convertWord(totalWordCount: Int, wordIndex: Int, word: String): String {
        return when (wordIndex) {
            0 -> word.lowercase()
            else -> word.lowercase().replaceFirstChar { it.uppercase() }
        }
    }
}
