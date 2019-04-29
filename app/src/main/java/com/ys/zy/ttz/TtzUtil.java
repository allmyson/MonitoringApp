package com.ys.zy.ttz;

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

    public static List<PaiBean> getRandomResult() {
        List<PaiBean> list = new ArrayList<>();
        Random random = new Random();
        list.add(new PaiBean("庄", 1 + random.nextInt(9), 1 + random.nextInt(9)));
        list.add(new PaiBean("闲1", 1 + random.nextInt(9), 1 + random.nextInt(9)));
        list.add(new PaiBean("闲2", 1 + random.nextInt(9), 1 + random.nextInt(9)));
        list.add(new PaiBean("闲3", 1 + random.nextInt(9), 1 + random.nextInt(9)));
        return list;
    }
}
