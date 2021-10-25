package com.example.androidstudy.hencoderplus.chapter12

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.example.androidstudy.R
import kotlin.math.min

/**
 * ProjectName: AndroidStudy
 * Package: com.example.androidstudy.hencoderplus.chapter12
 * ClassName: RoundAvatarView
 * CreateDate: 2021/10/25 3:40 下午
 * Author: zjy
 * Description: java类作用描述
 */
class RoundAvatarView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var centerX = 0f
    private var centerY = 0f
    private var radius = 200f
    private lateinit var bitmap: Bitmap
    private val path = Path()

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        centerX = (w / 2).toFloat()
        centerY = (h / 2).toFloat()
        bitmap = BitmapFactory.decodeResource(resources, R.mipmap.icon_amg_1)
        radius = min(bitmap.width, bitmap.height) / 2f
        path.addCircle(bitmap.width / 2f, bitmap.height / 2f, radius, Path.Direction.CW)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawCircle(centerX, centerY, radius + 20f, paint)
        canvas.save()
        canvas.translate(centerX - bitmap.width / 2, centerY - bitmap.height / 2)
        canvas.clipPath(path)
        canvas.drawBitmap(bitmap, 0f, 0f, paint)
        canvas.restore()
    }
}