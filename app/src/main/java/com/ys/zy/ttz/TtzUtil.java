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


    public static List<PaiBean> getRandomList(int num) {
        List<PaiBean> list = new ArrayList<>();
        list.add(new PaiBean("庄", num, num));
        list.add(new PaiBean("闲1", num, num));
        list.add(new PaiBean("闲2", num, num));
        list.add(new PaiBean("闲3", num, num));
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
