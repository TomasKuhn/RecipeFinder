package com.tkuhn.recipefinder.utils.extensions

import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<T>.setDistinctValue(newValue: T?) {
    if (value != newValue) {
        value = newValue
    }
}