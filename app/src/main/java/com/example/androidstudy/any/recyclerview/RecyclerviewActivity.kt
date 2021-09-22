package com.example.androidstudy.any.recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.androidstudy.R
import com.example.androidstudy.databinding.ActivityRecyclerviewBinding
import com.example.androidstudy.databinding.HolderTestBinding
import com.example.baselibrary.util.WidgetUtil
import com.littlemango.stacklayoutmanager.StackLayoutManager

class RecyclerviewActivity : AppCompatActivity() {
    private lateinit var bind: ActivityRecyclerviewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityRecyclerviewBinding.inflate(layoutInflater);
        setContentView(bind.root)

//        val horizontalConfig = StackCardLayoutManager.StackConfig()
//        horizontalConfig.stackScale = 0.9f
//        horizontalConfig.stackCount = 3
//        horizontalConfig.stackPosition = 0
//        horizontalConfig.space = WidgetUtil.dp2px(24f)
//        horizontalConfig.parallex = 1.5f
//        horizontalConfig.isCycle = true
//        horizontalConfig.isAdjustSize = false
//        horizontalConfig.direction = StackCardLayoutManager.StackDirection.RIGHT
//        bind.rvTest.layoutManager = StackCardLayoutManager(horizontalConfig)
        bind.rvTest.layoutManager = StackLayoutManager()
        bind.rvTest.adapter = MyAdapter()

    }

    class MyAdapter: RecyclerView.Adapter<MyHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
            return MyHolder(HolderTestBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }

        override fun onBindViewHolder(holder: MyHolder, position: Int) {
            holder.binding.tvTest.text = "第${position}个"
        }

        override fun getItemCount(): Int = 20

    }

    class MyHolder(var binding: HolderTestBinding) : RecyclerView.ViewHolder(binding.root)
}