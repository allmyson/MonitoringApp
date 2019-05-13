package com.ys.zy.fast3.bean;

import java.util.ArrayList;
import java.util.List;

public class Fast3Bean {
    public String value;
    public String odds;//赔率

    public Fast3Bean(String value, String odds) {
        this.value = value;
        this.odds = odds;
    }

    public static List<Fast3Bean> getList() {
        List<Fast3Bean> list = new ArrayList<>();
        list.add(new Fast3Bean("大", ""));
        list.add(new Fast3Bean("小", ""));
        list.add(new Fast3Bean("单", ""));
        list.add(new Fast3Bean("双", ""));
        list.add(new Fast3Bean("3", ""));
        list.add(new Fast3Bean("4", ""));
        list.add(new Fast3Bean("5", ""));
        list.add(new Fast3Bean("6", ""));
        list.add(new Fast3Bean("7", ""));
        list.add(new Fast3Bean("8", ""));
        list.add(new Fast3Bean("9", ""));
        list.add(new Fast3Bean("10", ""));
        list.add(new Fast3Bean("11", ""));
        list.add(new Fast3Bean("12", ""));
        list.add(new Fast3Bean("13", ""));
        list.add(new Fast3Bean("14", ""));
        list.add(new Fast3Bean("15", ""));
        list.add(new Fast3Bean("16", ""));
        list.add(new Fast3Bean("17", ""));
        list.add(new Fast3Bean("18", ""));
        return list;
    }
}
