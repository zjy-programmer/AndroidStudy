package com.example.androidstudy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.androidstudy.any.addressphoto.activity.AddressPhotoActivity;
import com.example.androidstudy.any.coordinatorlayout.CoordinatorLayoutTestActivity;
import com.example.androidstudy.any.fonttest.FontTestActivity;
import com.example.androidstudy.any.nestedscroll.NestedScrollActivity;
import com.example.androidstudy.databinding.ActivityMainBinding;
import com.example.baselibrary.activity.BaseActivity;

public class MainActivity extends BaseActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    private void toActivity(Class<?> clazz) {
        toActivity(clazz, null);
    }

    private void toActivity(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
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
}