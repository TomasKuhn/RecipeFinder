package com.tkuhn.recipefinder.utils.extensions

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.api.loadAny
import coil.request.LoadRequestBuilder
import com.google.android.material.snackbar.Snackbar
import com.tkuhn.recipefinder.R
import kotlin.math.max

fun Activity.hideKeyboard(view: View? = currentFocus, clearFocus: Boolean = false) {
    view?.let {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
        if (clearFocus) {
            it.clearFocus()
        }
    }
}

fun Fragment.hideKeyboard(view: View? = activity?.currentFocus, clearFocus: Boolean = false) {
    activity?.hideKeyboard(view, clearFocus)
}

fun View.showSnackbar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).show()
}

fun Fragment.showSnackbar(message: String) {
    view?.let { rootView ->
        Snackbar.make(rootView, message, Snackbar.LENGTH_LONG).show()
    }
}

fun RecyclerView.addDivider(divider: Drawable, skipAfterLast: Boolean = false) {
    addItemDecoration(com.tkuhn.recipefinder.utils.DividerItemDecoration(divider, skipAfterLast))
}

fun RecyclerView.addVerticalDivider() {
    addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
}

fun RecyclerView.scrollToTop() {
    adapter?.let {
        scrollToPosition(0)
    }
}

fun RecyclerView.scrollToBottom() {
    adapter?.let {
        scrollToPosition(max(0, it.itemCount - 1))
    }
}

fun RecyclerView.smoothScrollToTop() {
    adapter?.let {
        smoothScrollToPosition(0)
    }
}

fun RecyclerView.smoothScrollToBottom() {
    adapter?.let {
        smoothScrollToPosition(max(0, it.itemCount - 1))
    }
}

fun RecyclerView.setVerticalLayout() {
    layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
}

fun RecyclerView.setHorizontalLayout() {
    layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
}

fun Toolbar.addBackArrow() {
    setNavigationIcon(R.drawable.ic_arrow_back)
    setNavigationOnClickListener {
        findNavController().navigateUp()
    }
}

fun ImageView.setImageSource(
    data: Any?,
    fallback: Int? = null,
    builder: LoadRequestBuilder.() -> Unit = {}
) {
    // Use the placeholder as a fallback if data is null
    if (data != null) {
        this.loadAny(data) {
            fallback?.let { placeholder(fallback) }
            builder()
        }
    } else {
        fallback?.let {
            this.load(fallback) {
                builder()
            }
        }
    }
}
