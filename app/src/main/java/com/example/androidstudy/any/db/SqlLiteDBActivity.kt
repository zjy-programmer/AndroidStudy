package com.example.androidstudy.any.db

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.androidstudy.R
import com.example.baselibrary.util.LogUtil

class SqlLiteDBActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sql_lite_dbactivity)
    }

    // 创建数据库并创建表
    fun createDB(view: View) {
        val helper = MySQLiteOpenHelper(this, "test", null, 1)
        helper.writableDatabase
    }

    // 增加数据
    fun insertData(view: View) {
        val helper = MySQLiteOpenHelper(this, "test", null, 1)
        helper.writableDatabase.insert("book", null, ContentValues().apply {
            put("book_name", "名侦探柯南")
            put("price", 20.0)
        })
    }

    // 删除数据
    fun deleteData(view: View) {
        val helper = MySQLiteOpenHelper(this, "test", null, 1)
        helper.writableDatabase.delete("book", "book_name = ?", arrayOf("名侦探柯南"))
    }

    // 更新数据
    fun updateData(view: View) {
        val helper = MySQLiteOpenHelper(this, "test", null, 1)
        helper.writableDatabase.update("book", ContentValues().apply {
            put("price", 30)
        }, "price = ?", arrayOf("20.0"))
    }

    // 查询数据
    fun queryData(view: View) {
        val helper = MySQLiteOpenHelper(this, "test", null, 1)
        val cursor = helper.writableDatabase.query("book", null, null, null, null, null, null)
        if (cursor.moveToFirst()) {
            do {
                val bookName = cursor.getString(cursor.getColumnIndex("book_name"))
                val price = cursor.getFloat(cursor.getColumnIndex("price"))
                LogUtil.i("zjy", "bookName: $bookName price: $price")
            } while (cursor.moveToNext())
        }
        cursor.close()
    }
}