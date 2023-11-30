package com.burakkuyucu.reachard.namifier.namifier

import com.burakkuyucu.reachard.namifier.namifier.base.NamifierBase

object ScreamingSnakeCaseNamifier : NamifierBase() {

    override val replacingSeparator: String = "_"

    override fun convertWord(totalWordCount: Int, wordIndex: Int, word: String): String {
        return word.uppercase()
    }
}
