package com.ys.monitor.util;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class DateUtil {
    /**
     * @return void
     * @author 彭粟
     * @version 1.0
     * @Description: 传入时间戳
     * @time： 2016/12/28
     */

    public static String getLongDate(String date) {
        String resultDate = "";
        if (!TextUtils.isEmpty(date)) {
            if (!"0".equals(date)) {
                try {
                    long datel = Long.valueOf(date);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    resultDate = sdf.format(new Date(datel));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
        //Thu Jan 01 08:00:00 GMT+08:00 1970
        return resultDate;
    }

    /**
     * @return void
     * @author 彭粟
     * @version 1.0
     * @Description: 传入时间戳
     * @time： 2016/12/28
     */
    public static String getDateAndMinute(String date) {
        String resultDate = "";
        if (!TextUtils.isEmpty(date)) {
            if (!"0".equals(date)) {
                try {
                    long datel = Long.valueOf(date);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    resultDate = sdf.format(new Date(datel));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
        //Thu Jan 01 08:00:00 GMT+08:00 1970
        return resultDate;
    }

    /**
     * 根据毫秒时间得到日期
     *
     * @param time
     * @return String
     * @创建时间：2017年2月16日 下午7:54:31
     * @作者： 董杰科
     * @描述: TODO
     */
    public static String getLongDate(long time) {
        if (time == 0) {
            return "";
        } else {
            Date dat = new Date(time);
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(dat);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String sb = format.format(gc.getTime());
            return sb;
        }
    }

    /**
     * 获取时间戳
     *
     * @param
     * @return String
     * @创建时间：2017年2月27日
     * @作者： 李杰
     * @描述: TODO
     */
    public static long getLongTime() {
        return System.currentTimeMillis();
    }


    public static long getTimeYearMString(String time) {

        if (TextUtils.isEmpty(time)) {
            return 0;
        } else {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            long resultTime = 0;
            try {
                resultTime = format.parse(time).getTime();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return resultTime;
        }
    }


    /**
     * 根据毫秒时间得到日期
     *
     * @param time
     * @return String
     * @创建时间：2017年2月16日 下午7:54:31
     * @作者： 董杰科
     * @描述: TODO
     */
    public static String getLongDate2(long time) {
        if (time == 0) {
            return "";
        } else {
            Date dat = new Date(time);
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(dat);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String sb = format.format(gc.getTime());
            return sb;
        }
    }


    public static String changeTimeToYMD(String time) {
        SimpleDateFormat formatter = new SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        Date date = null;
        try {
            date = formatter.parse(time);
        } catch (Exception e) {
            e.printStackTrace();
            date = new Date();
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String res = simpleDateFormat.format(date);
        return res;
    }
    public static String changeTimeToHMS(String time) {
        SimpleDateFormat formatter = new SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        Date date = null;
        try {
            date = formatter.parse(time);
        } catch (Exception e) {
            e.printStackTrace();
            date = new Date();
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        String res = simpleDateFormat.format(date);
        return res;
    }
    public static String changeTimeToYMDHMS(String time) {
        SimpleDateFormat formatter = new SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        Date date = null;
        try {
            date = formatter.parse(time);
        } catch (Exception e) {
            e.printStackTrace();
            date = new Date();
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String res = simpleDateFormat.format(date);
        return res;
    }

    public static long changeTimeToLong(String time) {
        SimpleDateFormat formatter = new SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        Date date = null;
        try {
            date = formatter.parse(time);
        } catch (Exception e) {
            e.printStackTrace();
            date = new Date();
        }
        return date.getTime();
    }


    public static long changeTimeToLong2(String time) {
        SimpleDateFormat formatter = new SimpleDateFormat(
                "yyyy-MM-dd");
        Date date = null;
        try {
            date = formatter.parse(time);
        } catch (Exception e) {
            e.printStackTrace();
            date = new Date();
        }
        return date.getTime();
    }

    public static void isBelong() {

        SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
        Date now = null;
        Date beginTime = null;
        Date endTime = null;
        try {
            now = df.parse(df.format(new Date()));
            beginTime = df.parse("06:00");
            endTime = df.parse("18:21");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Boolean flag = belongCalendar(now, beginTime, endTime);
        System.out.println(flag);
    }

    /**
     * 判断时间是否在时间段内
     *
     * @param nowTime
     * @param beginTime
     * @param endTime
     * @return
     */
    public static boolean belongCalendar(Date nowTime, Date beginTime, Date endTime) {
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(beginTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param start 10:00
     * @param end   22:00
     * @return
     */
    public static boolean isIn(String start, String end) {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
        Date now = null;
        Date beginTime = null;
        Date endTime = null;
        try {
            now = df.parse(df.format(new Date()));
            beginTime = df.parse(start);
            endTime = df.parse(end);
        } catch (Exception e) {
            e.printStackTrace();
        }
        boolean flag = belongCalendar(now, beginTime, endTime);
        return flag;
    }

    //10:00-22:00
    public static boolean isTen() {
        return isIn("10:00", "22:00");
    }

    //22:00-02:00
    public static boolean isFive() {
        return isIn("00:00", "02:00") || isIn("22:00", "23:59");
    }

    //凌晨2点-早上10点之间不开奖
    public static boolean isOpen() {
        return !isIn("02:00", "09:59");
    }


    public static char[] getRemainTime(long saleTime) {
        long nowTime = System.currentTimeMillis();
        if (saleTime <= nowTime) {
            return new char[]{'0', '0', '0', '0', '0', '0'};
        }
        long free = saleTime - nowTime;
        int hour = (int) (free / 1000 / 60 / 60);
        int minute = (int) (free / 1000 / 60);
        minute = minute - hour * 60;
        int second = (int) (free / 1000);
        second = second - hour * 60 * 60 - minute * 60;
        String hourStr = hour >= 10 ? String.valueOf(hour) : "0" + hour;
        String muniteStr = minute >= 10 ? String.valueOf(minute) : "0" + minute;
        String secondStr = second >= 10 ? String.valueOf(second) : "0" + second;
        String resultStr = hourStr.concat(muniteStr).concat(secondStr);
        char[] resultChar = resultStr.toCharArray();
        return resultChar;
    }

    public static String getRemainTime2(long saleTime) {
        char[] c = getRemainTime(saleTime);
        StringBuilder sb = new StringBuilder();
        sb.append(c[2]);
        sb.append(c[3]);
        sb.append(":");
        sb.append(c[4]);
        sb.append(c[5]);
        return sb.toString();
    }


    /*
     * 将时间戳转换为时间
	 */
    public static String longToYMD(long s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(s);
        res = simpleDateFormat.format(date);
        return res;
    }


    /*
     * 将时间转换为时间戳
     */
    public static long dateToStamp(String s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = simpleDateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long ts = date.getTime();
        return ts;
    }

    public static long getCurrentDayStart() {
        long current = System.currentTimeMillis();
        String str = longToYMD(current);
        str = str + " 00:00:00";
        return dateToStamp(str);
    }

    public static long getCurrentDayEnd() {
        long current = System.currentTimeMillis();
        String str = longToYMD(current);
        str = str + " 23:59:59";
        return dateToStamp(str);
    }
    public static String getCurrentDayStartStr() {
        long current = System.currentTimeMillis();
        String str = longToYMD(current);
        str = str + " 00:00:00";
        return str;
    }

    public static String getCurrentDayEndStr() {
        long current = System.currentTimeMillis();
        String str = longToYMD(current);
        str = str + " 23:59:59";
        return str;
    }
    /**
     *
     * @param str 2018-12-05
     * @return
     */
    public static long getWhichDayStart(String str){
        return dateToStamp(str+" 00:00:00");
    }

    public static long getWhichDayEnd(String str){
        return dateToStamp(str+" 23:59:59");
    }
}
