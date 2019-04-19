package com.ys.zy.ssc;

import com.ys.zy.ssc.activity.SscActivity;
import com.ys.zy.util.GameUtil;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.ys.zy.util.GameUtil.toThreeDigit;

public class SscUtil {

    public static String getNumber(int i) {
        if (i < 10) {
            return "0" + i;
        } else {
            return "" + i;
        }
    }

    /**
     * 获取1分彩当前期数  1分钟1 期，每天1440 期，
     *
     * @return
     */
    public static String getCurrent1FCPeriods() {
        Calendar now = Calendar.getInstance();
        now.setTime(new Date());
        int month = now.get(Calendar.MONTH) + 1;
        int day = now.get(Calendar.HOUR_OF_DAY);
        int hour = now.get(Calendar.HOUR_OF_DAY);
        int minute = now.get(Calendar.MINUTE);
        return getNumber(month) + getNumber(day) + GameUtil.toFourDigit(hour * 60 + minute + 1);
    }

    /**
     * 获取一分彩加qi后的期数
     *
     * @param qi
     * @return
     */
    public static String get1FCPeriods(int qi) {
        Calendar now = Calendar.getInstance();
        now.setTime(new Date());
        int month = now.get(Calendar.MONTH) + 1;
        int day = now.get(Calendar.DAY_OF_MONTH);
        int hour = now.get(Calendar.HOUR_OF_DAY);
        int minute = now.get(Calendar.MINUTE);
        int qishu = hour * 60 + minute + 1 + qi - 1;
        if (qishu > 1440) {
            qishu = 1440;
        }
        return getNumber(month) + getNumber(day) + GameUtil.toFourDigit(qishu);
    }

    /**
     * 获取时时彩当前期数  10分钟1 期，每天144期，
     *
     * @return
     */
    public static String getCurrentSscPeriods() {
        Calendar now = Calendar.getInstance();
        now.setTime(new Date());
        int month = now.get(Calendar.MONTH) + 1;
        int day = now.get(Calendar.DAY_OF_MONTH);
        int hour = now.get(Calendar.HOUR_OF_DAY);
        int minute = now.get(Calendar.MINUTE);
        return getNumber(month) + getNumber(day) + toThreeDigit((hour * 60 + minute) / 10 + 1);
    }

    /**
     * 获取时时彩加qi期后的期数
     *
     * @return
     */
    public static String getSscPeriods(int qi) {
        Calendar now = Calendar.getInstance();
        now.setTime(new Date());
        int month = now.get(Calendar.MONTH) + 1;
        int day = now.get(Calendar.DAY_OF_MONTH);
        int hour = now.get(Calendar.HOUR_OF_DAY);
        int minute = now.get(Calendar.MINUTE);
        int qishu = (hour * 60 + minute) / 10 + 1 + qi - 1;
        if (qishu > 144) {
            qishu = 144;
        }
        return getNumber(month) + getNumber(day) + toThreeDigit(qishu);
    }

    public static String getSscPeriods(int type, int qi) {
        String result = "";
        switch (type) {
            case SscActivity.TYPE_SSC:
                result = getSscPeriods(qi);
                break;
            case SscActivity.TYPE_1FC:
                result = get1FCPeriods(qi);
                break;
        }
        return result;
    }

    public static int getJGTime(int type) {
        int jgTime = 1;
        switch (type) {
            case SscActivity.TYPE_SSC:
                jgTime = 10;
                break;
            case SscActivity.TYPE_1FC:
                jgTime = 1;
                break;
        }
        return jgTime;
    }

    public static List<Integer> getRandomResultList(int num) {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            result.add(num);
        }
        return result;
    }
}
