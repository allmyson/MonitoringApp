package com.ys.monitor.sp;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ys.monitor.bean.RecordBean;
import com.ys.monitor.util.SPUtil;
import com.ys.monitor.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lh
 * @version 1.0.0
 * @filename RecordSP
 * @description -------------------------------------------------------
 * @date 2020/10/26 17:27
 */
public class RecordSP {
    public static final String KEY_RECORD = "ys_record";//


    public static void saveRecord(Context context, List<RecordBean> list) {
        if (list == null) {
            return;
        }
        String json = new Gson().toJson(list);
        saveRecord(context, json);
    }

    public static void saveRecord(Context context, String json) {
        SPUtil.put(context, KEY_RECORD, json);
    }


    public static String getRecord(Context context) {
        return (String) SPUtil.get(context, KEY_RECORD, "");
    }

    public static List<RecordBean> getRecordList(Context context) {
        List<RecordBean> list = null;
        String json = getRecord(context);
        if (!StringUtil.isBlank(json) && StringUtil.isGoodJson(json)) {
            list = new Gson().fromJson(json, new TypeToken<List<RecordBean>>() {
            }.getType());
        }
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    public static void addRecord(Context context, RecordBean recordBean) {
        List<RecordBean> list = getRecordList(context);
        list.add(recordBean);
        saveRecord(context, list);
    }
}
