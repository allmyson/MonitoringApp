package com.ys.monitor.sp;

import android.content.Context;

import com.google.gson.Gson;
import com.ys.monitor.bean.LoginBean;
import com.ys.monitor.util.SPUtil;
import com.ys.monitor.util.StringUtil;

/**
 * @author lh
 * @version 1.0.0
 * @filename UserSP
 * @description -------------------------------------------------------
 * @date 2018/12/3 11:15
 */
public class UserSP {
    public static final String KEY_INFO = "jys_userinfo";
    public static final String KEY_USERNAME = "jys_username";
    public static final String KEY_PASSWORD = "jys_password";
    public static final String KEY_USER = "jys_user";

    //是否登录
    public static boolean isLogin(Context context) {
        String json = getLoginInfo(context);
        if (StringUtil.isBlank(json)) {
            return false;
        } else {
            return true;
        }
    }

    public static void saveUsername(Context context, String username) {
        SPUtil.put(context, KEY_USERNAME, username);
    }

    public static void savePassword(Context context, String password) {
        //正常需加密
        SPUtil.put(context, KEY_PASSWORD, password);
    }


    public static String getUsername(Context context) {
        return (String) SPUtil.get(context, KEY_USERNAME, "");
    }

//    public static String getPassword(Context context) {
//
//    }


    public static void saveLoginInfo(Context context, String infoJson) {
        SPUtil.put(context, KEY_INFO, infoJson);
    }

    public static String getLoginInfo(Context context) {
        return (String) SPUtil.get(context, KEY_INFO, "");
    }


    public static LoginBean getLoginBean(Context context) {
        LoginBean infoBean = null;
        String json = getLoginInfo(context);
        if (!StringUtil.isBlank(json)) {
            infoBean = new Gson().fromJson(json, LoginBean.class);
        }
        return infoBean;
    }

    //获取userId
    public static String getUserId(Context context) {
        String result = "";
        LoginBean loginBean = getLoginBean(context);
        if (loginBean != null && loginBean.data != null) {
            result = loginBean.data.s_recNo;
        }
        return result;
    }


    //是否云集群用户
    public static boolean isSoftPhone(Context context){
        String result = "";
        LoginBean loginBean = getLoginBean(context);
        if (loginBean != null && loginBean.data != null) {
            result = loginBean.data.isSoftPhone;
        }
        if("1".equals(result)){
            return true;
        }else {
            return false;
        }
    }
    //云集群编号
    public static String getSoftPhoneId(Context context){
        String result = "";
        LoginBean loginBean = getLoginBean(context);
        if (loginBean != null && loginBean.data != null) {
            result = loginBean.data.softPhoneId;
        }
        return result;
    }

    public static void clear(Context context) {
        SPUtil.remove(context, KEY_INFO);
        SPUtil.remove(context, KEY_USERNAME);
        SPUtil.remove(context, KEY_PASSWORD);
        SPUtil.remove(context, KEY_USER);
    }



}
