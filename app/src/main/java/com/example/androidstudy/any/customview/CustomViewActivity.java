package com.example.androidstudy.any.customview;


import android.os.Bundle;
import android.view.View;

import com.example.androidstudy.R;
import com.example.androidstudy.databinding.ActivityCustomViewBinding;
import com.example.baselibrary.activity.BaseActivity;

public class CustomViewActivity extends BaseActivity {

    private ActivityCustomViewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCustomViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        binding.vgEvent.setOnClickListener(v -> {
//
//        });
//        binding.vEvent.setOnClickListener(v -> {
//
//        });
    }
}