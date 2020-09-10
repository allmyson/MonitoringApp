package com.ys.monitor.util;

import android.content.Context;

import com.ys.monitor.http.BaseHttp;
import com.ys.monitor.http.HttpListener;

/**
 * @author lh
 * @version 1.0.0
 * @filename HttpUtil
 * @description -------------------------------------------------------
 * @date 2018/12/3 11:00
 */
public class HttpUtil {


    //登录
    public static void login(Context context, String username, String pwd,
                             HttpListener<String> httpListener) {
        long timeStamp = System.currentTimeMillis();
        if (String.valueOf(timeStamp).length() == 13) {
            timeStamp /= 1000;
        }
        String token = Md5Util.getMD5(YS.token + timeStamp);
        String imei = SystemUtil.IMEI(context);
        String url =
                YS.LOGIN + "?userName=" + username + "&password=" + Md5Util.getMD5(pwd) +
                        "&timeStamp=" + timeStamp + "&token=" + token + "&IMEI=" + imei;
        BaseHttp.getInstance().postSimpleJson(context, url, "", httpListener);
    }

    //获取火情列表
    public static void getFireList(Context context, String userId,
                                   HttpListener<String> httpListener) {
        long timeStamp = System.currentTimeMillis();
        if (String.valueOf(timeStamp).length() == 13) {
            timeStamp /= 1000;
        }
        String token = Md5Util.getMD5(YS.token + timeStamp);
        String url =
                YS.FIRE_LIST + "?timeStamp=" + timeStamp + "&token=" + token + "&userID=" + userId + "&page=1&limit=100&source="+YS.SOURSE;

//        url="http://219.153.13.225:8085/send/queryMonitorWarnInfoPage.mo?timeStamp=1597817109&token=d186b23cdbb8cb0353e5e1e0e8667d47&userID=402880905e31cb26015e31cb5d290000&page=1&limit=15&source=40289fa573e69082";
        BaseHttp.getInstance().postSimpleJson(context, url, "", httpListener);
    }


}
