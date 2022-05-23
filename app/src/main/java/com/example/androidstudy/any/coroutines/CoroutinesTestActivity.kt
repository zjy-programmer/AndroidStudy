package com.example.androidstudy.any.coroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.androidstudy.databinding.ActivityCoroutinesTestBinding
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