package com.burakkuyucu.reachard.namifier.namifier

import org.junit.Assert.assertEquals
import org.junit.Test

class PascalSnakeCaseNamifierTest {

    @Test
    fun `convert single word`() {
        val result = PascalSnakeCaseNamifier.convert(text = "TOTAL", separator = " ")
        assertEquals("Total", result)
    }

    @Test
    fun `convert multiple words`() {
        val result = PascalSnakeCaseNamifier.convert(text = "TOTAL item count", separator = " ")
        assertEquals("Total_Item_Count", result)
    }

    @Test
    fun `convert when there is a separator at the start`() {
        val result = PascalSnakeCaseNamifier.convert(text = " TOTAL item count", separator = " ")
        assertEquals("Total_Item_Count", result)
    }

    @Test
    fun `convert when there is a separator at the end`() {
        val result = PascalSnakeCaseNamifier.convert(text = "TOTAL item count ", separator = " ")
        assertEquals("Total_Item_Count", result)
    }

    @Test
    fun `split single word`() {
        val result = PascalSnakeCaseNamifier.split(word = "total")
        assertEquals(listOf("total"), result)
    }

    @Test
    fun `split multiple words`() {
        val result = PascalSnakeCaseNamifier.split(word = "Total_Item_Count")
        assertEquals(listOf("total", "item", "count"), result)
    }

    @Test
    fun `split when there is a separator at the start`() {
        val result = PascalSnakeCaseNamifier.split(word = "A_Total_Item_Count")
        assertEquals(listOf("a", "total", "item", "count"), result)
    }

    @Test
    fun `split when there is a separator at the end`() {
        val result = PascalSnakeCaseNamifier.split(word = "Total_Item_Count_A")
        assertEquals(listOf("total", "item", "count", "a"), result)
    }
}
