package com.ys.zy.util;

import android.content.Context;
import android.graphics.Bitmap;

import com.google.gson.Gson;
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
        BaseHttp.getInstance().postSimpleJson2(context, url, "", httpListener);
    }

    //查询消息
    public static void selectMsg(Context context, HttpListener<String>
            httpListener) {
        String url = YS.MSG + "?start=1&length=1000";
        BaseHttp.getInstance().postSimpleJson2(context, url, "", httpListener);
    }

    //获取广告
    public static void getAD(Context context, HttpListener<String> httpListener) {
        String url = YS.AD + "?start=1&length=1000";
        BaseHttp.getInstance().postSimpleJson2(context, url, "", httpListener);
    }

    //获取最热游戏排名
    public static void getHotGameList(Context context, HttpListener<String> httpListener) {
        String url = YS.HOT_GAME;
        BaseHttp.getInstance().postSimpleJson2(context, url, "", httpListener);
    }

    //获取昨日中奖榜用户排名
    public static void getWinnerUserList(Context context, HttpListener<String> httpListener) {
        String url = YS.WINNER_USER_LIST + "?start=1&length=20";
        BaseHttp.getInstance().postSimpleJson2(context, url, "", httpListener);
    }

    //获取短信验证码
    public static void getYZM(Context context, String phone, HttpListener<String> httpListener) {
        String url = YS.YZM + "?phone=" + phone;
        BaseHttp.getInstance().postSimpleJson2(context, url, "", httpListener);
    }


    //绑定手机
    public static void bindPhone(Context context, String userId, String phone, HttpListener<String> httpListener) {
        String url = YS.BIND_PHONE + "?userId=" + userId + "&phone=" + phone;
        BaseHttp.getInstance().postSimpleJson(context, url, "", httpListener);
    }

    //获取银行卡列表
    public static void getBankList(Context context, String userId, HttpListener<String> httpListener) {
        String url = YS.BANK_LIST + "?userId=" + userId;
        BaseHttp.getInstance().postSimpleJson(context, url, "", httpListener);
    }

    //添加银行卡
    public static void addBank(Context context, String userId, String userName, String cardNumber, String bankName, String jymm, HttpListener<String> httpListener) {
        String url = null;
        try {
            url = YS.ADD_BANK + "?consumerId=" + userId + "&cardNumber=" + cardNumber + "&payPwd=" + Md5Util.getMD5String(jymm) + "&cardholder=" + URLEncoder.encode(userName, "utf-8") + "&bankName=" + URLEncoder.encode(bankName, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            url = "";
        }
        BaseHttp.getInstance().postSimpleJson(context, url, "", httpListener);
    }

    //获取下级用户列表
    public static void getSubUserList(Context context, String userId, HttpListener<String> httpListener) {
        String url = YS.SUB_USER_LIST + "?userId=" + userId + "&start=1&length=1000";
        BaseHttp.getInstance().postSimpleJson(context, url, "", httpListener);
    }

    //获取今日盈亏
    public static void getTodayYK(Context context, String userId, HttpListener<String> httpListener) {
        String url = YS.TODAY_YK + "?userId=" + userId;
        BaseHttp.getInstance().postSimpleJson(context, url, "", httpListener);
    }

    //团队报表总览
    public static void getTotalTeamData(Context context, String userId, HttpListener<String> httpListener) {
        String url = YS.TEAM_BB_TOTAL + "?userId=" + userId;
        BaseHttp.getInstance().postSimpleJson(context, url, "", httpListener);
    }

    //团队报表列表
    public static void getTeamBBList(Context context, String userId, HttpListener<String> httpListener) {
        String url = YS.TEAM_BB_LIST + "?userId=" + userId + "&start=1&length=" + YS.LENGTH;
        BaseHttp.getInstance().postSimpleJson(context, url, "", httpListener);
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
    public static void getSscResult(Context context, int typeCode, int num, HttpListener<String> httpListener) {
        String url = YS.SSC_RESULT + "?gameTypeCode=" + typeCode + "&gameNum=" + num;
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
     * @param httpListener
     */
    public static void getTZJL(Context context, String userId, String gameTypeCode, HttpListener<String> httpListener) {
        String url = YS.TZJL + "?userId=" + userId + "&recordTypeCode=1001&gameTypeCode=" + gameTypeCode +
                "&complantTypeCode=1000&start=1&length=1000";
        BaseHttp.getInstance().postSimpleJson2(context, url, "", httpListener);
    }

    //获取游戏赔率
    public static void getGameOdds(Context context, String userId, HttpListener<String> httpListener) {
        String url = YS.GET_GAME_ODDS + "?userId=" + userId;
        BaseHttp.getInstance().postSimpleJson2(context, url, "", httpListener);
    }

    //获取轮盘每个生肖投注总额
    public static void getLpAllTz(Context context, String periodNum, HttpListener<String> httpListener) {
        String url = YS.GET_LP_TZ_SUM + "?periodNum=" + periodNum;
        BaseHttp.getInstance().postSimpleJson2(context, url, "", httpListener);
    }

    //获取推筒子每个类型投注总额
    public static void getTtzAllTz(Context context, String periodNum, HttpListener<String> httpListener) {
        String url = YS.GET_TTZ_TZ_SUM + "?periodNum=" + periodNum;
        BaseHttp.getInstance().postSimpleJson2(context, url, "", httpListener);
    }


    //获取下级交易记录
    public static void getSubJYJL(Context context, String userId, String startTime, String endTime, HttpListener<String> httpListener) {
        String url = "";
        try {
            String start = URLEncoder.encode(startTime, "utf-8");
            String end = URLEncoder.encode(endTime, "utf-8");
            url = YS.SUB_JYJL + "?userId=" + userId + "&start=1&length=1000&startTime=" + start + "&endTime=" + end;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        BaseHttp.getInstance().postSimpleJson(context, url, "", httpListener);
    }

    //获取我的交易报表
    public static void getMyJYJL(Context context, String userId, HttpListener<String> httpListener) {
        String url = YS.MY_JYJL + "?userId=" + userId + "&start=1&length=1000";
        BaseHttp.getInstance().postSimpleJson(context, url, "", httpListener);
    }

    //获取我的活动奖金记录
    public static void getMyHdjj(Context context, String userId, HttpListener<String> httpListener) {
        String url = YS.MY_HDJJ + "?userId=" + userId + "&start=1&length=1000";
        BaseHttp.getInstance().postSimpleJson(context, url, "", httpListener);
    }

    //获取我的消费报表
    public static void getMyXFJL(Context context, String userId, HttpListener<String> httpListener) {
        String url = YS.MY_XFJL + "?userId=" + userId + "&start=1&length=1000";
        BaseHttp.getInstance().postSimpleJson(context, url, "", httpListener);
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
    public static void getUserInfoById(Context context, String userId, HttpListener<String> httpListener) {
        String url = YS.USERINFO + "?userId=" + userId;
        BaseHttp.getInstance().postSimpleJson2(context, url, "", httpListener);
    }

    /**
     * 通过电话查询用户信息
     *
     * @param context
     * @param phone
     * @param httpListener
     */
    public static void getUserInfoByPhone(Context context, String phone, HttpListener<String> httpListener) {
        String url = YS.USERINFO + "?phone=" + phone;
        BaseHttp.getInstance().postSimpleJson2(context, url, "", httpListener);
    }

    //生成邀请码
    public static void createYqm(Context context, String userId, String backNum, HttpListener<String> httpListener) {
        String url = YS.ADD_YQM + "?userId=" + userId + "&backNum=" + backNum;
        BaseHttp.getInstance().postSimpleJson(context, url, "", httpListener);
    }

    //查询邀请码列表
    public static void getYqmList(Context context, String userId, HttpListener<String> httpListener) {
        String url = YS.YQM_LIST + "?userId=" + userId + "&start=1&length=1000";
        BaseHttp.getInstance().postSimpleJson(context, url, "", httpListener);
    }

    //获取赔率列表
    public static void getOddList(Context context, HttpListener<String> httpListener) {
        String url = YS.ODD_LIST + "?start=1&length=1000";
        BaseHttp.getInstance().postSimpleJson(context, url, "", httpListener);
    }

    //获取充值平台列表
    public static void getCZList(Context context, HttpListener<String> httpListener) {
        String url = YS.TYPE_CZ;
        BaseHttp.getInstance().postSimpleJson(context, url, "", httpListener);
    }

    /**
     * 充值
     *
     * @param context
     * @param userId
     * @param money
     * @param httpListener
     */
    public static void cz(Context context, String userId, String money, String accountId, HttpListener<String> httpListener) {
        String url = YS.CZ + "?rechargeUserId=" + userId + "&rechargeNum=" + money + "&accountId=" + accountId;
        BaseHttp.getInstance().postSimpleJson(context, url, "", httpListener);
    }

    /**
     * 提现
     *
     * @param context
     * @param userId
     * @param money
     * @param jymm
     * @param httpListener
     */
    public static void tx(Context context, String userId, String money, String jymm, String bankCard, HttpListener<String> httpListener) {
        Map<String, String> jsonMap = new HashMap<>();
        jsonMap.put("cardNumber", bankCard);
        String json = new Gson().toJson(jsonMap);
        String url = YS.TX + "?userId=" + userId + "&applyMoney=" + money + "&pwd=" + Md5Util.getMD5String(jymm) + "&bankcardInfo=" + json;
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

//    /**
//     * 查询最后的胜利者投注记录
//     *
//     * @param context
//     * @param httpListener
//     */
//    public static void getWinnerTZJL(Context context, String userId, HttpListener<String> httpListener) {
//        String url = YS.TZJL_WINNER + "?userId=" + userId + "&gameTypeCode=1002&start=1&length=" + YS.LENGTH;
//        BaseHttp.getInstance().postSimpleJson2(context, url, "", httpListener);
//    }


    /**
     * 查询最后的胜利者时间信息
     *
     * @param context
     * @param httpListener
     */
    public static void getWinnerInfo(Context context, String userId, HttpListener<String> httpListener) {
        String url = YS.WINNER_INFO + "?userId=" + userId;
        BaseHttp.getInstance().postSimpleJson2(context, url, "", httpListener);
    }

    /**
     * 查询最后的胜利者时间信息
     *
     * @param context
     * @param httpListener
     */
    public static void getWinnerResult(Context context, String periodNum, HttpListener<String> httpListener) {
        String url = YS.WINNER_RESULT + "?periodNum=" + periodNum;
        BaseHttp.getInstance().postSimpleJson2(context, url, "", httpListener);
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
        String url = YS.UPDATE_ZJMM + "?userId=" + userId + "&loginPwd=" + loginPwd + "&Moneypwd=" + Md5Util.getMD5String(Moneypwd);
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
        String url = YS.UPDATE_PSD + "?userId=" + userId + "&oldPwd=" + Md5Util.getMD5String(oldPsd) + "&pwd=" + Md5Util.getMD5String(newPsd);
        BaseHttp.getInstance().postSimpleJson(context, url, "", httpListener);
    }

    /**
     * 找回登录密码
     *
     * @param context
     * @param
     * @param httpListener
     */
    public static void findLoginPsd(Context context, String phone, String newPsd, String yzm, HttpListener<String> httpListener) {
        String url = YS.FIND_LOGIN_PSD + "?phone=" + phone + "&pwd=" + Md5Util.getMD5String(newPsd) + "&CheckCode=" + yzm;
        BaseHttp.getInstance().postSimpleJson(context, url, "", httpListener);
    }

    /**
     * 找回交易密码
     *
     * @param context
     * @param
     * @param httpListener
     */
    public static void findJyPsd(Context context, String phone, String newPsd, String yzm, HttpListener<String> httpListener) {
        String url = YS.FIND_JY_PSD + "?phone=" + phone + "&pwd=" + Md5Util.getMD5String(newPsd) + "&CheckCode=" + yzm;
        BaseHttp.getInstance().postSimpleJson(context, url, "", httpListener);
    }

    //根据银行卡号获取开户行
    public static void getBankNameByCard(Context context, String card, HttpListener<String> httpListener) {
        String url = YS.BANK_INFO + card;
        BaseHttp.getInstance().simpleGet(context, url, httpListener);
    }

    //获取平台银行卡列表
    public static void getPatformBankList(Context context, HttpListener<String> httpListener) {
        String url = YS.PATFORM_BANK;
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
        BaseHttp.getInstance().postSimpleJson2(context, url, "", httpListener);
    }
}
