package com.tkuhn.recipefinder.utils.extensions

import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import timber.log.Timber

fun View.navigateTo(direction: NavDirections) {
    try {
        Navigation.findNavController(this).navigate(direction)
    } catch (e: IllegalArgumentException) {
        Timber.e(e.message ?: "IllegalArgumentException: navigateTo")
    }
}

fun Fragment.navigateTo(direction: NavDirections) {
    view?.navigateTo(direction)
}
