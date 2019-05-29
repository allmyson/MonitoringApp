package com.ys.zy.fast3;

import com.ys.zy.R;
import com.ys.zy.fast3.activity.Fast3Activity;
import com.ys.zy.util.GameUtil;
import com.ys.zy.util.L;
import com.ys.zy.util.YS;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.ys.zy.util.GameUtil.toThreeDigit;

public class Fast3Util {
    public static String getNumber(int i) {
        if (i < 10) {
            return "0" + i;
        } else {
            return "" + i;
        }
    }

    /**
     * 获取江苏快3当前期数  每 10 分钟 1 期，每天 8：30-15：20，共计 41 期，
     *
     * @return
     */
    public static String getCurrentJSK3Periods() {
        if (isJSK3Running()) {
            Calendar now = Calendar.getInstance();
            now.setTime(YS.getCurrentDate());
            int month = now.get(Calendar.MONTH) + 1;
            int day = now.get(Calendar.DAY_OF_MONTH);
            int hour = now.get(Calendar.HOUR_OF_DAY);
            int minute = now.get(Calendar.MINUTE);
            return getNumber(month) + getNumber(day) + toThreeDigit((hour * 60 + minute - 8 * 60 - 30) / 10 + 1);
        }
        return "";
    }

    /**
     * 获取江苏快3追qi后的期数
     *
     * @param qi
     * @return
     */
    public static String getJSK3Periods(int qi) {
        if (isJSK3Running()) {
            Calendar now = Calendar.getInstance();
            now.setTime(YS.getCurrentDate());
            int month = now.get(Calendar.MONTH) + 1;
            int day = now.get(Calendar.DAY_OF_MONTH);
            int hour = now.get(Calendar.HOUR_OF_DAY);
            int minute = now.get(Calendar.MINUTE);
            int qishu = (hour * 60 + minute - 8 * 60 - 30) / 10 + 1 + qi - 1;
            if (qishu > 41) {
                qishu = 41;
            }
            return getNumber(month) + getNumber(day) + toThreeDigit(qishu);
        }
        return "";
    }

