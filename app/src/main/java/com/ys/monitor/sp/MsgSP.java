package com.ys.monitor.sp;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ys.monitor.bean.Msg;
import com.ys.monitor.bean.YjtzBean;
import com.ys.monitor.util.L;
import com.ys.monitor.util.SPUtil;
import com.ys.monitor.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lh
 * @version 1.0.0
 * @filename MsgSP
 * @description -------------------------------------------------------
 * @date 2020/9/28 14:42
 */
public class MsgSP {
    public static final String KEY_HELP = "jys_help";//指挥中心信息
    public static final String KEY_FIRST_MSG = "jys_first_msg";//首页第一条预警通知
    public static final String KEY_MSG = "jys_fragment_msg";//火情、预警、任务通知、指挥中心
    public static final String KEY_MSG_DETAIL = "jys_msg_detail";//预警通知


    public static void saveMsg(Context context, List<Msg> list) {
        if (list == null) {
            return;
        }
        String json = new Gson().toJson(list);
        saveMsg(context, json);
    }

    public static void saveMsg(Context context, String json) {
        SPUtil.put(context, KEY_MSG, json);
    }


    public static String getMsg(Context context) {
        return (String) SPUtil.get(context, KEY_MSG, "");
    }

    public static List<Msg> getTopList(Context context) {
        List<Msg> list = null;
        String json = getMsg(context);
        if (!StringUtil.isBlank(json) && StringUtil.isGoodJson(json)) {
            list = new Gson().fromJson(json, new TypeToken<List<Msg>>() {
            }.getType());
        }
        if (list == null || list.size() == 0) {
            list = Msg.getDefaultMsgList();
        }
        return list;
    }


    public static void saveYJList(Context context, String json) {
        SPUtil.put(context, KEY_MSG_DETAIL, json);
    }


    public static String getYJListStr(Context context) {
        return (String) SPUtil.get(context, KEY_MSG_DETAIL, "");
    }


    //缓存预警通知
    public static void saveYJTZ(Context context, List<YjtzBean.DataBean.RowsBean> list) {
        if (list == null) {
            return;
        }
        String json = new Gson().toJson(list);
        SPUtil.put(context, KEY_MSG_DETAIL, json);
    }

    public static List<YjtzBean.DataBean.RowsBean> getYJList(Context context) {
        List<YjtzBean.DataBean.RowsBean> list = null;
        String json = getYJListStr(context);
        if (!StringUtil.isBlank(json) && StringUtil.isGoodJson(json)) {
            list = new Gson().fromJson(json, new TypeToken<List<YjtzBean.DataBean.RowsBean>>() {
            }.getType());
        }
        if (list == null || list.size() == 0) {
            list = new ArrayList<>();
        }
        return list;
    }


    public static void saveFirstMsg(Context context, String json) {
        SPUtil.put(context, KEY_FIRST_MSG, json);
    }


    public static String getFirstMsg(Context context) {
        return (String) SPUtil.get(context, KEY_FIRST_MSG, "");
    }


    public static void saveHelpMsg(Context context, String json) {
        L.e("存入的help信息:" + json);
        SPUtil.put(context, KEY_HELP, json);
    }


    public static String getHelpMsg(Context context) {
        return (String) SPUtil.get(context, KEY_HELP, "");
    }
}
