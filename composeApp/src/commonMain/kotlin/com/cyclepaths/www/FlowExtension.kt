package com.cyclepaths.www

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

suspend fun <T> Flow<T>.getLastEmittedItem(): T? {
    var lastValue: T? = null

    val job = CoroutineScope(Dispatchers.Default).launch {
        collect { value ->
            lastValue = value
            cancel()
        }
    }
    job.join()
    return lastValue
}