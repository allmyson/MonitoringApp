package com.ys.zy.http;

import android.content.Context;

import com.yanzhenjie.nohttp.cookie.DBCookieStore;
import com.ys.zy.sp.CookieSP;
import com.ys.zy.util.L;

import java.net.HttpCookie;
import java.net.URI;

/**
 * @author lh
 * @version 1.0.0
 * @filename CookieListener
 * @description -------------------------------------------------------
 * @date 2017/10/9 14:11
 */
public class CookieListener implements DBCookieStore.CookieStoreListener {

    private Context context;

    public CookieListener(Context context) {
        this.context = context;
    }

    @Override
    public void onSaveCookie(URI uri, HttpCookie cookie) {
        // 1. 判断这个被保存的Cookie是我们服务器下发的Session。
        // 2. 这里的JSessionId是Session的name，
        //    比如java的是JSessionId，PHP的是PSessionId，
        //    当然这里只是举例，实际java中和php不一定是这个，具体要咨询你们服务器开发人员。
        L.e("onSaveCookie执行");
        L.e(cookie.getName());
        if ("jsessionid".equalsIgnoreCase(cookie.getName())) {
            // 设置有效期为最大。
//            cookie.setMaxAge(HeaderUtil.getMaxExpiryMillis());
//            String cookieStore = cookie.getName().concat("=").concat(cookie.getValue()).concat(";");
            String cookieStore = cookie.getName().concat("=").concat(cookie.getValue());
            L.e(cookieStore);
            CookieSP.saveCookie(context, cookieStore);
        }
    }

    @Override
    public void onRemoveCookie(URI uri, HttpCookie cookie) {

    }
}
