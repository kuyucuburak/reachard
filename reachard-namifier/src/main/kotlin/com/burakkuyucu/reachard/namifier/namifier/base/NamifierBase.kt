package com.burakkuyucu.reachard.namifier.namifier.base

abstract class NamifierBase {

    abstract val replacingSeparator: String

    fun convert(text: String, separator: String): String {
        val words = text
            .split(separator)
            .filter { it.isNotEmpty() }

        val totalWordCount = words.size

        return words
            .mapIndexed { index, word -> convertWord(totalWordCount, index, word) }
            .fold("") { folded, word ->
                when (folded) {
                    "" -> folded + word
                    else -> folded + replacingSeparator + word
                }
            }
    }

    protected abstract fun convertWord(totalWordCount: Int, wordIndex: Int, word: String): String

}
