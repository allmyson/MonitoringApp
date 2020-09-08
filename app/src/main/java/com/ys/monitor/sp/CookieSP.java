package com.ys.monitor.sp;

import android.content.Context;

import com.ys.monitor.util.SPUtil;
import com.ys.monitor.util.StringUtil;


/**
 * @author lh
 * @version 1.0.0
 * @filename CookieSP
 * @description -------------------------------------------------------
 * @date 2017/10/9 13:56
 */
public class CookieSP {
    private static final String KEY_TOKEN = "token";

    public static void saveCookie(Context context, String token) {
        SPUtil.put(context, KEY_TOKEN, StringUtil.valueOf(token));
    }

    public static String getCookie(Context context) {
        return (String) SPUtil.get(context, KEY_TOKEN, "");
    }
}
