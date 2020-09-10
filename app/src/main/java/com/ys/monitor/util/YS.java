package com.ys.monitor.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.ys.monitor.R;
import com.ys.monitor.sp.CookieSP;

import java.util.Date;

/**
 * @author lh
 * @version 1.0.0
 * @filename YS
 * @description -------------------------------------------------------
 * @date 2018/11/26 17:53
 */
public class YS {
    public static final String testImageUrl = "https://bkimg.cdn.bcebos" +
            ".com/pic/b219ebc4b74543a9a8a81e741d178a82b80114d2";
    public static final String kefuPhone = "023-67026768";
    public static final String kefuEmail = "huangjueshu@163.com";
    public static final String token = "641bd74eec96453aacad5b0acd785717";

    public static final long TIME_DELAY = 2000;//服务器时间和北京时间的差值（算上网络请求）
    public static final String SOURSE = "40289fa573e690820173e6a6437a0004";
    public static long CURRENT_TIME_SERVER = 0;//服务器当前时间


    //53KF固定链接
//    public static final String KF_URL = "https://tb.53kf
//    .com/code/app/d0abd1a231878826e99bfb89b70d3bbe/1?device=android";
    public static final String KF_URL = "https://tb.53kf" +
            ".com/code/app/d64eddc7eed37ad1918fb55807c85863/1?device=android";
    public static final String ACTION_TZ_SUCCESS = "action_tz_success";
    public static final int LENGTH = 1000;


    public static final String SUCCESE = "200";
    public static final String FAIL = "FAIL";


    //        public static final String IP = "http://148.163.169.41:8090";
//    public static final String IP = "http://47.244.135.12:8090";
    public static final String IP = "http://219.153.13.225:8085";
    //登录
    public static final String LOGIN = IP + "/receive/phoneLogin.mo";
    //火情列表
    public static final String FIRE_LIST = IP + "/send/queryMonitorWarnInfoPage.mo";
    //basebean == null 的时候的提示语
    public static final String HTTP_TIP = "服务器错误！";


    public static GlideUrl getGlideUrl(Context context, String url) {
        return new GlideUrl(url, new LazyHeaders.Builder().addHeader("Cookie",
                CookieSP.getCookie(context)).build());
    }

    public static long currentTimeMillis() {
        if (CURRENT_TIME_SERVER == 0) {
            return System.currentTimeMillis();
        }
        return CURRENT_TIME_SERVER;
    }

    public static void setCurrentTimeMillis(long time) {
        CURRENT_TIME_SERVER = time;
    }

    public static Date getCurrentDate() {
        if (CURRENT_TIME_SERVER == 0) {
            return new Date();
        }
        return new Date(CURRENT_TIME_SERVER);
    }

    public static void add() {
        if (CURRENT_TIME_SERVER != 0) {
            CURRENT_TIME_SERVER += 1000;
        }
    }


    public static void showRoundImage(Context context, String url, ImageView imageView) {
        showRoundImage(context, url, imageView, 4);

    }

    public static void showRoundImage(Context context, String url, ImageView imageView, int round) {
        Glide.with(context)
                .load(url)
                .transform(new CenterCrop(context), new GlideRoundTransform(context, round))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.mipmap.blank_inf_img)
                .dontAnimate()
                .crossFade()
                .into(imageView);
    }
}
