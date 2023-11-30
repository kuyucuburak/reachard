package com.burakkuyucu.reachard.namifier

import com.burakkuyucu.reachard.namifier.enums.CaseTypeEnums
import com.burakkuyucu.reachard.namifier.namifier.base.NamifierBase
import org.junit.Assert.assertEquals
import org.junit.Test

class ReachardNamifierTest {

    private object CustomNamifier : NamifierBase() {

        override val replacingSeparator: String = "&"

        override fun convertWord(totalWordCount: Int, wordIndex: Int, word: String): String {
            return word.lowercase().replaceFirstChar { it.uppercase() }
        }
    }

    @Test
    fun `convert case type camel`() {
        assertEquals("totalItemCount", ReachardNamifier.convert(CaseTypeEnums.CAMEL, "TOTAL item count", " "))
    }

    @Test
    fun `convert case type dot`() {
        assertEquals("total.item.count", ReachardNamifier.convert(CaseTypeEnums.DOT, "TOTAL item count", " "))
    }

    @Test
    fun `convert case type kebab`() {
        assertEquals("total-item-count", ReachardNamifier.convert(CaseTypeEnums.KEBAB, "TOTAL item count", " "))
    }

    @Test
    fun `convert case type pascal`() {
        assertEquals("TotalItemCount", ReachardNamifier.convert(CaseTypeEnums.PASCAL, "TOTAL item count", " "))
    }

    @Test
    fun `convert case type pascal snake`() {
        assertEquals("Total_Item_Count", ReachardNamifier.convert(CaseTypeEnums.PASCAL_SNAKE, "TOTAL item count", " "))
    }

    @Test
    fun `convert case type screaming snake`() {
        assertEquals("TOTAL_ITEM_COUNT", ReachardNamifier.convert(CaseTypeEnums.SCREAMING_SNAKE, "TOTAL item count", " "))
    }

    @Test
    fun `convert case type sentence`() {
        assertEquals("Total item count", ReachardNamifier.convert(CaseTypeEnums.SENTENCE, "TOTAL-item-count", "-"))
    }

    @Test
    fun `convert case type snake`() {
        assertEquals("total_item_count", ReachardNamifier.convert(CaseTypeEnums.SNAKE, "TOTAL item count", " "))
    }

    @Test
    fun `convert case type title`() {
        assertEquals("Total Item Count", ReachardNamifier.convert(CaseTypeEnums.TITLE, "TOTAL-item-count", "-"))
    }

    @Test
    fun `convert case type train`() {
        assertEquals("Total-Item-Count", ReachardNamifier.convert(CaseTypeEnums.TRAIN, "TOTAL item count", " "))
    }

    @Test
    fun `custom namifier`() {
        assertEquals("Apple&Banana&Orange", ReachardNamifier.convert(CustomNamifier, "apple banana orange", " "))
    }
}
