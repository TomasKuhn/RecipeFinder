package com.tkuhn.recipefinder

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import io.mockk.spyk
import io.mockk.verify

inline fun <reified T : Any> LiveData<T>.mockObserver(): Observer<T> {
    val mockObserver: Observer<T> = spyk(Observer { })
    observeForever(mockObserver)
    return mockObserver
}

inline fun <reified T : Any> Observer<T>.getValues(
    atLeast: Int = 1,
    atMost: Int = Int.MAX_VALUE,
    exactly: Int = -1,
    timeout: Long = 10
): List<T> {
    val results = mutableListOf<T>()
    verify(
        atLeast = atLeast,
        atMost = atMost,
        exactly = exactly,
        timeout = timeout
    ) { this@getValues.onChanged(capture(results)) }
    return results.toList()
}