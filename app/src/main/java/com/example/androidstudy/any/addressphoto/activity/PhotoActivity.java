package com.example.androidstudy.any.addressphoto.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.androidstudy.R;
import com.example.androidstudy.any.addressphoto.adapter.PhotoAdapter;
import com.example.androidstudy.any.addressphoto.bean.PhotoBean;
import com.example.androidstudy.databinding.ActivityPhotoBinding;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PhotoActivity extends AppCompatActivity {

    private ActivityPhotoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPhotoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            finish();
            return;
        }
        List<String> lists = (List<String>) bundle.getSerializable("lists");
        List<PhotoBean> photoBeans = new ArrayList<>();
        for (String url : lists) {
            PhotoBean bean = new PhotoBean();
            bean.url = url;
            photoBeans.add(bean);
        }
        PhotoAdapter adapter = new PhotoAdapter();
        binding.rvPhoto.setLayoutManager(new GridLayoutManager(this, 4));
        binding.rvPhoto.setAdapter(adapter);
        adapter.setLists(photoBeans);
    }
}