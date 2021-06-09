package com.example.baselibrary.util;

import android.widget.Toast;

import com.example.baselibrary.BuildConfig;
import com.example.baselibrary.MyApplication;


public class ToastUtil {
    public static void show(String msg) {
        show(msg, Toast.LENGTH_SHORT);
    }

    public static void show(String msg, int duration) {
        if (isDebug()) {
            Toast.makeText(MyApplication.getInstance(), msg, duration).show();
        }
    }

    private static boolean isDebug() {
        return BuildConfig.DEBUG;
    }
}
