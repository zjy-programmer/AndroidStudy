package com.example.androidstudy.hencoderplus.chapter11

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

/**
 * ProjectName: AndroidStudy
 * Package: com.example.androidstudy.hencoderplus.chapter11
 * ClassName: PieChartView
 * CreateDate: 2021/10/25 3:09 下午
 * Author: zjy
 * Description: java类作用描述
 */
class PieChartView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var centerX = 0f
    private var centerY = 0f
    private var radius = 400f
    private var rectf = RectF()

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        centerX = (w / 2).toFloat()
        centerY = (h / 2).toFloat()
        rectf = RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint.color = Color.YELLOW
        canvas.drawArc(rectf, 0f, -80f, true, paint)
        paint.color = Color.BLUE
        canvas.drawArc(rectf, 0f, 60f, true, paint)
        paint.color = Color.RED
        canvas.drawArc(rectf, 60f, 100f, true, paint)
        paint.color = Color.GREEN
        val offset = 50f

        canvas.drawArc(rectf, 160f, 120f, true, paint)
    }
}