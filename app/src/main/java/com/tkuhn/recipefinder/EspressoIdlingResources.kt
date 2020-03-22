package com.tkuhn.recipefinder

import androidx.test.espresso.idling.CountingIdlingResource

object EspressoIdlingResources {
    val resources = CountingIdlingResource("application-resources")

    fun increment() = resources.increment()

    fun decrement() = resources.decrement()
}