package com.burakkuyucu.reachard.namifier.namifier

import org.junit.Assert.assertEquals
import org.junit.Test

class CamelCaseNamifierTest {

    @Test
    fun `convert single word`() {
        val result = CamelCaseNamifier.convert(text = "TOTAL", separator = " ")
        assertEquals("total", result)
    }

    @Test
    fun `convert multiple words`() {
        val result = CamelCaseNamifier.convert(text = "TOTAL item count", separator = " ")
        assertEquals("totalItemCount", result)
    }

    @Test
    fun `convert when there is a separator at the start`() {
        val result = CamelCaseNamifier.convert(text = " TOTAL item count", separator = " ")
        assertEquals("totalItemCount", result)
    }

    @Test
    fun `convert when there is a separator at the end`() {
        val result = CamelCaseNamifier.convert(text = "TOTAL item count ", separator = " ")
        assertEquals("totalItemCount", result)
    }
}
