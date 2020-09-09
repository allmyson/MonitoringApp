package com.ys.monitor.activity;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ys.monitor.R;
import com.ys.monitor.base.BaseActivity;

public class SetActivity extends BaseActivity {
    private LinearLayout  updateLL;
    private TextView versionNameTV;

    @Override
    public int getLayoutId() {
        return R.layout.activity_set;
    }

    @Override
    public void initView() {
        setBarColor("#ffffff");
        titleView.setText("设置");
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
