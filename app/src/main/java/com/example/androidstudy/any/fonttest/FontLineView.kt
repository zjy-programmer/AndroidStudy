package com.example.androidstudy.any.fonttest

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import com.example.baselibrary.util.LogUtil
import com.example.baselibrary.util.WidgetUtil

/**
 * ProjectName: AndroidStudy
 * Package: com.example.androidstudy.any.fonttest
 * ClassName: FontLineView
 * CreateDate: 2021/10/20 11:32 上午
 * Author: zjy
 * Description: 用于画字体的基线
 */
class FontLineView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    // 从assets中读取字体
    val typeface1 = Typeface.createFromAsset(context.assets, "fonts/SourceHanSansCN-Normal.otf")
    val typeface2 = Typeface.createFromAsset(context.assets, "fonts/GlowSansSC-Normal-Medium.otf")
    val typeface3 = Typeface.createFromAsset(context.assets, "fonts/GlowSansSC-Normal-Regular.otf")
    val typeface4 = Typeface.createFromAsset(context.assets, "fonts/SourceHanSansCN-ExtraLight.otf")

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        paint.typeface = typeface1
        paint.textSize = WidgetUtil.sp2px(24f)
        val fontMetrics = paint.fontMetrics
//        LogUtil.i("zjy", "paint.fontMetrics.ascent: ${paint.fontMetrics.ascent} " +
//                " paint.fontMetrics.descent: ${paint.fontMetrics.descent} " +
//                " paint.fontMetrics.top: ${paint.fontMetrics.top} " +
//                " paint.fontMetrics.bottom: ${paint.fontMetrics.bottom} " +
//                " paint.fontMetrics.leading: ${paint.fontMetrics.leading}")
        val s = "GgJjQqPp测试一下文字 这是GlowSansSC-Normal-Light字体"
        val rect = Rect()
        paint.getTextBounds(s, 0, s.length, rect)
        canvas.drawText(s, 0, s.length, 50.0f, 500.0f, paint)
        canvas.drawLine(50f, 500f, rect.right + 50f, 500f, paint)
        canvas.drawLine(50f, fontMetrics.top + 500, rect.right + 50f, fontMetrics.top + 500, paint)
        canvas.drawLine(50f, fontMetrics.bottom + 500, rect.right + 50f, fontMetrics.bottom + 500, paint)
        canvas.drawLine(50f, fontMetrics.ascent + 500, rect.right + 50f, fontMetrics.ascent + 500, paint)
        canvas.drawLine(50f, fontMetrics.descent + 500, rect.right + 50f, fontMetrics.descent + 500, paint)

        paint.typeface = typeface2
        val fontMetrics1 = paint.fontMetrics
//        LogUtil.i("zjy", "paint.fontMetrics.ascent: ${paint.fontMetrics.ascent} " +
//                " paint.fontMetrics.descent: ${paint.fontMetrics.descent} " +
//                " paint.fontMetrics.top: ${paint.fontMetrics.top} " +
//                " paint.fontMetrics.bottom: ${paint.fontMetrics.bottom} " +
//                " paint.fontMetrics.leading: ${paint.fontMetrics.leading}")
        val s1 = "GgJjQqPp测试一下文字 这是GlowSansSC-Normal-Medium字体"
        val rect1 = Rect()
        paint.getTextBounds(s1, 0, s1.length, rect1)
        canvas.drawText(s1, 0, s1.length, 50.0f, 800f, paint)
        canvas.drawLine(50f, 800f, rect1.right + 50f, 800f, paint)
        canvas.drawLine(50f, fontMetrics1.top + 800f, rect1.right + 50f, fontMetrics1.top + 800f, paint)
        canvas.drawLine(50f, fontMetrics1.bottom + 800f, rect1.right + 50f, fontMetrics1.bottom + 800f, paint)
        canvas.drawLine(50f, fontMetrics1.ascent + 800f, rect1.right + 50f, fontMetrics1.ascent + 800f, paint)
        canvas.drawLine(50f, fontMetrics1.descent + 800f, rect1.right + 50f, fontMetrics1.descent + 800f, paint)
    }
}