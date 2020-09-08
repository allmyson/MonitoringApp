package com.ys.monitor.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.yanzhenjie.nohttp.rest.Response;
import com.ys.monitor.R;
import com.ys.monitor.base.BaseActivity;
import com.ys.monitor.bean.BaseBean;
import com.ys.monitor.http.HttpListener;
import com.ys.monitor.sp.UserSP;
import com.ys.monitor.util.HttpUtil;
import com.ys.monitor.util.KeyBoardUtils;
import com.ys.monitor.util.StringUtil;
import com.ys.monitor.util.YS;

public class LoginActivity extends BaseActivity implements View.OnFocusChangeListener {
    private EditText userET, psdET;
    private Button loginBtn;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
//        setBarColor("#ffffff", true);

        setTransparent2();

        userET = getView(R.id.et_user);
        psdET = getView(R.id.et_psd);
        userET.setOnFocusChangeListener(this);
        psdET.setOnFocusChangeListener(this);
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
        userET.setText("ceshi");
        psdET.setText("123456");
    }

    @Override
    public void getData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                KeyBoardUtils.closeKeybord(userET, mContext);
                KeyBoardUtils.closeKeybord(psdET, mContext);
                if (isCanLogin()) {
                    String user = userET.getText().toString().trim();
                    String psd = psdET.getText().toString().trim();
                    HttpUtil.login(mContext, user, psd, new HttpListener<String>() {
                        @Override
                        public void onSucceed(int what, Response<String> response) {
                            BaseBean baseBean = new Gson().fromJson(response.get(), BaseBean.class);
                            if (baseBean != null) {
                                if (YS.SUCCESE.equals(baseBean.code)) {
                                    //登录成功
                                    UserSP.saveLoginInfo(mContext, response.get());
                                    startActivity(new Intent(mContext, MainActivity.class));
                                    finish();
                                }
                                show(baseBean.msg);
                            } else {
                                show(YS.HTTP_TIP);
                            }
                        }

                        @Override
                        public void onFailed(int what, Response<String> response) {

                        }
                    });
//                    show("登录成功");
//                    startActivity(new Intent(mContext, MainActivity.class));
//                    finish();
                }
                break;
        }
    }

    private void getUser() {
        final String loginName = userET.getText().toString().trim();
        HttpUtil.getInfoByLoginName(mContext, loginName, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {

            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }
        });
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
        }
//        else if (user.length() < 8 || user.length() > 16 || !StringUtil.isLetterDigit(user)) {
//            show("用户名由8-16个字母或数字组成");
//            return false;
//        } else if (psd.length() < 8 || psd.length() > 16 || !StringUtil.isLetterDigit(psd)) {
//            show("密码由8-16个字母或数字组成");
//            return false;
//        }
        return true;
    }

    private boolean isCanFind() {
        String user = userET.getText().toString().trim();
        if (StringUtil.isBlank(user)) {
            show("用户名不能为空");
            return false;
        } else if (user.length() < 8 || user.length() > 16 || !StringUtil.isLetterDigit(user)) {
            show("用户名由8-16个字母或数字组成");
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

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.et_user:
                setFocusChange(hasFocus, getView(R.id.view_user));
                break;
            case R.id.et_psd:
                setFocusChange(hasFocus, getView(R.id.view_psd));
                break;
        }
    }
}
