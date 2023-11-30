package com.burakkuyucu.reachard.namifier.namifier

import org.junit.Assert.assertEquals
import org.junit.Test

class TrainCaseNamifierTest {

    @Test
    fun `convert single word`() {
        val result = TrainCaseNamifier.convert(text = "TOTAL", separator = " ")
        assertEquals("Total", result)
    }

    @Test
    fun `convert multiple words`() {
        val result = TrainCaseNamifier.convert(text = "TOTAL item count", separator = " ")
        assertEquals("Total-Item-Count", result)
    }

    @Test
    fun `convert when there is a separator at the start`() {
        val result = TrainCaseNamifier.convert(text = " TOTAL item count", separator = " ")
        assertEquals("Total-Item-Count", result)
    }

    @Test
    fun `convert when there is a separator at the end`() {
        val result = TrainCaseNamifier.convert(text = "TOTAL item count ", separator = " ")
        assertEquals("Total-Item-Count", result)
    }
}
