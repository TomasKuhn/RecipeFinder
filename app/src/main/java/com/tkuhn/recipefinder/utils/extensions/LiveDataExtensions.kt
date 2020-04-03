package com.tkuhn.recipefinder.utils.extensions

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take

fun <T> MutableLiveData<T>.setDistinctValue(newValue: T?) {
    if (value != newValue) {
        value = newValue
    }
}

suspend fun <T> Flow<T>.takeFirstOrNull(): T {
    return take(1).first()
}