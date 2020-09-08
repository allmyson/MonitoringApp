package com.ys.monitor.web;

import android.app.Activity;
import android.content.Context;
import android.webkit.JavascriptInterface;

public class JSAndroid {
    private Context context;

    public JSAndroid(Context context) {
        this.context = context;
    }

    private ConfigerManagner configerManagner;

    @JavascriptInterface
    public void openAndroid(String msg) {
//        Toast.makeText(context, "返回按钮监控", Toast.LENGTH_SHORT).show();
        if (context instanceof Activity) {
            ((Activity) context).finish();
        }
    }

    @JavascriptInterface
    public void writeData(String msg) {
        configerManagner = ConfigerManagner.getInstance(context);
        configerManagner.setString("js", msg);
    }

    @JavascriptInterface
    public String giveInformation(String msg) {
        configerManagner = ConfigerManagner.getInstance(context);
        String msg1 = configerManagner.getString("js");
        return msg1;
    }
}
