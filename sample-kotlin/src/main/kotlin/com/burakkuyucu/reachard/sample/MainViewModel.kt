package com.burakkuyucu.reachard.sample

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class MainViewModel {

    private val _counter: MutableLiveData<Int> = MutableLiveData(0)
    val counter: LiveData<Int>
        get() = _counter

    fun increaseCount() {
        _counter.value = (_counter.value ?: 0) + 1
    }
}
