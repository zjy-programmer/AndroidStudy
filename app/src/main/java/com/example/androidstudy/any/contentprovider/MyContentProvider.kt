package com.example.androidstudy.any.contentprovider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.example.androidstudy.any.db.MySQLiteOpenHelper
import com.example.baselibrary.MyApplication
import com.example.baselibrary.util.LogUtil

class MyContentProvider : ContentProvider() {
    private val BOOK_DIR: Int = 0
    private val BOOK_ITEM: Int = 1
    private val CATEGORY_DIR: Int = 2
    private val CATEGORY_ITEM: Int = 3


    private lateinit var mySQLiteOpenHelper: MySQLiteOpenHelper

    private val uriMatcher by lazy {
        val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        uriMatcher.addURI("com.example.androidstudy.provider", "book", BOOK_DIR)
        uriMatcher.addURI("com.example.androidstudy.provider", "book/#", BOOK_ITEM)
        uriMatcher.addURI("com.example.androidstudy.provider", "category", CATEGORY_DIR)
        uriMatcher.addURI("com.example.androidstudy.provider", "category/#", CATEGORY_ITEM)
        uriMatcher
    }

    override fun onCreate(): Boolean {
        // 初始化必须在这里 如果是在上面定义变量后直接赋值的话 context是空
        mySQLiteOpenHelper = MySQLiteOpenHelper(context, "test", null, 1)
        return true
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri?  = when (uriMatcher.match(uri)) {
        BOOK_DIR,
        BOOK_ITEM -> {
            val id = mySQLiteOpenHelper.readableDatabase.insert("book", null, values)
            Uri.parse("content://com.example.androidstudy.provider/book/$id")
        }
        CATEGORY_DIR,
        CATEGORY_ITEM -> {
            val id = mySQLiteOpenHelper.readableDatabase.insert("category", null, values)
            Uri.parse("content://com.example.androidstudy.provider/category/$id")
        }
        else -> null
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int = when (uriMatcher.match(uri)) {
        BOOK_DIR -> mySQLiteOpenHelper.readableDatabase.delete("book", selection, selectionArgs)
        BOOK_ITEM -> {
            val id = uri.pathSegments[1]
            LogUtil.i("zjy", "查询的book表 id是$id")
            mySQLiteOpenHelper.readableDatabase.delete("book", "id = ?", arrayOf(id))
        }
        CATEGORY_DIR -> mySQLiteOpenHelper.readableDatabase.delete("category", selection, selectionArgs)
        CATEGORY_ITEM -> {
            val id = uri.pathSegments[1]
            LogUtil.i("zjy", "查询的category表 id是$id")
            mySQLiteOpenHelper.readableDatabase.delete("category", "id = ?", arrayOf(id))
        }
        else -> 0
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?,
                        selectionArgs: Array<String>?): Int = when (uriMatcher.match(uri)) {
        BOOK_DIR -> mySQLiteOpenHelper.readableDatabase.update("book", values, selection, selectionArgs)
        BOOK_ITEM -> {
            val id = uri.pathSegments[1]
            LogUtil.i("zjy", "更新的book表 id是$id")
            mySQLiteOpenHelper.readableDatabase.update("book", values, "id = ?", arrayOf(id))
        }
        CATEGORY_DIR -> mySQLiteOpenHelper.readableDatabase.update("category", values, selection, selectionArgs)
        CATEGORY_ITEM -> {
            val id = uri.pathSegments[1]
            LogUtil.i("zjy", "更新的category表 id是$id")
            mySQLiteOpenHelper.readableDatabase.update("category", values, "id = ?", arrayOf(id))
        }
        else -> 0
    }

    override fun query(uri: Uri, projection: Array<String>?, selection: String?,
                       selectionArgs: Array<String>?, sortOrder: String?): Cursor? = when (uriMatcher.match(uri)) {
        BOOK_DIR -> mySQLiteOpenHelper.readableDatabase.query("book", null, null, null, null, null, null)
        BOOK_ITEM -> {
            val id = uri.pathSegments[1]
            LogUtil.i("zjy", "查询的book表 id是$id")
            mySQLiteOpenHelper.readableDatabase.query("book", null, "id = ?", arrayOf(id), null, null, null)
        }
        CATEGORY_DIR -> mySQLiteOpenHelper.readableDatabase.query("category", null, null, null, null, null, null)
        CATEGORY_ITEM -> {
            val id = uri.pathSegments[1]
            LogUtil.i("zjy", "查询的category表 id是$id")
            mySQLiteOpenHelper.readableDatabase.query("category", null, "id = ?", arrayOf(id), null, null, null)
        }
        else -> null
    }

    override fun getType(uri: Uri): String? = when (uriMatcher.match(uri)) {
        BOOK_DIR -> "vnd.example.androidstudy.provider.dir/book"
        BOOK_ITEM -> "vnd.example.androidstudy.provider.item/book"
        CATEGORY_DIR -> "vnd.example.androidstudy.provider.dir/category"
        CATEGORY_ITEM -> "vnd.example.androidstudy.provider.item/category"
        else -> null
    }

}