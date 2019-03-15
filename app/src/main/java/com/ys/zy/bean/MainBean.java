package com.ys.zy.bean;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.ys.zy.R;
import com.ys.zy.fragment.FourFragment;
import com.ys.zy.fragment.OneFragment;
import com.ys.zy.fragment.ThreeFragment;
import com.ys.zy.fragment.TwoFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lh
 * @version 1.0.0
 * @filename MainBean
 * @description -------------------------------------------------------
 * @date 2018/9/20 14:25
 */
public class MainBean {
    public Fragment fragment;
    public String title;
    public int selectIconId;
    public int unSelectIconId;
    public int selectTextColor;
    public int unSelectTextColor;


    public static List<MainBean> getMainBeanList(Context context) {
        List<MainBean> list = new ArrayList<>();
        MainBean mainBean1 = new MainBean();
        mainBean1.title = "首页";
        mainBean1.selectIconId = R.mipmap.tabar_home_icon_select;
        mainBean1.unSelectIconId = R.mipmap.tabar_home_icon_normal;
        mainBean1.fragment = OneFragment.newInstance();
        mainBean1.selectTextColor = context.getResources().getColor(R.color.main_color);
        mainBean1.unSelectTextColor = context.getResources().getColor(R.color.gray_color);
        list.add(mainBean1);


        MainBean mainBean2 = new MainBean();
        mainBean2.title = "发现";
        mainBean2.selectIconId = R.mipmap.tabar_find_icon_selectl;
        mainBean2.unSelectIconId = R.mipmap.tabar_find_icon_normal;
        mainBean2.fragment = TwoFragment.newInstance();
        mainBean2.selectTextColor = context.getResources().getColor(R.color.main_color);
        mainBean2.unSelectTextColor = context.getResources().getColor(R.color.gray_color);
        list.add(mainBean2);


        MainBean mainBean3 = new MainBean();
        mainBean3.title = "消息中心";
        mainBean3.selectIconId = R.mipmap.tabar_mes_icon_select;
        mainBean3.unSelectIconId = R.mipmap.tabar_mes_icon_normal;
        mainBean3.fragment = ThreeFragment.newInstance();
        mainBean3.selectTextColor = context.getResources().getColor(R.color.main_color);
        mainBean3.unSelectTextColor = context.getResources().getColor(R.color.gray_color);
        list.add(mainBean3);


        MainBean mainBean4 = new MainBean();
        mainBean4.title = "我的";
        mainBean4.selectIconId = R.mipmap.tabar_mine_icon_select;
        mainBean4.unSelectIconId = R.mipmap.tabar_mine_icon_normal;
        mainBean4.fragment = FourFragment.newInstance();
        mainBean4.selectTextColor = context.getResources().getColor(R.color.main_color);
        mainBean4.unSelectTextColor = context.getResources().getColor(R.color.gray_color);
        list.add(mainBean4);
        return list;
    }
}
