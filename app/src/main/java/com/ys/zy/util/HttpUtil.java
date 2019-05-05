package com.ys.zy.util;

import android.content.Context;
import android.graphics.Bitmap;

import com.ys.zy.http.BaseHttp;
import com.ys.zy.http.HttpListener;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lh
 * @version 1.0.0
 * @filename HttpUtil
 * @description -------------------------------------------------------
 * @date 2018/12/3 11:00
 */
public class HttpUtil {

    //获取图片验证码
    public static void getYzmImage(Context context, HttpListener<Bitmap> httpListener) {
        String url = YS.IMAGE_YZM;
        BaseHttp.getInstance().loadImag(context, url, httpListener);
    }

    //注册
    public static void regist(Context context, String username, String psd, String yqm, String yzm, HttpListener<String> httpListener) {
        String url = YS.REGIST + "?loginName=" + username + "&pwd=" + Md5Util.getMD5String(psd) + "&regCode=" + yqm + "&capText=" + yzm + "&consumerName=" + StringUtil.getUUID();
        BaseHttp.getInstance().postSimpleJson(context, url, "", httpListener);
    }

    //登录
    public static void login(Context context, String username, String pwd, HttpListener<String> httpListener) {
        String url = YS.LOGIN + "?userName=" + username + "&pwd=" + Md5Util.getMD5String(pwd);
        BaseHttp.getInstance().postSimpleJson(context, url, "", httpListener);
    }

    //获取游戏列表
    public static void getGameList(Context context, HttpListener<String> httpListener) {
        String url = YS.GAME_LIST;
        BaseHttp.getInstance().postSimpleJson(context, url, "", httpListener);
    }

    //查询消息
    public static void selectMsg(Context context, int start, int length, HttpListener<String>
            httpListener) {
        String url = YS.MSG + "?start=" + start + "&length=" + length;
        BaseHttp.getInstance().postSimpleJson2(context, url, "", httpListener);
    }

    //投注
    public static void tz(Context context, String json, HttpListener<String> httpListener) {
        String url = YS.TZ;
        BaseHttp.getInstance().postSimpleJson(context, url, json, httpListener);
    }

    /**
     * 查询开奖结果
     *
     * @param context
     * @param typeCode 1000  时时彩    1001   分分彩
     * @param num      查询数量
     */
    public static void getKJResult(Context context, int typeCode, int num, HttpListener<String> httpListener) {
        String url = YS.RESULT + "?gameTypeCode=" + typeCode + "&gameNum=" + num;
        BaseHttp.getInstance().postSimpleJson2(context, url, "", httpListener);
    }


    /**
     * 消费记录
     *
     * @param context
     * @param userId
     * @param start
     * @param length
     * @param httpListener
     */
    public static void getXFJL(Context context, String userId, int start, int length, HttpListener<String>
            httpListener) {
        String url = YS.TZJL + "?userId=" + userId + "&start=" + start + "&length=" + length;
        BaseHttp.getInstance().postSimpleJson(context, url, "", httpListener);
    }

    /**
     * 获取投注记录
     *
     * @param context
     * @param userId
     * @param gameTypeCode
     * @param complantTypeCode
     * @param start
     * @param length
     * @param httpListener
     */
    public static void getTZJL(Context context, String userId, String gameTypeCode, String complantTypeCode, int
            start, int length,
                               HttpListener<String>
                                       httpListener) {
        String url = YS.TZJL_WINNER + "?userId=" + userId + "&recordTypeCode=1001&gameTypeCode=" + gameTypeCode +
                "&complantTypeCode=" + complantTypeCode + "&start="
                + start + "&length=" + length;
        BaseHttp.getInstance().postSimpleJson2(context, url, "", httpListener);
    }

