package com.example.androidstudy.any.contentprovider

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import androidx.core.content.ContextCompat
import com.example.androidstudy.R
import com.example.baselibrary.activity.BaseActivity
import com.example.baselibrary.util.LogUtil
import com.example.baselibrary.util.ToastUtil

class ContentProviderTestActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content_provider_test)

        // 查询book表全部内容
//        val cursor = contentResolver.query(Uri.parse("content://com.example.androidstudy.provider/book"), null, null, null, null)
//        if (cursor != null) {
//            if (cursor.moveToFirst()) {
//                do {
//                    val bookName = cursor.getString(0)
//                    val price = cursor.getString(1)
//                    LogUtil.i("zjy", "bookName: $bookName price: $price")
//                } while (cursor.moveToNext())
//            }
//            cursor.close()
//        }

        // 查询book表的id为2的数据
//        val cursor = contentResolver.query(Uri.parse("content://com.example.androidstudy.provider/book/2"), null, null, null, null)
//        if (cursor!!.moveToFirst()) {
//            do {
//                val bookName = cursor.getString(0)
//                val price = cursor.getString(1)
//                LogUtil.i("zjy", "bookName: $bookName price: $price")
//            } while (cursor.moveToNext())
//        }
//        cursor.close()

        // 向category表插入数据 后面的id=2写不写都一样 provider那边在插入数据时根本不区分有没有id
//        contentResolver.insert(Uri.parse("content://com.example.androidstudy.provider/category/2"), ContentValues().apply {
//            put("category", "悬疑")
//        })

    }

    fun queryContact(view: View) {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            readContactFromPhone()
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(arrayOf(android.Manifest.permission.READ_CONTACTS), 1)
            }
        }
    }

    private fun readContactFromPhone() {
        contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null)?.apply {
            if (moveToFirst()) {
                do {
                    val name = getString(getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                    val number = getString(getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                    LogUtil.i("zjy", "name: $name  number: $number");
                } while (moveToNext())
            }
            close()
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == 1) {
            for (i in permissions.indices) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    ToastUtil.show("请求权限失败")
                    return
                }
            }
            readContactFromPhone()
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}