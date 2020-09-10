package com.ys.monitor.activity;

import android.view.View;

import com.ys.monitor.R;
import com.ys.monitor.base.BaseActivity;

public class YjtzDetailActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_yjtz_detail;
    }

    @Override
    public void initView() {
        setBarColor("#ffffff");
        titleView.setText("预警通知详情");
    }

    @Override
    public void getData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }
}
