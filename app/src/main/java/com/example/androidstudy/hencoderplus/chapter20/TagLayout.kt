package com.example.androidstudy.hencoderplus.chapter20

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup

/**
 * ProjectName: AndroidStudy
 * Package: com.example.androidstudy.hencoderplus.chapter20
 * ClassName: TagLayout
 * CreateDate: 2021/10/27 6:24 下午
 * Author: zjy
 * Description:
 */
class TagLayout(context: Context?, attrs: AttributeSet?) : ViewGroup(context, attrs) {
    private var usedWidth = 0
    private var usedHeight = 0

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        for (i in 0 until childCount) {
            val child = getChildAt(i)
//            resolveSize()
        }
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            child.layout(l, t, r, b)
        }
    }
}