package com.example.baselibrary.util;

import android.util.Log;

import com.example.baselibrary.BuildConfig;

public class LogUtil {

    public static void v(String tag, String msg) {
        print("v", tag, msg);
    }

    public static void d(String tag, String msg) {
        print("d", tag, msg);
    }

    public static void i(String tag, String msg) {
        print("i", tag, msg);
    }

    public static void w(String tag, String msg) {
        print("w", tag, msg);
    }

    public static void e(String tag, String msg) {
        print("e", tag, msg);
    }

    private static void print(String level, String tag, String msg) {
        if (isDebug()) {
            switch (level) {
                case "v" :
                    Log.v(tag,msg);
                    break;
                case "d" :
                    Log.d(tag,msg);
                    break;
                case "i" :
                    Log.i(tag,msg);
                    break;
                case "w" :
                    Log.w(tag,msg);
                    break;
                case "e" :
                    Log.e(tag,msg);
                    break;
            }
        }
    }

    private static boolean isDebug() {
        return BuildConfig.DEBUG;
    }
}
