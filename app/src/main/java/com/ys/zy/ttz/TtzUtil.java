package com.ys.zy.ttz;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ys.zy.ttz.bean.PaiBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TtzUtil {
    public static List<PaiBean> getDefaultList() {
        List<PaiBean> list = new ArrayList<>();
        list.add(new PaiBean("庄", 0, 0));
        list.add(new PaiBean("闲1", 0, 0));
        list.add(new PaiBean("闲2", 0, 0));
        list.add(new PaiBean("闲3", 0, 0));
        return list;
    }

    public static int[] result = new int[]{6, 8, 2, 5, 3, 4, 1, 9};

    /**
     * 6 8  2 5  3 4  19
     *
     * @param num
     * @return
     */
    public static List<PaiBean> getRandomList(int num) {
        List<PaiBean> list = new ArrayList<>();
        if (num == 1) {
            list.add(new PaiBean("庄", result[0], result[1]));
            list.add(new PaiBean("闲1", 0, 0));
            list.add(new PaiBean("闲2", 0, 0));
            list.add(new PaiBean("闲3", 0, 0));
        } else if (num == 2) {
            list.add(new PaiBean("庄", result[0], result[1]));
            list.add(new PaiBean("闲1", result[2], result[3]));
            list.add(new PaiBean("闲2", 0, 0));
            list.add(new PaiBean("闲3", 0, 0));
        } else if (num == 3) {
            list.add(new PaiBean("庄", result[0], result[1]));
            list.add(new PaiBean("闲1", result[2], result[3]));
            list.add(new PaiBean("闲2", result[4], result[5]));
            list.add(new PaiBean("闲3", 0, 0));
        } else if (num == 4) {
            list.add(new PaiBean("庄", result[0], result[1]));
            list.add(new PaiBean("闲1", result[2], result[3]));
            list.add(new PaiBean("闲2", result[4], result[5]));
            list.add(new PaiBean("闲3", result[6], result[7]));
        }
        return list;
    }

    public static List<PaiBean> getRandomList(int num, List<Integer> result) {
        List<PaiBean> list = new ArrayList<>();
        if (num == 1) {
            list.add(new PaiBean("庄", result.get(0), result.get(1)));
            list.add(new PaiBean("闲1", 0, 0));
            list.add(new PaiBean("闲2", 0, 0));
            list.add(new PaiBean("闲3", 0, 0));
        } else if (num == 2) {
            list.add(new PaiBean("庄", result.get(0), result.get(1)));
            list.add(new PaiBean("闲1", result.get(2), result.get(3)));
            list.add(new PaiBean("闲2", 0, 0));
            list.add(new PaiBean("闲3", 0, 0));
        } else if (num == 3) {
            list.add(new PaiBean("庄", result.get(0), result.get(1)));
            list.add(new PaiBean("闲1", result.get(2), result.get(3)));
            list.add(new PaiBean("闲2", result.get(4), result.get(5)));
            list.add(new PaiBean("闲3", 0, 0));
        } else if (num == 4) {
            list.add(new PaiBean("庄", result.get(0), result.get(1)));
            list.add(new PaiBean("闲1", result.get(2), result.get(3)));
            list.add(new PaiBean("闲2", result.get(4), result.get(5)));
            list.add(new PaiBean("闲3", result.get(6), result.get(7)));
        }
        return list;
    }

    public static List<PaiBean> getRandomResult() {
        List<PaiBean> list = new ArrayList<>();
        Random random = new Random();
        list.add(new PaiBean("庄", 1 + random.nextInt(9), 1 + random.nextInt(9)));
        list.add(new PaiBean("闲1", 1 + random.nextInt(9), 1 + random.nextInt(9)));
        list.add(new PaiBean("闲2", 1 + random.nextInt(9), 1 + random.nextInt(9)));
        list.add(new PaiBean("闲3", 1 + random.nextInt(9), 1 + random.nextInt(9)));
        return list;
    }

    public static String getShowNum(String lotteryNum) {
        String result = "";
        List<Integer> list = new Gson().fromJson(lotteryNum, new TypeToken<List<Integer>>() {
        }.getType());
        if (list != null && list.size() == 8) {
            result = "(" + list.get(0) + "," + list.get(1) + ")  " + "(" + list.get(2) + "," + list.get(3) + ")  " + "(" + list.get(4) + "," + list.get(5) + ")  " + "(" + list.get(6) + "," + list.get(7) + ")";
        }
        return result;
    }

    public static String[] getAllResult(String lotteryNum) {
        String[] result = new String[3];
        List<Integer> list = new Gson().fromJson(lotteryNum, new TypeToken<List<Integer>>() {
        }.getType());
        if (list != null && list.size() == 8) {
            result[0] = getResult(list.get(0), list.get(1), list.get(2), list.get(3));
            result[1] = getResult(list.get(0), list.get(1), list.get(4), list.get(5));
            result[2] = getResult(list.get(0), list.get(1), list.get(6), list.get(7));
        }
        return result;
    }


    //判断赢平输
    public static String getResult(int a, int b, int m, int n) {
        String result = "";
        //都是对子
        if (isDZ(a, b) && isDZ(m, n)) {
            if (a > m) {
                result = "庄";
            } else if (a == m) {
                result = "平";
            } else {
                result = "闲";
            }
        } else if (isDZ(a, b) && !isDZ(m, n)) {
            result = "庄";
        } else if (!isDZ(a, b) && isDZ(m, n)) {
            result = "闲";
        } else {
            //都不是对子
            if ((a + b) > (m + n)) {
                result = "庄";
            } else if ((a + b) < (m + n)) {
                result = "闲";
            } else {
                int x = Math.max(a, b);
                int y = Math.max(m, n);
                if (x > y) {
                    result = "庄";
                } else if (x == y) {
                    result = "平";
                } else {
                    result = "闲";
                }
            }
        }
        return result;
    }

    //是否是对子
    public static boolean isDZ(int a, int b) {
        if (a == b) {
            return true;
        }
        return false;
    }
}
