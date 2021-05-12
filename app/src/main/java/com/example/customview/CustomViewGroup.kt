package com.example.customview

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import kotlin.math.min

class CustomViewGroup @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        val count = childCount
        var leftPadding = left + paddingLeft
        var topPadding = top + paddingTop
        var rowHeight = 0
        repeat(count) {
            getChildAt(it).apply {
                if (leftPadding + measuredWidth < right - paddingRight) {
                    layout(
                        leftPadding,
                        topPadding,
                        leftPadding + measuredWidth,
                        topPadding + measuredHeight
                    )
                    leftPadding += measuredWidth
                } else {
                    leftPadding = left + paddingLeft
                    topPadding = top + paddingTop
                    rowHeight = 0
                }
                if (measuredHeight > rowHeight) rowHeight = measuredHeight
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var rowHeight = 0
        var maxWidth = 0
        var maxHeight = 0
        var left = 0
        var top = 0
        repeat(childCount) {
            getChildAt(it).apply {
                if (left + measuredWidth < width) {
                    left += measuredWidth
                } else {
                    if (left > maxWidth) maxWidth = left
                    left = 0
                    top += rowHeight
                    rowHeight = 0
                }
                if (measuredHeight > rowHeight) rowHeight = measuredHeight
            }
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