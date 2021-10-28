package com.example.androidstudy.hencoderplus.chapter34

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.androidstudy.R
import com.example.baselibrary.util.ToastUtil
import okio.Buffer
import okio.Okio
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

    fun copyFile(view: android.view.View) {
        val file = File("$filesDir${File.separator}homework4.txt")
        val copyFile = File("$filesDir${File.separator}homework4_copy.txt")
        if (file.exists()) {
            // kotlin的扩展函数
//            file.copyTo(copyFile)

            // 自己写
            val br = FileInputStream(file)
            val bw = FileOutputStream(copyFile)

            try {
                val buffer = ByteArray(1024)
                var length = br.read(buffer)
                while (length != -1) {
                    bw.write(buffer, 0, length)
                    length = br.read(buffer)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                try {
                    br.close()
                    bw.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    // okio用法忘了
    fun readDataFromFileOfOkio(view: android.view.View) {
//        val file = File("$filesDir${File.separator}homework4.txt")
//        val sink = Okio.sink(file)
//        val source = Buffer()
//        val readAll = source.readAll(sink)
    }
}