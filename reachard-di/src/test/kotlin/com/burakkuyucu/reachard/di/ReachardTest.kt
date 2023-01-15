package com.burakkuyucu.reachard.di

import com.burakkuyucu.reachard.di.enum.PutConflictStrategy
import com.burakkuyucu.reachard.di.exception.NoReachardInstanceFoundException
import com.burakkuyucu.reachard.di.exception.ReachardInstanceAlreadyExistException
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ReachardTest {

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
        Reachard.put(fooNormal1, key = keyNormal1)
        Reachard.put(fooNormal2, key = keyNormal2)
        Reachard.lazyPut({ fooForLazy }, key = keyForLazy)
    }

    @After
    fun teardown() {
        Reachard.reset()
    }

    @Test
    fun defaultPutConflictStrategy() {
        assertEquals(Reachard.defaultPutConflictStrategy, PutConflictStrategy.CRASH)
    }

    @Test
    fun defaultLazyPutConflictStrategy() {
        assertEquals(Reachard.defaultLazyPutConflictStrategy, PutConflictStrategy.UPDATE)
    }

    @Test
    fun instanceCount() {
        assertEquals(Reachard.instanceCount, 2)
    }

    @Test
    fun lazyInstanceCount() {
        assertEquals(Reachard.lazyInstanceCount, 1)
    }

    @Test
    fun setDefaultValues() {
        Reachard.setDefaultValues(
            defaultPutConflictStrategy = PutConflictStrategy.UPDATE,
            defaultLazyPutConflictStrategy = PutConflictStrategy.CRASH
        )

        assertEquals(Reachard.defaultPutConflictStrategy, PutConflictStrategy.UPDATE)
        assertEquals(Reachard.defaultLazyPutConflictStrategy, PutConflictStrategy.CRASH)
    }

    @Test
    fun put_putConflictStrategyCrash_conflictWithNormalInstance() {
        try {
            Reachard.put(fooTest, key = keyNormal1, putConflictStrategy = PutConflictStrategy.CRASH)
            fail("This should fail!")
        } catch (e: ReachardInstanceAlreadyExistException) {
            // success
        }
    }

    @Test
    fun put_putConflictStrategySkip_conflictWithNormalInstance() {
        Reachard.put(fooTest, key = keyNormal1, putConflictStrategy = PutConflictStrategy.SKIP)

        assertEquals(Reachard.get<Foo>(key = keyNormal1), fooNormal1)
        assertNotEquals(Reachard.get<Foo>(key = keyNormal1), fooTest)
    }

    @Test
    fun put_putConflictStrategyUpdate_conflictWithNormalInstance() {
        Reachard.put(fooTest, key = keyNormal1, putConflictStrategy = PutConflictStrategy.UPDATE)

        assertNotEquals(Reachard.get<Foo>(key = keyNormal1), fooNormal1)
        assertEquals(Reachard.get<Foo>(key = keyNormal1), fooTest)
    }

    @Test
    fun put_putConflictStrategyCrash_conflictWithLazyInstance() {
        try {
            Reachard.put(fooTest, key = keyForLazy, putConflictStrategy = PutConflictStrategy.CRASH)
            fail("This should fail!")
        } catch (e: ReachardInstanceAlreadyExistException) {
            // success
        }
    }

    @Test
    fun put_putConflictStrategySkip_conflictWithLazyInstance() {
        Reachard.put(fooTest, key = keyForLazy, putConflictStrategy = PutConflictStrategy.SKIP)

        assertEquals(Reachard.get<Foo>(key = keyForLazy), fooForLazy)
        assertNotEquals(Reachard.get<Foo>(key = keyForLazy), fooTest)
    }

    @Test
    fun put_putConflictStrategyUpdate_conflictWithLazyInstance() {
        Reachard.put(fooTest, key = keyForLazy, putConflictStrategy = PutConflictStrategy.UPDATE)

        assertEquals(Reachard.instanceCount, 3)
        assertEquals(Reachard.lazyInstanceCount, 0)

        assertNotEquals(Reachard.get<Foo>(key = keyForLazy), fooForLazy)
        assertEquals(Reachard.get<Foo>(key = keyForLazy), fooTest)
    }

    @Test
    fun lazyPut_putConflictStrategyCrash_conflictWithLazyInstance() {
        try {
            Reachard.lazyPut({ fooTest }, key = keyForLazy, putConflictStrategy = PutConflictStrategy.CRASH)
            fail("This should fail!")
        } catch (e: ReachardInstanceAlreadyExistException) {
            // success
        }
    }

    @Test
    fun lazyPut_putConflictStrategySkip_conflictWithLazyInstance() {
        Reachard.lazyPut({ fooTest }, key = keyForLazy, putConflictStrategy = PutConflictStrategy.SKIP)

        assertEquals(Reachard.get<Foo>(key = keyForLazy), fooForLazy)
        assertNotEquals(Reachard.get<Foo>(key = keyForLazy), fooTest)
    }

    @Test
    fun lazyPut_putConflictStrategyUpdate_conflictWithLazyInstance() {
        Reachard.lazyPut({ fooTest }, key = keyForLazy, putConflictStrategy = PutConflictStrategy.UPDATE)

        assertNotEquals(Reachard.get<Foo>(key = keyForLazy), fooForLazy)
        assertEquals(Reachard.get<Foo>(key = keyForLazy), fooTest)
    }

    @Test
    fun lazyPut_putConflictStrategyCrash_conflictWithNormalInstance() {
        try {
            Reachard.lazyPut({ fooTest }, key = keyNormal1, putConflictStrategy = PutConflictStrategy.CRASH)
            fail("This should fail!")
        } catch (e: ReachardInstanceAlreadyExistException) {
            // success
        }
    }

    @Test
    fun lazyPut_putConflictStrategySkip_conflictWithNormalInstance() {
        Reachard.lazyPut({ fooTest }, key = keyNormal1, putConflictStrategy = PutConflictStrategy.SKIP)

        assertEquals(Reachard.get<Foo>(key = keyNormal1), fooNormal1)
        assertNotEquals(Reachard.get<Foo>(key = keyNormal1), fooTest)
    }

    @Test
    fun lazyPut_putConflictStrategyUpdate_conflictWithNormalInstance() {
        Reachard.lazyPut({ fooTest }, key = keyNormal1, putConflictStrategy = PutConflictStrategy.UPDATE)

        assertEquals(Reachard.instanceCount, 1)
        assertEquals(Reachard.lazyInstanceCount, 2)

        assertNotEquals(Reachard.get<Foo>(key = keyNormal1), fooNormal1)
        assertEquals(Reachard.get<Foo>(key = keyNormal1), fooTest)
    }

    @Test
    fun remove_normalInstance() {
        Reachard.remove<Foo>(keyNormal1)

        assertEquals(Reachard.instanceCount, 1)
        assertEquals(Reachard.lazyInstanceCount, 1)

        assertEquals(Reachard.contains<Foo>(key = keyNormal1), false)
    }

    @Test
    fun remove_lazyInstance() {
        Reachard.remove<Foo>(keyForLazy)

        assertEquals(Reachard.instanceCount, 2)
        assertEquals(Reachard.lazyInstanceCount, 0)

        assertEquals(Reachard.contains<Foo>(key = keyForLazy), false)
    }

    @Test
    fun get_normalInstance() {
        assertEquals(Reachard.get<Foo>(key = keyNormal1), fooNormal1)
        assertEquals(Reachard.instanceCount, 2)
        assertEquals(Reachard.lazyInstanceCount, 1)
    }

    @Test
    fun get_lazyInstance() {
        assertEquals(Reachard.get<Foo>(key = keyForLazy), fooForLazy)
        assertEquals(Reachard.instanceCount, 3)
        assertEquals(Reachard.lazyInstanceCount, 0)
    }

    @Test
    fun get_noReachardInstanceFoundException() {
        try {
            Reachard.get<Foo>(key = unknownKey)
            fail("This must fail!")
        } catch (e: NoReachardInstanceFoundException) {
            // success
        }
    }

    @Test
    fun contains_normalInstance() {
        assertEquals(Reachard.contains<Foo>(key = keyNormal1), true)
        assertEquals(Reachard.contains<Foo>(key = keyNormal2), true)
    }

    @Test
    fun contains_lazyInstance() {
        assertEquals(Reachard.contains<Foo>(key = keyForLazy), true)
    }

    @Test
    fun contains_notFound() {
        assertEquals(Reachard.contains<Foo>(), false)
    }

    @Test
    fun reset() {
        Reachard.reset()

        assertEquals(Reachard.defaultPutConflictStrategy, PutConflictStrategy.CRASH)
        assertEquals(Reachard.defaultLazyPutConflictStrategy, PutConflictStrategy.UPDATE)

        assertEquals(Reachard.instanceCount, 0)
        assertEquals(Reachard.lazyInstanceCount, 0)
    }
}
