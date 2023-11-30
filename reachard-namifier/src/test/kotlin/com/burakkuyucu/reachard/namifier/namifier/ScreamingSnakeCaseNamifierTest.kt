package com.burakkuyucu.reachard.namifier.namifier

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
}
