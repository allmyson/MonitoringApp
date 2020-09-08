package com.ys.monitor.util;

import android.content.Context;

import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
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

    public static final String kefuPhone= "023-67026768";
    public static final String kefuEmail= "huangjueshu@163.com";
    public static final String token = "641bd74eec96453aacad5b0acd785717";

    public static final long TIME_DELAY = 2000;//服务器时间和北京时间的差值（算上网络请求）
    public static long CURRENT_TIME_SERVER = 0;//服务器当前时间


    //53KF固定链接
//    public static final String KF_URL = "https://tb.53kf.com/code/app/d0abd1a231878826e99bfb89b70d3bbe/1?device=android";
    public static final String KF_URL = "https://tb.53kf.com/code/app/d64eddc7eed37ad1918fb55807c85863/1?device=android";
    public static final String ACTION_TZ_SUCCESS = "action_tz_success";
    public static final int LENGTH = 1000;


    public static final String SUCCESE = "200";
    public static final String FAIL = "FAIL";


    //        public static final String IP = "http://148.163.169.41:8090";
//    public static final String IP = "http://47.244.135.12:8090";
    public static final String IP = "http://219.153.13.225:8085";
    //图片验证码
    public static final String IMAGE_YZM = IP + "/KaptchaImage?52";
    //注册
    public static final String REGIST = IP + "/appService/addConsumer";
    //登录
    public static final String LOGIN = IP + "/receive/phoneLogin.mo";
    //获取游戏列表
    public static final String GAME_LIST = IP + "/appService/listGameForApp";
    //系统公告
    public static final String MSG = IP + "/appService/msgcenter/notice";
    //广告
    public static final String AD = IP + "/appService/activity/list";
    //最热游戏排名
    public static final String HOT_GAME = IP + "/appService/discover/hotGameRank";
    //昨日中奖榜
    public static final String WINNER_USER_LIST = IP + "/appService/discover/yesterdayWinRank";
    //个人信息
    public static final String USER_INFO = IP + "/appService/getUserInfoForApp";
    //根据登录名获取用户信息
    public static final String USER_INFO_BY_LOGIN = IP + "/appService/getUserByLoginNameForApp";
    //获取短信验证码
    public static final String YZM = IP + "/appService/getVerificationCode";
    //绑定手机号
    public static final String BIND_PHONE = IP + "/appService/editTelForApp";
    //找回登录密码
    public static final String FIND_LOGIN_PSD = IP + "/appService/fogetPwdForApp";
    //找回交易密码
    public static final String FIND_JY_PSD = IP + "/appService/fogetPayPwdForApp";
    //生成邀请码
    public static final String ADD_YQM = IP + "/appService/my/proxy/regcode/add";
    //邀请码列表
    public static final String YQM_LIST = IP + "/appService/my/proxy/regcode/list";
    //赔率列表
    public static final String ODD_LIST = IP + "/appService/my/proxy/odd/list";
    //查询绑定的银行卡列表
    public static final String BANK_LIST = IP + "/appService/listCardForApp";
    //添加银行卡
    public static final String ADD_BANK = IP + "/appService/my/security/bankcard/bind";
    //下级用户管理
    public static final String SUB_USER_LIST = IP + "/appService/my/proxy/sub/userlist";
    //今日盈亏
    public static final String TODAY_YK = IP + "/appService/my/report/todayWinAndLoss";
    //提现
    public static final String TX = IP + "/appService/my/center/apply/add";
    //APP下载地址
    public static final String APP_DOWNLOAD_URL = IP + "/downloadFile?type=1000";
    //获取游戏赔率
    public static final String GET_GAME_ODDS = IP + "/appService/gameBackNum";
    //获取轮盘每个生肖投注总额
    public static final String GET_LP_TZ_SUM = IP + "/appService/wheelResult";
    //获取推筒子每个类型投注总额
    public static final String GET_TTZ_TZ_SUM = IP + "/appService/ttzResult";
    //下级交易记录
    public static final String SUB_JYJL = IP + "/appService/my/proxy/sub/tradeReport";
    //我的交易记录
    public static final String MY_JYJL = IP + "/appService/my/report/tradeReport";
    //我的消费记录
    public static final String MY_XFJL = IP + "/appService/my/report/consumeReport";
    //我的活动奖金
    public static final String MY_HDJJ = IP + "/appService/my/report/activityMoney";
    //团队报表总览
    public static final String TEAM_BB_TOTAL = IP + "/appService/my/proxy/teamcount/summary";
    //团队报表列表
    public static final String TEAM_BB_LIST = IP + "/appService/my/proxy/teamcount/list";


    //投注
    public static final String TZ = IP + "/appService/bet";
    //开奖结果
    public static final String SSC_RESULT = IP + "/appService/getGameResultList";
    //投注记录
//    public static final String TZJL = IP + "/appService/payRecords";
    //修改用户信息
    public static final String UPDATE_USERINFO = IP + "/appService/editUserForApp";
    //开户
    public static final String KH = IP + "/appService/addConsumer";
    //团队管理
    public static final String TEAM_GL = IP + "/appService/appTeam";
    //查询用户信息
    public static final String USERINFO = IP + "/appService/getUserInfoForApp";
    //充值
    public static final String CZ = IP + "/appService/rechargeApply";
    //充值方式列表
    public static final String TYPE_CZ = IP + "/appService/patformaccount/list";
    //追号
    public static final String ZH = IP + "/appService/trackNum";
    //团队记录
    public static final String TEAM_JL = IP + "/appService/listTeamRecords";
    //胜利者数据
    public static final String WINNER_DATA = IP + "/appService/lastWinnerDetail";
    //查询投注记录
    public static final String TZJL = IP + "/appService/getSelfRecordList";
    //最后的胜利者信息
    public static final String WINNER_INFO = IP + "/appService/lastWinnerDetail";
    //查询胜利者开奖结果
    public static final String WINNER_RESULT = IP + "/appService/lastWinnerResult";

    //获取老板二维码
    public static final String GET_BOSS_PAY = IP + "/appService/getBossPay";
    //获取用户详细信息
    public static final String GET_USERINFO_DETAIL = IP + "/appService/getUserInfoForApp";
    //绑定用户二维码
    public static final String BIND_WX_CODE = IP + "/appService/bindAccountForApp";
    //修改资金密码
    public static final String UPDATE_ZJMM = IP + "/appService/editMoneypwdForApp";
    //修改登录密码
    public static final String UPDATE_PSD = IP + "/appService/editPwdForApp";
    //APP在线更新
    public static final String APP_VERSION = IP + "/appService/getNewApk?type=1000";
    //平台银行卡
    public static final String PATFORM_BANK = IP + "/appService/patformaccount/bank/list";


    //根据卡号获取开户行
    public static final String BANK_INFO = "https://ccdcapi.alipay.com/validateAndCacheCardInfo.json?_input_charset=utf-8&cardBinCheck=true&cardNo=";
    //basebean == null 的时候的提示语
    public static final String HTTP_TIP = "服务器错误！";


    public static GlideUrl getGlideUrl(Context context, String url) {
        return new GlideUrl(url, new LazyHeaders.Builder().addHeader("Cookie", CookieSP.getCookie(context)).build());
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
}
