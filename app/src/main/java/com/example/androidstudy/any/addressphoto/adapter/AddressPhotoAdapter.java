package com.example.androidstudy.any.addressphoto.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.androidstudy.any.addressphoto.bean.AlbumBean;
import com.example.androidstudy.databinding.HolderAddressPhotoAlbumBinding;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AddressPhotoAdapter extends RecyclerView.Adapter<AddressPhotoAdapter.AddressPhotoHolder> {
    private List<AlbumBean> albums = new ArrayList<>();

    @NonNull
    @Override
    public AddressPhotoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        HolderAddressPhotoAlbumBinding binding = HolderAddressPhotoAlbumBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new AddressPhotoHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressPhotoAdapter.AddressPhotoHolder holder, int position) {
        AlbumBean albumBean = albums.get(position);
        Glide.with(holder.binding.ivThumbnail).load(albumBean.urls.get(0)).centerCrop().into(holder.binding.ivThumbnail);
        holder.binding.tvAlbumName.setText(albumBean.albumName);
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return albums == null ? 0 : albums.size();
    }

    public void setAlbums(List<AlbumBean> albums) {
        this.albums = albums;
        notifyDataSetChanged();
    }

    public static class AddressPhotoHolder extends RecyclerView.ViewHolder {
        HolderAddressPhotoAlbumBinding binding;
        public AddressPhotoHolder(@NonNull HolderAddressPhotoAlbumBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}


