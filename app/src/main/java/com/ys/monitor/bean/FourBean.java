package com.ys.monitor.bean;


import com.ys.monitor.R;

import java.util.ArrayList;
import java.util.List;

public class FourBean {
    public int id;
    public String name;

    public FourBean() {
    }

    public FourBean(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static List<FourBean> getDefaultList(){
        List<FourBean> list = new ArrayList<>();
        list.add(new FourBean(R.mipmap.mine_icon_personalinf,"个人信息"));
        list.add(new FourBean(R.mipmap.mine_icon_safe,"安全中心"));
        list.add(new FourBean(R.mipmap.mine_icon_proxy,"代理中心"));
        list.add(new FourBean(R.mipmap.mine_icon_personalreport,"个人报表"));
        list.add(new FourBean(R.mipmap.mine_icon_about,"关于"));
        return list;
    }
}
