package com.kuyucuburak.reachard.namifier

import com.kuyucuburak.reachard.namifier.enums.CaseTypeEnums
import com.kuyucuburak.reachard.namifier.namifier.base.NamifierBase

object ReachardNamifier {

    fun convert(caseType: CaseTypeEnums, text: String, separator: String): String {
        return convert(namifier = caseType.namifier, text = text, separator = separator)
    }

    fun convert(namifier: NamifierBase, text: String, separator: String): String {
        return namifier.convert(text = text, separator = separator)
    }

    fun convert(from: CaseTypeEnums, to: CaseTypeEnums, text: String): String {
        val newText = from.namifier.split(text)
        return to.namifier.convert(newText)
    }

    fun split(namifier: NamifierBase, text: String): List<String> {
        return namifier.split(text)
    }
}
