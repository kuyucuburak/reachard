package com.burakkuyucu.reachard.namifier.namifier

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
}
