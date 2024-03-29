package com.kuyucuburak.reachard.namifier.enums

import com.kuyucuburak.reachard.namifier.namifier.*
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
    TRAIN(TrainCaseNamifier);

}
