package com.ys.zy.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yanzhenjie.nohttp.rest.Response;
import com.ys.zy.R;
import com.ys.zy.adapter.BankCardAdapter;
import com.ys.zy.base.BaseActivity;
import com.ys.zy.http.HttpListener;
import com.ys.zy.sp.User;
import com.ys.zy.sp.UserSP;
import com.ys.zy.util.HttpUtil;
import com.ys.zy.util.StringUtil;

public class SafeActivity extends BaseActivity {
    private LinearLayout setLoginPsdLL, setJyPsdLL, bindPhoneLL, cardLL;
    private TextView bindTV;
    private String userId;
    private TextView jymmTV;
    private boolean hasJymm = false;

    @Override
    public int getLayoutId() {
        return R.layout.activity_safe;
    }

    @Override
    public void initView() {
        setBarColor("#ededed");
        titleView.setText("安全中心");
        jymmTV = getView(R.id.tv_jymm);
        setLoginPsdLL = getView(R.id.ll_setLoginPsd);
        setJyPsdLL = getView(R.id.ll_setJyPsd);
        bindPhoneLL = getView(R.id.ll_bindPhone);
        cardLL = getView(R.id.ll_card);
        bindTV = getView(R.id.tv_isBind);
        setLoginPsdLL.setOnClickListener(this);
        setJyPsdLL.setOnClickListener(this);
        bindPhoneLL.setOnClickListener(this);
        cardLL.setOnClickListener(this);
        userId = UserSP.getUserId(mContext);
    }

    @Override
    protected void onResume() {
        super.onResume();
        HttpUtil.getUserInfoById(mContext, userId, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                User user = new Gson().fromJson(response.get(), User.class);
                if (user != null && user.data != null) {
                    if (StringUtil.isBlank(user.data.moneyPwd)) {
                        jymmTV.setText("设置交易密码");
                        hasJymm = false;
                    } else {
                        jymmTV.setText("修改交易密码");
                        hasJymm = true;
                    }
                    if (StringUtil.isBlank(user.data.tel)) {
                        bindTV.setText("未绑定");
                    } else {
                        bindTV.setText("已绑定");
                    }
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }
        });
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
                if ("设置交易密码".equals(jymmTV.getText().toString().trim())) {
                    startActivity(new Intent(mContext, SetJyPsdActivity.class));
                } else {
                    startActivity(new Intent(mContext, UpdateJyPsdActivity.class));
                }
                break;
            case R.id.ll_bindPhone:
                BindPhoneActivity.intentToVerifyPhone(mContext, BindPhoneActivity.TYPE_BIND_PHONE);
                break;
            case R.id.ll_card:
                if (hasJymm) {
                    startActivity(new Intent(mContext, BankCardActivity.class));
                } else {
                    show("请先绑定交易密码");
                }
                break;
        }
    }
}