    /**
     * 修改用户信息
     *
     * @param context
     * @param userId
     * @param nickName
     * @param photoUrl
     * @param httpListener
     */
    public static void updateUserInfo(Context context, String userId, String nickName, String photoUrl,
                                      HttpListener<String> httpListener) {
        String url = YS.UPDATE_USERINFO + "?userId=" + userId + "&userName=";
        if (!StringUtil.isBlank(nickName)) {
            try {
                url += URLEncoder.encode(nickName, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        Map<String, File> map = null;
        if (!StringUtil.isBlank(photoUrl)) {
            map = new HashMap<>();
            map.put("file", new File(photoUrl));
        }
        BaseHttp.getInstance().postFile(context, url, map, httpListener);
    }

    /**
     * 开户
     *
     * @param context
     * @param nickName
     * @param loginName
     * @param psd
     * @param fd
     * @param httpListener
     */
    public static void kh(Context context, String userId, String nickName, String loginName, String psd, String fd,
                          HttpListener<String> httpListener) {
        String consumerName = "";
        try {
            consumerName = URLEncoder.encode(nickName, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
//        String url = YS.KH + "?consumerName=" + consumerName + "&loginName=" + loginName + "&pwd=" + psd + "&backNum=" + fd + "&userId=" + userId;
        String url = YS.KH + "?consumerName=" + consumerName + "&loginName=" + loginName + "&pwd=" + psd + "&userId=" + userId;
        BaseHttp.getInstance().postSimpleJson(context, url, "", httpListener);
    }

    /**
     * 获取团队管理
     *
     * @param context
     * @param userId
     * @param httpListener
     */
    public static void getTeamData(Context context, String userId, HttpListener<String> httpListener) {
        String url = YS.TEAM_GL + "?userId=" + userId;
        BaseHttp.getInstance().postSimpleJson(context, url, "", httpListener);
    }

    /**
     * 获取用户信息
     *
     * @param context
     * @param userId
     * @param httpListener
     */
    public static void getUserInfo(Context context, String userId, HttpListener<String> httpListener) {
        String url = YS.USERINFO + "?userId=" + userId;
        BaseHttp.getInstance().postSimpleJson2(context, url, "", httpListener);
    }

    /**
     * 充值
     *
     * @param context
     * @param userId
     * @param money
     * @param httpListener
     */
    public static void cz(Context context, String userId, String money, HttpListener<String> httpListener) {
        String url = YS.CZ + "?userId=" + userId + "&applyMoney=" + money;
        BaseHttp.getInstance().postSimpleJson(context, url, "", httpListener);
    }

    /**
     * 提现
     *
     * @param context
     * @param userId
     * @param money
     * @param pwd
     * @param httpListener
     */
    public static void tx(Context context, String userId, String money, String pwd, HttpListener<String> httpListener) {
        String url = YS.TX + "?userId=" + userId + "&applyTypeCode=1000&applyMoney=" + money + "&pwd=" + pwd;
        BaseHttp.getInstance().postSimpleJson(context, url, "", httpListener);
    }


    //追号
    public static void zh(Context context, String json, HttpListener<String> httpListener) {
        String url = YS.ZH;
        BaseHttp.getInstance().postSimpleJson(context, url, json, httpListener);
    }

    //查询团队记录

    /**
     * @param context
     * @param userId
     * @param type    1000充值记录，1001消费记录
     */
    public static void getTeamJL(Context context, String userId, String type, HttpListener<String> httpListener) {
        String url = YS.TEAM_JL + "?userId=" + userId + "&recordTypeCode=" + type + "&start=1&length=" + YS.LENGTH;
        BaseHttp.getInstance().postSimpleJson(context, url, "", httpListener);
    }

    /**
     * 获取最后的胜利者数据
     *
     * @param context
     * @param userId
     * @param httpListener
     */
    public static void getWinnerData(Context context, String userId, HttpListener<String> httpListener) {
        String url = YS.WINNER_DATA + "?userId=" + userId;
        BaseHttp.getInstance().postSimpleJson2(context, url, "", httpListener);
    }

    /**
     * 查询最后的胜利者投注记录
     *
     * @param context
     * @param httpListener
     */
    public static void getWinnerTZJL(Context context, String userId, HttpListener<String> httpListener) {
        String url = YS.TZJL_WINNER + "?userId=" + userId + "&gameTypeCode=1002&start=1&length=" + YS.LENGTH;
        BaseHttp.getInstance().postSimpleJson2(context, url, "", httpListener);
    }


    /**
     * 查询最后的胜利者时间信息
     *
     * @param context
     * @param httpListener
     */
    public static void getWinnerInfo(Context context, String periodsNum, HttpListener<String> httpListener) {
        String url = YS.WINNER_INFO + "?periodsNum=" + periodsNum;
        BaseHttp.getInstance().postSimpleJson(context, url, "", httpListener);
    }

    /**
     * 获取老板二维码
     *
     * @param context
     * @param httpListener
     */
    public static void getBossPay(Context context, HttpListener<String> httpListener) {
        String url = YS.GET_BOSS_PAY;
        BaseHttp.getInstance().postSimpleJson(context, url, "", httpListener);
    }

    /**
     * 获取用户详细信息
     *
     * @param context
     * @param userId
     * @param httpListener
     */
    public static void getDetailUserInfo(Context context, String userId, HttpListener<String> httpListener) {
        String url = YS.GET_USERINFO_DETAIL + "?userId=" + userId;
        BaseHttp.getInstance().postSimpleJson(context, url, "", httpListener);
    }

    /**
     * 绑定收款码
     *
     * @param context
     * @param userId
     * @param filePath
     * @param httpListener
     */
    public static void bindUserCode(Context context, String userId, String filePath, HttpListener<String> httpListener) {
        String url = YS.BIND_WX_CODE + "?userId=" + userId;
        Map<String, File> map = null;
        if (!StringUtil.isBlank(filePath)) {
            map = new HashMap<>();
            map.put("file", new File(filePath));
        }
        BaseHttp.getInstance().postFile(context, url, map, httpListener);
    }

    /**
     * 修改资金密码
     *
     * @param context
     * @param userId
     * @param httpListener
     */
    public static void updateZJMM(Context context, String userId, String loginPwd, String Moneypwd, HttpListener<String> httpListener) {
        String url = YS.UPDATE_ZJMM + "?userId=" + userId + "&loginPwd=" + loginPwd + "&Moneypwd=" + Moneypwd;
        BaseHttp.getInstance().postSimpleJson(context, url, "", httpListener);
    }

    /**
     * 修改登录密码
     *
     * @param context
     * @param userId
     * @param httpListener
     */
    public static void updatePsd(Context context, String userId, String oldPsd, String newPsd, HttpListener<String> httpListener) {
        String url = YS.UPDATE_PSD + "?userId=" + userId + "&oldPwd=" + oldPsd + "&pwd=" + newPsd;
        BaseHttp.getInstance().postSimpleJson(context, url, "", httpListener);
    }

    /**
     * 获取APP最新版本数据
     *
     * @param context
     * @param httpListener
     */
    public static void getAppVersion(Context context, HttpListener<String> httpListener) {
        String url = YS.APP_VERSION;
        BaseHttp.getInstance().postSimpleJson(context, url, "", httpListener);
    }
}
