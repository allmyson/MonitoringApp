package com.ys.zy.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ys.zy.R;
import com.ys.zy.base.BaseActivity;
import com.ys.zy.ui.TitleView;

public class UserInfoActivity extends BaseActivity {


    @Override
    public int getLayoutId() {
        return R.layout.activity_user_info;
    }

    @Override
    public void initView() {
        titleView.setText("个人信息");
    }

    @Override
    public void getData() {

    }

    @Override
    public void onClick(View v) {

    }
}
