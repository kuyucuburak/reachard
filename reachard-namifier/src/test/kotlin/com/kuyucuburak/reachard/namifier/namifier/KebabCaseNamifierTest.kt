package com.kuyucuburak.reachard.namifier.namifier

import org.junit.Assert.assertEquals
import org.junit.Test

class KebabCaseNamifierTest {

    @Test
    fun `convert single word`() {
        val result = KebabCaseNamifier.convert(text = "TOTAL", separator = " ")
        assertEquals("total", result)
    }

    @Test
    fun `convert multiple words`() {
        val result = KebabCaseNamifier.convert(text = "TOTAL item count", separator = " ")
        assertEquals("total-item-count", result)
    }

    @Test
    fun `convert when there is a separator at the start`() {
        val result = KebabCaseNamifier.convert(text = " TOTAL item count", separator = " ")
        assertEquals("total-item-count", result)
    }

    @Test
    fun `convert when there is a separator at the end`() {
        val result = KebabCaseNamifier.convert(text = "TOTAL item count ", separator = " ")
        assertEquals("total-item-count", result)
    }

    @Test
    fun `split single word`() {
        val result = KebabCaseNamifier.split(word = "total")
        assertEquals(listOf("total"), result)
    }

    @Test
    fun `split multiple words`() {
        val result = KebabCaseNamifier.split(word = "total-item-count")
        assertEquals(listOf("total", "item", "count"), result)
    }

    @Test
    fun `split when there is a separator at the start`() {
        val result = KebabCaseNamifier.split(word = "a-total-item-count")
        assertEquals(listOf("a", "total", "item", "count"), result)
    }

    @Test
    fun `split when there is a separator at the end`() {
        val result = KebabCaseNamifier.split(word = "total-item-count-a")
        assertEquals(listOf("total", "item", "count", "a"), result)
    }
}
