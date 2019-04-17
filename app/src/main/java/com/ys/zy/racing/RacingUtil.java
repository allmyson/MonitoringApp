package com.ys.zy.racing;

import com.ys.zy.util.GameUtil;
import com.ys.zy.util.L;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.ys.zy.util.GameUtil.toThreeDigit;

public class RacingUtil {
    private static List<String> nameList = new ArrayList<>();

    static {
        nameList.add("冠军");
        nameList.add("亚军");
        nameList.add("季军");
        nameList.add("第四");
        nameList.add("第五");
        nameList.add("第六");
        nameList.add("第七");
        nameList.add("第八");
        nameList.add("第九");
        nameList.add("第十");
    }

    public static String getNumber(int i) {
        if (i < 10) {
            return "0" + i;
        } else {
            return "" + i;
        }
    }

    public static String getNameByPosition(int position) {
        return nameList.get(position);
    }

    /**
     * 获取北京赛车当前期数  每 20 分钟 1 期，每天 9：10-23：50，共计 44 期，
     *
     * @return
     */
    public static String getCurrentBJSCPeriods() {
        if (isBJSCRunning()) {
            Calendar now = Calendar.getInstance();
            now.setTime(new Date());
            int month = now.get(Calendar.MONTH) + 1;
            int day = now.get(Calendar.DAY_OF_MONTH);
            int hour = now.get(Calendar.HOUR_OF_DAY);
            int minute = now.get(Calendar.MINUTE);
            return getNumber(month) + getNumber(day) + getNumber((hour * 60 + minute - 9 * 60 - 10) / 20);
        }
        return "";
    }

    /**
     * 北京赛车是否正在进行
     *
     * @return
     */
    public static boolean isBJSCRunning() {
        boolean runFlag = false;
        String format = "HH:mm:ss";
        SimpleDateFormat sf = new SimpleDateFormat("HH:mm:ss");
        String now = sf.format(new Date());
        Date nowTime;
        try {
            nowTime = new SimpleDateFormat(format).parse(now);
            Date startTime = new SimpleDateFormat(format).parse("09:10:00");
            Date endTime = new SimpleDateFormat(format).parse("23:50:00");
            if (GameUtil.isEffectiveDate(nowTime, startTime, endTime)) {
                runFlag = true;
                L.e("系统时间在早上9点10到下午23点50之间.");
            } else {
                runFlag = false;
                L.e("系统时间不在早上9点10到下午23点50之间.");
            }
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return runFlag;
    }


    /**
     * 获取1分赛车当前期数  1分钟1 期，每天1440 期，
     *
     * @return
     */
    public static String getCurrent1FSCPeriods() {
        Calendar now = Calendar.getInstance();
        now.setTime(new Date());
        int month = now.get(Calendar.MONTH) + 1;
        int day = now.get(Calendar.HOUR_OF_DAY);
        int hour = now.get(Calendar.HOUR_OF_DAY);
        int minute = now.get(Calendar.MINUTE);
        return getNumber(month) + getNumber(day) + GameUtil.toFourDigit(hour * 60 + minute);
    }

    /**
     * 获取5分赛车当前期数  5分钟1 期，每天288期，
     *
     * @return
     */
    public static String getCurrent5FSCPeriods() {
        Calendar now = Calendar.getInstance();
        now.setTime(new Date());
        int month = now.get(Calendar.MONTH) + 1;
        int day = now.get(Calendar.DAY_OF_MONTH);
        int hour = now.get(Calendar.HOUR_OF_DAY);
        int minute = now.get(Calendar.MINUTE);
        return getNumber(month) + getNumber(day) + toThreeDigit((hour * 60 + minute) / 5);
    }
}
