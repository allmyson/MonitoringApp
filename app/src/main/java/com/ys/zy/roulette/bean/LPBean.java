package com.ys.zy.roulette.bean;

import com.ys.zy.R;

import java.util.ArrayList;
import java.util.List;

public class LPBean {
    public int value;
    public String name;
    public int drawableId;
    public String color;
    public float percentage; //百分比
    public float angle; //角度
    public double totalValue;//总下注额
    public double myValue;//自己的下注额

    public LPBean(int value, String name, int drawableId, String color) {
        this.value = value;
        this.name = name;
        this.drawableId = drawableId;
        this.color = color;
    }

    public static List<LPBean> getList() {
        List<LPBean> list = new ArrayList<>();
        list.add(new LPBean(30, "兔", R.mipmap.lp_icon_tu, "#ffa958"));
        list.add(new LPBean(30, "龙", R.mipmap.lp_icon_long, "#ff854b"));
        list.add(new LPBean(30, "蛇", R.mipmap.lp_icon_she, "#ffa958"));
        list.add(new LPBean(30, "马", R.mipmap.lp_icon_ma, "#ff854b"));
        list.add(new LPBean(30, "羊", R.mipmap.lp_icon_yang, "#ffa958"));
        list.add(new LPBean(30, "猴", R.mipmap.lp_icon_hou, "#ff854b"));
        list.add(new LPBean(30, "鸡", R.mipmap.lp_icon_ji, "#ffa958"));
        list.add(new LPBean(30, "狗", R.mipmap.lp_icon_gou, "#ff854b"));
        list.add(new LPBean(30, "猪", R.mipmap.lp_icon_zhu, "#ffa958"));
        list.add(new LPBean(30, "鼠", R.mipmap.lp_icon_shu, "#ff854b"));
        list.add(new LPBean(30, "牛", R.mipmap.lp_icon_niu, "#ffa958"));
        list.add(new LPBean(30, "虎", R.mipmap.lp_icon_hu, "#ff854b"));
        return list;
    }
}
