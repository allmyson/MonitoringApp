package com.ys.zy.util;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lh
 * @version 1.0.0
 * @filename PermissionUtil
 * @description -------------------------------------------------------
 * @date 2018/9/18 15:16
 */
public class PermissionUtil {
    public static final String[] danger = new String[]{
            "android.permission.READ_CALENDAR",
            "android.permission.WRITE_CALENDAR",
            "android.permission.CAMERA",
            "android.permission.READ_CONTACTS",
            "android.permission.WRITE_CONTACTS",
            "android.permission.GET_ACCOUNTS",
            "android.permission.ACCESS_FINE_LOCATION",
            "android.permission.ACCESS_COARSE_LOCATION",
            "android.permission.RECORD_AUDIO",
            "android.permission.READ_PHONE_STATE",
            "android.permission.CALL_PHONE",
            "android.permission.READ_CALL_LOG",
            "android.permission.WRITE_CALL_LOG",
            "android.permission.ADD_VOICEMAIL",
            "android.permission.USE_SIP",
            "android.permission.PROCESS_OUTGOING_CALLS",
            "android.permission.BODY_SENSORS",
            "android.permission.SEND_SMS",
            "android.permission.RECEIVE_SMS",
            "android.permission.READ_SMS",
            "android.permission.RECEIVE_WAP_PUSH",
            "android.permission.RECEIVE_MMS",
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"
    };

    public static Map<String, String> dangerMap = new HashMap<>();

    static {
        //日历
        dangerMap.put("android.permission.READ_CALENDAR", "CALENDAR");
        dangerMap.put("android.permission.WRITE_CALENDAR", "CALENDAR");
        //相机
        dangerMap.put("android.permission.CAMERA", "CAMERA");
        //联系人
        dangerMap.put("android.permission.READ_CONTACTS", "CONTACTS");
        dangerMap.put("android.permission.WRITE_CONTACTS", "CONTACTS");
        dangerMap.put("android.permission.GET_ACCOUNTS", "CONTACTS");
        //位置
        dangerMap.put("android.permission.ACCESS_FINE_LOCATION", "LOCATION");
        dangerMap.put("android.permission.ACCESS_COARSE_LOCATION", "LOCATION");
        //麦克风
        dangerMap.put("android.permission.RECORD_AUDIO", "MICROPHONE");
        //手机
        dangerMap.put("android.permission.READ_PHONE_STATE", "PHONE");
        dangerMap.put("android.permission.CALL_PHONE", "PHONE");
        dangerMap.put("android.permission.READ_CALL_LOG", "PHONE");
        dangerMap.put("android.permission.WRITE_CALL_LOG", "PHONE");
        dangerMap.put("android.permission.ADD_VOICEMAIL", "PHONE");
        dangerMap.put("android.permission.USE_SIP", "PHONE");
        dangerMap.put("android.permission.PROCESS_OUTGOING_CALLS", "PHONE");
        //传感器
        dangerMap.put("android.permission.BODY_SENSORS", "SENSORS");
        //短信
        dangerMap.put("android.permission.SEND_SMS", "SMS");
        dangerMap.put("android.permission.RECEIVE_SMS", "SMS");
        dangerMap.put("android.permission.READ_SMS", "SMS");
        dangerMap.put("android.permission.RECEIVE_WAP_PUSH", "SMS");
        dangerMap.put("android.permission.RECEIVE_MMS", "SMS");
        //存储卡
        dangerMap.put("android.permission.READ_EXTERNAL_STORAGE", "STORAGE");
        dangerMap.put("android.permission.WRITE_EXTERNAL_STORAGE", "STORAGE");

    }

    /**
     * 危险权限
     *
     * @return
     */
    public static String[] getDanger() {
        return danger;
    }

    public static Map<String, String> getDangerMap() {
        return dangerMap;
    }

    /**
     * 获取应用需要申请的权限
     *
     * @param context
     * @return
     */
    public static String[] getMyDanger(Context context) {
        String[] myPermissions = SystemUtil.AppPremission(context);
        String[] allDanger = getDanger();
        List<String> list = new ArrayList<>();
        for (String s : myPermissions) {
            if (useLoop(allDanger, s)) {
                list.add(s);
            }
        }
        String[] result = list.toArray(new String[list.size()]);
        return result;
    }

    public static boolean useLoop(String[] arr, String targetValue) {
        for (String s : arr) {
            if (s.equals(targetValue))
                return true;
        }
        return false;
    }

}
