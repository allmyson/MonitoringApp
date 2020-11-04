package com.ys.monitor.bean;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.ys.monitor.R;
import com.ys.monitor.fragment.FourFragment;
import com.ys.monitor.fragment.MapFragment;
import com.ys.monitor.fragment.OneFragment;
import com.ys.monitor.fragment.ThreeFragment;

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
        mainBean1.selectIconId = R.drawable.ic_title_home_selected;
        mainBean1.unSelectIconId = R.drawable.ic_title_home_unselected;
        mainBean1.fragment = OneFragment.newInstance();
        mainBean1.selectTextColor = context.getResources().getColor(R.color.main_color);
        mainBean1.unSelectTextColor = context.getResources().getColor(R.color.gray_color);
        list.add(mainBean1);


        MainBean mainBean2 = new MainBean();
        mainBean2.title = "地图";
        mainBean2.selectIconId = R.drawable.ic_title_map_selected;
        mainBean2.unSelectIconId = R.drawable.ic_title_map_unselected;
        mainBean2.fragment = MapFragment.newInstance();
        mainBean2.selectTextColor = context.getResources().getColor(R.color.main_color);
        mainBean2.unSelectTextColor = context.getResources().getColor(R.color.gray_color);
        list.add(mainBean2);

//
//        MainBean mainBean3 = new MainBean();
//        mainBean3.title = "通知";
//        mainBean3.selectIconId = R.drawable.ic_title_notice_selected;
//        mainBean3.unSelectIconId = R.drawable.ic_title_notice_unselected;
//        mainBean3.fragment = ThreeFragment.newInstance();
//        mainBean3.selectTextColor = context.getResources().getColor(R.color.main_color);
//        mainBean3.unSelectTextColor = context.getResources().getColor(R.color.gray_color);
//        list.add(mainBean3);


        MainBean mainBean4 = new MainBean();
        mainBean4.title = "个人中心";
        mainBean4.selectIconId = R.drawable.ic_title_my_selected;
        mainBean4.unSelectIconId = R.drawable.ic_title_my_unselected;
        mainBean4.fragment = FourFragment.newInstance();
        mainBean4.selectTextColor = context.getResources().getColor(R.color.main_color);
        mainBean4.unSelectTextColor = context.getResources().getColor(R.color.gray_color);
        list.add(mainBean4);
        return list;
    }

    public static List<MainBean> getMainBeanList2(Context context) {
        List<MainBean> list = new ArrayList<>();
        MainBean mainBean1 = new MainBean();
        mainBean1.title = "首页";
        mainBean1.selectIconId = R.drawable.ic_title_home_selected;
        mainBean1.unSelectIconId = R.drawable.ic_title_home_unselected;
        mainBean1.fragment = OneFragment.newInstance();
        mainBean1.selectTextColor = context.getResources().getColor(R.color.main_color);
        mainBean1.unSelectTextColor = context.getResources().getColor(R.color.gray_color);
        list.add(mainBean1);


        MainBean mainBean2 = new MainBean();
        mainBean2.title = "地图";
        mainBean2.selectIconId = R.drawable.ic_title_map_selected;
        mainBean2.unSelectIconId = R.drawable.ic_title_map_unselected;
        mainBean2.fragment = MapFragment.newInstance();
        mainBean2.selectTextColor = context.getResources().getColor(R.color.main_color);
        mainBean2.unSelectTextColor = context.getResources().getColor(R.color.gray_color);
        list.add(mainBean2);


        MainBean mainBean3 = new MainBean();
        mainBean3.title = "通知";
        mainBean3.selectIconId = R.drawable.ic_title_notice_selected;
        mainBean3.unSelectIconId = R.drawable.ic_title_notice_unselected;
        mainBean3.fragment = ThreeFragment.newInstance();
        mainBean3.selectTextColor = context.getResources().getColor(R.color.main_color);
        mainBean3.unSelectTextColor = context.getResources().getColor(R.color.gray_color);
        list.add(mainBean3);


        MainBean mainBean4 = new MainBean();
        mainBean4.title = "个人中心";
        mainBean4.selectIconId = R.drawable.ic_title_my_selected;
        mainBean4.unSelectIconId = R.drawable.ic_title_my_unselected;
        mainBean4.fragment = FourFragment.newInstance();
        mainBean4.selectTextColor = context.getResources().getColor(R.color.main_color);
        mainBean4.unSelectTextColor = context.getResources().getColor(R.color.gray_color);
        list.add(mainBean4);
        return list;
    }
}
