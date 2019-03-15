package com.ys.zy.util;

import android.content.Context;
import android.widget.Toast;

/**
 * @author lh
 * @version 1.0.0
 * @filename ToastUtil
 * @description -------------------------------------------------------
 * @date 2017/10/9 10:35
 */
public class ToastUtil {
    private ToastUtil() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static boolean isShow = true;

    /**
     * 短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showShort(Context context, CharSequence message) {
        if (isShow)
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showShort(Context context, int message) {
        if (isShow)
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
    /**
     * 短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showShort(Context context, String message) {
        if (isShow)
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
    /**
     * 长时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showLong(Context context, CharSequence message) {
        if (isShow)
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    /**
     * 长时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showLong(Context context, int message) {
        if (isShow)
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
    /**
     * 长时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showLong(Context context, String message) {
        if (isShow)
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration
     */
    public static void show(Context context, CharSequence message, int duration) {
        if (isShow)
            Toast.makeText(context, message, duration).show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration
     */
    public static void show(Context context, int message, int duration) {
        if (isShow)
            Toast.makeText(context, message, duration).show();
    }

    public static void show(Context context, String message) {
        if (isShow)
            Toast.makeText(context, message,Toast.LENGTH_SHORT).show();
    }


    /**
     * @author 李杰
     * @version 1.0
     * @Description: TODO
     * @return void
     * @time： 2016/11/25
     */
    private static Toast toast = null;
    public static void showToast(Context context, String msg) {
        if (toast == null) {
            toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        toast.show();
    }

}
