package com.example.androidstudy.any.coroutines

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.androidstudy.R
import com.example.androidstudy.any.coroutines.flowpractice.activity.FlowPracticeActivity
import com.example.androidstudy.databinding.ActivityCoroutinesTestBinding
import com.example.baselibrary.util.WidgetUtil
import kotlinx.coroutines.*

class CoroutinesTestActivity : AppCompatActivity() {
    lateinit var binding: ActivityCoroutinesTestBinding
    private val mainScope = MainScope()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoroutinesTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 第一版
//        binding.tvDelay.setOnClickListener {
//            GlobalScope.launch(Dispatchers.Main) {
//                withContext(Dispatchers.IO) {
//                    delay(2000)
//                }
//                binding.tvDelay.text = "${System.currentTimeMillis()}"
//            }
//        }

        // 第二版
//        binding.tvDelay.setOnClickListener {
//            GlobalScope.launch(Dispatchers.Main) {
//                Log.e("zjy", "当前线程：${Thread.currentThread().name}")
//                delayAndShow()
//            }
//        }

        binding.tvDelay.setOnClickListener {
            mainScope.launch {
                delayAndShow()
            }
        }

        binding.tvViewModel.setOnClickListener {
            startActivity(Intent(this, CoroutinesTestActivity2::class.java))
        }

        binding.tvCoroutineExceptionHandler.setOnClickListener {
            startActivity(Intent(this, CoroutineExceptionHandlerActivity::class.java))
        }

        binding.tvFlow.setOnClickListener {
            startActivity(Intent(this, FlowPracticeActivity::class.java))
        }

        val url = "https://img.36krcdn.com/20220720/v2_274eb922329e4bf5942b01ba1682187e_img_png?x-oss-process=image/resize,m_mfit,w_660,h_660,limit_0/crop,w_660,h_660,g_center"
        val width = WidgetUtil.getScreenWidth() - WidgetUtil.dp2px(77f) * 2
        Glide.with(this)
            .load(url)
            .placeholder(R.drawable.permissionx_default_dialog_bg)
            .transform(CenterCrop(), RoundedCorners(20))
            .override(width, width)
            .into(binding.iv)

    }

    private suspend fun delayAndShow() {
        delay()
        show()
    }

    private suspend fun delay() {
        withContext(Dispatchers.IO) {
            delay(2000)
        }
    }

    private fun show(){
        binding.tvDelay.text = "${System.currentTimeMillis()}"
    }
}