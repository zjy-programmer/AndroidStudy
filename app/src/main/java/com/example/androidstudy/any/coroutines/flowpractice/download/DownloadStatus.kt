package com.example.androidstudy.any.coroutines.flowpractice.download

import java.io.File

/**
 * @author zjy
 * @date 2022/7/7 4:21 下午
 * @description
 */
sealed class DownloadStatus {
    object None: DownloadStatus()
    data class Progress(val value: Int) : DownloadStatus()
    data class Error(val throwable: Throwable) : DownloadStatus()
    data class Done(val file: File) : DownloadStatus()
}
