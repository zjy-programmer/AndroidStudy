package com.firstline3.taskaffinitytest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun toB(view: android.view.View) {
        startActivity(Intent(this@MainActivity, BActivity::class.java))
    }
}