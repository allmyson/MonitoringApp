package com.ys.zy.util;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lh
 * @version 1.0.0
 * @filename ActivityUtil
 * @description -------------------------------------------------------
 * @date 2017/5/23 17:40
 */
public class ActivityUtil {
    public static List<Activity> list = new ArrayList<>();

    public static void addActivity(Activity activity) {
        list.add(activity);
    }
    public static void removeActivity(Activity activity) {
        list.remove(activity);
    }
    public static void finish() {
        for (Activity activity : list) {
            activity.finish();
        }
    }

    public static void finish(Activity activity){
        list.remove(activity);
        activity.finish();
    }


}
