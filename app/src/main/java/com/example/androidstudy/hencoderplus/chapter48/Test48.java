package com.example.androidstudy.hencoderplus.chapter48;

import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.appcompat.widget.AppCompatTextView;

/**
 * ProjectName: AndroidStudy
 * Package: com.example.androidstudy.hencoderplus.chapter48
 * ClassName: Test48
 * CreateDate: 2021/10/29 10:56 上午
 * Author: zjy
 * Description:
 */
public class Test48 {
    // 1. 扩展左边泛型实例化的限制范围，消除报错
//    ArrayList<View> fruitList = new ArrayList<TextView>();
    ArrayList<? extends View> fruitList = new ArrayList<TextView>();

    // 2. 扩展左边泛型实例化的限制范围，消除报错
//    ArrayList<AppCompatTextView> fruitList1 = new ArrayList<TextView>();
    ArrayList<? super AppCompatTextView> fruitList1 = new ArrayList<TextView>();
}
