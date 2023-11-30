package com.burakkuyucu.reachard.di.data

import com.burakkuyucu.reachard.di.util.InstanceFactory

@PublishedApi
internal data class InstanceFactoryHolderData<T : Any>(
    val key: String?,
    val instanceFactory: InstanceFactory<T>,
)
