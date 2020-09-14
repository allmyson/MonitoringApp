package com.ys.monitor.base;

import android.support.multidex.MultiDexApplication;
import android.util.DisplayMetrics;

import com.yanzhenjie.nohttp.InitializationConfig;
import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.cookie.DBCookieStore;
import com.ys.monitor.http.CookieListener;
import com.ys.monitor.util.Constant;

/**
 * @author lh
 * @version 1.0.0
 * @filename App
 * @description -------------------------------------------------------
 * @date 2018/10/23 16:01
 */
public class App extends MultiDexApplication {
    /**
     * 屏幕宽度
     */
    public static int screenWidth;
    /**
     * 屏幕高度
     */
    public static int screenHeight;
    /**
     * 屏幕密度
     */
    public static float screenDensity;
    @Override
    public void onCreate() {
        super.onCreate();
        Constant.buildFile();
        NoHttp.initialize(InitializationConfig.newBuilder(this).cookieStore(new DBCookieStore(this).setCookieStoreListener(new CookieListener(this))).build());
        Logger.setDebug(true);
        initScreenSize();
    }

    /**
     * 初始化当前设备屏幕宽高
     */
    private void initScreenSize() {
        DisplayMetrics curMetrics = getApplicationContext().getResources().getDisplayMetrics();
        screenWidth = curMetrics.widthPixels;
        screenHeight = curMetrics.heightPixels;
        screenDensity = curMetrics.density;
    }
}
