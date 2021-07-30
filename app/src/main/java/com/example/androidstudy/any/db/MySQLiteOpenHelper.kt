package com.example.androidstudy.any.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * ProjectName: AndroidStudy
 * Package: com.example.androidstudy.any.db
 * ClassName: MySqlLiteDataBaseHelper
 * CreateDate: 2021/7/29 11:54 上午
 * Author: zjy
 * Description: 数据库帮助类
 */
class MySQLiteOpenHelper(context: Context?, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int) : SQLiteOpenHelper(context, name, factory, version) {
    private val createBookDbSql = "CREATE TABLE book(id integer PRIMARY KEY AUTOINCREMENT, book_name text, price real)"
    private val createCategoryDbSql = "CREATE TABLE category(id integer PRIMARY KEY AUTOINCREMENT, category text)"


    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(createBookDbSql)
        db?.execSQL(createCategoryDbSql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion <= 1) {
            db?.execSQL(createCategoryDbSql)
        }

    }
}