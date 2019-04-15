package com.ys.zy.util;

import java.util.Calendar;
import java.util.Date;

public class GameUtil {
    /**
     * 获取轮盘当前期数  1天1440期，每分钟一期
     *
     * @return
     */
    public static String getCurrentLpPeriods() {
        return getCurrentLpPeriods(System.currentTimeMillis());
    }

    public static String getCurrentLpPeriods(long time) {
        Calendar now = Calendar.getInstance();
        now.setTime(new Date(time));
        int month = now.get(Calendar.MONTH) + 1;
        int day = now.get(Calendar.HOUR_OF_DAY);
        int hour = now.get(Calendar.HOUR_OF_DAY);
        int minute = now.get(Calendar.MINUTE);
        return toDoubleDigit(month) + toDoubleDigit(day) + toFourDigit(hour * 60 + minute);
    }

    public static String toDoubleDigit(int num) {
        if (num < 10) {
            return "0" + num;
        } else {
            return "" + num;
        }
    }

    public static String toFourDigit(int num) {
        if (num < 10) {
            return "000" + num;
        } else if (num < 100) {
            return "00" + num;
        } else if (num < 1000) {
            return "0" + num;
        } else {
            return "" + num;
        }
    }
}
