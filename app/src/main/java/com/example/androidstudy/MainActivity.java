package com.example.androidstudy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.androidstudy.any.addressphoto.activity.AddressPhotoActivity;
import com.example.androidstudy.any.contentprovider.ContentProviderTestActivity;
import com.example.androidstudy.any.coordinatorlayout.CoordinatorLayoutTestActivity;
import com.example.androidstudy.any.customview.CustomViewActivity;
import com.example.androidstudy.any.db.SqlLiteDBActivity;
import com.example.androidstudy.any.fonttest.FontTestActivity;
import com.example.androidstudy.any.nestedscroll.NestedScrollActivity;
import com.example.androidstudy.any.recyclerview.RecyclerviewActivity;
import com.example.androidstudy.databinding.ActivityMainBinding;
import com.example.androidstudy.hencoder.HenCoderActivity;
import com.example.baselibrary.activity.BaseActivity;

public class MainActivity extends BaseActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // 这是用来测试revert的代码
    }

    public void toAddressPhoto(View view) {
        toActivity(AddressPhotoActivity.class);
    }


    public void toFontTest(View view) {
        toActivity(FontTestActivity.class);
    }

    public void toCoordinatorLayout(View view) {
        toActivity(CoordinatorLayoutTestActivity.class);
    }

    public void toNestedScroll(View view) {
        toActivity(NestedScrollActivity.class);
    }

    public void toCustomViewTest(View view) {
        toActivity(CustomViewActivity.class);
    }

    public void toHenCoderTest(View view) {
        toActivity(HenCoderActivity.class);
    }

    public void toDBTest(View view) {
        toActivity(SqlLiteDBActivity.class);
    }

    public void toContentProvider(View view) {
        toActivity(ContentProviderTestActivity.class);
    }

    public void toRecyclerView(View view) {
        toActivity(RecyclerviewActivity.class);
    }
}