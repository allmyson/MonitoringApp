package com.ys.monitor.sp;

import android.content.Context;

import com.ys.monitor.util.SPUtil;

/**
 * @author lh
 * @version 1.0.0
 * @filename ContactSP
 * @description -------------------------------------------------------
 * @date 2020/9/28 15:31
 */
public class ContactSP {
    public static final String KEY_CONTACT = "jys_contact";

    public static void saveContact(Context context, String infoJson) {
        SPUtil.put(context, KEY_CONTACT, infoJson);
    }

    public static String getContactStr(Context context) {
        return (String) SPUtil.get(context, KEY_CONTACT, "");
    }
}
