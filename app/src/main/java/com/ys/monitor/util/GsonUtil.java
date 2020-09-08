package com.ys.monitor.util;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * @author lh
 * @version 1.0.0
 * @filename GsonUtil
 * @description -------------------------------------------------------
 * @date 2018/10/18 13:41
 */
public class GsonUtil {
    /**
     * Google Gson
     * @param jsonInString
     * @return
     */
    public final static boolean isJSONValid3(String jsonInString) {
        try {
            new Gson().fromJson(jsonInString, Object.class);
            return true;
        } catch(JsonSyntaxException ex) {
            return false;
        }
    }
}
