package com.example.androidstudy.widget

import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.marginEnd
import androidx.core.view.marginStart

/**
 * @author zjy
 * @date 2022/7/13 10:31 上午
 * @description
 */

// 处理英文自动换行问题 在Textview.post{}中调用 获取处理好的文字后再设置文字
fun TextView.toNonBreakingString(text: String?): String {
    if (text == null) return ""
    val container = parent as? ViewGroup ?: return text
    val lineWidth = (container.width - container.paddingStart - container.paddingEnd - marginStart - marginEnd).toFloat()
    val maxCharsInOneLine = paint.breakText(text, 0, text.length, true, lineWidth, null)
    if (maxCharsInOneLine == 0) return text
    val sb = StringBuilder()
    var currentLine = 1
    var end = 0
    for (i in 0..text.count() step maxCharsInOneLine) {
        end = currentLine * maxCharsInOneLine
        if (end > text.length) end = text.length
        sb.append(text.subSequence(i, end))
        sb.append("\n")
        currentLine = currentLine.inc()
    }
    if (end < text.length) {
        val remainingChars = text.length - end
        sb.append(text.takeLast(remainingChars))
    }
    return sb.toString()
}