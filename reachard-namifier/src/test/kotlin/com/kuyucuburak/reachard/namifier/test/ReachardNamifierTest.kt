package com.kuyucuburak.reachard.namifier.test

import com.kuyucuburak.reachard.namifier.ReachardNamifier
import com.kuyucuburak.reachard.namifier.base.NamifierBase
import com.kuyucuburak.reachard.namifier.enums.CaseTypeEnums
import org.junit.Assert
import org.junit.Test

class ReachardNamifierTest {

    @Test
    fun `convert case type camel`() {
        Assert.assertEquals("totalItemCount", ReachardNamifier.convert(caseType = CaseTypeEnums.CAMEL, text = "TOTAL item count", separator = " "))
    }

    @Test
    fun `convert case type dot`() {
        Assert.assertEquals("total.item.count", ReachardNamifier.convert(caseType = CaseTypeEnums.DOT, text = "TOTAL item count", separator = " "))
    }

    @Test
    fun `convert case type kebab`() {
        Assert.assertEquals("total-item-count", ReachardNamifier.convert(caseType = CaseTypeEnums.KEBAB, text = "TOTAL item count", separator = " "))
    }

    @Test
    fun `convert case type pascal`() {
        Assert.assertEquals("TotalItemCount", ReachardNamifier.convert(caseType = CaseTypeEnums.PASCAL, text = "TOTAL item count", separator = " "))
    }

    @Test
    fun `convert case type pascal snake`() {
        Assert.assertEquals("Total_Item_Count", ReachardNamifier.convert(caseType = CaseTypeEnums.PASCAL_SNAKE, text = "TOTAL item count", separator = " "))
    }

    @Test
    fun `convert case type screaming snake`() {
        Assert.assertEquals("TOTAL_ITEM_COUNT", ReachardNamifier.convert(caseType = CaseTypeEnums.SCREAMING_SNAKE, text = "TOTAL item count", separator = " "))
    }

    @Test
    fun `convert case type sentence`() {
        Assert.assertEquals("Total item count", ReachardNamifier.convert(caseType = CaseTypeEnums.SENTENCE, text = "TOTAL-item-count", separator = "-"))
    }

    @Test
    fun `convert case type snake`() {
        Assert.assertEquals("total_item_count", ReachardNamifier.convert(caseType = CaseTypeEnums.SNAKE, text = "TOTAL item count", separator = " "))
    }

    @Test
    fun `convert case type title`() {
        Assert.assertEquals("Total Item Count", ReachardNamifier.convert(caseType = CaseTypeEnums.TITLE, text = "TOTAL-item-count", separator = "-"))
    }

    @Test
    fun `convert case type train`() {
        Assert.assertEquals("Total-Item-Count", ReachardNamifier.convert(caseType = CaseTypeEnums.TRAIN, text = "TOTAL item count", separator = " "))
    }

    @Test
    fun `custom namifier`() {
        Assert.assertEquals("Total&Item&Count", ReachardNamifier.convert(namifier = CustomNamifier, text = "TOTAL item count", separator = " "))
    }

    @Test
    fun `convert from case type to another case type`() {
        Assert.assertEquals("TOTAL_ITEM_COUNT", ReachardNamifier.convert(from = CaseTypeEnums.CAMEL, to = CaseTypeEnums.SCREAMING_SNAKE, text = "totalItemCount"))
    }

    private object CustomNamifier : NamifierBase() {

        override val replacingSeparator: String = "&"

        override fun convertWord(totalWordCount: Int, wordIndex: Int, word: String): String {
            return word
                .lowercase()
                .replaceFirstChar { it.uppercase() }
        }
    }
}
