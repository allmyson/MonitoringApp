package com.ys.monitor.activity;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ys.monitor.R;
import com.ys.monitor.base.BaseActivity;
import com.ys.monitor.util.AppUtil;

public class AboutActivity extends BaseActivity {
    private LinearLayout  updateLL;
    private TextView versionNameTV;

    @Override
    public int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    public void initView() {
        setBarColor("#ffffff");
        titleView.setText("关于");
        updateLL = getView(R.id.ll_update);
        updateLL.setOnClickListener(this);
        versionNameTV = getView(R.id.tv_versionName);
        versionNameTV.setText("V" + AppUtil.getVersionName(mContext, AppUtil.getPackageName(mContext)));
    }

    @Override
    public void getData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_update:
                startActivity(new Intent(mContext, UpdateInfoActivity.class));
                break;
        }
    }
}
