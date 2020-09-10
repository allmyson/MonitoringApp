package com.ys.monitor.activity;

import android.support.design.widget.TabLayout;
import android.view.View;

import com.ys.monitor.R;
import com.ys.monitor.adapter.CommonFragmentAdapter;
import com.ys.monitor.base.BaseActivity;
import com.ys.monitor.bean.TabBean;
import com.ys.monitor.fragment.FireFragment;
import com.ys.monitor.fragment.TaskFragment;
import com.ys.monitor.ui.LhViewPager;

import java.util.ArrayList;
import java.util.List;

public class TaskListActivity extends BaseActivity {
    private TabLayout tabLayout;
    private LhViewPager vp;
    private CommonFragmentAdapter mAdapter;
    @Override
    public int getLayoutId() {
        return R.layout.activity_task;
    }

    @Override
    public void initView() {
        setBarColor("#ffffff");
        titleView.setText("任务通知");
        tabLayout = getView(R.id.tl_);
        vp = getView(R.id.vp_);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        mAdapter = new CommonFragmentAdapter(getSupportFragmentManager(), getList());
        vp.setAdapter(mAdapter);
        vp.setOffscreenPageLimit(2);
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
        list.add(new TabBean("待处理", TaskFragment.newInstance(FireFragment.TYPE_UNDO)));
        list.add(new TabBean("已完成", TaskFragment.newInstance(FireFragment.TYPE_FINISH)));
        return list;
    }

}
