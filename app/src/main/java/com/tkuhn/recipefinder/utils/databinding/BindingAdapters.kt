package com.tkuhn.recipefinder.utils.databinding

import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.core.text.HtmlCompat
import androidx.core.view.forEach
import androidx.core.widget.ContentLoadingProgressBar
import androidx.databinding.BindingAdapter
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import coil.transform.CircleCropTransformation
import com.google.android.material.textfield.TextInputLayout
import com.tkuhn.recipefinder.BR
import com.tkuhn.recipefinder.utils.Identifiable
import com.tkuhn.recipefinder.utils.extensions.scrollToBottom
import com.tkuhn.recipefinder.utils.extensions.setImageSource

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

@BindingAdapter("htmlText")
fun TextView.setHtmlText(htmlText: String?) {
    text = htmlText?.let { HtmlCompat.fromHtml(it, HtmlCompat.FROM_HTML_MODE_LEGACY) }
}

@BindingAdapter(value = ["imageSource", "placeHolder", "circleCrop"], requireAll = false)
fun ImageView.setImageSource(data: Any?, placeHolder: Int?, circleCrop: Boolean = false) {
    setImageSource(data, placeHolder) {
        if (circleCrop) {
            transformations(CircleCropTransformation())
        }
    }
}

@BindingAdapter(value = ["items", "itemMapper", "itemLayout", "scrollToBottom", "onItemClick"], requireAll = false)
fun <ITEM, VIEW> RecyclerView.setRecyclerViewItems(
    items: List<ITEM>?,
    itemMapper: (ITEM) -> VIEW,
    @LayoutRes layoutId: Int,
    scrollToBottom: Boolean = false,
    onItemClick: ((ITEM) -> Unit)?
) {
    if (items != null) {
        var currentAdapter = (adapter as? BindingRecyclerAdapter<VIEW, *>)
        if (currentAdapter == null) {
            currentAdapter = BindingRecyclerAdapter(
                layoutId,
                LayoutBinder(),
                context as? LifecycleOwner
            ) { pos ->
                onItemClick?.invoke(items[pos])
            }
            adapter = currentAdapter
        }
        val viewItems = items.map { itemMapper(it) }
        currentAdapter.setItems(viewItems)

        if (scrollToBottom) {
            scrollToBottom()
        }
    }
}

@BindingAdapter(value = ["idItems", "itemMapper", "itemLayout", "scrollToBottom", "onItemClick"], requireAll = false)
fun <ITEM, VIEW : Identifiable> RecyclerView.setRecyclerViewIdItems(
    items: List<ITEM>?,
    itemMapper: (ITEM) -> VIEW,
    @LayoutRes layoutId: Int,
    scrollToBottom: Boolean = false,
    onItemClick: ((ITEM) -> Unit)?
) {
    if (items != null) {
        var currentAdapter = (adapter as? IdBindingRecyclerAdapter<VIEW, *>)
        if (currentAdapter == null) {
            currentAdapter = IdBindingRecyclerAdapter(
                layoutId,
                LayoutBinder(),
                context as? LifecycleOwner
            ) { pos ->
                onItemClick?.invoke(items[pos])
            }
            adapter = currentAdapter
        }
        val viewItems = items.map { itemMapper(it) }
        currentAdapter.setItems(viewItems)

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
