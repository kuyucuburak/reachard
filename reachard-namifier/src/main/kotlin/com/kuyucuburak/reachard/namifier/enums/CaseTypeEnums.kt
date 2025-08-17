package com.kuyucuburak.reachard.namifier.enums

import com.kuyucuburak.reachard.namifier.namifier.CamelCaseNamifier
import com.kuyucuburak.reachard.namifier.namifier.DotCaseNamifier
import com.kuyucuburak.reachard.namifier.namifier.KebabCaseNamifier
import com.kuyucuburak.reachard.namifier.namifier.PascalCaseNamifier
import com.kuyucuburak.reachard.namifier.namifier.PascalSnakeCaseNamifier
import com.kuyucuburak.reachard.namifier.namifier.ScreamingSnakeCaseNamifier
import com.kuyucuburak.reachard.namifier.namifier.SentenceCaseNamifier
import com.kuyucuburak.reachard.namifier.namifier.SnakeCaseNamifier
import com.kuyucuburak.reachard.namifier.namifier.TitleCaseNamifier
import com.kuyucuburak.reachard.namifier.namifier.TrainCaseNamifier
import com.kuyucuburak.reachard.namifier.namifier.base.NamifierBase

enum class CaseTypeEnums(
    internal val namifier: NamifierBase,
) {

    CAMEL(CamelCaseNamifier),
    DOT(DotCaseNamifier),
    KEBAB(KebabCaseNamifier),
    PASCAL(PascalCaseNamifier),
    PASCAL_SNAKE(PascalSnakeCaseNamifier),
    SCREAMING_SNAKE(ScreamingSnakeCaseNamifier),
    SENTENCE(SentenceCaseNamifier),
    SNAKE(SnakeCaseNamifier),
    TITLE(TitleCaseNamifier),
    TRAIN(TrainCaseNamifier),
    ;

}
