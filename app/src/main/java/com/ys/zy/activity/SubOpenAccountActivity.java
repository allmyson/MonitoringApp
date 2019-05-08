package com.ys.zy.activity;

import android.support.design.widget.TabLayout;
import android.view.View;

import com.ys.zy.R;
import com.ys.zy.adapter.CommonFragmentAdapter;
import com.ys.zy.base.BaseActivity;
import com.ys.zy.base.BaseFragment;
import com.ys.zy.bean.TabBean;
import com.ys.zy.fragment.HotGameFragment;
import com.ys.zy.fragment.InvitationCodeFragment;
import com.ys.zy.fragment.OddsTableFragment;
import com.ys.zy.fragment.SubReturnRateFragment;
import com.ys.zy.fragment.WinnerListFragment;
import com.ys.zy.ui.LhViewPager;
import com.ys.zy.util.DensityUtil;
import com.ys.zy.util.TabUtil;

import java.util.ArrayList;
import java.util.List;

//下级开户
public class SubOpenAccountActivity extends BaseActivity {
    private TabLayout tabLayout;
    private LhViewPager vp;
    private CommonFragmentAdapter mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_sub_open_account;
    }

    @Override
    public void initView() {
        setBarColor("#ededed");
        titleView.setText("下级开户");
        tabLayout = getView(R.id.tl_);
        vp = getView(R.id.vp_);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        mAdapter = new CommonFragmentAdapter(getSupportFragmentManager(), getList());
        vp.setAdapter(mAdapter);
        vp.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(vp);
    }

    @Override
    public void getData() {

    }

    @Override
    public void onClick(View v) {

    }

    private List<TabBean> getList() {
        List<TabBean> list = new ArrayList<>();
        list.add(new TabBean("下级返点率", SubReturnRateFragment.newInstance()));
        list.add(new TabBean("邀请码", InvitationCodeFragment.newInstance()));
        list.add(new TabBean("返点赔率表", OddsTableFragment.newInstance()));
        return list;
    }

    //刷新邀请码列表
    public void refreshInvitationCodeFragment() {
        ((InvitationCodeFragment) mAdapter.getItem(1)).onRefresh();
    }
}
