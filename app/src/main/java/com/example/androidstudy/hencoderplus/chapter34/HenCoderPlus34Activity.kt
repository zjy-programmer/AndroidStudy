package com.example.androidstudy.hencoderplus.chapter34

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.androidstudy.R
import com.example.baselibrary.util.ToastUtil
import java.io.*
import java.lang.Exception

class HenCoderPlus34Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hen_coder_plus34ctivity)
    }

    fun createFileAndWriteData(view: android.view.View) {
        val file = File("$filesDir${File.separator}homework4.txt")
        if (!file.exists()) {
            try {
                file.createNewFile()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        if (file.exists()) {
            try {
                BufferedWriter(OutputStreamWriter(FileOutputStream(file))).use {
                    it.write("通过Kotlin代码来写入一段文本")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun readDataFromFile(view: android.view.View) {
        val file = File("$filesDir${File.separator}homework4.txt")
        if (file.exists()) {
            BufferedReader(InputStreamReader(FileInputStream(file))).use {
                val readLine = it.readLine()
                ToastUtil.show(readLine)
            }
        }
    }
}