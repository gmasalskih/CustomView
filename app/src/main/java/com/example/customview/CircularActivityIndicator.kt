package com.example.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Region
import android.util.AttributeSet
import android.view.View
import kotlin.math.min

class CircularActivityIndicator @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val foregroundPaint: Paint = Paint().apply {
        color = DEFAULT_FG_COLOR
        style = Paint.Style.FILL
    }
    private val backgroundPaint:Paint = Paint().apply {
        color = DEFAULT_BG_COLOR
        style = Paint.Style.FILL
    }
    private val selectedAngle = 280

    private var clipPath: Path = Path()

    private var marker:Boolean = false

    override fun onDraw(canvas: Canvas?) {
        if (!marker) {
            val clipSize = ((min(width, height) / 2) * 0.75).toFloat()
            clipPath.addCircle(
                (min(width, height)/2).toFloat(),
                (min(width, height)/2).toFloat(),
                clipSize,
                Path.Direction.CCW
            )
            marker = !marker
        }
        canvas?.clipPath(clipPath, Region.Op.DIFFERENCE)
        canvas?.drawArc(
            0f,
            0f,
            min(width, height).toFloat(),
            min(width, height).toFloat(),
            0f,
            360f,
            true,
            backgroundPaint
        )
        canvas?.drawArc(
            0f,
            0f,
            min(width, height).toFloat(),
            min(width, height).toFloat(),
            0f,
            selectedAngle.toFloat(),
            true,
            foregroundPaint
        )
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
        private const val DEFAULT_FG_COLOR = (0xffff0000).toInt()
        private const val DEFAULT_BG_COLOR = (0xffa0a0a0).toInt()

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