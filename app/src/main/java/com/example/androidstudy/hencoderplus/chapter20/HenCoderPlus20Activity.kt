package com.example.androidstudy.hencoderplus.chapter20

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.TextView
import com.example.androidstudy.databinding.ActivityHenCoderPlus20Binding
import com.example.baselibrary.util.WidgetUtil

class HenCoderPlus20Activity : AppCompatActivity() {
    private val cityList = listOf("北京市", /*"天津市", "上海市", "重庆市","河北省","山西省","辽宁省",
        "吉林省","黑龙江省","江苏省","浙江省","安徽省","福建省","江西省","山东省","河南省","湖北省","湖南省",
        "广东省","海南省","四川省","贵州省","云南省","陕西省","甘肃省","青海省","台湾省","内蒙古自治区",
        "广西壮族自治区","西藏自治区","宁夏回族自治区","新疆维吾尔自治区"*/)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityHenCoderPlus20Binding.inflate(layoutInflater)
        setContentView(binding.root)

        for (index in cityList.indices) {
            val tv = TextView(this)
            tv.text = cityList[index]
            tv.textSize = 16f
            tv.setTextColor(Color.BLACK)
            val lp = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            binding.tagLayout.addView(tv, lp)
        }
    }
}