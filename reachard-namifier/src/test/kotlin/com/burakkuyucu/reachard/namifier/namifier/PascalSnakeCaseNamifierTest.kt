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
}
