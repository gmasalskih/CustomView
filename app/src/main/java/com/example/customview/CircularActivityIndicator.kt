package com.example.customview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import kotlin.math.min

class CircularActivityIndicator @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val backgroundBitmap: Bitmap =
        BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)
    private val bitmapSource = Rect().apply {
        top = 0
        left = 0
        right = backgroundBitmap.width
        bottom = backgroundBitmap.height
    }
    private val bitmapDest = Rect()

    init {
        isClickable = true
    }

    private val foregroundPaint: Paint = Paint().apply {
        isAntiAlias = false
        color = DEFAULT_FG_COLOR
        style = Paint.Style.FILL
        strokeWidth = 2f
        strokeCap = Paint.Cap.BUTT
        textSize = 100f
        textScaleX= 5f
    }
    private val backgroundPaint: Paint = Paint().apply {
        color = DEFAULT_BG_COLOR
        style = Paint.Style.FILL
    }
    private val selectedAngle = 280

    private var clipPath: Path = Path()

    private var marker: Boolean = false

    private var clipSize = 0f
    private var centerPoint = 0f
    private var viewSizeX = 0f
    private var viewSizeY = 0f

    private var deltaX = 0f
    private var deltaY = 0f

    private val gestureDetector =
        GestureDetector(context, object : GestureDetector.OnGestureListener {
            override fun onDown(e: MotionEvent?): Boolean {
                TODO("Not yet implemented")
            }

            override fun onShowPress(e: MotionEvent?) {
                TODO("Not yet implemented")
            }

            override fun onSingleTapUp(e: MotionEvent?): Boolean {
                TODO("Not yet implemented")
            }

            override fun onScroll(
                e1: MotionEvent?,
                e2: MotionEvent?,
                distanceX: Float,
                distanceY: Float
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onLongPress(e: MotionEvent?) {
                TODO("Not yet implemented")
            }

            override fun onFling(
                e1: MotionEvent?,
                e2: MotionEvent?,
                velocityX: Float,
                velocityY: Float
            ): Boolean {
                TODO("Not yet implemented")
            }

        })

    override fun onDraw(canvas: Canvas?) {
//        if (!marker) {
//            clipPath.addCircle(
//                centerPoint,
//                centerPoint,
//                clipSize,
//                Path.Direction.CCW
//            )
//            marker = !marker
//        }
//        canvas?.clipPath(clipPath, Region.Op.DIFFERENCE)
        canvas?.apply {
//            drawArc(
//                0f + deltaX,
//                0f + deltaY,
//                viewSizeX + deltaX,
//                viewSizeY + deltaY,
//                0f,
//                360f,
//                true,
//                backgroundPaint
//            )
//            drawArc(
//                0f + deltaX,
//                0f + deltaY,
//                viewSizeX + deltaX,
//                viewSizeY + deltaY,
//                0f,
//                selectedAngle.toFloat(),
//                true,
//                foregroundPaint
//            )
//            drawLine(
//                50f,
//                50f,
//                width.toFloat() -50f,
//                height.toFloat()-50f,
//                foregroundPaint
//            )
//            val p = Path().apply {
//                addRoundRect(
//                    RectF(
//                        0f,
//                        0f,
//                        width.toFloat(),
//                        height.toFloat()
//                    ),
//                    floatArrayOf(
//                        100f,100f,
//                        0f,0f,
//                        0f,0f,
//                        0f,0f
//                    ),
//                    Path.Direction.CW
//                )
//            }
//            drawPath(p, foregroundPaint)
            drawText("sadad", 100f,100f,foregroundPaint)
//            bitmapDest.right = min(width, height)
//            bitmapDest.bottom = min(width, height)
//            drawBitmap(
//                backgroundBitmap,
//                bitmapSource,
//                bitmapDest,
//                null
//            )
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(
            getMeasurementSize(widthMeasureSpec, 100.dpToPx()),
            getMeasurementSize(heightMeasureSpec, 100.dpToPx())
        )
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        viewSizeX = min(w, h).toFloat()
        viewSizeY = min(w, h).toFloat()
        clipSize = (min(w, h) / 2.0 * 0.75).toFloat()
        centerPoint = (min(w, h) / 2.0).toFloat()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
//        return gestureDetector.onTouchEvent(event)
        Log.d("---", "${event?.rawX}")
        MotionEvent.ACTION_UP
        deltaX = event?.x!! - width / 2
        deltaY = event?.y!! - height / 2
//        viewSizeY = event?.rawY!!
        invalidate()
        return super.onTouchEvent(event)
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