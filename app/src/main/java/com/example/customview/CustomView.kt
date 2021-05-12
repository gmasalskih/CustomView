package com.example.customview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import kotlin.math.min
import kotlin.properties.Delegates

class CustomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint()
    private var bgColor by Delegates.notNull<Int>()
    private val ta = context.theme.obtainStyledAttributes(
        attrs,
        R.styleable.CustomView,
        defStyleAttr,
        0
    )

    val colorArray = IntArray(5).apply {
        set(0, (0xff000000).toInt())
        set(1, (0xffFF0000).toInt())
        set(2, (0xff00FF00).toInt())
        set(3, (0xff0000FF).toInt())
        set(4, (0xffFFFFFF).toInt())
    }

    init {
        with(ta) {
            if (hasValue(R.styleable.CustomView_bgc)) {
                try {
                    bgColor = getColor(R.styleable.CustomView_bgc, Color.YELLOW)
                } finally {
                    recycle()
                }
            }
        }

        paint.color = bgColor
        paint.style = Paint.Style.FILL
    }

    fun setBG(@ColorInt color: Int) {
        paint.color = color
    }

    override fun onDraw(canvas: Canvas?) {
        val lg = LinearGradient(
            0f,
            0f,
            width.toFloat(),
            height.toFloat(),
            colorArray,
            null,
            Shader.TileMode.REPEAT
        )
        paint.setShader(lg)
        canvas?.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)
        super.onDraw(canvas)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(
            getMeasurementSize(widthMeasureSpec, 100.dpToPx()),
            getMeasurementSize(heightMeasureSpec, 100.dpToPx())
        )
    }

    private fun Int.dpToPx(): Int = (this * resources.displayMetrics.density + 0.5).toInt()
    private fun Int.pxToDp(): Int = (this / resources.displayMetrics.density + 0.5).toInt()

    companion object {
        private fun getMeasurementSize(measureSpec: Int, defaultSize: Int): Int {
            val mode = MeasureSpec.getMode(measureSpec)
            val size = MeasureSpec.getSize(measureSpec)
            return when (mode) {
                MeasureSpec.EXACTLY -> size
                MeasureSpec.AT_MOST -> min(defaultSize, size)
                else -> defaultSize
            }
        }
    }
}