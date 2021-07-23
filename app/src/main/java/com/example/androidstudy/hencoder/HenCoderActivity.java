package com.example.androidstudy.hencoder;


import android.os.Bundle;
import android.view.View;

import com.example.androidstudy.R;
import com.example.baselibrary.activity.BaseActivity;

public class HenCoderActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hen_coder);
    }

    public void practiceDraw(View view) {
        Bundle bundle = new Bundle();
        bundle.putString("practice", "PracticeDraw");
        toActivity(HenCoderTestActivity.class, bundle);
    }
}