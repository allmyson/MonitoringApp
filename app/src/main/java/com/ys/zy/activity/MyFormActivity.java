package com.ys.zy.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.LinearLayout;

import com.ys.zy.R;
import com.ys.zy.adapter.CommonFragmentAdapter;
import com.ys.zy.base.BaseActivity;
import com.ys.zy.bean.TabBean;
import com.ys.zy.fragment.InvitationCodeFragment;
import com.ys.zy.fragment.MyHdjjFragment;
import com.ys.zy.fragment.MyJybbFragment;
import com.ys.zy.fragment.MyXfbbFragment;
import com.ys.zy.fragment.OddsTableFragment;
import com.ys.zy.fragment.SubReturnRateFragment;
import com.ys.zy.fragment.TodayYKFragment;
import com.ys.zy.ui.LhViewPager;
import com.ys.zy.util.TabUtil;

import java.util.ArrayList;
import java.util.List;

public class MyFormActivity extends BaseActivity {
    private TabLayout tabLayout;
    private LhViewPager vp;
    private CommonFragmentAdapter mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_form;
    }

    @Override
    public void initView() {
        setBarColor("#ededed");
        titleView.setText("个人报表");
        tabLayout = getView(R.id.tl_);
        vp = getView(R.id.vp_);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        mAdapter = new CommonFragmentAdapter(getSupportFragmentManager(), getList());
        vp.setAdapter(mAdapter);
        vp.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(vp);
        LinearLayout linearLayout = (LinearLayout) tabLayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(this,
                R.drawable.layout_divider_vertical));
    }

    @Override
    public void getData() {

    }

    @Override
    public void onClick(View v) {

    }

    private List<TabBean> getList() {
        List<TabBean> list = new ArrayList<>();
        list.add(new TabBean("交易报表", MyJybbFragment.newInstance()));
        list.add(new TabBean("消费报表", MyXfbbFragment.newInstance()));
        list.add(new TabBean("活动奖金", MyHdjjFragment.newInstance()));
        list.add(new TabBean("今日盈亏", TodayYKFragment.newInstance()));
        return list;
    }
}
