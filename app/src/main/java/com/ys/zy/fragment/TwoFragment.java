package com.ys.zy.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ys.zy.R;
import com.ys.zy.adapter.CommonFragmentAdapter;
import com.ys.zy.base.BaseFragment;
import com.ys.zy.bean.TabBean;
import com.ys.zy.ui.LhViewPager;
import com.ys.zy.util.DensityUtil;
import com.ys.zy.util.TabUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lh
 * @version 1.0.0
 * @filename OneFragment
 * @description -------------------------------------------------------
 * @date 2018/10/23 17:09
 */
public class TwoFragment extends BaseFragment implements View.OnClickListener {
    private TabLayout tabLayout;
    private LhViewPager vp;
    private CommonFragmentAdapter mAdapter;

    public static TwoFragment newInstance() {
        return new TwoFragment();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void init() {
        tabLayout = getView(R.id.tl_);
        vp = getView(R.id.vp_);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        mAdapter = new CommonFragmentAdapter(getChildFragmentManager(), getList());
        vp.setAdapter(mAdapter);
        vp.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(vp);
        TabUtil.setTabWidth(tabLayout, DensityUtil.dp2px(mContext, 48));
    }

    @Override
    protected void getData() {

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_two;
    }

    private List<TabBean> getList() {
        List<TabBean> list = new ArrayList<>();
        list.add(new TabBean("最热游戏榜", HotGameFragment.newInstance()));
        list.add(new TabBean("昨日中奖榜", WinnerListFragment.newInstance()));
        return list;
    }

}
