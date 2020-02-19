package com.tkuhn.recipefinder.utils

import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.RecyclerView

class DividerItemDecoration(
    private val divider: Drawable,
    private val skipAfterLast: Boolean = false
) : RecyclerView.ItemDecoration() {

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight

        val childCount = parent.childCount - if (skipAfterLast) 1 else 0
        (0 until childCount).forEach { childPosition ->
            val child = parent.getChildAt(childPosition)
            val lp = child.layoutParams as RecyclerView.LayoutParams

            val top = child.bottom + lp.bottomMargin
            val bottom = top + divider.intrinsicHeight

            divider.setBounds(left, top, right, bottom)
            divider.draw(c)
        }
    }
}