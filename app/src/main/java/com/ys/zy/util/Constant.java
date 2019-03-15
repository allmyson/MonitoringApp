package com.ys.zy.util;

import android.os.Environment;

import com.yanzhenjie.nohttp.error.NetworkError;
import com.yanzhenjie.nohttp.error.NotFoundCacheError;
import com.yanzhenjie.nohttp.error.TimeoutError;
import com.yanzhenjie.nohttp.error.URLError;
import com.yanzhenjie.nohttp.error.UnKnownHostError;
import com.yanzhenjie.nohttp.rest.Response;

import java.io.IOException;

/**
 * @author lh
 * @version 1.0.0
 * @filename Constant
 * @description -------------------------------------------------------
 * @date 2017/10/9 14:44
 */
public class Constant {
    public static final String SESSION_EXPIRED = "session_expired";//Session 已过期
    public static final String SESSION_INVALID = "session_invalid";//Session 已失效
    //登录失败errorCode
    public static final String WRONG_USER = "auth_fail";//登录用户名密码错误
    public static final String WRONG_VERIFY = "invalid_verify_code";//

//    public static String CURRENT_IP = "172.16.1.12";
//    public static String IP = "http://" + CURRENT_IP;//IP地址

    public static String PROJECT_NAME = "";
    public static String DIR_PATH;
    public static String DOWNLOAD_PATH;//文件下载保存地址
    public static String WEEX_PATH;//weex相关文件地址
    public static String TEMPIM_PATH;//拍照保存地址
    public static String VIDEO_PATH;//视频保存地址
    public static String AUDIO_PATH;//录音保存地址
    public static String CACHE_LOGE_PATH;//崩溃日志保存地址
    public static String EWM_PATH;//二维码


    public static void buildFile() {
        DIR_PATH = Environment.getExternalStorageDirectory() + "/ysgame" + (StringUtil.isBlank
                (PROJECT_NAME) ? "" : "/" + StringUtil.valueOf(PROJECT_NAME));
        DOWNLOAD_PATH = DIR_PATH + "/download";//文件下载保存地址
        WEEX_PATH = DIR_PATH + "/weex";//weex相关文件地址
        TEMPIM_PATH = DIR_PATH + "/image";//拍照保存地址
        VIDEO_PATH = DIR_PATH + "/video";//视频保存地址
        AUDIO_PATH = DIR_PATH + "/audio";//录音保存地址
        CACHE_LOGE_PATH = DIR_PATH + "/log";//崩溃日志保存地址
        EWM_PATH = DIR_PATH + "/ewm";//二维码

        try {
            FileUtil.buildFile(Constant.DOWNLOAD_PATH, true);
            FileUtil.buildFile(Constant.WEEX_PATH, true);
            FileUtil.buildFile(Constant.TEMPIM_PATH, true);
            FileUtil.buildFile(Constant.VIDEO_PATH, true);
            FileUtil.buildFile(Constant.AUDIO_PATH, true);
            FileUtil.buildFile(Constant.CACHE_LOGE_PATH, true);
            FileUtil.buildFile(Constant.EWM_PATH, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static class Url {
        //登录
        public static final String LOGIN = "/cgi/bp/sso/login";
        //登出
        public static final String LOGOUT = "/cgi/bp/sso/logout";
        //获取登录用户信息
        public static final String USERINFO = "/cgi/bp/usermgt/user/getCurrent";
        //软件版本更新
        public static final String UPDATA = "/cgi/bp/mobile/appversion/getNewestVersion";
        //三方插件列表
        public static final String PLUGIN_LIST = "/cgi/mobile/plugin/list";
        //位置上传
        public static final String UPLOAD_GPS_DATA = "/cgi/mobile/location/upload";
        //用户行为记录上传
        public static final String UPLOAD_STATISTICS = "/cgi/mobile/statistics/upload";
        //获取用户的角色列表
        public static final String USER_ROLE = "/cgi/bp/auth/userrole/listInChecked";
        //修改密码
        public static String URL_CHANGEPSD = "/cgi/bp/usermgt/user/modMyPwd";
        //图片地址获取
        public static String GETPIC = "/cgi/bp/filemgt/fileupload/get?fileId=";
        //上传头像
        public static String UPDATE_HEAD = "/cgi/bp/usermgt/user/uploadHead";
        //上传文件
        public static String UPLOAD_FILE = "/cgi/bp/filemgt/fileupload/upload";
        //修改用户信息
        public static String UPDATE_USERINFO = "/cgi/bp/usermgt/user/mod";
        //插件列表界面
        public static String PLUG_PAGE = "/cgi/bp/mobile/plugin/getPlugins";
        //相关下载列表界面
        public static String DOWNLOAD_PAGE = "/cgi/bp/mobile/filedownloadinfo/page";


    }


    public static class Receiver {
        public static final String ACTION_LOGIN_SUCCESS = "com.upsoft.sdk.action_login_success";//登录成功广播
    }


    public static class ErrorCode {
        //HTTP错误
        public static final int WRONG_CONNECT = 500;//Http错误
        public static final int WRONG_NETWORK = 501;//网络不好
        public static final int WRONG_TIME = 502;//请求超时
        public static final int WRONG_HOST = 503;//找不到服务器
        public static final int WRONG_URL = 504;//URL错误
        public static final int WRONG_CACHE = 505;//这个异常只会在仅仅查找缓存时没有找到缓存时返回
        public static final int WRONG_UNKNOW = 506;//未知异常

        //业务错误
        public static final int WRONG_NULL = 99;//服务器返回数据为空
        public static final int WRONG_USER = 100;//用户名密码错误
        public static final int WRONG_VERIFY = 101;//验证码错误
        public static final int WRONG_USERINFO = 102;//获取用户信息失败


        public int errorCode;

        public ErrorCode() {

        }

        public ErrorCode(int errorCode) {
            this.errorCode = errorCode;
        }


        public static Constant.ErrorCode getServerErrorCode(Response<String> response) {
            Constant.ErrorCode errorCode = new Constant.ErrorCode();
            if (response == null || response.getException() == null) {
                errorCode.errorCode = WRONG_CONNECT;
            }
            if (response != null && response.getException() != null) {
                Exception exception = response.getException();
                if (exception instanceof NetworkError) {// 网络不好
                    errorCode.errorCode = WRONG_NETWORK;
                } else if (exception instanceof TimeoutError) {// 请求超时
                    errorCode.errorCode = WRONG_TIME;
                } else if (exception instanceof UnKnownHostError) {// 找不到服务器
                    errorCode.errorCode = WRONG_HOST;
                } else if (exception instanceof URLError) {// URL是错的
                    errorCode.errorCode = WRONG_URL;
                } else if (exception instanceof NotFoundCacheError) {
                    // 这个异常只会在仅仅查找缓存时没有找到缓存时返回
                    errorCode.errorCode = WRONG_CACHE;
                } else {
                    errorCode.errorCode = WRONG_UNKNOW;
                }
            }
            return errorCode;
        }
    }

    //广播Action
    public static class Action {
        public static final String ACTION_SESSION_INVALID = "com.upsoft.sdk.action.sessionReceiver";
    }

}
