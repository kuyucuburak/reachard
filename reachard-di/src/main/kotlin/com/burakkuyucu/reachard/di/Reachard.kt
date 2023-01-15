package com.burakkuyucu.reachard.di

import com.burakkuyucu.reachard.di.data.InstanceFactoryHolder
import com.burakkuyucu.reachard.di.data.InstanceHolder
import com.burakkuyucu.reachard.di.enum.PutConflictStrategy
import com.burakkuyucu.reachard.di.exception.NoReachardInstanceFoundException
import com.burakkuyucu.reachard.di.exception.ReachardInstanceAlreadyExistException
import com.burakkuyucu.reachard.di.util.InstanceFactory
import java.util.concurrent.CopyOnWriteArrayList

object Reachard {

    private val DEFAULT_PUT_CONFLICT_STRATEGY = PutConflictStrategy.CRASH
    private val DEFAULT_LAZY_PUT_CONFLICT_STRATEGY = PutConflictStrategy.UPDATE

    @PublishedApi internal val instanceHolderList: MutableList<InstanceHolder<out Any>> = CopyOnWriteArrayList()
    @PublishedApi internal val instanceFactoryHolderList: MutableList<InstanceFactoryHolder<out Any>> = CopyOnWriteArrayList()

    @PublishedApi internal var defaultPutConflictStrategy = DEFAULT_PUT_CONFLICT_STRATEGY
    @PublishedApi internal var defaultLazyPutConflictStrategy = DEFAULT_LAZY_PUT_CONFLICT_STRATEGY

    val instanceCount: Int
        get() = instanceHolderList.size

    val lazyInstanceCount: Int
        get() = instanceFactoryHolderList.size

    fun setDefaultValues(
        defaultPutConflictStrategy: PutConflictStrategy,
        defaultLazyPutConflictStrategy: PutConflictStrategy,
    ) {
        Reachard.defaultPutConflictStrategy = defaultPutConflictStrategy
        Reachard.defaultLazyPutConflictStrategy = defaultLazyPutConflictStrategy
    }

    inline fun <reified T : Any> put(
        instance: T,
        key: String? = null,
        putConflictStrategy: PutConflictStrategy = defaultPutConflictStrategy,
    ) {
        val instanceHolder = getInstanceHolder<T>(key)
        if (instanceHolder != null) {
            when (putConflictStrategy) {
                PutConflictStrategy.CRASH -> throw ReachardInstanceAlreadyExistException()
                PutConflictStrategy.SKIP -> return
                PutConflictStrategy.UPDATE -> {
                    instanceHolderList.remove(instanceHolder)

                    val newInstanceHolder = InstanceHolder(key, instance)
                    instanceHolderList.add(newInstanceHolder)
                }
            }

            return
        }

        val instanceFactoryHolder = getInstanceFactoryHolder<T>(key)
        if (instanceFactoryHolder != null) {
            when (putConflictStrategy) {
                PutConflictStrategy.CRASH -> throw ReachardInstanceAlreadyExistException()
                PutConflictStrategy.SKIP -> return
                PutConflictStrategy.UPDATE -> {
                    instanceFactoryHolderList.remove(instanceFactoryHolder)

                    val newInstanceHolder = InstanceHolder(key, instance)
                    instanceHolderList.add(newInstanceHolder)
                }
            }

            return
        }

        val newInstanceHolder = InstanceHolder(key, instance)
        instanceHolderList.add(newInstanceHolder)
    }

    inline fun <reified T : Any> lazyPut(
        noinline instanceFactory: InstanceFactory<T>,
        key: String? = null,
        putConflictStrategy: PutConflictStrategy = defaultLazyPutConflictStrategy,
    ) {
        val instanceHolder = getInstanceHolder<T>(key)
        if (instanceHolder != null) {
            when (putConflictStrategy) {
                PutConflictStrategy.CRASH -> throw ReachardInstanceAlreadyExistException()
                PutConflictStrategy.SKIP -> return
                PutConflictStrategy.UPDATE -> {
                    instanceHolderList.remove(instanceHolder)

                    val newInstanceFactoryHolder = InstanceFactoryHolder(key, instanceFactory)
                    instanceFactoryHolderList.add(newInstanceFactoryHolder)
                }
            }

            return
        }

        val instanceFactoryHolder = getInstanceFactoryHolder<T>(key)
        if (instanceFactoryHolder != null) {
            when (putConflictStrategy) {
                PutConflictStrategy.CRASH -> throw ReachardInstanceAlreadyExistException()
                PutConflictStrategy.SKIP -> return
                PutConflictStrategy.UPDATE -> {
                    instanceFactoryHolderList.remove(instanceFactoryHolder)

                    val newInstanceFactoryHolder = InstanceFactoryHolder(key, instanceFactory)
                    instanceFactoryHolderList.add(newInstanceFactoryHolder)
                }
            }

            return
        }

        val newInstanceFactoryHolder = InstanceFactoryHolder(key, instanceFactory)
        instanceFactoryHolderList.add(newInstanceFactoryHolder)
    }

    inline fun <reified T : Any> remove(key: String? = null) {
        val instance = getInstanceHolder<T>(key)
        if (instance != null) {
            instanceHolderList.remove(instance)
            return
        }

        val instanceFactory = getInstanceFactoryHolder<T>(key)
        if (instanceFactory != null) {
            instanceFactoryHolderList.remove(instanceFactory)
            return
        }

        throw NoReachardInstanceFoundException()
    }

    inline fun <reified T : Any> get(key: String? = null): T {
        val instance = getInstanceHolder<T>(key)
        if (instance != null) {
            return instance.instance
        }

        val instanceFactory = getInstanceFactoryHolder<T>(key)
        if (instanceFactory != null) {
            val newInstanceHolder = InstanceHolder(key, instanceFactory.instanceFactory())
            instanceFactoryHolderList.remove(instanceFactory)
            instanceHolderList.add(newInstanceHolder)
            return newInstanceHolder.instance
        }

        throw NoReachardInstanceFoundException()
    }

    inline fun <reified T : Any> contains(key: String? = null): Boolean {
        return getInstanceHolder<T>(key) != null || getInstanceFactoryHolder<T>(key) != null
    }

    fun reset() {
        defaultPutConflictStrategy = DEFAULT_PUT_CONFLICT_STRATEGY
        defaultLazyPutConflictStrategy = DEFAULT_LAZY_PUT_CONFLICT_STRATEGY

        instanceHolderList.clear()
        instanceFactoryHolderList.clear()
    }

    @PublishedApi
    internal inline fun <reified T : Any> getInstanceHolder(key: String?): InstanceHolder<T>? {
        return instanceHolderList
            .filterIsInstance<InstanceHolder<T>>()
            .firstOrNull { it.key == key }
    }

    @PublishedApi
    internal inline fun <reified T : Any> getInstanceFactoryHolder(key: String?): InstanceFactoryHolder<T>? {
        return instanceFactoryHolderList
            .filterIsInstance<InstanceFactoryHolder<T>>()
            .firstOrNull { it.key == key }
    }
}
