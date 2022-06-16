package com.example.androidstudy.any.coroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.androidstudy.R
import com.example.androidstudy.databinding.ActivityCoroutineExceptionHandlerBinding
import com.example.baselibrary.activity.BaseActivity
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CoroutineExceptionHandlerActivity : BaseActivity() {
    private lateinit var binding: ActivityCoroutineExceptionHandlerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoroutineExceptionHandlerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val handler = CoroutineExceptionHandler { _, throwable ->
            println("Caught exception ${throwable.message}")
        }

        // handler捕获异常并且不会闪退
//        binding.btn.setOnClickListener {
//            GlobalScope.launch(handler) {
//                println("点击了按钮")
//                listOf(1,2,3)[4] // 模拟报错
//            }
//        }

        // 不用handler捕获异常 用全局异常捕获handler来捕获 ps:能捕获到协程中没有捕获的异常 但是程序依然会闪退
        binding.btn.setOnClickListener {
            GlobalScope.launch {
                println("点击了按钮")
                listOf(1,2,3)[4] // 模拟报错
            }
        }
    }
}