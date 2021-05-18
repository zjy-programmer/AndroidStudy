package com.example.androidstudy.any.addressphoto.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.androidstudy.any.addressphoto.bean.PhotoBean;
import com.example.androidstudy.databinding.HolderPhotoBinding;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoHolder> {
    private List<PhotoBean> lists = new ArrayList<>();
    @NonNull
    @Override
    public PhotoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PhotoHolder(HolderPhotoBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoAdapter.PhotoHolder holder, int position) {
        Glide.with(holder.binding.ivPhoto).load(lists.get(position).url).centerCrop().into(holder.binding.ivPhoto);
    }

    @Override
    public int getItemCount() {
        return lists == null ? 0 : lists.size();
    }

    public void setLists(List<PhotoBean> lists) {
        this.lists = lists;
        notifyDataSetChanged();
    }

    public static class PhotoHolder extends RecyclerView.ViewHolder {
        HolderPhotoBinding binding;
        public PhotoHolder(@NonNull HolderPhotoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
