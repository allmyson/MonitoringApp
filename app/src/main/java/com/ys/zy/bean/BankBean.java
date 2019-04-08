package com.ys.zy.bean;

import com.ys.zy.R;

import java.util.ArrayList;
import java.util.List;

public class BankBean {
    public int drawableId;
    public String name;
    public String number;

    public BankBean() {
    }

    public BankBean(int drawableId, String name, String number) {
        this.drawableId = drawableId;
        this.name = name;
        this.number = number;
    }

    public static List<BankBean> getList() {
        List<BankBean> list = new ArrayList<>();
        list.add(new BankBean(R.mipmap.wcash_icon_banklogo10, "工商银行", "2314231332133245"));
        list.add(new BankBean(R.mipmap.wcash_icon_banklogo9, "民生银行", "1234231452355353"));
        list.add(new BankBean(R.mipmap.wcash_icon_banklogo1, "招商银行", "1287906743253785"));
        return list;
    }
}
