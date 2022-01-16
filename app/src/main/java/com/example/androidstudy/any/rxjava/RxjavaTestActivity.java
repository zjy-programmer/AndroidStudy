package com.example.androidstudy.any.rxjava;

import android.os.Bundle;

import com.example.androidstudy.R;
import com.example.androidstudy.any.rxjava.api.IRxjavaTest;
import com.example.androidstudy.any.rxjava.api.RxjavaTestImpl1;
import com.example.androidstudy.any.rxjava.api.RxjavaTestImpl10;
import com.example.androidstudy.any.rxjava.api.RxjavaTestImpl11;
import com.example.androidstudy.any.rxjava.api.RxjavaTestImpl12;
import com.example.androidstudy.any.rxjava.api.RxjavaTestImpl13;
import com.example.androidstudy.any.rxjava.api.RxjavaTestImpl14;
import com.example.androidstudy.any.rxjava.api.RxjavaTestImpl15;
import com.example.androidstudy.any.rxjava.api.RxjavaTestImpl16;
import com.example.androidstudy.any.rxjava.api.RxjavaTestImpl17;
import com.example.androidstudy.any.rxjava.api.RxjavaTestImpl18;
import com.example.androidstudy.any.rxjava.api.RxjavaTestImpl2;
import com.example.androidstudy.any.rxjava.api.RxjavaTestImpl3;
import com.example.androidstudy.any.rxjava.api.RxjavaTestImpl4;
import com.example.androidstudy.any.rxjava.api.RxjavaTestImpl5;
import com.example.androidstudy.any.rxjava.api.RxjavaTestImpl6;
import com.example.androidstudy.any.rxjava.api.RxjavaTestImpl7;
import com.example.androidstudy.any.rxjava.api.RxjavaTestImpl8;
import com.example.androidstudy.any.rxjava.api.RxjavaTestImpl9;
import com.example.baselibrary.activity.BaseActivity;

public class RxjavaTestActivity extends BaseActivity {
    private IRxjavaTest iRxjavaTest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava_test);

//        iRxjavaTest = new RxjavaTestImpl1();
//        iRxjavaTest = new RxjavaTestImpl2();
//        iRxjavaTest = new RxjavaTestImpl3();
//        iRxjavaTest = new RxjavaTestImpl4();
//        iRxjavaTest = new RxjavaTestImpl5();
        iRxjavaTest = new RxjavaTestImpl6();
//        iRxjavaTest = new RxjavaTestImpl7();
//        iRxjavaTest = new RxjavaTestImpl8();
//        iRxjavaTest = new RxjavaTestImpl9();
//        iRxjavaTest = new RxjavaTestImpl10();
//        iRxjavaTest = new RxjavaTestImpl11();
//        iRxjavaTest = new RxjavaTestImpl12();
//        iRxjavaTest = new RxjavaTestImpl13();
//        iRxjavaTest = new RxjavaTestImpl14();
//        iRxjavaTest = new RxjavaTestImpl15();
//        iRxjavaTest = new RxjavaTestImpl16();
//        iRxjavaTest = new RxjavaTestImpl17();
//        iRxjavaTest = new RxjavaTestImpl18();

        iRxjavaTest.run();
    }
}