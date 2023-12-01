package com.kuyucuburak.reachard.namifier.namifier

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

    @Test
    fun `split single word`() {
        val result = CamelCaseNamifier.split(word = "total")
        assertEquals(listOf("total"), result)
    }

    @Test
    fun `split multiple words`() {
        val result = CamelCaseNamifier.split(word = "totalItemCount")
        assertEquals(listOf("total", "item", "count"), result)
    }

    @Test
    fun `split when there is a separator at the start`() {
        val result = CamelCaseNamifier.split(word = "aTotalItemCount")
        assertEquals(listOf("a", "total", "item", "count"), result)
    }

    @Test
    fun `split when there is a separator at the end`() {
        val result = CamelCaseNamifier.split(word = "totalItemCountA")
        assertEquals(listOf("total", "item", "count", "a"), result)
    }
}
