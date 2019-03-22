package com.ys.zy.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ys.zy.R;
import com.ys.zy.adapter.BankCardAdapter;
import com.ys.zy.base.BaseActivity;

public class SafeActivity extends BaseActivity {
    private LinearLayout setLoginPsdLL, setJyPsdLL, bindPhoneLL, cardLL;
    private TextView bindTV;

    @Override
    public int getLayoutId() {
        return R.layout.activity_safe;
    }

    @Override
    public void initView() {
        titleView.setText("安全中心");
        setLoginPsdLL = getView(R.id.ll_setLoginPsd);
        setJyPsdLL = getView(R.id.ll_setJyPsd);
        bindPhoneLL = getView(R.id.ll_bindPhone);
        cardLL = getView(R.id.ll_card);
        bindTV = getView(R.id.tv_isBind);
        setLoginPsdLL.setOnClickListener(this);
        setJyPsdLL.setOnClickListener(this);
        bindPhoneLL.setOnClickListener(this);
        cardLL.setOnClickListener(this);
    }

    @Override
    public void getData() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_setLoginPsd:
                startActivity(new Intent(mContext, SetLoginPsdActivity.class));
                break;
            case R.id.ll_setJyPsd:
                startActivity(new Intent(mContext, SetJyPsdActivity.class));
                break;
            case R.id.ll_bindPhone:
                startActivity(new Intent(mContext, BindPhoneActivity.class));
                break;
            case R.id.ll_card:
                startActivity(new Intent(mContext, BankCardActivity.class));
                break;
        }
    }
}
