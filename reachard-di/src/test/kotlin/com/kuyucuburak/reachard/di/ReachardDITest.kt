package com.kuyucuburak.reachard.di

import com.kuyucuburak.reachard.di.enums.PutConflictStrategyEnums
import com.kuyucuburak.reachard.di.exception.NoReachardInstanceFoundException
import com.kuyucuburak.reachard.di.exception.ReachardInstanceAlreadyExistsException
import org.junit.After
import org.junit.Assert.*
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
        assertEquals(ReachardDI.defaultPutConflictStrategy, PutConflictStrategyEnums.CRASH)
    }

    @Test
    fun defaultLazyPutConflictStrategy() {
        assertEquals(ReachardDI.defaultLazyPutConflictStrategy, PutConflictStrategyEnums.UPDATE)
    }

    @Test
    fun instanceCount() {
        assertEquals(ReachardDI.instanceCount, 2)
    }

    @Test
    fun lazyInstanceCount() {
        assertEquals(ReachardDI.lazyInstanceCount, 1)
    }

    @Test
    fun setDefaultValues() {
        ReachardDI.setDefaultValues(
            defaultPutConflictStrategy = PutConflictStrategyEnums.UPDATE,
            defaultLazyPutConflictStrategy = PutConflictStrategyEnums.CRASH,
        )

        assertEquals(ReachardDI.defaultPutConflictStrategy, PutConflictStrategyEnums.UPDATE)
        assertEquals(ReachardDI.defaultLazyPutConflictStrategy, PutConflictStrategyEnums.CRASH)
    }

    @Test
    fun put_putConflictStrategyCrash_conflictWithNormalInstance() {
        try {
            ReachardDI.put(fooTest, key = keyNormal1, putConflictStrategy = PutConflictStrategyEnums.CRASH)
            fail("This should fail!")
        } catch (e: ReachardInstanceAlreadyExistsException) {
            // success
        }
    }

    @Test
    fun put_putConflictStrategySkip_conflictWithNormalInstance() {
        ReachardDI.put(fooTest, key = keyNormal1, putConflictStrategy = PutConflictStrategyEnums.SKIP)

        assertEquals(ReachardDI.get<Foo>(key = keyNormal1), fooNormal1)
        assertNotEquals(ReachardDI.get<Foo>(key = keyNormal1), fooTest)
    }

    @Test
    fun put_putConflictStrategyUpdate_conflictWithNormalInstance() {
        ReachardDI.put(fooTest, key = keyNormal1, putConflictStrategy = PutConflictStrategyEnums.UPDATE)

        assertNotEquals(ReachardDI.get<Foo>(key = keyNormal1), fooNormal1)
        assertEquals(ReachardDI.get<Foo>(key = keyNormal1), fooTest)
    }

    @Test
    fun put_putConflictStrategyCrash_conflictWithLazyInstance() {
        try {
            ReachardDI.put(fooTest, key = keyForLazy, putConflictStrategy = PutConflictStrategyEnums.CRASH)
            fail("This should fail!")
        } catch (e: ReachardInstanceAlreadyExistsException) {
            // success
        }
    }

    @Test
    fun put_putConflictStrategySkip_conflictWithLazyInstance() {
        ReachardDI.put(fooTest, key = keyForLazy, putConflictStrategy = PutConflictStrategyEnums.SKIP)

        assertEquals(ReachardDI.get<Foo>(key = keyForLazy), fooForLazy)
        assertNotEquals(ReachardDI.get<Foo>(key = keyForLazy), fooTest)
    }

    @Test
    fun put_putConflictStrategyUpdate_conflictWithLazyInstance() {
        ReachardDI.put(fooTest, key = keyForLazy, putConflictStrategy = PutConflictStrategyEnums.UPDATE)

        assertEquals(ReachardDI.instanceCount, 3)
        assertEquals(ReachardDI.lazyInstanceCount, 0)

        assertNotEquals(ReachardDI.get<Foo>(key = keyForLazy), fooForLazy)
        assertEquals(ReachardDI.get<Foo>(key = keyForLazy), fooTest)
    }

    @Test
    fun lazyPut_putConflictStrategyCrash_conflictWithLazyInstance() {
        try {
            ReachardDI.lazyPut({ fooTest }, key = keyForLazy, putConflictStrategy = PutConflictStrategyEnums.CRASH)
            fail("This should fail!")
        } catch (e: ReachardInstanceAlreadyExistsException) {
            // success
        }
    }

    @Test
    fun lazyPut_putConflictStrategySkip_conflictWithLazyInstance() {
        ReachardDI.lazyPut({ fooTest }, key = keyForLazy, putConflictStrategy = PutConflictStrategyEnums.SKIP)

        assertEquals(ReachardDI.get<Foo>(key = keyForLazy), fooForLazy)
        assertNotEquals(ReachardDI.get<Foo>(key = keyForLazy), fooTest)
    }

    @Test
    fun lazyPut_putConflictStrategyUpdate_conflictWithLazyInstance() {
        ReachardDI.lazyPut({ fooTest }, key = keyForLazy, putConflictStrategy = PutConflictStrategyEnums.UPDATE)

        assertNotEquals(ReachardDI.get<Foo>(key = keyForLazy), fooForLazy)
        assertEquals(ReachardDI.get<Foo>(key = keyForLazy), fooTest)
    }

    @Test
    fun lazyPut_putConflictStrategyCrash_conflictWithNormalInstance() {
        try {
            ReachardDI.lazyPut({ fooTest }, key = keyNormal1, putConflictStrategy = PutConflictStrategyEnums.CRASH)
            fail("This should fail!")
        } catch (e: ReachardInstanceAlreadyExistsException) {
            // success
        }
    }

    @Test
    fun lazyPut_putConflictStrategySkip_conflictWithNormalInstance() {
        ReachardDI.lazyPut({ fooTest }, key = keyNormal1, putConflictStrategy = PutConflictStrategyEnums.SKIP)

        assertEquals(ReachardDI.get<Foo>(key = keyNormal1), fooNormal1)
        assertNotEquals(ReachardDI.get<Foo>(key = keyNormal1), fooTest)
    }

    @Test
    fun lazyPut_putConflictStrategyUpdate_conflictWithNormalInstance() {
        ReachardDI.lazyPut({ fooTest }, key = keyNormal1, putConflictStrategy = PutConflictStrategyEnums.UPDATE)

        assertEquals(ReachardDI.instanceCount, 1)
        assertEquals(ReachardDI.lazyInstanceCount, 2)

        assertNotEquals(ReachardDI.get<Foo>(key = keyNormal1), fooNormal1)
        assertEquals(ReachardDI.get<Foo>(key = keyNormal1), fooTest)
    }

    @Test
    fun remove_normalInstance() {
        ReachardDI.remove<Foo>(keyNormal1)

        assertEquals(ReachardDI.instanceCount, 1)
        assertEquals(ReachardDI.lazyInstanceCount, 1)

        assertEquals(ReachardDI.contains<Foo>(key = keyNormal1), false)
    }

    @Test
    fun remove_lazyInstance() {
        ReachardDI.remove<Foo>(keyForLazy)

        assertEquals(ReachardDI.instanceCount, 2)
        assertEquals(ReachardDI.lazyInstanceCount, 0)

        assertEquals(ReachardDI.contains<Foo>(key = keyForLazy), false)
    }

    @Test
    fun get_normalInstance() {
        assertEquals(ReachardDI.get<Foo>(key = keyNormal1), fooNormal1)
        assertEquals(ReachardDI.instanceCount, 2)
        assertEquals(ReachardDI.lazyInstanceCount, 1)
    }

    @Test
    fun get_lazyInstance() {
        assertEquals(ReachardDI.get<Foo>(key = keyForLazy), fooForLazy)
        assertEquals(ReachardDI.instanceCount, 3)
        assertEquals(ReachardDI.lazyInstanceCount, 0)
    }

    @Test
    fun get_noReachardInstanceFoundException() {
        try {
            ReachardDI.get<Foo>(key = unknownKey)
            fail("This must fail!")
        } catch (e: NoReachardInstanceFoundException) {
            // success
        }
    }

    @Test
    fun contains_normalInstance() {
        assertEquals(ReachardDI.contains<Foo>(key = keyNormal1), true)
        assertEquals(ReachardDI.contains<Foo>(key = keyNormal2), true)
    }

    @Test
    fun contains_lazyInstance() {
        assertEquals(ReachardDI.contains<Foo>(key = keyForLazy), true)
    }

    @Test
    fun contains_notFound() {
        assertEquals(ReachardDI.contains<Foo>(), false)
    }

    @Test
    fun reset() {
        ReachardDI.reset()

        assertEquals(ReachardDI.defaultPutConflictStrategy, PutConflictStrategyEnums.CRASH)
        assertEquals(ReachardDI.defaultLazyPutConflictStrategy, PutConflictStrategyEnums.UPDATE)

        assertEquals(ReachardDI.instanceCount, 0)
        assertEquals(ReachardDI.lazyInstanceCount, 0)
    }

    private class Foo

}
