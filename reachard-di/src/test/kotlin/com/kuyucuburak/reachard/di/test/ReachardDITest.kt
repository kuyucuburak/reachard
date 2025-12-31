package com.kuyucuburak.reachard.di.test

import com.kuyucuburak.reachard.di.ReachardDI
import com.kuyucuburak.reachard.di.enums.PutConflictStrategyEnums
import com.kuyucuburak.reachard.di.exception.NoReachardInstanceFoundException
import com.kuyucuburak.reachard.di.exception.ReachardInstanceAlreadyExistsException
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ReachardDITest {

    private val fooNormal1 = Foo()
    private val fooNormal2 = Foo()
    private val fooForLazy = Foo()
    private val fooTest = Foo()

    private val keyNormal1 = "key_normal_1"
    private val keyNormal2 = "key_normal_2"
    private val keyForLazy = "key_for_lazy"
    private val unknownKey = "unknown_key"

    @Before
    fun setup() {
        ReachardDI.put(fooNormal1, key = keyNormal1)
        ReachardDI.put(fooNormal2, key = keyNormal2)
        ReachardDI.lazyPut({ fooForLazy }, key = keyForLazy)
    }

    @After
    fun teardown() {
        ReachardDI.reset()
    }

    @Test
    fun defaultPutConflictStrategy() {
        Assert.assertEquals(ReachardDI.defaultPutConflictStrategy, PutConflictStrategyEnums.CRASH)
    }

    @Test
    fun defaultLazyPutConflictStrategy() {
        Assert.assertEquals(ReachardDI.defaultLazyPutConflictStrategy, PutConflictStrategyEnums.UPDATE)
    }

    @Test
    fun instanceCount() {
        Assert.assertEquals(ReachardDI.instanceCount, 2)
    }

    @Test
    fun lazyInstanceCount() {
        Assert.assertEquals(ReachardDI.lazyInstanceCount, 1)
    }

    @Test
    fun setDefaultValues() {
        ReachardDI.setDefaultValues(
            defaultPutConflictStrategy = PutConflictStrategyEnums.UPDATE,
            defaultLazyPutConflictStrategy = PutConflictStrategyEnums.CRASH,
        )

        Assert.assertEquals(ReachardDI.defaultPutConflictStrategy, PutConflictStrategyEnums.UPDATE)
        Assert.assertEquals(ReachardDI.defaultLazyPutConflictStrategy, PutConflictStrategyEnums.CRASH)
    }

    @Test
    fun put_putConflictStrategyCrash_conflictWithNormalInstance() {
        try {
            ReachardDI.put(fooTest, key = keyNormal1, putConflictStrategy = PutConflictStrategyEnums.CRASH)
            Assert.fail("This should fail!")
        } catch (_: ReachardInstanceAlreadyExistsException) {
            // success
        }
    }

    @Test
    fun put_putConflictStrategySkip_conflictWithNormalInstance() {
        ReachardDI.put(fooTest, key = keyNormal1, putConflictStrategy = PutConflictStrategyEnums.SKIP)

        Assert.assertEquals(ReachardDI.get<Foo>(key = keyNormal1), fooNormal1)
        Assert.assertNotEquals(ReachardDI.get<Foo>(key = keyNormal1), fooTest)
    }

    @Test
    fun put_putConflictStrategyUpdate_conflictWithNormalInstance() {
        ReachardDI.put(fooTest, key = keyNormal1, putConflictStrategy = PutConflictStrategyEnums.UPDATE)

        Assert.assertNotEquals(ReachardDI.get<Foo>(key = keyNormal1), fooNormal1)
        Assert.assertEquals(ReachardDI.get<Foo>(key = keyNormal1), fooTest)
    }

    @Test
    fun put_putConflictStrategyCrash_conflictWithLazyInstance() {
        try {
            ReachardDI.put(fooTest, key = keyForLazy, putConflictStrategy = PutConflictStrategyEnums.CRASH)
            Assert.fail("This should fail!")
        } catch (_: ReachardInstanceAlreadyExistsException) {
            // success
        }
    }

    @Test
    fun put_putConflictStrategySkip_conflictWithLazyInstance() {
        ReachardDI.put(fooTest, key = keyForLazy, putConflictStrategy = PutConflictStrategyEnums.SKIP)

        Assert.assertEquals(ReachardDI.get<Foo>(key = keyForLazy), fooForLazy)
        Assert.assertNotEquals(ReachardDI.get<Foo>(key = keyForLazy), fooTest)
    }

    @Test
    fun put_putConflictStrategyUpdate_conflictWithLazyInstance() {
        ReachardDI.put(fooTest, key = keyForLazy, putConflictStrategy = PutConflictStrategyEnums.UPDATE)

        Assert.assertEquals(ReachardDI.instanceCount, 3)
        Assert.assertEquals(ReachardDI.lazyInstanceCount, 0)

        Assert.assertNotEquals(ReachardDI.get<Foo>(key = keyForLazy), fooForLazy)
        Assert.assertEquals(ReachardDI.get<Foo>(key = keyForLazy), fooTest)
    }

    @Test
    fun lazyPut_putConflictStrategyCrash_conflictWithLazyInstance() {
        try {
            ReachardDI.lazyPut({ fooTest }, key = keyForLazy, putConflictStrategy = PutConflictStrategyEnums.CRASH)
            Assert.fail("This should fail!")
        } catch (_: ReachardInstanceAlreadyExistsException) {
            // success
        }
    }

    @Test
    fun lazyPut_putConflictStrategySkip_conflictWithLazyInstance() {
        ReachardDI.lazyPut({ fooTest }, key = keyForLazy, putConflictStrategy = PutConflictStrategyEnums.SKIP)

        Assert.assertEquals(ReachardDI.get<Foo>(key = keyForLazy), fooForLazy)
        Assert.assertNotEquals(ReachardDI.get<Foo>(key = keyForLazy), fooTest)
    }

    @Test
    fun lazyPut_putConflictStrategyUpdate_conflictWithLazyInstance() {
        ReachardDI.lazyPut({ fooTest }, key = keyForLazy, putConflictStrategy = PutConflictStrategyEnums.UPDATE)

        Assert.assertNotEquals(ReachardDI.get<Foo>(key = keyForLazy), fooForLazy)
        Assert.assertEquals(ReachardDI.get<Foo>(key = keyForLazy), fooTest)
    }

    @Test
    fun lazyPut_putConflictStrategyCrash_conflictWithNormalInstance() {
        try {
            ReachardDI.lazyPut({ fooTest }, key = keyNormal1, putConflictStrategy = PutConflictStrategyEnums.CRASH)
            Assert.fail("This should fail!")
        } catch (_: ReachardInstanceAlreadyExistsException) {
            // success
        }
    }

    @Test
    fun lazyPut_putConflictStrategySkip_conflictWithNormalInstance() {
        ReachardDI.lazyPut({ fooTest }, key = keyNormal1, putConflictStrategy = PutConflictStrategyEnums.SKIP)

        Assert.assertEquals(ReachardDI.get<Foo>(key = keyNormal1), fooNormal1)
        Assert.assertNotEquals(ReachardDI.get<Foo>(key = keyNormal1), fooTest)
    }

    @Test
    fun lazyPut_putConflictStrategyUpdate_conflictWithNormalInstance() {
        ReachardDI.lazyPut({ fooTest }, key = keyNormal1, putConflictStrategy = PutConflictStrategyEnums.UPDATE)

        Assert.assertEquals(ReachardDI.instanceCount, 1)
        Assert.assertEquals(ReachardDI.lazyInstanceCount, 2)

        Assert.assertNotEquals(ReachardDI.get<Foo>(key = keyNormal1), fooNormal1)
        Assert.assertEquals(ReachardDI.get<Foo>(key = keyNormal1), fooTest)
    }

    @Test
    fun remove_normalInstance() {
        ReachardDI.remove<Foo>(keyNormal1)

        Assert.assertEquals(ReachardDI.instanceCount, 1)
        Assert.assertEquals(ReachardDI.lazyInstanceCount, 1)

        Assert.assertEquals(ReachardDI.contains<Foo>(key = keyNormal1), false)
    }

    @Test
    fun remove_lazyInstance() {
        ReachardDI.remove<Foo>(keyForLazy)

        Assert.assertEquals(ReachardDI.instanceCount, 2)
        Assert.assertEquals(ReachardDI.lazyInstanceCount, 0)

        Assert.assertEquals(ReachardDI.contains<Foo>(key = keyForLazy), false)
    }

    @Test
    fun get_normalInstance() {
        Assert.assertEquals(ReachardDI.get<Foo>(key = keyNormal1), fooNormal1)
        Assert.assertEquals(ReachardDI.instanceCount, 2)
        Assert.assertEquals(ReachardDI.lazyInstanceCount, 1)
    }

    @Test
    fun get_lazyInstance() {
        Assert.assertEquals(ReachardDI.get<Foo>(key = keyForLazy), fooForLazy)
        Assert.assertEquals(ReachardDI.instanceCount, 3)
        Assert.assertEquals(ReachardDI.lazyInstanceCount, 0)
    }

    @Test
    fun get_noReachardInstanceFoundException() {
        try {
            ReachardDI.get<Foo>(key = unknownKey)
            Assert.fail("This must fail!")
        } catch (_: NoReachardInstanceFoundException) {
            // success
        }
    }

    @Test
    fun contains_normalInstance() {
        Assert.assertEquals(ReachardDI.contains<Foo>(key = keyNormal1), true)
        Assert.assertEquals(ReachardDI.contains<Foo>(key = keyNormal2), true)
    }

    @Test
    fun contains_lazyInstance() {
        Assert.assertEquals(ReachardDI.contains<Foo>(key = keyForLazy), true)
    }

    @Test
    fun contains_notFound() {
        Assert.assertEquals(ReachardDI.contains<Foo>(), false)
    }

    @Test
    fun reset() {
        ReachardDI.reset()

        Assert.assertEquals(ReachardDI.defaultPutConflictStrategy, PutConflictStrategyEnums.CRASH)
        Assert.assertEquals(ReachardDI.defaultLazyPutConflictStrategy, PutConflictStrategyEnums.UPDATE)

        Assert.assertEquals(ReachardDI.instanceCount, 0)
        Assert.assertEquals(ReachardDI.lazyInstanceCount, 0)
    }

    private class Foo

}
