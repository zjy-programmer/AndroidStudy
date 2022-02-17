package com.example.androidstudy.any.loadclass

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.androidstudy.R

class LoadClassTestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_load_class_test)
    }

    fun loadClass(view: View) {
        // 这里调用了类的静态变量 所以会初始化类所对应的class 这属于隐式装载
//        LogUtil.i("zjy", "${MyClass.value}")
        // 显示装载一个class对象也是会初始化类所对应的class
        val forName = Class.forName("com.example.androidstudy.any.loadclass.MyClass")
    }

    fun newClass(view: View) {
        // 这里实例化了类的对象 所以会初始化非静态代码块
        val myClass = MyClass()
    }
}