package com.kuyucuburak.reachard.namifier

import com.kuyucuburak.reachard.namifier.enums.CaseTypeEnums
import com.kuyucuburak.reachard.namifier.namifier.base.NamifierBase
import org.junit.Assert.assertEquals
import org.junit.Test

class ReachardNamifierTest {

    private object CustomNamifier : NamifierBase() {

        override val replacingSeparator: String = "&"

        override fun convertWord(totalWordCount: Int, wordIndex: Int, word: String): String {
            return word
                .lowercase()
                .replaceFirstChar { it.uppercase() }
        }
    }

    @Test
    fun `convert case type camel`() {
        assertEquals("totalItemCount", ReachardNamifier.convert(caseType = CaseTypeEnums.CAMEL, text = "TOTAL item count", separator = " "))
    }

    @Test
    fun `convert case type dot`() {
        assertEquals("total.item.count", ReachardNamifier.convert(caseType = CaseTypeEnums.DOT, text = "TOTAL item count", separator = " "))
    }

    @Test
    fun `convert case type kebab`() {
        assertEquals("total-item-count", ReachardNamifier.convert(caseType = CaseTypeEnums.KEBAB, text = "TOTAL item count", separator = " "))
    }

    @Test
    fun `convert case type pascal`() {
        assertEquals("TotalItemCount", ReachardNamifier.convert(caseType = CaseTypeEnums.PASCAL, text = "TOTAL item count", separator = " "))
    }

    @Test
    fun `convert case type pascal snake`() {
        assertEquals("Total_Item_Count", ReachardNamifier.convert(caseType = CaseTypeEnums.PASCAL_SNAKE, text = "TOTAL item count", separator = " "))
    }

    @Test
    fun `convert case type screaming snake`() {
        assertEquals("TOTAL_ITEM_COUNT", ReachardNamifier.convert(caseType = CaseTypeEnums.SCREAMING_SNAKE, text = "TOTAL item count", separator = " "))
    }

    @Test
    fun `convert case type sentence`() {
        assertEquals("Total item count", ReachardNamifier.convert(caseType = CaseTypeEnums.SENTENCE, text = "TOTAL-item-count", separator = "-"))
    }

    @Test
    fun `convert case type snake`() {
        assertEquals("total_item_count", ReachardNamifier.convert(caseType = CaseTypeEnums.SNAKE, text = "TOTAL item count", separator = " "))
    }

    @Test
    fun `convert case type title`() {
        assertEquals("Total Item Count", ReachardNamifier.convert(caseType = CaseTypeEnums.TITLE, text = "TOTAL-item-count", separator = "-"))
    }

    @Test
    fun `convert case type train`() {
        assertEquals("Total-Item-Count", ReachardNamifier.convert(caseType = CaseTypeEnums.TRAIN, text = "TOTAL item count", separator = " "))
    }

    @Test
    fun `custom namifier`() {
        assertEquals("Total&Item&Count", ReachardNamifier.convert(namifier = CustomNamifier, text = "TOTAL item count", separator = " "))
    }

    @Test
    fun `convert from case type to another case type`() {
        assertEquals("TOTAL_ITEM_COUNT", ReachardNamifier.convert(from = CaseTypeEnums.CAMEL, to = CaseTypeEnums.SCREAMING_SNAKE, text = "totalItemCount"))
    }
}
