[![Eligibility Bot](https://github.com/kuyucuburak/Reachard/actions/workflows/eligibility_bot.yml/badge.svg)](https://github.com/kuyucuburak/Reachard/actions/workflows/eligibility_bot.yml)
[![](https://jitpack.io/v/kuyucuburak/Reachard.svg)](https://jitpack.io/#kuyucuburak/Reachard)

# Reachard - Dependency Injection

`reachard-di` is an Android library that let you put and reach your objects from everywhere very easily. Think it as an object warehouse! Easy to build, easy to use!

## Attribution

I inspired from a Flutter library called [Getx](https://github.com/jonataslaw/getx). It contains a lot of utilitization. One of them is for a dependency injection management. It is very easy to manage your objects! I couldn't see a library in Android
world similar to it and wanted to develop a similar library.

## Table of Contents

1. [Integration](#integration)
    1. [Gradle Setup](#gradle-setup)
    1. [Maven Setup](#maven-setup)
1. [Documentation](#documentation)
    1. [Put](#put)
    1. [Lazy Put](#lazy-put)
    1. [Put Conflict Strategy](#put-conflict-strategy)
        1. [CRASH](#crash)
        1. [SKIP](#skip)
        1. [UPDATE](#update)
        1. [Default Put Strategies](#default-put-strategies)
    1. [Get](#get)
    1. [Remove](#remove)
    1. [Contains](#contains)
    1. [Reset](#reset)
1. [Pros vs Cons](#pros-vs-cons)

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
    implementation "com.github.kuyucuburak.Reachard:reachard-di:$reachardVersion"
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
    <artifactId>reachard-di</artifactId>
    <version>reachardVersion</version>
</dependency>
```

## Documentation

This is very easy to use. All of details are explained below. Let's say that we have `TimeUtils` classes in our app.

### Put

At first, we need to put our objects in Reachard. I recommend you to put your objects in the `onCreate` method of `MainActivity` class of your app.

We can put the `TimeUtils` instance like:

```kotlin
val timeUtils = TimeUtils()
ReachardDI.put(timeUtils)
```

or

```kotlin
ReachardDI.put(TimeUtils())
```

### Lazy Put

Putting all of your objects at the same time can cause performance issues. Or an object can have expensive constructor. `LazyPut` is here to help!

```kotlin
ReachardDI.lazyPut({ TimeUtils() })
```

The `TimeUtils` object will be created when you reach the it. Until that time, it won't be created. That is how lazy work.

### Key

Reachard throws an exception if you put the same instance again with the same key. But using different keys, you put instances of the same class as many as you want:

```kotlin
ReachardDI.put({ TimeUtils() })
ReachardDI.put({ TimeUtils() }, key = "myKey")
ReachardDI.lazyPut({ TimeUtils() }, key = "myLazyInstanceKey")
```

The `TimeUtils` object will be created when you reach it. Until that time, it won't be created. That is how lazy work.

### Put Conflict Strategy

You can select `putConflictStrategy`. There are 3 types of strategies: `CRASH`, `SKIP`, `UPDATE`. You can use these strategies for both `put` and `lazyPut`.

#### CRASH

```kotlin
val timeUtils1 = TimeUtils()
ReachardDI.put(timeUtils1, key = "myKey")

val timeUtils2 = TimeUtils()
ReachardDI.put(timeUtils2, key = "myKey", putConflictStrategy = PutConflictStrategy.CRASH)
// The app will crash.
```

#### SKIP

```kotlin
val timeUtils1 = TimeUtils()
ReachardDI.put(timeUtils1, key = "myKey")

val timeUtils2 = TimeUtils()
ReachardDI.put(timeUtils2, key = "myKey", putConflictStrategy = PutConflictStrategy.SKIP)
// timeUtils2 will not be added to the Reachard. timeUtils1 will continue to be used.
```

#### UPDATE

```kotlin
val timeUtils1 = TimeUtils()
ReachardDI.put(timeUtils1, key = "myKey")

val timeUtils2 = TimeUtils()
ReachardDI.put(timeUtils2, key = "myKey", putConflictStrategy = PutConflictStrategy.UPDATE)
// timeUtils1 will be removed from Reachard and timeUtils2 will be added.
```

#### Default Put Strategies

The default strategy `CRASH` for `put` and `UPDATE` for `lazyPut`. But instead of writing the strategy you want every time you put a variable, you can set default values:

```kotlin
ReachardDI.setDefaultValues(
    defaultPutConflictStrategy = PutConflictStrategy.UPDATE,
    defaultLazyPutConflictStrategy = PutConflictStrategy.CRASH
)
```

**It is not persistent. The default values are hold in a variable of an `object` type class in kotlin. When the app is closed those default values probably will return to its old values back.**

### Get

Now you can get your variable from everywhere you want as below:

```kotlin
ReachardDI.get<TimeUtils>()
```

If you put the instance with a key, it is like below:

```kotlin
ReachardDI.get<TimeUtils>(key = "myKey")
```

### Remove

You can remove an object as below:

```kotlin
ReachardDI.remove<TimeUtils>()
```

If you put the instance with a key, it is like below:

```kotlin
ReachardDI.remove<TimeUtils>(key = "myKey")
```

### Contains

You can check an object existance as below:

```kotlin
ReachardDI.contains<TimeUtils>()
```

If you put the instance with a key, it is like below:

```kotlin
ReachardDI.contains<TimeUtils>(key = "myKey")
```

### Reset

If you want to clear all instances from Reachard, just call the reset function:

```kotlin
ReachardDI.reset()
```

It clears variables you put normally or lazily, it also converts default values to `CRASH` for `put` and `UPDATE` for `lazyPut`.

## Pros vs Cons

So there are a lot of dependency injection (DI) libraries like dagger. Why should you use Reachard instead of it? It really depends on the project you work on. Let me mention about advantage and disadvantage of Reachard:

**Adtantage:** DI libraries, especially like Dagger, sometimes can be difficult to build in your project. But Reachard doesn't need any code to build. No factory creations, no annotations, no annotation processors etc. You just use `Reachard`
instance to manage your objects.

**Disadvantage:** You don't have a way to tell how to create your instances to Reachard unlike dagger. You have to actually create all of your objects by yourself and put it into the Reachard.
