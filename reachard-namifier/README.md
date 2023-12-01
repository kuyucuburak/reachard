[![Eligibility Bot](https://github.com/kuyucuburak/Reachard/actions/workflows/eligibility_bot.yml/badge.svg)](https://github.com/kuyucuburak/Reachard/actions/workflows/eligibility_bot.yml)
[![](https://jitpack.io/v/kuyucuburak/Reachard.svg)](https://jitpack.io/#kuyucuburak/Reachard)

# Reachard - Namifier

`reachard-namifier` is an Android library that let you change casing of strings.

## Table of Contents

1. [Integration](#integration)
    1. [Gradle Setup](#gradle-setup)
    1. [Maven Setup](#maven-setup)
1. [Documentation](#documentation)
    1. [Supported Naming Conventions](#supported-naming-conventions)
        1. [Camel Case](#camel-case)
        1. [Dot Case](#dot-case)
        1. [Kebab Case](#kebab-case)
        1. [Pascal Case](#pascal-case)
        1. [Pascal Snake Case](#pascal-snake-case)
        1. [Screaming Snake Case](#screaming-snake-case)
        1. [Sentence Case](#sentence-case)
        1. [Snake Case](#snake-case)
        1. [Title Case](#title-case)
        1. [Train Case](#train-case)
    1. [Custom Naming Conventions](#custom-naming-conventions)
    1. [Converting Using Case Types](#converting-using-case-types)

## Integration

### Gradle Setup

**Step 1:** Add below code in your root build.gradle:

```gradle
	allprojects {
		repositories {
			maven { url 'https://jitpack.io' }
		}
	}
```

**Step 2:** Add the Reachard:

```gradle
    dependencies {
	    implementation "com.github.kuyucuburak.Reachard:reachard-namifier:$reachardVersion"
	}
```

### Maven Setup

**Step 1:** Add below code to your repositories:

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

**Step 2:** Add the Reachard:

```xml
<dependency>
    <groupId>com.github.kuyucuburak.Reachard</groupId>
    <artifactId>reachard-namifier</artifactId>
    <version>reachardVersion</version>
</dependency>
```

## Documentation

This is very easy to use. All of details are explained below.

### Supported Naming Conventions

There are numerous casing types supported in this library.

#### Camel Case

```kotlin
val result = ReachardNamifier.convert(caseType = CaseTypeEnums.CAMEL, text = "TOTAL item count", separator = " ")
print(result == "totalItemCount") // prints true
```

#### Dot Case

```kotlin
val result = ReachardNamifier.convert(caseType = CaseTypeEnums.DOT, text = "TOTAL item count", separator = " ")
print(result == "total.item.count") // prints true
```

#### Kebab Case

```kotlin
val result = ReachardNamifier.convert(caseType = CaseTypeEnums.KEBAB, text = "TOTAL item count", separator = " ")
print(result == "total-item-count") // prints true
```

#### Pascal Case

```kotlin
val result = ReachardNamifier.convert(caseType = CaseTypeEnums.PASCAL, text = "TOTAL item count", separator = " ")
print(result == "TotalItemCount") // prints true
```

#### Pascal Snake Case

```kotlin
val result = ReachardNamifier.convert(caseType = CaseTypeEnums.PASCAL_SNAKE, text = "TOTAL item count", separator = " ")
print(result == "Total_Item_Count") // prints true
```

#### Screaming Snake Case

```kotlin
val result = ReachardNamifier.convert(caseType = CaseTypeEnums.SCREAMING_SNAKE, text = "TOTAL item count", separator = " ")
print(result == "TOTAL_ITEM_COUNT") // prints true
```

#### Sentence Case

```kotlin
val result = ReachardNamifier.convert(caseType = CaseTypeEnums.SENTENCE, text = "TOTAL item count", separator = " ")
print(result == "Total item count") // prints true
```

#### Snake Case

```kotlin
val result = ReachardNamifier.convert(caseType = CaseTypeEnums.SNAKE, text = "TOTAL item count", separator = " ")
print(result == "total_item_count") // prints true
```

#### Title Case

```kotlin
val result = ReachardNamifier.convert(caseType = CaseTypeEnums.TITLE, text = "TOTAL item count", separator = " ")
print(result == "Total Item Count") // prints true
```

#### Train Case

```kotlin
val result = ReachardNamifier.convert(caseType = CaseTypeEnums.TRAIN, text = "TOTAL item count", separator = " ")
print(result == "Total-Item-Count") // prints true
```

### Custom Naming Conventions

You can create your own naming conventions like below:

```kotlin
private object CustomNamifier : NamifierBase() {

    override val replacingSeparator: String = "&"

    override fun convertWord(totalWordCount: Int, wordIndex: Int, word: String): String {
        return word
            .lowercase()
            .replaceFirstChar { it.uppercase() }
    }
}
```

You can use your custom namifier like below:

```kotlin
val result = ReachardNamifier.convert(namifier = CustomNamifier, text = "TOTAL item count", separator = " ")
print(result == "Total&Item&Count") // prints true
```

### Converting Using Case Types

If you know the casing type of the string, you can just do this:

```kotlin
ReachardNamifier.convert(from = CaseTypeEnums.CAMEL, to = CaseTypeEnums.SCREAMING_SNAKE, text = "totalItemCount")
print(result == "TOTAL_ITEM_COUNT") // prints true
```