    /**
     * 江苏快3是否正在进行
     *
     * @return
     */
    public static boolean isJSK3Running() {
        boolean runFlag = false;
        String format = "HH:mm:ss";
        SimpleDateFormat sf = new SimpleDateFormat("HH:mm:ss");
        String now = sf.format(YS.getCurrentDate());
        Date nowTime;
        try {
            nowTime = new SimpleDateFormat(format).parse(now);
            Date startTime = new SimpleDateFormat(format).parse("08:30:00");
            Date endTime = new SimpleDateFormat(format).parse("15:20:00");
            if (GameUtil.isEffectiveDate(nowTime, startTime, endTime)) {
                runFlag = true;
                L.e("系统时间在早上8点30到下午15点20之间.");
            } else {
                runFlag = false;
                L.e("系统时间不在早上8点30到下午15点20之间.");
            }
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return runFlag;
    }


    public static boolean isK3Running(int type) {
        if (type == Fast3Activity.TYPE_JSK3) {
            return isJSK3Running();
        } else {
            return true;
        }
    }

    /**
     * 获取1分快3当前期数  1分钟1 期，每天1440 期，
     *
     * @return
     */
    public static String getCurrent1FK3Periods() {
        Calendar now = Calendar.getInstance();
        now.setTime(YS.getCurrentDate());
        int month = now.get(Calendar.MONTH) + 1;
        int day = now.get(Calendar.HOUR_OF_DAY);
        int hour = now.get(Calendar.HOUR_OF_DAY);
        int minute = now.get(Calendar.MINUTE);
        return getNumber(month) + getNumber(day) + GameUtil.toFourDigit(hour * 60 + minute + 1);
    }

    /**
     * 获取一分快3加qi后的期数
     *
     * @param qi
     * @return
     */
    public static String get1FK3Periods(int qi) {
        Calendar now = Calendar.getInstance();
        now.setTime(YS.getCurrentDate());
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
     * 获取5分快3当前期数  5分钟1 期，每天288期，
     *
     * @return
     */
    public static String getCurrent5FK3Periods() {
        Calendar now = Calendar.getInstance();
        now.setTime(YS.getCurrentDate());
        int month = now.get(Calendar.MONTH) + 1;
        int day = now.get(Calendar.DAY_OF_MONTH);
        int hour = now.get(Calendar.HOUR_OF_DAY);
        int minute = now.get(Calendar.MINUTE);
        return getNumber(month) + getNumber(day) + toThreeDigit((hour * 60 + minute) / 5 + 1);
    }

    /**
     * 获取5分快3加qi期后的期数
     *
     * @return
     */
    public static String get5FK3Periods(int qi) {
        Calendar now = Calendar.getInstance();
        now.setTime(YS.getCurrentDate());
        int month = now.get(Calendar.MONTH) + 1;
        int day = now.get(Calendar.DAY_OF_MONTH);
        int hour = now.get(Calendar.HOUR_OF_DAY);
        int minute = now.get(Calendar.MINUTE);
        int qishu = (hour * 60 + minute) / 5 + 1 + qi - 1;
        if (qishu > 288) {
            qishu = 288;
        }
        return getNumber(month) + getNumber(day) + toThreeDigit(qishu);
    }

    public static String getK3Periods(int type, int qi) {
        String result = "";
        switch (type) {
            case Fast3Activity.TYPE_JSK3:
                result = getJSK3Periods(qi);
                break;
            case Fast3Activity.TYPE_1FK3:
                result = get1FK3Periods(qi);
                break;
            case Fast3Activity.TYPE_5FK3:
                result = get5FK3Periods(qi);
                break;
        }
        return result;
    }

    public static int getJGTime(int type) {
        int jgTime = 1;
        switch (type) {
            case Fast3Activity.TYPE_JSK3:
                jgTime = 10;
                break;
            case Fast3Activity.TYPE_1FK3:
                jgTime = 1;
                break;
            case Fast3Activity.TYPE_5FK3:
                jgTime = 5;
                break;
        }
        return jgTime;
    }

    public static List<String> getRandomResultList(int num) {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            result.add(getNumber(num));
        }
        return result;
    }

    public static int getDrawableIdByNum(int i) {
        int drawableId = R.mipmap.k3_s1_icon;
        switch (i) {
            case 1:
                drawableId = R.mipmap.k3_s1_icon;
                break;
            case 2:
                drawableId = R.mipmap.k3_s2_icon;
                break;
            case 3:
                drawableId = R.mipmap.k3_s3_icon;
                break;
            case 4:
                drawableId = R.mipmap.k3_s4_icon;
                break;
            case 5:
                drawableId = R.mipmap.k3_s5_icon;
                break;
            case 6:
                drawableId = R.mipmap.k3_s6_icon;
                break;
        }
        return drawableId;
    }

    //获取江苏快3不在运营时间内的下一期期号
    public static String getNextJSK3Periods() {
        String result="";
        String format = "HH:mm:ss";
        SimpleDateFormat sf = new SimpleDateFormat("HH:mm:ss");
        String now = sf.format(YS.getCurrentDate());
        Date nowTime;
        try {
            nowTime = new SimpleDateFormat(format).parse(now);
            Date startTime = new SimpleDateFormat(format).parse("00:00:01");
            Date endTime = new SimpleDateFormat(format).parse("08:29:59");
            if (GameUtil.isEffectiveDate(nowTime, startTime, endTime)) {
                L.e("系统时间在早上00:00:01到08:29:59之间.");
                Calendar c = Calendar.getInstance();
                c.setTime(YS.getCurrentDate());
                int month = c.get(Calendar.MONTH) + 1;
                int day = c.get(Calendar.DAY_OF_MONTH);
                result = getNumber(month) + getNumber(day) + "001";
            } else {
                L.e("系统时间在早上15:20:01到23:59:59之间.");
                Calendar c = Calendar.getInstance();
                c.setTime(YS.getCurrentDate());
                c.add(c.DATE,1);//把日期往后增加一天.整数往后推,负数往前移动
                int month = c.get(Calendar.MONTH) + 1;
                int day = c.get(Calendar.DAY_OF_MONTH);
                result = getNumber(month) + getNumber(day) + "001";
            }
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    //获取江苏快3不在运营时间内的开奖最后一期期号
    public static String getLastJSK3Periods() {
        String result="";
        String format = "HH:mm:ss";
        SimpleDateFormat sf = new SimpleDateFormat("HH:mm:ss");
        String now = sf.format(YS.getCurrentDate());
        Date nowTime;
        try {
            nowTime = new SimpleDateFormat(format).parse(now);
            Date startTime = new SimpleDateFormat(format).parse("00:00:01");
            Date endTime = new SimpleDateFormat(format).parse("08:29:59");
            if (GameUtil.isEffectiveDate(nowTime, startTime, endTime)) {
                L.e("系统时间在早上00:00:01到08:29:59之间.");
                Calendar c = Calendar.getInstance();
                c.setTime(YS.getCurrentDate());
                c.add(c.DATE,-1);//把日期往前减一天.整数往后推,负数往前移动
                int month = c.get(Calendar.MONTH) + 1;
                int day = c.get(Calendar.DAY_OF_MONTH);
                result = getNumber(month) + getNumber(day) + "041";
            } else {
                L.e("系统时间在早上15:20:01到23:59:59之间.");
                Calendar c = Calendar.getInstance();
                c.setTime(YS.getCurrentDate());
                int month = c.get(Calendar.MONTH) + 1;
                int day = c.get(Calendar.DAY_OF_MONTH);
                result = getNumber(month) + getNumber(day) + "041";
            }
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return result;
    }
}
