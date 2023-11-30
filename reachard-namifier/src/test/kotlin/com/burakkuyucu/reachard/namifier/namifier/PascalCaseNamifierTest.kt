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
}
