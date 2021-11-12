package com.example.androidstudy.any.rxffmpegtest

import android.Manifest
import android.content.Context
import android.os.Bundle
import com.example.androidstudy.R
import com.example.baselibrary.activity.BaseActivity
import com.example.baselibrary.util.LogUtil
import com.permissionx.guolindev.PermissionX
import io.microshow.rxffmpeg.RxFFmpegSubscriber

import io.microshow.rxffmpeg.RxFFmpegInvoke
import android.graphics.Bitmap
import android.graphics.Color

import android.view.Gravity
import android.view.View

import com.airbnb.lottie.LottieProperty.TEXT_SIZE

import android.widget.LinearLayout

import android.widget.TextView
import com.arthenica.mobileffmpeg.Config.RETURN_CODE_CANCEL
import com.arthenica.mobileffmpeg.Config.RETURN_CODE_SUCCESS
import com.arthenica.mobileffmpeg.ExecuteCallback
import com.arthenica.mobileffmpeg.FFmpeg
import java.io.FileOutputStream
import java.io.IOException
import io.microshow.rxffmpeg.RxFFmpegCommandList





class RxFFmpegActivity : BaseActivity() {
    lateinit var myRxFFmpegSubscriber: MyRxFFmpegSubscriber

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rx_ffmpeg)

        PermissionX.init(this).permissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE).request { allGranted, grantedList, deniedList -> }
    }

    // val text = "ffmpeg -y -i /storage/emulated/0/1/input.mp4 -vf boxblur=25:5 -preset superfast /storage/emulated/0/1/result.mp4"

    fun obtainOverlay(offsetX: Int, offsetY: Int, location: Int): String? {
        return when (location) {
            2 -> "overlay='(main_w-overlay_w)-$offsetX:$offsetY'"
            3 -> "overlay='$offsetX:(main_h-overlay_h)-$offsetY'"
            4 -> "overlay='(main_w-overlay_w)-$offsetX:(main_h-overlay_h)-$offsetY'"
            1 -> "overlay=$offsetX:$offsetY"
            else -> "overlay=$offsetX:$offsetY"
        }
    }

    private fun addPictureWaterMark(srcVideoPath: String, imageWatermarkPath: String, targetVideoPath: String) {
        val text = "ffmpeg -y -i $srcVideoPath -i $imageWatermarkPath -filter_complex [0:v]scale=iw:ih[outv0];[1:0]scale=0.0:0.0[outv1];[outv0][outv1]overlay=0:200 -preset superfast $targetVideoPath"
        runFFmpegRxJava(text)
    }

    private fun addGifWaterMark(srcVideoPath: String, imageWatermarkPath: String, targetVideoPath: String) {
//        val text = "ffmpeg -y -i $srcVideoPath -ignore_loop 0 -i $imageWatermarkPath -filter_complex [1:v]scale=100:200[s];[0:v][s]overlay=0:0 -shortest $targetVideoPath"
        val text = "ffmpeg -y -i $srcVideoPath -i /storage/emulated/0/1.png -ignore_loop 0 -i $imageWatermarkPath -filter_complex overlay=10:10,overlay=200:200 -shortest $targetVideoPath"
        runFFmpegRxJava(text)


//        val text = "ffmpeg -i $srcVideoPath -vf \"movie=$imageWatermarkPath:loop=0,setpts=N/FRAME_RATE/TB[out];[0:v][out]overlay=x=main_w-overlay_w:y=0\" -shortest $targetVideoPath -y"
//        val cmdlist = RxFFmpegCommandList()
//        cmdlist.append("-i").append(srcVideoPath).append("-vf").append("\"movie=$imageWatermarkPath:loop=0,setpts=N/FRAME_RATE/TB[out];[0:v][out]overlay=x=main_w-overlay_w:y=0")
//            .append("-shortest").append(targetVideoPath)
//        RxFFmpegInvoke.getInstance().runCommandAsync(cmdlist.build(true), object :
//            RxFFmpegInvoke.IFFmpegListener {
//            override fun onFinish() {
//                LogUtil.i("zjy", "finish")
//            }
//
//            override fun onProgress(progress: Int, progressTime: Long) {
//                LogUtil.i("zjy", "progress: $progress")
//            }
//
//            override fun onCancel() {
//                LogUtil.i("zjy", "onCancel")
//            }
//
//            override fun onError(message: String?) {
//                LogUtil.i("zjy", "onError: $message")
//            }
//
//        })
    }

    private fun addVideoWaterMark(srcVideoPath: String, imageWatermarkPath: String, targetVideoPath: String) {
        val text = "ffmpeg -i /storage/emulated/0/video.mp4 -i /storage/emulated/0/b2.mp4 -filter_complex [1:v]scale=320:240[v1];[0:v][v1]overlay=200:200 /storage/emulated/0/output_video_watermark5.mp4 -y"
        runFFmpegRxJava(text)
    }

    /**
     * 文本转成Bitmap
     * @param text 文本内容
     * @param context 上下文
     * @return 图片的bitmap
     */
    private fun textToBitmap(text: String, context: Context): Bitmap {
        val scale: Float = context.resources.displayMetrics.scaledDensity
        val tv = TextView(context)
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        tv.layoutParams = layoutParams
        tv.text = text
        tv.textSize = scale * TEXT_SIZE
        tv.gravity = Gravity.CENTER_HORIZONTAL
        tv.isDrawingCacheEnabled = true
        tv.setTextColor(Color.BLUE)
        tv.setBackgroundColor(Color.WHITE)
        tv.measure(
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        )
        tv.layout(0, 0, tv.measuredWidth, tv.measuredHeight)
        tv.buildDrawingCache()
        val bitmap = tv.drawingCache
        val rate = bitmap.height / 20
        return Bitmap.createScaledBitmap(bitmap, bitmap.width / rate, 20, false)
    }

    /**
     * 文字生成图片
     * @param filePath filePath
     * @param text text
     * @param context context
     * @return 生成图片是否成功
     */
    fun textToPicture(filePath: String?, text: String, context: Context): Boolean {
        val bitmap = textToBitmap(text, context)
        var outputStream: FileOutputStream? = null
        try {
            outputStream = FileOutputStream(filePath)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
            outputStream.flush()
        } catch (e: IOException) {
            e.printStackTrace()
            return false
        } finally {
            try {
                outputStream?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return true
    }

    private fun runFFmpegRxJava(command: String) {
        LogUtil.i("zjy", "执行的FFmpeg命令是$command")
        val commands = command.split(" ").toTypedArray()
        myRxFFmpegSubscriber = MyRxFFmpegSubscriber()

        //开始执行FFmpeg命令
        RxFFmpegInvoke.getInstance()
            .runCommandRxJava(commands)
            .subscribe(myRxFFmpegSubscriber)
    }

    class MyRxFFmpegSubscriber :
        RxFFmpegSubscriber() {
        override fun onFinish() {
            LogUtil.i("zjy", "处理成功")
        }

        override fun onProgress(progress: Int, progressTime: Long) {
            //progressTime 可以在结合视频总时长去计算合适的进度值
            LogUtil.i("zjy", "progress: $progress, progressTime: $progressTime")
        }

        override fun onCancel() {
            LogUtil.i("zjy", "已取消")
        }

        override fun onError(message: String) {
            LogUtil.i("zjy", "出错了 onError：$message")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        myRxFFmpegSubscriber.dispose()
    }

    // 添加图片水印
    fun addPictureWaterMark(view: View) {
        val srcVideoPath = "/storage/emulated/0/video.mp4"
        val imageWaterMaskPath = "/storage/emulated/0/1.png"
        val targetVideoPath = "/storage/emulated/0/output.mp4"
        addPictureWaterMark(srcVideoPath, imageWaterMaskPath, targetVideoPath)
    }

    fun addTextWaterMark(view: View) {
        val path = "/storage/emulated/0/text.png"
        if (textToPicture(path, "我是文字水印", this)) {
            val srcVideoPath = "/storage/emulated/0/video.mp4"
            val imageWatermarkPath = path
            val targetVideoPath = "/storage/emulated/0/output_text_watermark.mp4"
            addPictureWaterMark(srcVideoPath, imageWatermarkPath, targetVideoPath)
        }
    }

    fun addGifWaterMark(view: View) {
        val srcVideoPath = "/storage/emulated/0/video.mp4"
//        val gifWaterMaskPath = "/storage/emulated/0/ok.gif"
        val gifWaterMaskPath = "/storage/emulated/0/snowman.gif"
        val targetVideoPath = "/storage/emulated/0/output_gif_watermark.mp4"
        addGifWaterMark(srcVideoPath, gifWaterMaskPath, targetVideoPath)
    }

    fun addVideoWaterMark(view: View) {
        val srcVideoPath = "/storage/emulated/0/video.mp4"
        val gifWaterMaskPath = "/storage/emulated/0/ok.gif"
        val targetVideoPath = "/storage/emulated/0/output_gif_watermark.mp4"
        addVideoWaterMark(srcVideoPath, gifWaterMaskPath, targetVideoPath)
    }

    fun gifToVideo(view: View) {
        val text = "ffmpeg -f gif -i /storage/emulated/0/ok.gif -c:v libx264 -pix_fmt yuv420p -f mp4 /storage/emulated/0/gif1.mp4"
        runFFmpegRxJava(text)
    }

    // 图片合成视频貌似只支持jpg格式图片
    fun picturesToVideo(view: View) {
//        val text = "ffmpeg -loop 1 -f image2 -i /storage/emulated/0/zPics/%04d.jpg -vcodec libx264 -r 10 -t 10 /storage/emulated/0/picToVideo.mp4"
//        runFFmpegRxJava(text)

        val text = "ffmpeg -f image2 -i /storage/emulated/0/z/%04d.png -vcodec libx264 -r 10 -t 10 /storage/emulated/0/movie.mp4 -y"
        runFFmpegRxJava(text)
    }

    fun picturesToGif(view: View) {
        val text = "ffmpeg -f image2 -r 25 -i /storage/emulated/0/z/%04d.png /storage/emulated/0/movie.gif -y"
        runFFmpegRxJava(text)
    }

    fun picturesSequenceToGif(view: View) {
        // 序列帧叠加到视频中做画中画 序列帧帧率15
        val text = "ffmpeg -y -i /storage/emulated/0/video.mp4" +
                " -loop 1 -framerate 15 -i /storage/emulated/0/z/%04d.png" +
                " -filter_complex overlay=200:200:shortest=1" +
                " /storage/emulated/0/picturesSequenceToGif.mp4"
        runFFmpegRxJava(text)
    }


}