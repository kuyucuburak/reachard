package com.kuyucuburak.reachard.namifier.namifier

import com.kuyucuburak.reachard.namifier.namifier.base.NamifierBase

internal object SnakeCaseNamifier : NamifierBase() {

    override val replacingSeparator: String = "_"

    override fun convertWord(totalWordCount: Int, wordIndex: Int, word: String): String {
        return word.lowercase()
    }
}
