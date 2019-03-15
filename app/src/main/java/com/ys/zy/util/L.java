package com.ys.zy.util;

import android.util.Log;


public class L {
    private L() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static boolean isDebug = true;// 是否需要打印bug，可以在application的onCreate函数里面初始化
    private static String TAG = "lh";

    public static String getTAG() {
        return TAG;
    }

    public static void setTAG(String TAG) {
        L.TAG = TAG;
    }

    public static void setDebugMode(boolean debug) {
        isDebug = debug;
    }

    // 下面四个是默认tag的函数
    public static void i(String msg) {
        if (isDebug)
            Log.i(TAG, StringUtil.valueOf(msg));
    }

    public static void d(String msg) {
        if (isDebug)
            Log.d(TAG, StringUtil.valueOf(msg));
    }

    public static void e(String msg) {
        if (isDebug)
            Log.e(TAG, StringUtil.valueOf(msg));
    }

    public static void v(String msg) {
        if (isDebug)
            Log.v(TAG, StringUtil.valueOf(msg));
    }

    public static void i(String tag, String msg) {
        if (isDebug)
            Log.i(tag, StringUtil.valueOf(msg));
    }

    public static void d(String tag, String msg) {
        if (isDebug)
            Log.d(tag, StringUtil.valueOf(msg));
    }

    public static void e(String tag, String msg) {
        if (isDebug)
            Log.e(tag, StringUtil.valueOf(msg));
    }

    public static void v(String tag, String msg) {
        if (isDebug)
            Log.v(tag, StringUtil.valueOf(msg));
    }

    public static void println(String msg) {
        if (isDebug)
            System.out.println(StringUtil.valueOf(msg));
    }
}
