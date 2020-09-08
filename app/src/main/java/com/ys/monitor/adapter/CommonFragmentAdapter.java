package com.ys.monitor.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ys.monitor.bean.TabBean;

import java.util.List;

public class CommonFragmentAdapter extends FragmentPagerAdapter {
    private List<TabBean> list;
    public CommonFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    public CommonFragmentAdapter(FragmentManager fm, List<TabBean> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position).fragment;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    //此方法用来显示tab上的名字
    @Override
    public CharSequence getPageTitle(int position) {
        return list.get(position).title;
    }

}
