package com.burakkuyucu.reachard.namifier.namifier

import org.junit.Assert.assertEquals
import org.junit.Test

class TitleCaseNamifierTest {

    @Test
    fun `convert single word`() {
        val result = TitleCaseNamifier.convert(text = "TOTAL", separator = "-")
        assertEquals("Total", result)
    }

    @Test
    fun `convert multiple words`() {
        val result = TitleCaseNamifier.convert(text = "TOTAL-item-count", separator = "-")
        assertEquals("Total Item Count", result)
    }

    @Test
    fun `convert when there is a separator at the start`() {
        val result = TitleCaseNamifier.convert(text = "-TOTAL-item-count", separator = "-")
        assertEquals("Total Item Count", result)
    }

    @Test
    fun `convert when there is a separator at the end`() {
        val result = TitleCaseNamifier.convert(text = "TOTAL-item-count-", separator = "-")
        assertEquals("Total Item Count", result)
    }

    @Test
    fun `split single word`() {
        val result = TitleCaseNamifier.split(word = "total")
        assertEquals(listOf("total"), result)
    }

    @Test
    fun `split multiple words`() {
        val result = TitleCaseNamifier.split(word = "Total Item Count")
        assertEquals(listOf("total", "item", "count"), result)
    }

    @Test
    fun `split when there is a separator at the start`() {
        val result = TitleCaseNamifier.split(word = "A Total Item Count")
        assertEquals(listOf("a", "total", "item", "count"), result)
    }

    @Test
    fun `split when there is a separator at the end`() {
        val result = TitleCaseNamifier.split(word = "Total Item Count A")
        assertEquals(listOf("total", "item", "count", "a"), result)
    }
}
