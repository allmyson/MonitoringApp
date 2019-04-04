package com.ys.zy.bean;

import java.util.ArrayList;
import java.util.List;

public class Fast3Bean {
    public String value;
    public double odds;//赔率

    public Fast3Bean(String value, double odds) {
        this.value = value;
        this.odds = odds;
    }

    public static List<Fast3Bean> getList(){
        List<Fast3Bean> list = new ArrayList<>();
        list.add(new Fast3Bean("大",1.960));
        list.add(new Fast3Bean("小",1.960));
        list.add(new Fast3Bean("单",1.960));
        list.add(new Fast3Bean("双",1.960));
        list.add(new Fast3Bean("3",190.08));
        list.add(new Fast3Bean("4",63.36));
        list.add(new Fast3Bean("5",31.68));
        list.add(new Fast3Bean("6",19.00));
        list.add(new Fast3Bean("7",12.67));
        list.add(new Fast3Bean("8",9.05));
        list.add(new Fast3Bean("9",7.60));
        list.add(new Fast3Bean("10",7.04));
        list.add(new Fast3Bean("11",7.04));
        list.add(new Fast3Bean("12",7.60));
        list.add(new Fast3Bean("13",9.05));
        list.add(new Fast3Bean("14",12.67));
        list.add(new Fast3Bean("15",19.00));
        list.add(new Fast3Bean("16",31.68));
        list.add(new Fast3Bean("17",63.36));
        list.add(new Fast3Bean("18",190.08));
        return list;
    }
}
