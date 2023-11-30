package com.burakkuyucu.reachard.namifier

import com.burakkuyucu.reachard.namifier.enums.CaseTypeEnums
import com.burakkuyucu.reachard.namifier.namifier.base.NamifierBase

object ReachardNamifier {

    fun convert(caseType: CaseTypeEnums, text: String, separator: String): String {
        return convert(namifier = caseType.namifier, text = text, separator = separator)
    }

    fun convert(namifier: NamifierBase, text: String, separator: String): String {
        return namifier.convert(text = text, separator = separator)
    }
}
