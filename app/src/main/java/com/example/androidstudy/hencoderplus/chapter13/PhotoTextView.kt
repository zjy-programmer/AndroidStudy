package com.example.androidstudy.hencoderplus.chapter13

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.example.androidstudy.R
import com.example.baselibrary.util.LogUtil
import com.example.baselibrary.util.WidgetUtil
import android.graphics.Bitmap




/**
 * ProjectName: AndroidStudy
 * Package: com.example.androidstudy.hencoderplus.chapter13
 * ClassName: PhotoTextView
 * CreateDate: 2021/10/25 5:49 下午
 * Author: zjy
 * Description: 图文混排页面
 */
class PhotoTextView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val str = "Ted Nelson, the inventor of hypertext, published ``Computer Lib'' in 1973. This book was more a stream-of-consciousness collage than anything else, nominally about nonlinear texts, and effectively an example of the same. It was written as hundreds of individual typewritten rants, and then pasted together for printing. Ironically, it was printed with a third of the pages out of order, allegedly due to a mix-up with the printer: one wonders, however, whether that really mattered."
    private var textY = 50f
    private lateinit var scaleBitmap: Bitmap

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        paint.textSize = WidgetUtil.sp2px(16f)
        val options = BitmapFactory.Options()
        val matrix = Matrix()
        matrix.setScale(0.5f, 0.5f) // 缩小为原来的一半

        val bitmap = BitmapFactory.decodeResource(resources, R.mipmap.icon_amg_1)
        val bm = Bitmap.createBitmap(
            bitmap, 0, 0, bitmap.width,
            bitmap.height, matrix, true
        )
        scaleBitmap = bm
        bitmap.recycle()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // 画图
        canvas.drawBitmap(scaleBitmap, width.toFloat() - scaleBitmap.width, 100f, paint)

        // 画换行的文字
        var start = 0
        val fontSpacing = paint.fontSpacing
        while (true) {
            var textWidths = 0
            if ((textY + paint.descent()) < 100f || (textY + paint.ascent()) > 100f + scaleBitmap.height) {
                textWidths = paint.breakText(str, start, str.length,  true, width.toFloat(), null)
            } else {
                textWidths = paint.breakText(str, start, str.length,  true, width.toFloat() - scaleBitmap.width, null)
            }

            if (textWidths == 0) {
                break
            }
            LogUtil.i("zjy", "textWidths: " + textWidths + " str的长度: " + str.length + " start： " + start)
            canvas.drawText(str, start, start + textWidths, 0f, textY, paint)
            textY += fontSpacing
            start += textWidths
        }
    }
}