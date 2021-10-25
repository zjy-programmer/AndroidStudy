package com.example.androidstudy.hencoderplus.chapter13

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View

/**
 * ProjectName: AndroidStudy
 * Package: com.example.androidstudy.hencoderplus.chapter13
 * ClassName: SportView
 * CreateDate: 2021/10/25 5:01 下午
 * Author: zjy
 * Description: java类作用描述
 */
class SportView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var centerX = 0f
    private var centerY = 0f
    private val str = "abab"
    private val rect = Rect()

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        centerX = (w / 2).toFloat()
        centerY = (h / 2).toFloat()
        paint.strokeCap = Paint.Cap.ROUND
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 50f
        paint.color = Color.GRAY
        paint.textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 100f, resources.displayMetrics)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawCircle(centerX, centerY, 400f, paint)
        paint.color = Color.MAGENTA
        canvas.drawArc(centerX - 400f, centerY - 400f, centerX + 400f, centerY + 400f, -90f, 240f, false, paint)
        paint.strokeWidth = 1f
        paint.style = Paint.Style.FILL
        paint.getTextBounds(str, 0, str.length, rect)
        val fontMetrics = paint.fontMetrics
        val realH = (fontMetrics.descent - fontMetrics.ascent) / 2
        canvas.drawText(str, centerX - rect.width() / 2, centerY + realH - fontMetrics.descent, paint)

    }
}