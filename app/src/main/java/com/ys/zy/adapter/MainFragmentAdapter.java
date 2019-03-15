package com.ys.zy.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ys.zy.R;
import com.ys.zy.bean.MainBean;

import java.util.List;


/**
 * @author lh
 * @version 1.0.0
 * @filename MainFragmentAdapter
 * @description -------------------------------------------------------
 * @date 2018/9/20 14:24
 */
public class MainFragmentAdapter extends FragmentPagerAdapter {
    private List<MainBean> list;
    private Context context;

    public MainFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    public MainFragmentAdapter(FragmentManager fm, Context context, List<MainBean> list) {
        super(fm);
        this.context = context;
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

    public View getTabView(int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.tab_item, null);
        TextView tv = (TextView) view.findViewById(R.id.tv);
        tv.setText(list.get(position).title);
        ImageView img = (ImageView) view.findViewById(R.id.iv);
        img.setImageResource(list.get(position).unSelectIconId);
        return view;
    }
}
