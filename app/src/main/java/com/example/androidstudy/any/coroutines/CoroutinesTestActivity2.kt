package com.example.androidstudy.any.coroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.androidstudy.R
import com.example.androidstudy.any.coroutines.viewmodel.MainViewModel
import com.example.androidstudy.databinding.ActivityCoroutinesTest2Binding
import com.example.baselibrary.activity.BaseActivity

class CoroutinesTestActivity2 : BaseActivity() {
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityCoroutinesTest2Binding>(this, R.layout.activity_coroutines_test2)
        binding.viewModel = mainViewModel
        binding.lifecycleOwner = this

        binding.btn.setOnClickListener {
            mainViewModel.getUser()
        }

    }
}