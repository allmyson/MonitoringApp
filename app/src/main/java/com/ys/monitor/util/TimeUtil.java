package com.ys.monitor.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {

    public static String getTime(int second) {
        if (second < 10) {
            return "00:00:0" + second;
        }
        if (second < 60) {
            return "00:00:" + second;
        }
        if (second < 3600) {
            int minute = second / 60;
            second = second - minute * 60;
            if (minute < 10) {
                if (second < 10) {
                    return "00:0" + minute + ":0" + second;
                }
                return "00:0" + minute + ":" + second;
            }
            if (second < 10) {
                return "00:" + minute + ":0" + second;
            }
            return "00:" + minute + ":" + second;
        }
        int hour = second / 3600;
        int minute = (second - hour * 3600) / 60;
        second = second - hour * 3600 - minute * 60;
        if (hour < 10) {
            if (minute < 10) {
                if (second < 10) {
                    return "0" + hour + ":0" + minute + ":0" + second;
                }
                return "0" + hour + ":0" + minute + ":" + second;
            }
            if (second < 10) {
                return "0" + hour + minute + ":0" + second;
            }
            return "0" + hour + minute + ":" + second;
        }
        if (minute < 10) {
            if (second < 10) {
                return hour + ":0" + minute + ":0" + second;
            }
            return hour + ":0" + minute + ":" + second;
        }
        if (second < 10) {
            return hour + minute + ":0" + second;
        }
        return hour + minute + ":" + second;
    }

    public static String getBeginTime() {
        long timeStamp = System.currentTimeMillis();  //获取当前时间戳
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String sd = sdf.format(new Date(timeStamp));      // 时间戳转换成时间
        return sd + " 00:00";
    }

    public static String getEndTime() {
        long timeStamp = System.currentTimeMillis();  //获取当前时间戳
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String sd = sdf.format(new Date(timeStamp));      // 时间戳转换成时间
        return sd + " 23:59";
    }
}
