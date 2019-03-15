package com.ys.zy.util;

import android.content.Context;

import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.ys.zy.sp.CookieSP;

/**
 * @author lh
 * @version 1.0.0
 * @filename YS
 * @description -------------------------------------------------------
 * @date 2018/11/26 17:53
 */
public class YS {
    public static final String ACTION_TZ_SUCCESS = "action_tz_success";
    public static final String UNIT = "YB";
    public static final int LENGTH = 1000;
    public static final int TYPE_CQSSC = 1000;//重庆时时彩
    public static final int TYPE_TXFFC = 1001;//腾讯分分彩
    public static final int TYPE_ZHDSLZ = 1002;//最后的胜利者

    public static final double MAX_MONEY = 125205;//最后的胜利者奖池最大金额
    public static final double MAX_SN_PRICE = 500;//当前SN最大价格
    public static final double MAX_FHFL = 12520.5;//个人返红分利最大


    public static final String KF_QQ = "8789234124";
    public static final String KF_WX = "123456";

    public static final String SUCCESE = "SUCCESS";
    public static final String FAIL = "FAIL";


    //        public static final String IP = "http://148.163.169.41:8090";
    public static final String IP = "http://103.65.180.104:8090";
    //登录
    public static final String LOGIN = IP + "/appService/loginForApp";
    //系统公告
    public static final String MSG = IP + "/appService/appNews";
    //投注
    public static final String TZ = IP + "/appService/bet";
    //开奖结果
    public static final String RESULT = IP + "/appService/getGameResultList";
    //投注记录
    public static final String TZJL = IP + "/appService/payRecords";
    //修改用户信息
    public static final String UPDATE_USERINFO = IP + "/appService/editUserForApp";
    //开户
    public static final String KH = IP + "/appService/addConsumer";
    //团队管理
    public static final String TEAM_GL = IP + "/appService/appTeam";
    //查询用户信息
    public static final String USERINFO = IP + "/appService/getSelfInfo";
    //充值
    public static final String CZ = IP + "/appService/rechargeApply";
    //提现
    public static final String TX = IP + "/appService/applyCash";
    //追号
    public static final String ZH = IP + "/appService/trackNum";
    //团队记录
    public static final String TEAM_JL = IP + "/appService/listTeamRecords";
    //胜利者数据
    public static final String WINNER_DATA = IP + "/appService/lastWinnerDetail";
    //查询投注记录
    public static final String TZJL_WINNER = IP + "/appService/getSelfRecordList";
    //最后的胜利者信息
    public static final String WINNER_INFO = IP + "/appService/getSlzGameInfo";

    //获取老板二维码
    public static final String GET_BOSS_PAY = IP + "/appService/getBossPay";
    //获取用户详细信息
    public static final String GET_USERINFO_DETAIL = IP + "/appService/getUserInfoForApp";
    //绑定用户二维码
    public static final String BIND_WX_CODE = IP + "/appService/bindAccountForApp";
    //修改资金密码
    public static final String UPDATE_ZJMM = IP + "/appService/editMoneypwdForApp";
    //修改登录密码
    public static final String UPDATE_PSD= IP + "/appService/editPwdForApp";
    //APP在线更新
    public static final String APP_VERSION= IP + "/appService/getNewApk?type=1000";


    public static GlideUrl getGlideUrl(Context context, String url) {
        return new GlideUrl(url, new LazyHeaders.Builder().addHeader("Cookie", CookieSP.getCookie(context)).build());
    }
}
