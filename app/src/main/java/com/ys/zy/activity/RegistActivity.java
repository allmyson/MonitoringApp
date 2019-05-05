package com.ys.zy.activity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.yanzhenjie.nohttp.rest.Response;
import com.ys.zy.R;
import com.ys.zy.base.BaseActivity;
import com.ys.zy.bean.BaseBean;
import com.ys.zy.http.HttpListener;
import com.ys.zy.util.HttpUtil;
import com.ys.zy.util.StringUtil;
import com.ys.zy.util.YS;

public class RegistActivity extends BaseActivity implements View.OnFocusChangeListener {
    private EditText userET, psdET, rePsdET, yzmET, yqmET;
    private ImageView showOrHide1IV, showOrHide2IV;
    private boolean isShow1 = false;
    private boolean isShow2 = false;
    private ImageView yzmIV;
    private Button registBtn;

    @Override
    public int getLayoutId() {
        return R.layout.activity_regist;
    }

    @Override
    public void initView() {
        setBarColor("#ffffff", true);
        userET = getView(R.id.et_user);
        psdET = getView(R.id.et_psd);
        rePsdET = getView(R.id.et_rePsd);
        yzmET = getView(R.id.et_yzm);
        yqmET = getView(R.id.et_yqm);
        userET.setOnFocusChangeListener(this);
        psdET.setOnFocusChangeListener(this);
        rePsdET.setOnFocusChangeListener(this);
        yzmET.setOnFocusChangeListener(this);
        yqmET.setOnFocusChangeListener(this);
        showOrHide1IV = getView(R.id.iv_showOrHide);
        showOrHide2IV = getView(R.id.iv_showOrHide2);
        showOrHide1IV.setOnClickListener(this);
        showOrHide2IV.setOnClickListener(this);
        yzmIV = getView(R.id.iv_yzm);
        yzmIV.setOnClickListener(this);
        registBtn = getView(R.id.btn_regist);
        registBtn.setOnClickListener(this);
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
                if (s.length() == 0 || StringUtil.isBlank(psdET.getText().toString()) || StringUtil.isBlank(rePsdET.getText().toString()) || StringUtil.isBlank(yzmET.getText().toString()) || StringUtil.isBlank(yqmET.getText().toString())) {
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
                if (s.length() == 0 || StringUtil.isBlank(userET.getText().toString()) || StringUtil.isBlank(rePsdET.getText().toString()) || StringUtil.isBlank(yzmET.getText().toString()) || StringUtil.isBlank(yqmET.getText().toString())) {
                    setCanClick(false);
                } else {
                    setCanClick(true);
                }
            }
        });
        rePsdET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0 || StringUtil.isBlank(psdET.getText().toString()) || StringUtil.isBlank(userET.getText().toString()) || StringUtil.isBlank(yzmET.getText().toString()) || StringUtil.isBlank(yqmET.getText().toString())) {
                    setCanClick(false);
                } else {
                    setCanClick(true);
                }
            }
        });
        yzmET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0 || StringUtil.isBlank(psdET.getText().toString()) || StringUtil.isBlank(rePsdET.getText().toString()) || StringUtil.isBlank(userET.getText().toString()) || StringUtil.isBlank(yqmET.getText().toString())) {
                    setCanClick(false);
                } else {
                    setCanClick(true);
                }
            }
        });
        yqmET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0 || StringUtil.isBlank(psdET.getText().toString()) || StringUtil.isBlank(rePsdET.getText().toString()) || StringUtil.isBlank(yzmET.getText().toString()) || StringUtil.isBlank(userET.getText().toString())) {
                    setCanClick(false);
                } else {
                    setCanClick(true);
                }
            }
        });
    }

    @Override
    public void getData() {
        getYzm();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_showOrHide:
                if (isShow1) {
                    isShow1 = false;
                    showOrHide1IV.setImageResource(R.mipmap.btn_hide);
                    psdET.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    isShow1 = true;
                    showOrHide1IV.setImageResource(R.mipmap.btn_show);
                    psdET.setTransformationMethod(null);
                }
                break;

            case R.id.iv_showOrHide2:
                if (isShow2) {
                    isShow2 = false;
                    showOrHide2IV.setImageResource(R.mipmap.btn_hide);
                    rePsdET.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    isShow2 = true;
                    showOrHide2IV.setImageResource(R.mipmap.btn_show);
                    rePsdET.setTransformationMethod(null);
                }
                break;
            case R.id.iv_yzm:
                getYzm();
                break;
            case R.id.btn_regist:
                if (isCanRegist()) {
                    String user = userET.getText().toString().trim();
                    String psd = psdET.getText().toString().trim();
                    String yzm = yzmET.getText().toString().trim();
                    String yqm = yqmET.getText().toString().trim();
                    HttpUtil.regist(mContext, user, psd, yqm, yzm, new HttpListener<String>() {
                        @Override
                        public void onSucceed(int what, Response<String> response) {
                            BaseBean baseBean = new Gson().fromJson(response.get(), BaseBean.class);
                            if (baseBean != null) {
                                if (YS.SUCCESE.equals(baseBean.code)) {
                                    finish();
                                } else {
                                    //注册失败刷新验证码
                                    getYzm();
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
                }
                break;
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
            case R.id.et_rePsd:
                setFocusChange(hasFocus, getView(R.id.view_rePsd));
                break;
            case R.id.et_yzm:
                setFocusChange(hasFocus, getView(R.id.view_yzm));
                break;
            case R.id.et_yqm:
                setFocusChange(hasFocus, getView(R.id.view_yqm));
                break;
        }
    }

    private boolean isCanRegist() {
        String user = userET.getText().toString().trim();
        String psd = psdET.getText().toString().trim();
        String rePsd = rePsdET.getText().toString().trim();
        String yzm = yzmET.getText().toString().trim();
        String yqm = yqmET.getText().toString().trim();
        if (StringUtil.isBlank(user)) {
            show("用户名不能为空");
            return false;
        } else if (StringUtil.isBlank(psd)) {
            show("密码不能为空");
            return false;
        } else if (StringUtil.isBlank(rePsd)) {
            show("密码不能为空");
            return false;
        } else if (StringUtil.isBlank(yzm)) {
            show("验证码不能为空");
            return false;
        } else if (StringUtil.isBlank(yqm)) {
            show("邀请码不能为空");
            return false;
        } else if (user.length() < 8 || user.length() > 16 || !StringUtil.isLetterDigit(user)) {
            show("用户名由8-16个字母或数字组成");
            return false;
        } else if (psd.length() < 8 || psd.length() > 16 || !StringUtil.isLetterDigit(psd)) {
            show("密码由8-16个字母或数字组成");
            return false;
        } else if (rePsd.length() < 8 || rePsd.length() > 16 || !StringUtil.isLetterDigit(rePsd)) {
            show("密码由8-16个字母或数字组成");
            return false;
        } else if (!psd.equals(rePsd)) {
            show("两次输入密码不一致");
            return false;
        }
        return true;
    }

    private void setCanClick(boolean isCan) {
        if (isCan) {
            registBtn.setClickable(true);
            registBtn.setAlpha(1f);
        } else {
            registBtn.setClickable(false);
            registBtn.setAlpha(0.1f);
        }
    }

    private void getYzm() {
        HttpUtil.getYzmImage(mContext, new HttpListener<Bitmap>() {
            @Override
            public void onSucceed(int what, Response<Bitmap> response) {
                Bitmap bitmap = response.get();
                if (bitmap != null) {
                    yzmIV.setImageBitmap(bitmap);
                }
            }

            @Override
            public void onFailed(int what, Response<Bitmap> response) {

            }
        });
    }
}
