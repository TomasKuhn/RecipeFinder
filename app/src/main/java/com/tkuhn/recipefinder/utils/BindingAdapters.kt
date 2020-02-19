package com.tkuhn.recipefinder.utils

import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.annotation.LayoutRes
import androidx.core.view.forEach
import androidx.core.widget.ContentLoadingProgressBar
import androidx.databinding.BindingAdapter
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout
import com.tkuhn.recipefinder.BR

@BindingAdapter("isVisible")
fun View.setIsVisible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

@BindingAdapter("isLoading")
fun ContentLoadingProgressBar.setIsLoading(isLoading: Boolean) {
    if (isLoading) show() else hide()
}

@BindingAdapter("isActivated")
fun setIsActivated(view: View, isActivated: Boolean) {
    view.isActivated = isActivated
}

@BindingAdapter("isEnabled")
fun setIsEnabled(view: View, isEnabled: Boolean) {
    view.isEnabled = isEnabled
    if (view is ViewGroup) {
        view.forEach {
            setIsEnabled(it, isEnabled)
        }
    }
}

@BindingAdapter("url")
fun setWebViewUrl(view: WebView, url: String?) {
    if (url != null) {
        view.loadUrl(url)
    }
}

@BindingAdapter("error")
fun setTextInputError(view: TextInputLayout, error: String?) {
    view.error = error
}

@BindingAdapter(value = ["items", "itemLayout", "scrollToBottom"], requireAll = false)
fun <T> setRecyclerViewItems(
    view: RecyclerView,
    items: List<T>?, @LayoutRes layoutId: Int,
    scrollToBottom: Boolean = false
) {
    if (items != null) {
        var currentAdapter = (view.adapter as? BindingRecyclerAdapter<T, *>)
        if (currentAdapter == null) {
            currentAdapter =
                BindingRecyclerAdapter(layoutId, LayoutBinder(), view.context as? LifecycleOwner)
            view.adapter = currentAdapter
        }
        currentAdapter.setItems(items)

        if (scrollToBottom) {
            view.scrollToBottom()
        }
    }
}

@BindingAdapter(value = ["idItems", "itemLayout", "scrollToBottom"], requireAll = false)
fun <T : Identifiable> setRecyclerViewIdItems(
    view: RecyclerView,
    items: List<T>?, @LayoutRes layoutId: Int,
    scrollToBottom: Boolean = false
) {
    if (items != null) {
        var currentAdapter = (view.adapter as? IdBindingRecyclerAdapter<T, *>)
        if (currentAdapter == null) {
            currentAdapter =
                IdBindingRecyclerAdapter(layoutId, LayoutBinder(), view.context as? LifecycleOwner)
            view.adapter = currentAdapter
        }
        currentAdapter.setItems(items)

        if (scrollToBottom) {
            view.scrollToBottom()
        }
    }
}

private class LayoutBinder<T> : DataBinder<T, ViewDataBinding>() {
    override fun bind(data: T, binding: ViewDataBinding) {
        binding.setVariable(BR.data, data)
    }
}
