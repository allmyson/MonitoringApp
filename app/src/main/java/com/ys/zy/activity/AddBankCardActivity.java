package com.ys.zy.activity;

import android.view.View;

import com.ys.zy.R;
import com.ys.zy.base.BaseActivity;

public class AddBankCardActivity extends BaseActivity {


    @Override
    public int getLayoutId() {
        return R.layout.activity_add_bank_card;
    }

    @Override
    public void initView() {
        setBarColor("#ededed");
        titleView.setText("添加银行卡");
    }

    @Override
    public void getData() {

    }

    @Override
    public void onClick(View v) {

    }
}
