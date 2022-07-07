package com.example.androidstudy.any.coroutines.flowpractice.utils

import java.io.InputStream
import java.io.OutputStream

/**
 * @author zjy
 * @date 2022/7/7 5:20 下午
 * @description
 */
inline fun InputStream.copyTo(out: OutputStream, bufferSize: Int = DEFAULT_BUFFER_SIZE, progress: (Long) -> Unit) : Long {
    var bytesCopied: Long = 0
    val buffer = ByteArray(bufferSize)
    var bytes = read(buffer)
    while (bytes >= 0) {
        out.write(buffer, 0, bytes)
        bytesCopied += bytes
        bytes = read(buffer)

        progress(bytesCopied)
    }
    return bytesCopied
}