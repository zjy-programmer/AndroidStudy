package com.example.androidstudy.any.smartrefresh

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidstudy.R
import com.example.androidstudy.databinding.ActivitySmartRefreshLayoutTestBinding
import com.example.androidstudy.databinding.HolderTestBinding
import com.scwang.smart.refresh.layout.constant.RefreshState

import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.simple.SimpleMultiListener


class SmartRefreshLayoutTestActivity : AppCompatActivity() {
    lateinit var binding: ActivitySmartRefreshLayoutTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySmartRefreshLayoutTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rv.layoutManager = LinearLayoutManager(this)
        binding.rv.adapter = MyAdapter()
        binding.smartRefresh.apply {
            setReboundDuration(500)
            setHeaderMaxDragRate(1.1f)
            setHeaderTriggerRate(0.8f)
            setEnableOverScrollBounce(false)
            setEnableOverScrollDrag(false)
            autoRefresh(0)
        }
    }

    private fun reloadData() {
        if (binding.smartRefresh.getState() === RefreshState.None) {
            binding.smartRefresh.autoRefresh(0)
        } else {
            binding.smartRefresh.setReboundDuration(0)
            binding.smartRefresh.finishRefresh(0)
            binding.smartRefresh.setOnMultiListener(object : SimpleMultiListener() {
                override fun onStateChanged(
                    refreshLayout: RefreshLayout,
                    oldState: RefreshState,
                    newState: RefreshState
                ) {
                    super.onStateChanged(refreshLayout, oldState, newState)
                    if (oldState == RefreshState.RefreshFinish && newState == RefreshState.None) {
                        refreshLayout.setOnMultiListener(null)
                        refreshLayout.setReboundDuration(250)
                        refreshLayout.autoRefresh(0)
                    }
                }
            })
        }
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