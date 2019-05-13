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

    public static String getShowByType(int type) {
        String show = "";
        switch (type) {
            case TtzTZFragment.TYPE_Z1:
                show = "庄对闲1(庄)";
                break;
            case TtzTZFragment.TYPE_P1:
                show = "庄对闲1(平)";
                break;
            case TtzTZFragment.TYPE_X1:
                show = "庄对闲1(闲)";
                break;
            case TtzTZFragment.TYPE_Z2:
                show = "庄对闲2(庄)";
                break;
            case TtzTZFragment.TYPE_P2:
                show = "庄对闲2(平)";
                break;
            case TtzTZFragment.TYPE_X2:
                show = "庄对闲2(闲)";
                break;
            case TtzTZFragment.TYPE_Z3:
                show = "庄对闲3(庄)";
                break;
            case TtzTZFragment.TYPE_P3:
                show = "庄对闲3(平)";
                break;
            case TtzTZFragment.TYPE_X3:
                show = "庄对闲4(闲)";
                break;
        }
        return show;
    }

    public static String getCodeByType(int type) {
        String show = "";
        switch (type) {
            case TtzTZFragment.TYPE_Z1:
                show = "1000";
                break;
            case TtzTZFragment.TYPE_P1:
                show = "1001";
                break;
            case TtzTZFragment.TYPE_X1:
                show = "1002";
                break;
            case TtzTZFragment.TYPE_Z2:
                show = "1010";
                break;
            case TtzTZFragment.TYPE_P2:
                show = "1011";
                break;
            case TtzTZFragment.TYPE_X2:
                show = "1012";
                break;
            case TtzTZFragment.TYPE_Z3:
                show = "1100";
                break;
            case TtzTZFragment.TYPE_P3:
                show = "1101";
                break;
            case TtzTZFragment.TYPE_X3:
                show = "1102";
                break;
        }
        return show;
    }

    public static double getMoney(List<ChipBean> list) {
        double sum = 0;
        if (list != null && list.size() > 0) {
            for (ChipBean chipBean : list) {
                sum += chipBean.money;
            }
        }
        return sum;
    }
}
