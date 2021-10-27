package com.example.androidstudy.hencoderplus

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.androidstudy.R
import com.example.androidstudy.hencoderplus.chapter11.HenCoderPlusChapter11Activity
import com.example.androidstudy.hencoderplus.chapter12.HenCoderPlusChapter12Activity
import com.example.androidstudy.hencoderplus.chapter13.HenCoderPlusChapter13Activity
import com.example.androidstudy.hencoderplus.chapter17.HenCoderPlus17Activity
import com.example.androidstudy.hencoderplus.chapter19.HenCoderPlus19Activity
import com.example.baselibrary.activity.BaseActivity

class HenCoderPlusActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hencoder_plus)
    }

    fun toHenCoderPlusChapter11(view: View) {
        startActivity(Intent(this, HenCoderPlusChapter11Activity::class.java))
    }

    fun toHenCoderPlusChapter12(view: View) {
        startActivity(Intent(this, HenCoderPlusChapter12Activity::class.java))
    }

    fun toHenCoderPlusChapter13(view: View) {
        startActivity(Intent(this, HenCoderPlusChapter13Activity::class.java))
    }

    fun toHenCoderPlusChapter17(view: View) {
        startActivity(Intent(this, HenCoderPlus17Activity::class.java))
    }

    fun toHenCoderPlusChapter19(view: View) {
        startActivity(Intent(this, HenCoderPlus19Activity::class.java))
    }
}