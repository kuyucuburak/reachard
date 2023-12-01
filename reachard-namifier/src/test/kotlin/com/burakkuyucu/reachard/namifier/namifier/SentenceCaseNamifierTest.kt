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

    @Test
    fun `split single word`() {
        val result = SentenceCaseNamifier.split(word = "total")
        assertEquals(listOf("total"), result)
    }

    @Test
    fun `split multiple words`() {
        val result = SentenceCaseNamifier.split(word = "Total item count")
        assertEquals(listOf("total", "item", "count"), result)
    }

    @Test
    fun `split when there is a separator at the start`() {
        val result = SentenceCaseNamifier.split(word = "A total item count")
        assertEquals(listOf("a", "total", "item", "count"), result)
    }

    @Test
    fun `split when there is a separator at the end`() {
        val result = SentenceCaseNamifier.split(word = "Total item count a")
        assertEquals(listOf("total", "item", "count", "a"), result)
    }
}
