package com.example.androidstudy.any.nestedscroll;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.androidstudy.R;
import com.example.androidstudy.databinding.ActivityNestedScrollBinding;
import com.example.androidstudy.databinding.HolderTestBinding;
import com.example.baselibrary.activity.BaseActivity;
import com.example.baselibrary.util.LogUtil;
import com.example.baselibrary.util.WidgetUtil;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class NestedScrollActivity extends BaseActivity {

    private ActivityNestedScrollBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNestedScrollBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        binding.tvHead.post(this::initView);
        initView();
    }

    private void initView() {
        binding.rvHead.setLayoutManager(new LinearLayoutManager(this));
        binding.rvHead.setAdapter(new MyAdapter(true));
        binding.rvHead.setNestedScrollingEnabled(false);

        binding.rv.setLayoutManager(new LinearLayoutManager(this));
        binding.rv.setAdapter(new MyAdapter(false));
    }

    static class MyAdapter extends RecyclerView.Adapter<MyHolder> {
        private boolean isHead;

        public MyAdapter(boolean isHead) {
            this.isHead = isHead;
        }

        @NonNull
        @Override
        public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MyHolder(HolderTestBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull NestedScrollActivity.MyHolder holder, int position) {
            LogUtil.i("zjy", "position: " + position);
            holder.binding.tvTest.setText("第" + position + "个");
        }

        @Override
        public int getItemCount() {
            return isHead ? 10 : 100;
        }
    }
    
    static class MyHolder extends RecyclerView.ViewHolder {
        HolderTestBinding binding;
        public MyHolder(@NonNull HolderTestBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}