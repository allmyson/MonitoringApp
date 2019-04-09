package com.ys.zy.roulette.bean;

import com.ys.zy.R;

import java.util.ArrayList;
import java.util.List;

public class ChipBean {
    public double money;
    public int drawableId;
    public int selectDrawableId;

    public ChipBean() {
    }

    public ChipBean(double money, int drawableId, int selectDrawableId) {
        this.money = money;
        this.drawableId = drawableId;
        this.selectDrawableId = selectDrawableId;
    }

    public static List<ChipBean> getList() {
        List<ChipBean> list = new ArrayList<>();
        list.add(new ChipBean(1,R.mipmap.bottom_icon_chip1_normal, R.mipmap.bottom_icon_chip1));
        list.add(new ChipBean(5,R.mipmap.bottom_icon_chip5_normal, R.mipmap.bottom_icon_chip5));
        list.add(new ChipBean(10, R.mipmap.bottom_icon_chip10_normal,R.mipmap.bottom_icon_chip10));
        list.add(new ChipBean(50, R.mipmap.bottom_icon_chip50_normal,R.mipmap.bottom_icon_chip50));
        list.add(new ChipBean(100, R.mipmap.bottom_icon_chip100_normal,R.mipmap.bottom_icon_chip100));
        list.add(new ChipBean(500,R.mipmap.bottom_icon_chip500_normal, R.mipmap.bottom_icon_chip500));
        list.add(new ChipBean(1000,R.mipmap.bottom_icon_chip1000_normal, R.mipmap.bottom_icon_chip1000));
        list.add(new ChipBean(5000,R.mipmap.bottom_icon_chip5000_normal, R.mipmap.bottom_icon_chip5000));
        return list;
    }
}
