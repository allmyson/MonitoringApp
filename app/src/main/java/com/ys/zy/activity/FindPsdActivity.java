package com.ys.zy.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ys.zy.R;
import com.ys.zy.api.FunctionApi;
import com.ys.zy.base.BaseActivity;

public class FindPsdActivity extends BaseActivity {
    private LinearLayout phoneLL, kfLL;
    private ImageView phoneIV, kfIV;
    private Button nextBtn;
    private boolean isPhone = false;
    public static final int TYPE_LOGIN_PSD = 100;//找回登录密码
    public static final int TYPE_JY_PSD = 101;//找回交易密码
    private int type = TYPE_LOGIN_PSD;
    private static final String KEY = "key";

    @Override
    public int getLayoutId() {
        return R.layout.activity_find_psd;
    }

    @Override
    public void initView() {
        type = getIntent().getIntExtra(KEY, TYPE_LOGIN_PSD);
        setBarColor("#ededed");
        //通过手机找回
        if (type == TYPE_LOGIN_PSD) {
            //找回登录密码
            titleView.setText("忘记登录密码").showBtn(false);
        } else if (type == TYPE_JY_PSD) {
            //找回交易密码
            titleView.setText("忘记交易密码").showBtn(false);
        }
        phoneLL = getView(R.id.ll_phone);
        kfLL = getView(R.id.ll_kf);
        phoneIV = getView(R.id.iv_phone);
        kfIV = getView(R.id.iv_kf);
        nextBtn = getView(R.id.btn_next);
        phoneLL.setOnClickListener(this);
        kfLL.setOnClickListener(this);
        nextBtn.setOnClickListener(this);
        isPhone = true;
        phoneIV.setImageResource(R.mipmap.checkbox_icon_select);
        kfIV.setImageResource(R.mipmap.checkbox_icon_normal);
        setBtnClickable(true, nextBtn);
    }

    @Override
    public void getData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_phone:
                if (!isPhone) {
                    isPhone = true;
                    phoneIV.setImageResource(R.mipmap.checkbox_icon_select);
                    kfIV.setImageResource(R.mipmap.checkbox_icon_normal);
                }
                break;
            case R.id.ll_kf:
                if (isPhone) {
                    isPhone = false;
                    kfIV.setImageResource(R.mipmap.checkbox_icon_select);
                    phoneIV.setImageResource(R.mipmap.checkbox_icon_normal);
                }
                break;
            case R.id.btn_next:
                if (isPhone) {
                    //通过手机找回
                    if (type == TYPE_LOGIN_PSD) {
                        //找回登录密码
                        BindPhoneActivity.intentToVerifyPhone(mContext, BindPhoneActivity.TYPE_FIND_LOGIN_PSD);
                        finish();
                    } else if (type == TYPE_JY_PSD) {
                        //找回交易密码
                        BindPhoneActivity.intentToVerifyPhone(mContext, BindPhoneActivity.TYPE_FIND_JY_PSD);
                        finish();
                    }
                } else {
                    //通过客服找回
                    FunctionApi.contactKF(mContext);
                }
                break;
        }
    }

    public static void intentToFindPsdType(Context context, int type) {
        Intent intent = new Intent(context, FindPsdActivity.class);
        intent.putExtra(KEY, type);
        context.startActivity(intent);
    }
}
