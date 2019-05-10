package com.ys.zy.web;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

import com.yanzhenjie.nohttp.NoHttp;
import com.ys.zy.sp.CookieSP;
import com.ys.zy.util.L;

import java.net.HttpCookie;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lh
 * @version 1.0.0
 * @filename UpsoftWebview
 * @description -------------------------------------------------------
 * @date 2017/10/13 14:05
 */
public class UpsoftWebview extends WebView {

    public UpsoftWebview(Context context) {
        super(context);
    }

    public UpsoftWebview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public UpsoftWebview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void loadUrl(String url, Map<String, String> additionalHttpHeaders) {

        if (url != null && url.startsWith("javascript")) {
            //处理upsoftsdk回调
            super.loadUrl(url, additionalHttpHeaders);
            return;
        }

        if (additionalHttpHeaders == null) {
            additionalHttpHeaders = new HashMap<>();
        }
//
//        // 这里你还可以添加一些自定头。
//        additionalHttpHeaders.put("AppVersion", "1.0.0"); // 比如添加app版本信息，当然实际开发中要自动获取哦。
        //首次加载本地网页的时候设置cookie
        if (url != null && url.startsWith("file")) {
//            String cookieUrl = FunctionApi.getIp(getContext());
            String cookieUrl = "";
            String cookie = CookieSP.getCookie(getContext());
            String cookieValue = cookie + "path=/;domain=" + cookieUrl;
            L.e("file---cookieValue=" + cookieValue);
            // 同步到WebView。
            android.webkit.CookieManager webCookieManager = android.webkit.CookieManager.getInstance();
            webCookieManager.setAcceptCookie(true);
            webCookieManager.setCookie(cookieUrl, cookieValue);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                webCookieManager.flush();
            } else {
                android.webkit.CookieSyncManager.createInstance(NoHttp.getContext()).sync();
            }
            super.loadUrl(url, additionalHttpHeaders);
            return;
        }

        //处理在线网页cookie
        URI uri = null;
        try {
            uri = new URI(url);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        if (uri != null) {
//            java.net.CookieStore cookieStore = NoHttp.getCookieManager().getCookieStore();
            java.net.CookieStore cookieStore = NoHttp.getInitializeConfig().getCookieStore();
            List<HttpCookie> cookies = cookieStore.get(uri);

            // 同步到WebView。
            android.webkit.CookieManager webCookieManager = android.webkit.CookieManager.getInstance();
            webCookieManager.setAcceptCookie(true);
            for (HttpCookie cookie : cookies) {
                String cookieUrl = cookie.getDomain();
                String cookieValue = cookie.getName() + "=" + cookie.getValue()
                        + "; path=" + cookie.getPath()
                        + "; domain=" + cookie.getDomain();

                webCookieManager.setCookie(cookieUrl, cookieValue);
            }

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                webCookieManager.flush();
            } else {
                android.webkit.CookieSyncManager.createInstance(NoHttp.getContext()).sync();
            }
        }
        super.loadUrl(url, additionalHttpHeaders);
    }

    @Override
    public void loadUrl(String url) {
        loadUrl(url, null);
    }
}
