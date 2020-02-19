package com.tkuhn.recipefinder.utils.databinding

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
import com.tkuhn.recipefinder.utils.Identifiable
import com.tkuhn.recipefinder.utils.extensions.scrollToBottom

@BindingAdapter("isVisible")
fun View.setIsVisible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

@BindingAdapter("isLoading")
fun ContentLoadingProgressBar.setIsLoading(isLoading: Boolean) {
    if (isLoading) show() else hide()
}

@BindingAdapter("isActivated")
fun View.setIsActivated(isActivated: Boolean) {
    this@setIsActivated.isActivated = isActivated
}

@BindingAdapter("isEnabled")
fun View.setIsEnabled(isEnabled: Boolean) {
    this@setIsEnabled.isEnabled = isEnabled
    if (this is ViewGroup) {
        forEach {
            setIsEnabled(isEnabled)
        }
    }
}

@BindingAdapter("url")
fun WebView.setWebViewUrl(url: String?) {
    if (url != null) {
        loadUrl(url)
    }
}

@BindingAdapter("error")
fun TextInputLayout.setTextInputError(error: String?) {
    this@setTextInputError.error = error
}

@BindingAdapter(value = ["items", "itemLayout", "scrollToBottom"], requireAll = false)
fun <T> RecyclerView.setRecyclerViewItems(
    items: List<T>?, @LayoutRes layoutId: Int,
    scrollToBottom: Boolean = false
) {
    if (items != null) {
        var currentAdapter = (adapter as? BindingRecyclerAdapter<T, *>)
        if (currentAdapter == null) {
            currentAdapter =
                BindingRecyclerAdapter(
                    layoutId,
                    LayoutBinder(),
                    context as? LifecycleOwner
                )
            adapter = currentAdapter
        }
        currentAdapter.setItems(items)

        if (scrollToBottom) {
            scrollToBottom()
        }
    }
}

@BindingAdapter(value = ["idItems", "itemLayout", "scrollToBottom"], requireAll = false)
fun <T : Identifiable> RecyclerView.setRecyclerViewIdItems(
    items: List<T>?, @LayoutRes layoutId: Int,
    scrollToBottom: Boolean = false
) {
    if (items != null) {
        var currentAdapter = (adapter as? IdBindingRecyclerAdapter<T, *>)
        if (currentAdapter == null) {
            currentAdapter = IdBindingRecyclerAdapter(
                layoutId,
                LayoutBinder(),
                context as? LifecycleOwner
            )
            adapter = currentAdapter
        }
        currentAdapter.setItems(items)

        if (scrollToBottom) {
            scrollToBottom()
        }
    }
}

private class LayoutBinder<T> : DataBinder<T, ViewDataBinding>() {
    override fun bind(data: T, binding: ViewDataBinding) {
        binding.setVariable(BR.data, data)
    }
}
