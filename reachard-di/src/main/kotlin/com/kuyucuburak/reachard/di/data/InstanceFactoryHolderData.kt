package com.kuyucuburak.reachard.di.data

import com.kuyucuburak.reachard.di.utils.InstanceFactory

@PublishedApi
internal data class InstanceFactoryHolderData<T : Any>(
    val key: String?,
    val instanceFactory: InstanceFactory<T>,
)
