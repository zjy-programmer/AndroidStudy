package com.example.androidstudy.any.changeskin

import android.annotation.SuppressLint
import android.content.res.AssetManager
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.util.DisplayMetrics
import com.example.androidstudy.R
import com.example.androidstudy.databinding.ActivityChangeSkinBinding
import com.example.baselibrary.MyApplication
import com.example.baselibrary.activity.BaseActivity
import com.example.baselibrary.util.LogUtil
import java.io.File
import java.lang.reflect.Method

class ChangeSkinActivity : BaseActivity() {
    private val binding: ActivityChangeSkinBinding by lazy {
        ActivityChangeSkinBinding.inflate(
            layoutInflater
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.btn.setOnClickListener {
            changeSkin()
        }
        binding.btnReset.setOnClickListener {
            reset()
        }
    }

    private fun reset() {
        binding.ivImage.setImageDrawable(resources.getDrawable(R.mipmap.ic_home, null))
        binding.tvTitle.text = resources.getString(R.string.hello_skin)
        binding.tvTitle.setTextColor(resources.getColor(R.color.change_color, null))
    }

    private fun changeSkin() {
        try {
            val assetManager: AssetManager = AssetManager::class.java.newInstance()

            @SuppressLint("DiscouragedPrivateApi")
            val method: Method =
                AssetManager::class.java.getDeclaredMethod("addAssetPath", String::class.java)
            method.isAccessible = true
            // 反射执行方法
            method.invoke(assetManager, "${filesDir}${File.separator}skin.apk")

            // 创建自己的Resources
            val resources = Resources(assetManager, createDisplayMetrics(), createConfiguration())

            /*
             * getIdentifier 根据名字拿id
             * name: 资源名
             * defType: 资源类型
             * defPackage: 所在包名
             * return:如果返回0则表示没有找到
             */

            val packName = "com.example.skinlib"

            // 加载drawable
            val drawableId: Int = resources.getIdentifier("ic_home", "mipmap", packName)
            // 加载string
            val stringId: Int = resources.getIdentifier("hello_skin", "string", packName)
            // 加载color
            val colorId: Int = resources.getIdentifier("change_color", "color", packName)
            binding.ivImage.setImageDrawable(resources.getDrawable(drawableId, null))
            binding.tvTitle.text = resources.getString(stringId)
            binding.tvTitle.setTextColor(resources.getColor(colorId, null))
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    // 这些关于屏幕的就用原来的就可以
    private fun createDisplayMetrics(): DisplayMetrics {
        return resources.displayMetrics
    }

    private fun createConfiguration(): Configuration {
        return resources.configuration
    }
}