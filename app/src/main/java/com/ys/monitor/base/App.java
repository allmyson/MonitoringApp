package com.ys.monitor.base;

import android.support.multidex.MultiDexApplication;

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
    @Override
    public void onCreate() {
        super.onCreate();
        Constant.buildFile();
        NoHttp.initialize(InitializationConfig.newBuilder(this).cookieStore(new DBCookieStore(this).setCookieStoreListener(new CookieListener(this))).build());
        Logger.setDebug(true);
    }
}
