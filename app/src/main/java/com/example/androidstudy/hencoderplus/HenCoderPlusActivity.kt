package com.example.androidstudy.hencoderplus

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.androidstudy.R
import com.example.androidstudy.hencoderplus.chapter11.HenCoderPlusChapter11Activity
import com.example.androidstudy.hencoderplus.chapter12.HenCoderPlusChapter12Activity
import com.example.baselibrary.activity.BaseActivity

class HenCoderPlusActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hencoder_plus)
    }

    fun toHenCoderPlusChapter11(view: View) {
        startActivity(Intent(this, HenCoderPlusChapter11Activity::class.java))
    }

    fun toHenCoderPlusChapter12(view: android.view.View) {
        startActivity(Intent(this, HenCoderPlusChapter12Activity::class.java))
    }
}