package com.ys.zy.bean;

import com.ys.zy.R;

import java.util.ArrayList;
import java.util.List;

public class PayBean {
    public static final int PAY_YL = 100;
    public static final int PAY_WX = 101;
    public static final int PAY_ZFB = 102;
    public int type;
    public int drawableId;
    public String name;
    public String description;

    public PayBean() {
    }

    public PayBean(int type, int drawableId, String name, String description) {
        this.type = type;
        this.drawableId = drawableId;
        this.name = name;
        this.description = description;
    }

    public static List<PayBean> getList() {
        List<PayBean> list = new ArrayList<>();
        list.add(new PayBean(PAY_YL, R.mipmap.recharge_icon_yl, "银联支付", "单笔最低10元，最高500000元"));
        list.add(new PayBean(PAY_WX, R.mipmap.recharge_icon_wx, "微信支付", "单笔最低10元，最高20000元"));
        list.add(new PayBean(PAY_ZFB, R.mipmap.recharge_icon_z, "支付宝支付", "单笔最低10元，最高50000元"));
        return list;
    }
}
