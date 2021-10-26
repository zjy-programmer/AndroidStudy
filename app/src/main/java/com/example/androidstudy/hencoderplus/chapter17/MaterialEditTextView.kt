package com.example.androidstudy.hencoderplus.chapter17

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Build
import android.util.AttributeSet
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatEditText
import com.example.baselibrary.util.WidgetUtil

/**
 * ProjectName: AndroidStudy
 * Package: com.example.androidstudy.hencoderplus.chapter17
 * ClassName: MaterialEditTextView
 * CreateDate: 2021/10/26 2:09 下午
 * Author: zjy
 * Description:
 */
class MaterialEditTextView(context: Context, attrs: AttributeSet?) : AppCompatEditText(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var objectAnimator: ObjectAnimator? = null
    private var alphaAnimator: ObjectAnimator? = null
    private var animator: AnimatorSet? = null
    private var hintY = 0f
        set(value) {
            field = value
            invalidate()
        }
    private var hintAlpha = 0
        set(value) {
            field = value
//            invalidate()
        }

    init {
        paint.textSize = WidgetUtil.sp2px(14f)
        val h = paint.descent() - paint.ascent()
        setPadding(paddingStart, paddingTop + h.toInt(), paddingEnd, paddingBottom)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (objectAnimator == null) {
            objectAnimator = ObjectAnimator.ofFloat(this, "hintY", height.toFloat(), 50f)
        }
        if (alphaAnimator == null) {
            alphaAnimator = ObjectAnimator.ofInt(this, "hintAlpha", 0, 255)
        }
        if (animator == null) {
            animator = AnimatorSet()
            animator?.playTogether(objectAnimator, alphaAnimator)
        }
        paint.alpha = hintAlpha
        canvas.drawText(hint.toString(), paddingStart.toFloat(), hintY, paint)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onTextChanged(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)
        if (text?.isNotEmpty() == true && hintY != 50f && objectAnimator?.isRunning != true) {
            doAnimation()
        }
        if (text?.isEmpty() == true) {
            reverseAnimation()
        }
    }

    private fun doAnimation() {
        animator?.start()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun reverseAnimation() {
        animator?.reverse()
    }
}