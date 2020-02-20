package com.tkuhn.recipefinder.utils.extensions

import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.tkuhn.recipefinder.App

fun Int.toText(): String = App.instance.getString(this)

fun Int.toText(vararg formatArgs: Any): String = App.instance.getString(this, *formatArgs)

fun Int.toDrawable(): Drawable? {
    val context = App.instance
    return ContextCompat.getDrawable(context, this)
}