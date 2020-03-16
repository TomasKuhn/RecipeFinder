package com.tkuhn.recipefinder

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.tkuhn.recipefinder.utils.extensions.toText
import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.spyk
import io.mockk.verify

inline fun <reified T : Any> LiveData<T>.mockObserver(): Observer<T> {
    val mockObserver: Observer<T> = spyk(Observer { })
    observeForever(mockObserver)
    return mockObserver
}

inline fun <reified T : Any> Observer<T>.getValues(): List<T> {
    val results = mutableListOf<T>()
    verify { this@getValues.onChanged(capture(results)) }
    return results.toList()
}

fun Int.mockResource(mockValue: String) {
    mockkStatic("com.tkuhn.recipefinder.utils.extensions.ResourcesExtensionsKt")
    every {
        toText()
    } returns mockValue
}