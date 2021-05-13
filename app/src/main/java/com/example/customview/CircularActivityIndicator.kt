package com.example.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class CircularActivityIndicator @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val foregroundPaint: Paint = Paint().apply {
        color = DEFAULT_FG_COLOR
        style = Paint.Style.FILL
    }
    private val selectedAngle = 280

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawArc(
            0f,
            0f,
            width.toFloat(),
            height.toFloat(),
            0f,
            selectedAngle.toFloat(),
            true,
            foregroundPaint
        )
    }

    companion object {
        private const val DEFAULT_FG_COLOR = (0xffff0000).toInt()
        private const val DEFAULT_BG_COLOR = (0xffa0a0a0).toInt()
    }
}