package com.burakkuyucu.reachard.namifier.namifier

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
}
