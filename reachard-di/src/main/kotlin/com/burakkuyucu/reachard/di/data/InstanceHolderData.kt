package com.burakkuyucu.reachard.di.data

@PublishedApi
internal data class InstanceHolderData<T : Any>(
    val key: String?,
    val instance: T,
)
