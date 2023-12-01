package com.burakkuyucu.reachard.namifier.namifier

import org.junit.Assert.assertEquals
import org.junit.Test

class PascalCaseNamifierTest {

    @Test
    fun `convert single word`() {
        val result = PascalCaseNamifier.convert(text = "TOTAL", separator = " ")
        assertEquals("Total", result)
    }

    @Test
    fun `convert multiple words`() {
        val result = PascalCaseNamifier.convert(text = "TOTAL item count", separator = " ")
        assertEquals("TotalItemCount", result)
    }

    @Test
    fun `convert when there is a separator at the start`() {
        val result = PascalCaseNamifier.convert(text = " TOTAL item count", separator = " ")
        assertEquals("TotalItemCount", result)
    }

    @Test
    fun `convert when there is a separator at the end`() {
        val result = PascalCaseNamifier.convert(text = "TOTAL item count ", separator = " ")
        assertEquals("TotalItemCount", result)
    }

    @Test
    fun `split single word`() {
        val result = PascalCaseNamifier.split(word = "total")
        assertEquals(listOf("total"), result)
    }

    @Test
    fun `split multiple words`() {
        val result = PascalCaseNamifier.split(word = "TotalItemCount")
        assertEquals(listOf("total", "item", "count"), result)
    }

    @Test
    fun `split when there is a separator at the start`() {
        val result = PascalCaseNamifier.split(word = "ATotalItemCount")
        assertEquals(listOf("a", "total", "item", "count"), result)
    }

    @Test
    fun `split when there is a separator at the end`() {
        val result = PascalCaseNamifier.split(word = "TotalItemCountA")
        assertEquals(listOf("total", "item", "count", "a"), result)
    }
}
