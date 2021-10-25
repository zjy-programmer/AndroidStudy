package com.example.baselibrary.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.example.baselibrary.MyApplication;

public class WidgetUtil {
    public static int dp2px(float dpValue) {
        final float scale = MyApplication.getInstance().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dp(float pxValue) {
        final float scale = MyApplication.getInstance().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static float sp2px(float sp) {
        Context context = MyApplication.getInstance().getApplicationContext();
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return sp * fontScale + 0.5f;
    }

    public static int getScreenWidth() {
        WindowManager service = (WindowManager) MyApplication.getInstance().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        service.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
//        return service.getCurrentWindowMetrics().getBounds().width();
    }

    public static int getScreenHeight() {
        WindowManager service = (WindowManager) MyApplication.getInstance().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        service.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
//        return service.getCurrentWindowMetrics().getBounds().height();
    }
}
