package com.example.baselibrary;

import android.app.Application;

import io.microshow.rxffmpeg.RxFFmpegInvoke;

public class MyApplication extends Application {
    private static MyApplication INSTANCE;

    public static MyApplication getInstance() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        RxFFmpegInvoke.getInstance().setDebug(true);
    }
}
