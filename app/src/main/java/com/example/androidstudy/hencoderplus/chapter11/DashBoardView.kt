package com.example.androidstudy.hencoderplus.chapter11

import android.content.Context
import android.graphics.*
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
    private var dashBoardRadius = 400f
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val path = Path()
    private val pathMeasure = PathMeasure()

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        centerX = (w / 2).toFloat()
        centerY = (h / 2).toFloat()
        dashBoardRadius = centerX - 50

        path.addArc(centerX - dashBoardRadius,
            centerY - dashBoardRadius,
            centerX + dashBoardRadius,
            centerY + dashBoardRadius,
            150f,
            250f)

        pathMeasure.setPath(path, false)
        val floats = floatArrayOf(10f, (pathMeasure.length - 21 * 10f) / 20)
        paint.pathEffect = DashPathEffect(floats, 0f)
        paint.color = Color.BLACK
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 40f

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawPath(path, paint)
        paint.pathEffect = null
        paint.strokeWidth = 5f
        canvas.drawArc(centerX - dashBoardRadius - 20,
            centerY - dashBoardRadius - 20,
            centerX + dashBoardRadius + 20,
            centerY + dashBoardRadius + 20,
            150f,
            250f,
            false,
            paint)
        canvas.drawLine(centerX, centerY, centerX - 300, centerY - 60, paint)
    }
}