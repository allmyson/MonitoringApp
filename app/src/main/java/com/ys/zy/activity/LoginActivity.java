package com.ys.zy.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ys.zy.R;
import com.ys.zy.base.BaseActivity;
import com.ys.zy.util.StringUtil;

public class LoginActivity extends BaseActivity {
    private EditText userET, psdET;
    private Button loginBtn;
    private ImageView showOrHideIV;
    private boolean isShow = false;
    private TextView forgetTV, kfTV;
    private LinearLayout registLL;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        setBarColor("#ffffff", true);
        showOrHideIV = getView(R.id.iv_showOrHide);
        showOrHideIV.setOnClickListener(this);
        userET = getView(R.id.et_user);
        psdET = getView(R.id.et_psd);
        forgetTV = getView(R.id.tv_forget);
        kfTV = getView(R.id.tv_kf);
        registLL = getView(R.id.ll_regist);
        forgetTV.setOnClickListener(this);
        kfTV.setOnClickListener(this);
        registLL.setOnClickListener(this);
        loginBtn = getView(R.id.btn_login);
        loginBtn.setOnClickListener(this);
        setCanClick(false);
        userET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0 || StringUtil.isBlank(psdET.getText().toString())) {
                    setCanClick(false);
                } else {
                    setCanClick(true);
                }
            }
        });
        psdET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0 || StringUtil.isBlank(userET.getText().toString())) {
                    setCanClick(false);
                } else {
                    setCanClick(true);
                }
            }
        });
    }

    @Override
    public void getData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_showOrHide:
                if (isShow) {
                    isShow = false;
                    showOrHideIV.setImageResource(R.mipmap.btn_hide);
                    psdET.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    isShow = true;
                    showOrHideIV.setImageResource(R.mipmap.btn_show);
                    psdET.setTransformationMethod(null);
                }
                break;
            case R.id.tv_forget:
//                startActivity(new Intent(mContext,));
                break;
            case R.id.tv_kf:
                break;
            case R.id.ll_regist:
                startActivity(new Intent(mContext, RegistActivity.class));
                break;
            case R.id.btn_login:
                if (isCanLogin()) {
                    show("登录成功");
                    startActivity(new Intent(mContext, MainActivity.class));
                    finish();
                }
                break;
        }
    }

    private boolean isCanLogin() {
        String user = userET.getText().toString().trim();
        String psd = psdET.getText().toString().trim();
        if (StringUtil.isBlank(user)) {
            show("用户名不能为空");
            return false;
        } else if (StringUtil.isBlank(psd)) {
            show("密码不能为空");
            return false;
        } else if (user.length() < 4 || user.length() > 16) {
            show("用户名由4-16个字母或数字组成");
            return false;
        } else if (psd.length() < 4 || psd.length() > 16) {
            show("密码由8-16个字母或数字组成");
            return false;
        }
        return true;
    }

    private void setCanClick(boolean isCan) {
        if (isCan) {
            loginBtn.setClickable(true);
            loginBtn.setAlpha(1f);
        } else {
            loginBtn.setClickable(false);
            loginBtn.setAlpha(0.1f);
        }
    }
}
