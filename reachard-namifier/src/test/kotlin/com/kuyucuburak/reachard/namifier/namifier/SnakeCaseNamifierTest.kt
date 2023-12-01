package com.kuyucuburak.reachard.namifier.namifier

import org.junit.Assert.assertEquals
import org.junit.Test

class SnakeCaseNamifierTest {

    @Test
    fun `convert single word`() {
        val result = SnakeCaseNamifier.convert(text = "TOTAL", separator = " ")
        assertEquals("total", result)
    }

    @Test
    fun `convert multiple words`() {
        val result = SnakeCaseNamifier.convert(text = "TOTAL item count", separator = " ")
        assertEquals("total_item_count", result)
    }

    @Test
    fun `convert when there is a separator at the start`() {
        val result = SnakeCaseNamifier.convert(text = " TOTAL item count", separator = " ")
        assertEquals("total_item_count", result)
    }

    @Test
    fun `convert when there is a separator at the end`() {
        val result = SnakeCaseNamifier.convert(text = "TOTAL item count ", separator = " ")
        assertEquals("total_item_count", result)
    }

    @Test
    fun `split single word`() {
        val result = SnakeCaseNamifier.split(word = "total")
        assertEquals(listOf("total"), result)
    }

    @Test
    fun `split multiple words`() {
        val result = SnakeCaseNamifier.split(word = "total_item_count")
        assertEquals(listOf("total", "item", "count"), result)
    }

    @Test
    fun `split when there is a separator at the start`() {
        val result = SnakeCaseNamifier.split(word = "a_total_item_count")
        assertEquals(listOf("a", "total", "item", "count"), result)
    }

    @Test
    fun `split when there is a separator at the end`() {
        val result = SnakeCaseNamifier.split(word = "total_item_count_a")
        assertEquals(listOf("total", "item", "count", "a"), result)
    }
}
