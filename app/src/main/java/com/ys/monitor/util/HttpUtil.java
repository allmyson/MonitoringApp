package com.ys.monitor.util;

import android.content.Context;

import com.yanzhenjie.nohttp.rest.Response;
import com.ys.monitor.bean.RecordBean;
import com.ys.monitor.http.BaseHttp;
import com.ys.monitor.http.HttpListener;

import java.io.UnsupportedEncodingException;
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
                        userId + "&page=1&limit=100";
//        String url =
//                YS.FIRE_LIST + "?timeStamp=" + timeStamp + "&token=" + token + "&userID=" +
//                userId + "&page=1&limit=100";

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
                        userId + "&page=1&limit=100";
//        String url =
//                YS.FIRE_LIST + "?timeStamp=" + timeStamp + "&token=" + token + "&userID=" +
//                userId + "&page=1&limit=100";

//        url="http://219.153.13.225:8085/send/queryMonitorWarnInfoPage
//        .mo?timeStamp=1597817109&token=d186b23cdbb8cb0353e5e1e0e8667d47&userID
//        =402880905e31cb26015e31cb5d290000&page=1&limit=15&source=40289fa573e69082";
        BaseHttp.getInstance().postSimpleJsonWithNoDialog(context, url, "", httpListener);
    }

    //异步上传文件
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

    //异步上传文件
    public static void uploadFileWithNoDialog(Context context, String userId, String fileType,
                                              List<String> filePath,
                                              HttpListener<String> httpListener) {
        long timeStamp = System.currentTimeMillis();
        if (String.valueOf(timeStamp).length() == 13) {
            timeStamp /= 1000;
        }
        String token = Md5Util.getMD5(YS.token + timeStamp);
        String url =
                YS.UPLOAD_FILE + "?timeStamp=" + timeStamp + "&token=" + token + "&userID=" + userId + "&type=" + fileType;
        BaseHttp.getInstance().postSimpleJson(context, url, "", filePath, httpListener, false);
    }

    //同步上传文件
    public static Response<String> uploadFile(Context context, String userId, String fileType,
                                              List<String> filePath) {
        long timeStamp = System.currentTimeMillis();
        if (String.valueOf(timeStamp).length() == 13) {
            timeStamp /= 1000;
        }
        String token = Md5Util.getMD5(YS.token + timeStamp);
        String url =
                YS.UPLOAD_FILE + "?timeStamp=" + timeStamp + "&token=" + token + "&userID=" + userId + "&type=" + fileType;
        Response<String> response = BaseHttp.getInstance().postFileSync(context, url, "", filePath);
        return response;
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

    //同步添加火情
    public static Response<String> addFireSync(Context context, String userId, String data
    ) {
        long timeStamp = System.currentTimeMillis();
        if (String.valueOf(timeStamp).length() == 13) {
            timeStamp /= 1000;
        }
        String token = Md5Util.getMD5(YS.token + timeStamp);
        String url =
                YS.ADD_FIRE + "?timeStamp=" + timeStamp + "&token=" + token + "&userID=" + userId + "&data=" + URLEncoder.encode(data);
        Response<String> response = BaseHttp.getInstance().postJsonSync(context, url, "");
        return response;
    }

    //火情核查
    public static void updateFire(Context context, String userId, String data,
                                  HttpListener<String> httpListener) {
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
        BaseHttp.getInstance().postSimpleJsonWithNoDialog(context, url, "", httpListener);
    }

    //任务处理
    public static void updateTask(Context context, String userId, String recNo,
                                  HttpListener<String> httpListener) {
        long timeStamp = System.currentTimeMillis();
        if (String.valueOf(timeStamp).length() == 13) {
            timeStamp /= 1000;
        }
        String token = Md5Util.getMD5(YS.token + timeStamp);
        String url =
                YS.UPDATE_TASK + "?timeStamp=" + timeStamp + "&token=" + token + "&userID=" + userId + "&recNo=" + recNo;
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
                        userId + "&elementNo=4028819073c3b2580173c3bb5f850001&recNo" +
                        "=40289fba74673c6b0174674ef8570035";
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

    //资源上报
    public static void addResource(Context context, String userId, String data,
                                   HttpListener<String> httpListener) {
        long timeStamp = System.currentTimeMillis();
        if (String.valueOf(timeStamp).length() == 13) {
            timeStamp /= 1000;
        }
        String token = Md5Util.getMD5(YS.token + timeStamp);
        String dataCode;
        try {
            dataCode = URLEncoder.encode(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            dataCode = URLEncoder.encode(data);
            e.printStackTrace();
        }
        String url =
                YS.ADD_RESOURCE + "?timeStamp=" + timeStamp + "&token=" + token + "&userID=" + userId + "&data=" + dataCode;
        BaseHttp.getInstance().postSimpleJson(context, url, "", httpListener);
    }


    //获取设备列表
    public static void getVideoList(Context context, String userId,
                                    HttpListener<String> httpListener) {
        long timeStamp = System.currentTimeMillis();
        if (String.valueOf(timeStamp).length() == 13) {
            timeStamp /= 1000;
        }
        String token = Md5Util.getMD5(YS.token + timeStamp);
        String url =
                YS.VIDEO_LIST + "?timeStamp=" + timeStamp + "&token=" + token + "&userID=" +
                        userId + "&page=1&limit=100";
        BaseHttp.getInstance().postSimpleJson(context, url, "", httpListener);
    }

    //获取设备播放地址
    public static void getVideoUrl(Context context, String userId, String recNo,
                                   HttpListener<String> httpListener) {
        long timeStamp = System.currentTimeMillis();
        if (String.valueOf(timeStamp).length() == 13) {
            timeStamp /= 1000;
        }
        String token = Md5Util.getMD5(YS.token + timeStamp);
        String url =
                YS.RTSP + "?timeStamp=" + timeStamp + "&token=" + token + "&userID=" +
                        userId + "&recNo=" + recNo;
        //本地测试
        url = "http://192.168.31.188:8080/send/queryDeviceInfoByNo.mo" + "?timeStamp=" + timeStamp + "&token=" + token + "&userID=" +
                userId + "&recNo=" + recNo;
        BaseHttp.getInstance().postSimpleJson(context, url, "", httpListener);
    }


    //上传扑救信息
    public static void addHelpMsgWithNoDialog(Context context, String userId, String data,
                                              HttpListener<String> httpListener) {
        long timeStamp = System.currentTimeMillis();
        if (String.valueOf(timeStamp).length() == 13) {
            timeStamp /= 1000;
        }
        String token = Md5Util.getMD5(YS.token + timeStamp);
        String dataCode;
        try {
            dataCode = URLEncoder.encode(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            dataCode = URLEncoder.encode(data);
            e.printStackTrace();
        }
        String url =
                YS.ADD_HELP_MSG + "?timeStamp=" + timeStamp + "&token=" + token + "&userID=" + userId + "&data=" + dataCode;
        BaseHttp.getInstance().postSimpleJsonWithNoDialog(context, url, "", httpListener);
    }

    //添加巡护
    public static void addXH(Context context, String userId, String data,
                             HttpListener<String> httpListener) {
        long timeStamp = System.currentTimeMillis();
        if (String.valueOf(timeStamp).length() == 13) {
            timeStamp /= 1000;
        }
        String token = Md5Util.getMD5(YS.token + timeStamp);
        String url =
                YS.ADD_XH + "?timeStamp=" + timeStamp + "&token=" + token + "&userID=" + userId + "&data=" + URLEncoder.encode(data);
        BaseHttp.getInstance().postSimpleJson(context, url, "", httpListener);
    }

    //获取机构列表
    public static void getOrgList(Context context, String userId,
                                  HttpListener<String> httpListener) {
        long timeStamp = System.currentTimeMillis();
        if (String.valueOf(timeStamp).length() == 13) {
            timeStamp /= 1000;
        }
        String token = Md5Util.getMD5(YS.token + timeStamp);
        String url =
                YS.ORG_LIST + "?timeStamp=" + timeStamp + "&token=" + token + "&userID=" +
                        userId + "&page=1&limit=100";
        BaseHttp.getInstance().postSimpleJson(context, url, "", httpListener);
    }


    //获取用户列表
    public static void getUserList(Context context, String userId,
                                   HttpListener<String> httpListener) {
        long timeStamp = System.currentTimeMillis();
        if (String.valueOf(timeStamp).length() == 13) {
            timeStamp /= 1000;
        }
        String token = Md5Util.getMD5(YS.token + timeStamp);
        String url =
                YS.USER_LIST + "?timeStamp=" + timeStamp + "&token=" + token + "&userID=" +
                        userId + "&page=1&limit=1000";
        BaseHttp.getInstance().postSimpleJson(context, url, "", httpListener);
    }

    //获取天气  105 是重庆 111是北碚  北碚没得AQI
    public static void getWeather(Context context, int weaid, HttpListener<String> httpListener) {
        String url = YS.WEATHER + "&weaid=" + weaid;
        BaseHttp.getInstance().simpleGet(context, url, httpListener);

    }

    //获取天气    北碚101040800
    public static void getHFWeather(Context context, HttpListener<String> httpListener) {
        String url = YS.HF_WEATHER;
        BaseHttp.getInstance().simpleGet(context, url, httpListener);

    }

    //获取天气    重庆101040100
    public static void getHFAQI(Context context, HttpListener<String> httpListener) {
        String url = YS.HF_AQI;
        BaseHttp.getInstance().simpleGet(context, url, httpListener);

    }


    //更换头像
    public static void uploadHeadImg(Context context, String userId, String icon,
                                     HttpListener<String> httpListener) {
        long timeStamp = System.currentTimeMillis();
        if (String.valueOf(timeStamp).length() == 13) {
            timeStamp /= 1000;
        }
        String token = Md5Util.getMD5(YS.token + timeStamp);
        String url =
                YS.UPLOAD_HEAD + "?timeStamp=" + timeStamp + "&token=" + token + "&userID=" +
                        userId + "&icon=" + icon;
        BaseHttp.getInstance().postSimpleJson(context, url, "", httpListener);
    }


    //同步添加火情、巡护、资源
    public static Response<String> addDataSync(Context context, String userId, String data,
                                               String type, String handleType) {
        long timeStamp = System.currentTimeMillis();
        if (String.valueOf(timeStamp).length() == 13) {
            timeStamp /= 1000;
        }
        String token = Md5Util.getMD5(YS.token + timeStamp);
        String baseUrl = YS.ADD_FIRE;
        if (RecordBean.TYPE_FIRE.equals(type)) {
            baseUrl = YS.ADD_FIRE;
        } else if (RecordBean.TYPE_XUHU.equals(type)) {
            baseUrl = YS.ADD_XH;
        } else if (RecordBean.TYPE_ZIYUAN.equals(type)) {
            baseUrl = YS.ADD_RESOURCE;
        }
        String url =
                baseUrl + "?timeStamp=" + timeStamp + "&token=" + token + "&userID=" + userId;
        if (RecordBean.TYPE_ZIYUAN.equals(type)) {
            if (RecordBean.DO_ADD.equals(handleType)) {
                url += "&operationType=0";
            } else if (RecordBean.DO_UPDATE.equals(handleType)) {
                url += "&operationType=1";
            } else if (RecordBean.DO_DELETE.equals(handleType)) {
                url += "&operationType=2";
            }
        }
        url += "&data=" + URLEncoder.encode(data);
        Response<String> response = BaseHttp.getInstance().postJsonSync(context, url, "");
        return response;
    }


    //获取资源详情
    public static void getResourceValue(Context context, String userId, String id,
                                        HttpListener<String> httpListener) {
        long timeStamp = System.currentTimeMillis();
        if (String.valueOf(timeStamp).length() == 13) {
            timeStamp /= 1000;
        }
        String token = Md5Util.getMD5(YS.token + timeStamp);
        String url =
                YS.SELECT_RESOURCE + "?timeStamp=" + timeStamp + "&token=" + token + "&userID=" +
                        userId + "&recNo=" + "620e65bc427c32702917fd8dfaeb0004";
        BaseHttp.getInstance().postSimpleJson(context, url, "", httpListener);
    }
}
