package com.burakkuyucu.reachard.namifier.namifier

import org.junit.Assert.assertEquals
import org.junit.Test

class SentenceCaseNamifierTest {

    @Test
    fun `convert single word`() {
        val result = SentenceCaseNamifier.convert(text = "TOTAL", separator = "-")
        assertEquals("Total", result)
    }

    @Test
    fun `convert multiple words`() {
        val result = SentenceCaseNamifier.convert(text = "TOTAL-item-count", separator = "-")
        assertEquals("Total item count", result)
    }

    @Test
    fun `convert when there is a separator at the start`() {
        val result = SentenceCaseNamifier.convert(text = "-TOTAL-item-count", separator = "-")
        assertEquals("Total item count", result)
    }

    @Test
    fun `convert when there is a separator at the end`() {
        val result = SentenceCaseNamifier.convert(text = "TOTAL-item-count-", separator = "-")
        assertEquals("Total item count", result)
    }
}
