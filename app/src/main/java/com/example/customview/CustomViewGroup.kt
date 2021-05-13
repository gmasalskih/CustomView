package com.example.customview

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import kotlin.math.min

class CustomViewGroup @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val count = childCount
        var left = l + paddingLeft
        var top = t + paddingTop
        var rowHeight = 0
        repeat(count) {
            val child = getChildAt(it)
            val childWidth = child.measuredWidth
            val childHeight = child.measuredHeight
            if (left + childWidth < r - paddingRight) {
                child.layout(left, top, left + childWidth, top + childHeight)
                left += childWidth
            } else {
                left = l + paddingLeft
                top += rowHeight
                rowHeight = 0
            }
            if (childHeight > rowHeight) rowHeight = childHeight
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var rowHeight = 0
        var maxWidth = 0
        var maxHeight = 0
        var left = 0
        var top = 0
        repeat(childCount) {
            val child = getChildAt(it)
            measureChild(child, widthMeasureSpec, heightMeasureSpec)
            val childWidth = child.measuredWidth
            val childHeight = child.measuredHeight
            if (left + childWidth < width) {
                left += childHeight
            } else {
                if (left > maxWidth) maxWidth = left
                left = 0
                top += rowHeight
                rowHeight = 0
            }
            if (childHeight > rowHeight) rowHeight = childHeight
        }
        if (left > maxWidth) maxWidth = left
        maxHeight = top + rowHeight
        setMeasuredDimension(
            getMeasure(widthMeasureSpec, maxWidth),
            getMeasure(heightMeasureSpec, maxHeight)
        )
    }

    private fun getMeasure(spec: Int, desired: Int) = when (MeasureSpec.getMode(spec)) {
        MeasureSpec.EXACTLY -> MeasureSpec.getSize(spec)
        MeasureSpec.AT_MOST -> min(MeasureSpec.getSize(spec), desired)
        else -> desired
    }
}