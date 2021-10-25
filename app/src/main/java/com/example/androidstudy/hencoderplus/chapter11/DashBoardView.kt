package com.example.androidstudy.hencoderplus.chapter11

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

/**
 * ProjectName: AndroidStudy
 * Package: com.example.androidstudy.hencoderplus.chapter11
 * ClassName: DashBoardView
 * CreateDate: 2021/10/24 12:09 下午
 * Author: zjy
 * Description: 仪表盘
 */
class DashBoardView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private var centerX = 0f
    private var centerY = 0f
    private val dashBoardRadius = 200f
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        centerX = (w / 2).toFloat()
        centerY = (h / 2).toFloat()
        val floats = floatArrayOf(10f, 20f)
        paint.pathEffect = DashPathEffect(floats, 0f)
        paint.color = Color.BLACK
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 10f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawCircle(centerX, centerY, dashBoardRadius, paint)
    }
}