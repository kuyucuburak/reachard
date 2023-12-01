package com.kuyucuburak.reachard.di

import com.kuyucuburak.reachard.di.data.InstanceFactoryHolderData
import com.kuyucuburak.reachard.di.data.InstanceHolderData
import com.kuyucuburak.reachard.di.enums.PutConflictStrategyEnums
import com.kuyucuburak.reachard.di.exception.NoReachardInstanceFoundException
import com.kuyucuburak.reachard.di.exception.ReachardInstanceAlreadyExistsException
import com.kuyucuburak.reachard.di.util.InstanceFactory
import java.util.concurrent.CopyOnWriteArrayList

object ReachardDI {

    private val DEFAULT_PUT_CONFLICT_STRATEGY = PutConflictStrategyEnums.CRASH
    private val DEFAULT_LAZY_PUT_CONFLICT_STRATEGY = PutConflictStrategyEnums.UPDATE

    @PublishedApi internal val instanceHolderList: MutableList<InstanceHolderData<out Any>> = CopyOnWriteArrayList()
    @PublishedApi internal val instanceFactoryHolderList: MutableList<InstanceFactoryHolderData<out Any>> = CopyOnWriteArrayList()

    @PublishedApi internal var defaultPutConflictStrategy = DEFAULT_PUT_CONFLICT_STRATEGY
    @PublishedApi internal var defaultLazyPutConflictStrategy = DEFAULT_LAZY_PUT_CONFLICT_STRATEGY

    val instanceCount: Int
        get() = instanceHolderList.size

    val lazyInstanceCount: Int
        get() = instanceFactoryHolderList.size

    fun setDefaultValues(
        defaultPutConflictStrategy: PutConflictStrategyEnums,
        defaultLazyPutConflictStrategy: PutConflictStrategyEnums,
    ) {
        ReachardDI.defaultPutConflictStrategy = defaultPutConflictStrategy
        ReachardDI.defaultLazyPutConflictStrategy = defaultLazyPutConflictStrategy
    }

    inline fun <reified T : Any> put(
        instance: T,
        key: String? = null,
        putConflictStrategy: PutConflictStrategyEnums = defaultPutConflictStrategy,
    ) {
        val instanceHolder = getInstanceHolder<T>(key)
        if (instanceHolder != null) {
            when (putConflictStrategy) {
                PutConflictStrategyEnums.CRASH -> throw ReachardInstanceAlreadyExistsException()
                PutConflictStrategyEnums.SKIP -> return
                PutConflictStrategyEnums.UPDATE -> {
                    instanceHolderList.remove(instanceHolder)

                    val newInstanceHolder = InstanceHolderData(key, instance)
                    instanceHolderList.add(newInstanceHolder)
                }
            }

            return
        }

        val instanceFactoryHolder = getInstanceFactoryHolder<T>(key)
        if (instanceFactoryHolder != null) {
            when (putConflictStrategy) {
                PutConflictStrategyEnums.CRASH -> throw ReachardInstanceAlreadyExistsException()
                PutConflictStrategyEnums.SKIP -> return
                PutConflictStrategyEnums.UPDATE -> {
                    instanceFactoryHolderList.remove(instanceFactoryHolder)

                    val newInstanceHolder = InstanceHolderData(key, instance)
                    instanceHolderList.add(newInstanceHolder)
                }
            }

            return
        }

        val newInstanceHolder = InstanceHolderData(key, instance)
        instanceHolderList.add(newInstanceHolder)
    }

    inline fun <reified T : Any> lazyPut(
        noinline instanceFactory: InstanceFactory<T>,
        key: String? = null,
        putConflictStrategy: PutConflictStrategyEnums = defaultLazyPutConflictStrategy,
    ) {
        val instanceHolder = getInstanceHolder<T>(key)
        if (instanceHolder != null) {
            when (putConflictStrategy) {
                PutConflictStrategyEnums.CRASH -> throw ReachardInstanceAlreadyExistsException()
                PutConflictStrategyEnums.SKIP -> return
                PutConflictStrategyEnums.UPDATE -> {
                    instanceHolderList.remove(instanceHolder)

                    val newInstanceFactoryHolder = InstanceFactoryHolderData(key, instanceFactory)
                    instanceFactoryHolderList.add(newInstanceFactoryHolder)
                }
            }

            return
        }

        val instanceFactoryHolder = getInstanceFactoryHolder<T>(key)
        if (instanceFactoryHolder != null) {
            when (putConflictStrategy) {
                PutConflictStrategyEnums.CRASH -> throw ReachardInstanceAlreadyExistsException()
                PutConflictStrategyEnums.SKIP -> return
                PutConflictStrategyEnums.UPDATE -> {
                    instanceFactoryHolderList.remove(instanceFactoryHolder)

                    val newInstanceFactoryHolder = InstanceFactoryHolderData(key, instanceFactory)
                    instanceFactoryHolderList.add(newInstanceFactoryHolder)
                }
            }

            return
        }

        val newInstanceFactoryHolder = InstanceFactoryHolderData(key, instanceFactory)
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
            val newInstanceHolder = InstanceHolderData(key, instanceFactory.instanceFactory())
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
    internal inline fun <reified T : Any> getInstanceHolder(key: String?): InstanceHolderData<T>? {
        return instanceHolderList
            .filterIsInstance<InstanceHolderData<T>>()
            .firstOrNull { it.key == key }
    }

    @PublishedApi
    internal inline fun <reified T : Any> getInstanceFactoryHolder(key: String?): InstanceFactoryHolderData<T>? {
        return instanceFactoryHolderList
            .filterIsInstance<InstanceFactoryHolderData<T>>()
            .firstOrNull { it.key == key }
    }
}
