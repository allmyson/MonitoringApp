package com.ys.monitor.util;

import android.content.Context;

import com.ys.monitor.http.BaseHttp;
import com.ys.monitor.http.HttpListener;

import java.net.URLEncoder;
import java.util.List;

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
                YS.FIRE_LIST + "?timeStamp=" + timeStamp + "&token=" + token + "&userID=" +
                        userId + "&page=1&limit=100&source=" + YS.SOURSE;
//        String url =
//                YS.FIRE_LIST + "?timeStamp=" + timeStamp + "&token=" + token + "&userID=" + userId + "&page=1&limit=100";

//        url="http://219.153.13.225:8085/send/queryMonitorWarnInfoPage
//        .mo?timeStamp=1597817109&token=d186b23cdbb8cb0353e5e1e0e8667d47&userID
//        =402880905e31cb26015e31cb5d290000&page=1&limit=15&source=40289fa573e69082";
        BaseHttp.getInstance().postSimpleJson(context, url, "", httpListener);
    }


    //获取火情列表
    public static void getFireListWithNoDialog(Context context, String userId,
                                               HttpListener<String> httpListener) {
        long timeStamp = System.currentTimeMillis();
        if (String.valueOf(timeStamp).length() == 13) {
            timeStamp /= 1000;
        }
        String token = Md5Util.getMD5(YS.token + timeStamp);
        String url =
                YS.FIRE_LIST + "?timeStamp=" + timeStamp + "&token=" + token + "&userID=" +
                        userId + "&page=1&limit=100&source=" + YS.SOURSE;
//        String url =
//                YS.FIRE_LIST + "?timeStamp=" + timeStamp + "&token=" + token + "&userID=" + userId + "&page=1&limit=100";

//        url="http://219.153.13.225:8085/send/queryMonitorWarnInfoPage
//        .mo?timeStamp=1597817109&token=d186b23cdbb8cb0353e5e1e0e8667d47&userID
//        =402880905e31cb26015e31cb5d290000&page=1&limit=15&source=40289fa573e69082";
        BaseHttp.getInstance().postSimpleJsonWithNoDialog(context, url, "", httpListener);
    }

    //上传文件
    public static void uploadFile(Context context, String userId, String fileType,
                                  List<String> filePath, HttpListener<String> httpListener) {
        long timeStamp = System.currentTimeMillis();
        if (String.valueOf(timeStamp).length() == 13) {
            timeStamp /= 1000;
        }
        String token = Md5Util.getMD5(YS.token + timeStamp);
        String url =
                YS.UPLOAD_FILE + "?timeStamp=" + timeStamp + "&token=" + token + "&userID=" + userId + "&type=" + fileType;
        BaseHttp.getInstance().postSimpleJson(context, url, "", filePath, httpListener);
    }

    //添加火情
    public static void addFire(Context context, String userId, String data,
                               HttpListener<String> httpListener) {
        long timeStamp = System.currentTimeMillis();
        if (String.valueOf(timeStamp).length() == 13) {
            timeStamp /= 1000;
        }
        String token = Md5Util.getMD5(YS.token + timeStamp);
        String url =
                YS.ADD_FIRE + "?timeStamp=" + timeStamp + "&token=" + token + "&userID=" + userId + "&data=" + URLEncoder.encode(data);
        BaseHttp.getInstance().postSimpleJson(context, url, "", httpListener);
    }

    //火情核查
    public static void updateFire(Context context, String userId, String data, HttpListener<String> httpListener) {
        long timeStamp = System.currentTimeMillis();
        if (String.valueOf(timeStamp).length() == 13) {
            timeStamp /= 1000;
        }
        String token = Md5Util.getMD5(YS.token + timeStamp);
        String url =
                YS.UPDATE_FIRE + "?timeStamp=" + timeStamp + "&token=" + token + "&userID=" + userId + "&data=" + URLEncoder.encode(data);
        BaseHttp.getInstance().postSimpleJson(context, url, "", httpListener);
    }


    //获取预警通知列表
    public static void getYjtzList(Context context, String userId,
                                   HttpListener<String> httpListener) {
        long timeStamp = System.currentTimeMillis();
        if (String.valueOf(timeStamp).length() == 13) {
            timeStamp /= 1000;
        }
        String token = Md5Util.getMD5(YS.token + timeStamp);
        String url =
                YS.YJTZ_LIST + "?timeStamp=" + timeStamp + "&token=" + token + "&userID=" +
                        userId + "&page=1&limit=100&source=1";
//        String url =
//                YS.FIRE_LIST + "?timeStamp=" + timeStamp + "&token=" + token + "&userID=" + userId + "&page=1&limit=100";

//        url="http://219.153.13.225:8085/send/queryMonitorWarnInfoPage
//        .mo?timeStamp=1597817109&token=d186b23cdbb8cb0353e5e1e0e8667d47&userID
//        =402880905e31cb26015e31cb5d290000&page=1&limit=15&source=40289fa573e69082";
        BaseHttp.getInstance().postSimpleJson(context, url, "", httpListener);
    }

    //获取预警通知列表
    public static void getYjtzListWithNoDialog(Context context, String userId,
                                               HttpListener<String> httpListener) {
        long timeStamp = System.currentTimeMillis();
        if (String.valueOf(timeStamp).length() == 13) {
            timeStamp /= 1000;
        }
        String token = Md5Util.getMD5(YS.token + timeStamp);
        String url =
                YS.YJTZ_LIST + "?timeStamp=" + timeStamp + "&token=" + token + "&userID=" +
                        userId + "&page=1&limit=100&source=1";
//        String url =
//                YS.FIRE_LIST + "?timeStamp=" + timeStamp + "&token=" + token + "&userID=" + userId + "&page=1&limit=100";

//        url="http://219.153.13.225:8085/send/queryMonitorWarnInfoPage
//        .mo?timeStamp=1597817109&token=d186b23cdbb8cb0353e5e1e0e8667d47&userID
//        =402880905e31cb26015e31cb5d290000&page=1&limit=15&source=40289fa573e69082";
        BaseHttp.getInstance().postSimpleJsonWithNoDialog(context, url, "", httpListener);
    }


    //获取任务通知列表
    public static void getTaskList(Context context, String userId,
                                   HttpListener<String> httpListener) {
        long timeStamp = System.currentTimeMillis();
        if (String.valueOf(timeStamp).length() == 13) {
            timeStamp /= 1000;
        }
        String token = Md5Util.getMD5(YS.token + timeStamp);
        String url =
                YS.TASK_LIST + "?timeStamp=" + timeStamp + "&token=" + token + "&userID=" +
                        userId + "&page=1&limit=100&type=4028819073d76f4d0173d7aa84d50008";
//        String url =
//                YS.FIRE_LIST + "?timeStamp=" + timeStamp + "&token=" + token + "&userID=" + userId + "&page=1&limit=100";

//        url="http://219.153.13.225:8085/send/queryMonitorWarnInfoPage
//        .mo?timeStamp=1597817109&token=d186b23cdbb8cb0353e5e1e0e8667d47&userID
//        =402880905e31cb26015e31cb5d290000&page=1&limit=15&source=40289fa573e69082";
        BaseHttp.getInstance().postSimpleJson(context, url, "", httpListener);
    }

    //获取任务通知列表
    public static void getTaskListWithNoDialog(Context context, String userId,
                                               HttpListener<String> httpListener) {
        long timeStamp = System.currentTimeMillis();
        if (String.valueOf(timeStamp).length() == 13) {
            timeStamp /= 1000;
        }
        String token = Md5Util.getMD5(YS.token + timeStamp);
        String url =
                YS.TASK_LIST + "?timeStamp=" + timeStamp + "&token=" + token + "&userID=" +
                        userId + "&page=1&limit=100&type=4028819073d76f4d0173d7aa84d50008";
//        String url =
//                YS.FIRE_LIST + "?timeStamp=" + timeStamp + "&token=" + token + "&userID=" + userId + "&page=1&limit=100";

//        url="http://219.153.13.225:8085/send/queryMonitorWarnInfoPage
//        .mo?timeStamp=1597817109&token=d186b23cdbb8cb0353e5e1e0e8667d47&userID
//        =402880905e31cb26015e31cb5d290000&page=1&limit=15&source=40289fa573e69082";
        BaseHttp.getInstance().postSimpleJsonWithNoDialog(context, url, "", httpListener);
    }

    //任务处理
    public static void updateTask(Context context, String userId, String data, HttpListener<String> httpListener) {
        long timeStamp = System.currentTimeMillis();
        if (String.valueOf(timeStamp).length() == 13) {
            timeStamp /= 1000;
        }
        String token = Md5Util.getMD5(YS.token + timeStamp);
        String url =
                YS.UPDATE_TASK + "?timeStamp=" + timeStamp + "&token=" + token + "&userID=" + userId + "&data=" + URLEncoder.encode(data);
        BaseHttp.getInstance().postSimpleJson(context, url, "", httpListener);
    }


    //获取资源分类列表
    public static void getResourceTypeList(Context context, String userId,
                                           HttpListener<String> httpListener) {
        long timeStamp = System.currentTimeMillis();
        if (String.valueOf(timeStamp).length() == 13) {
            timeStamp /= 1000;
        }
        String token = Md5Util.getMD5(YS.token + timeStamp);
        String url =
                YS.RESOURCE_TYPE_LIST + "?timeStamp=" + timeStamp + "&token=" + token + "&userID=" +
                        userId + "&page=1&limit=100&resourcetype=4028819073c3b2580173c3bbab4d0003";
        BaseHttp.getInstance().postSimpleJsonWithNoDialog(context, url, "", httpListener);
    }

    //获取资源详情
    public static void getResourceDetail(Context context, String userId,
                                         HttpListener<String> httpListener) {
        long timeStamp = System.currentTimeMillis();
        if (String.valueOf(timeStamp).length() == 13) {
            timeStamp /= 1000;
        }
        String token = Md5Util.getMD5(YS.token + timeStamp);
        String url =
                YS.RESOURCE_DETAIL + "?timeStamp=" + timeStamp + "&token=" + token + "&userID=" +
                        userId + "&elementNo=4028819073c3b2580173c3bb5f850001&recNo=40289fba74673c6b0174674ef8570035";
        BaseHttp.getInstance().postSimpleJson(context, url, "", httpListener);
    }

    //获取资源字段
    public static void getResourceZiduan(Context context, String userId, String id,
                                         HttpListener<String> httpListener) {
        long timeStamp = System.currentTimeMillis();
        if (String.valueOf(timeStamp).length() == 13) {
            timeStamp /= 1000;
        }
        String token = Md5Util.getMD5(YS.token + timeStamp);
        String url =
                YS.RESOURCE_ZIDUAN + "?timeStamp=" + timeStamp + "&token=" + token + "&userID=" +
                        userId + "&elementNo=" + id;
        BaseHttp.getInstance().postSimpleJson(context, url, "", httpListener);
    }
}
