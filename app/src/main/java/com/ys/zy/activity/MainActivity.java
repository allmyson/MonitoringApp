package com.ys.zy.activity;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ys.zy.R;
import com.ys.zy.adapter.MainFragmentAdapter;
import com.ys.zy.base.BaseActivity;
import com.ys.zy.bean.MainBean;
import com.ys.zy.ui.LhViewPager;
import com.ys.zy.update.UpdateManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private TabLayout mTabLayout;
    private LhViewPager mViewPager;
    private MainFragmentAdapter mainFragmentAdapter;
    private List<MainBean> list;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        mTabLayout = getView(R.id.tabs);
        mViewPager = getView(R.id.vp_view);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        list = new ArrayList<>();
        list.addAll(MainBean.getMainBeanList(mContext));
        mainFragmentAdapter = new MainFragmentAdapter(getSupportFragmentManager(), mContext, list);
        mViewPager.setAdapter(mainFragmentAdapter);
        mViewPager.setOffscreenPageLimit(5);
        mTabLayout.setupWithViewPager(mViewPager);
        for (int i = 0; i < mainFragmentAdapter.getCount(); i++) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);//获得每一个tab
            tab.setCustomView(mainFragmentAdapter.getTabView(i));//给每一个tab设置view
        }
        mTabLayout.setSelectedTabIndicatorHeight(0);
        mTabLayout.setBackgroundColor(Color.WHITE);
        ((TextView) mTabLayout.getTabAt(0).getCustomView().findViewById(R.id.tv)).setTextColor(list.get(0)
                .selectTextColor);

        ((ImageView) mTabLayout.getTabAt(0).getCustomView().findViewById(R.id.iv)).setImageResource(list.get(0)
                .selectIconId);

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                ((TextView) tab.getCustomView().findViewById(R.id.tv)).setTextColor(list.get(tab.getPosition())
                        .selectTextColor);
                ((ImageView) tab.getCustomView().findViewById(R.id.iv)).setImageResource(list.get(tab.getPosition())
                        .selectIconId);
                if(tab.getPosition()==3){
                    setBarColor("#ffffff");
                }else {
                    setBarColor("#f7f7f7");
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                ((TextView) tab.getCustomView().findViewById(R.id.tv)).setTextColor(list.get(tab.getPosition())
                        .unSelectTextColor);
                ((ImageView) tab.getCustomView().findViewById(R.id.iv)).setImageResource(list.get(tab.getPosition())
                        .unSelectIconId);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
//        new UpdateManager(mContext,false).checkUpdate();
    }

    public void setPositionTab(int position) {
        if (position >= 0 && position <= 3) {
            mViewPager.setCurrentItem(position);
        }
    }

    @Override
    public void getData() {
        new UpdateManager(mContext,false).checkUpdate();
    }

    @Override
    public void onClick(View v) {

    }

    /**
     * 返回退出
     */
    private long firstTime = 0;

    public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long time = System.currentTimeMillis();
            if (time - firstTime > 2000) {
                Toast.makeText(MainActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();
                firstTime = time;
                return true;
            } else {
                //退出相关操作
                System.exit(0);
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
