package com.example.androidstudy.any.coroutines

import com.example.baselibrary.util.LogUtil
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlin.coroutines.CoroutineContext

/**
 * @author zjy
 * @date 2022/6/16 4:38 下午
 * @description 用于获取全局协程未捕获的异常信息 需要在kotlinx.coroutines.CoroutineExceptionHandler配置
 */
class GlobalCoroutineExceptionHandler: CoroutineExceptionHandler {
    override val key = CoroutineExceptionHandler

    override fun handleException(context: CoroutineContext, exception: Throwable) {
        // 可以用于上报 调试
        LogUtil.e("zjy", "全局异常捕获器捕获到的异常：${exception.message}")
    }
}