package com.example.baselibrary;

import android.app.Application;
import android.os.Debug;
import android.os.Process;
import android.system.Os;

public class MyApplication extends Application {
    private static MyApplication INSTANCE;

    public static MyApplication getInstance() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
    }
}
