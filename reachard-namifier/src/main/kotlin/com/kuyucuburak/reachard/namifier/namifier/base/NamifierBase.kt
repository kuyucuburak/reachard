package com.kuyucuburak.reachard.namifier.namifier.base

abstract class NamifierBase {

    abstract val replacingSeparator: String

    protected abstract fun convertWord(totalWordCount: Int, wordIndex: Int, word: String): String

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

    fun convert(words: List<String>): String {
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

    open fun split(word: String): List<String> {
        return word
            .split(replacingSeparator)
            .map { it.lowercase() }
    }

    companion object {

        const val TEMPORARY_SEPARATOR = "(&)"

    }
}
