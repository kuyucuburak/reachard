package com.burakkuyucu.reachard.di.data

@PublishedApi
internal data class InstanceHolder<T : Any>(
    val key: String?,
    val instance: T,
)
