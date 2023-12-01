package com.kuyucuburak.reachard.namifier.namifier

import org.junit.Assert.assertEquals
import org.junit.Test

class ScreamingSnakeCaseNamifierTest {

    @Test
    fun `convert single word`() {
        val result = ScreamingSnakeCaseNamifier.convert(text = "TOTAL", separator = " ")
        assertEquals("TOTAL", result)
    }

    @Test
    fun `convert multiple words`() {
        val result = ScreamingSnakeCaseNamifier.convert(text = "TOTAL item count", separator = " ")
        assertEquals("TOTAL_ITEM_COUNT", result)
    }

    @Test
    fun `convert when there is a separator at the start`() {
        val result = ScreamingSnakeCaseNamifier.convert(text = " TOTAL item count", separator = " ")
        assertEquals("TOTAL_ITEM_COUNT", result)
    }

    @Test
    fun `convert when there is a separator at the end`() {
        val result = ScreamingSnakeCaseNamifier.convert(text = "TOTAL item count ", separator = " ")
        assertEquals("TOTAL_ITEM_COUNT", result)
    }

    @Test
    fun `split single word`() {
        val result = ScreamingSnakeCaseNamifier.split(word = "TOTAL")
        assertEquals(listOf("total"), result)
    }

    @Test
    fun `split multiple words`() {
        val result = ScreamingSnakeCaseNamifier.split(word = "TOTAL_ITEM_COUNT")
        assertEquals(listOf("total", "item", "count"), result)
    }

    @Test
    fun `split when there is a separator at the start`() {
        val result = ScreamingSnakeCaseNamifier.split(word = "A_TOTAL_ITEM_COUNT")
        assertEquals(listOf("a", "total", "item", "count"), result)
    }

    @Test
    fun `split when there is a separator at the end`() {
        val result = ScreamingSnakeCaseNamifier.split(word = "TOTAL_ITEM_COUNT_A")
        assertEquals(listOf("total", "item", "count", "a"), result)
    }
}
