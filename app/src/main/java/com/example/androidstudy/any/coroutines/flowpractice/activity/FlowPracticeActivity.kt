package com.example.androidstudy.any.coroutines.flowpractice.activity

import android.os.Bundle
import com.example.androidstudy.R
import com.example.androidstudy.databinding.ActivityFlowPracticeBinding
import com.example.baselibrary.activity.BaseActivity

class FlowPracticeActivity : BaseActivity() {
    val binding: ActivityFlowPracticeBinding by lazy {
        ActivityFlowPracticeBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}