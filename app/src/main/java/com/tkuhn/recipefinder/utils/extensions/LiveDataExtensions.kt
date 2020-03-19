package com.tkuhn.recipefinder.utils.extensions

import androidx.lifecycle.MutableLiveData
import timber.log.Timber

fun <T> MutableLiveData<T>.setDistinctValue(newValue: T?) {
    if (value != newValue) {
        Timber.d("xxx set newValue $newValue")
        value = newValue
    }
}