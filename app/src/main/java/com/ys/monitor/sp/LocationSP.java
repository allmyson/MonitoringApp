package com.ys.monitor.sp;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ys.monitor.util.SPUtil;
import com.ys.monitor.util.StringUtil;

import java.util.HashMap;
import java.util.Map;


/**
 * @author lh
 * @version 1.0.0
 * @filename CookieSP
 * @description -------------------------------------------------------
 * @date 2017/10/9 13:56
 */
public class LocationSP {
    private static final String KEY_LOCATION = "location";

    public static void saveLocation(Context context, String json) {
        SPUtil.put(context, KEY_LOCATION, StringUtil.valueOf(json));
    }

    public static String getLocationJson(Context context) {
        return (String) SPUtil.get(context, KEY_LOCATION, "");
    }

    public static Map<String, Object> getLocationData(Context context) {
        Map<String, Object> map = null;
        String json = getLocationJson(context);
        if (StringUtil.isGoodJson(json)) {
            map = new Gson().fromJson(json, new TypeToken<Map<String, Object>>() {
            }.getType());
        }
        return map;
    }

    public static void saveLocation(Context context, double latitude, double longitude,
                                    String address) {
        Map<String, Object> map = new HashMap();
        map.put("lat", latitude);
        map.put("lon", longitude);
        map.put("address", address);
        String json = new Gson().toJson(map);
        saveLocation(context, json);
    }
}
