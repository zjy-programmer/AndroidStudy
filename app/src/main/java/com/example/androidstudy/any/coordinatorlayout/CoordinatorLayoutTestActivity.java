package com.example.androidstudy.any.coordinatorlayout;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.androidstudy.R;
import com.example.baselibrary.activity.BaseActivity;

import androidx.appcompat.app.ActionBar;

public class CoordinatorLayoutTestActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_NO_TITLE); //取消状态栏的标题
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//判断SDK的版本是否>=21
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION); //允许页面可以拉伸到顶部状态栏并且定义顶部状态栏透名
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |  //设置全屏显示
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT); //设置状态栏为透明
            window.setNavigationBarColor(Color.TRANSPARENT); //设置虚拟键为透明
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_layout_test);

    }
}