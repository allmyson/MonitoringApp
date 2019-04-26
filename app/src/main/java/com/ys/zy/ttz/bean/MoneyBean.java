package com.ys.zy.ttz.bean;

import com.ys.zy.roulette.bean.ChipBean;
import com.ys.zy.ttz.fragment.TtzTZFragment;

import java.util.ArrayList;
import java.util.List;

public class MoneyBean {
    public int type;
    public List<ChipBean> chipBeanList;

    public MoneyBean(int type, List<ChipBean> chipBeanList) {
        this.type = type;
        this.chipBeanList = chipBeanList;
    }

    public static List<MoneyBean> getList() {
        List<MoneyBean> list = new ArrayList<>();
        list.add(new MoneyBean(TtzTZFragment.TYPE_Z1, new ArrayList<ChipBean>()));
        list.add(new MoneyBean(TtzTZFragment.TYPE_P1, new ArrayList<ChipBean>()));
        list.add(new MoneyBean(TtzTZFragment.TYPE_X1, new ArrayList<ChipBean>()));
        list.add(new MoneyBean(TtzTZFragment.TYPE_Z2, new ArrayList<ChipBean>()));
        list.add(new MoneyBean(TtzTZFragment.TYPE_P2, new ArrayList<ChipBean>()));
        list.add(new MoneyBean(TtzTZFragment.TYPE_X2, new ArrayList<ChipBean>()));
        list.add(new MoneyBean(TtzTZFragment.TYPE_Z3, new ArrayList<ChipBean>()));
        list.add(new MoneyBean(TtzTZFragment.TYPE_P3, new ArrayList<ChipBean>()));
        list.add(new MoneyBean(TtzTZFragment.TYPE_X3, new ArrayList<ChipBean>()));
        return list;
    }
}
