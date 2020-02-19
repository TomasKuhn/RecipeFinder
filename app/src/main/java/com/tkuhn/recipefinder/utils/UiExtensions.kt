package com.tkuhn.recipefinder.utils

import android.graphics.drawable.Drawable
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.tkuhn.recipefinder.R
import kotlin.math.max

fun View.showSnackbar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).show()
}

fun Fragment.showSnackbar(message: String) {
    view?.let { rootView ->
        Snackbar.make(rootView, message, Snackbar.LENGTH_LONG).show()
    }
}

fun RecyclerView.addDivider(divider: Drawable, skipAfterLast: Boolean = false) {
    addItemDecoration(DividerItemDecoration(divider, skipAfterLast))
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