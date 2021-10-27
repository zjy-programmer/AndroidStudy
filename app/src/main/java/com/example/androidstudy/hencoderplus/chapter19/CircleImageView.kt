package com.example.androidstudy.hencoderplus.chapter19

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

/**
 * ProjectName: AndroidStudy
 * Package: com.example.androidstudy.hencoderplus.chapter19
 * ClassName: CircleImageView
 * CreateDate: 2021/10/27 6:14 下午
 * Author: zjy
 * Description:
 */
class CircleImageView(context: Context, attrs: AttributeSet?) : AppCompatImageView(context, attrs) {
    private val path = Path()
    override fun onDraw(canvas: Canvas) {
        path.addCircle(width / 2f, height / 2f, width / 2f, Path.Direction.CW)
        canvas.clipPath(path)
        super.onDraw(canvas)
    }
}