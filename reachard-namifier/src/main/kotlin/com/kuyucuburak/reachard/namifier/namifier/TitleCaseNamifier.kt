package com.kuyucuburak.reachard.namifier.namifier

import com.kuyucuburak.reachard.namifier.namifier.base.NamifierBase

internal object TitleCaseNamifier : NamifierBase() {

    override val replacingSeparator: String = " "

    override fun convertWord(totalWordCount: Int, wordIndex: Int, word: String): String {
        return word.lowercase().replaceFirstChar { it.uppercase() }
    }
}
