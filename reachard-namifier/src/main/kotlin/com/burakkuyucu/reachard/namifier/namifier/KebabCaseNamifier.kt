package com.burakkuyucu.reachard.namifier.namifier

import com.burakkuyucu.reachard.namifier.namifier.base.NamifierBase

internal object KebabCaseNamifier : NamifierBase() {

    override val replacingSeparator: String = "-"

    override fun convertWord(totalWordCount: Int, wordIndex: Int, word: String): String {
        return word.lowercase()
    }
}
