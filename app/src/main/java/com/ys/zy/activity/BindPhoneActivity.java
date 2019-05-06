package com.ys.zy.activity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.yanzhenjie.nohttp.rest.Response;
import com.ys.zy.R;
import com.ys.zy.base.BaseActivity;
import com.ys.zy.bean.Yzm;
import com.ys.zy.http.HttpListener;
import com.ys.zy.ui.TitleView;
import com.ys.zy.util.HttpUtil;
import com.ys.zy.util.PwdCheckUtil;
import com.ys.zy.util.StringUtil;
import com.ys.zy.util.YS;

public class BindPhoneActivity extends BaseActivity implements TextWatcher, View.OnFocusChangeListener {
    private EditText phoneET, numET;
    private Button sendBtn;
    private String yzm;

    @Override
    public int getLayoutId() {
        return R.layout.activity_bind_phone;
    }

    @Override
    public void initView() {
        setBarColor("#ededed");
        titleView.setText("绑定手机").showBtn(true).setBtnText("完成").setBtnClickable(false).setDoListener(new TitleView.DoListener() {
            @Override
            public void finish() {
                if (isCanSave()) {
                    bindPhone();
                }
            }
        });
        phoneET = getView(R.id.et_phone);
        numET = getView(R.id.et_num);
        phoneET.addTextChangedListener(this);
        numET.addTextChangedListener(this);
        sendBtn = getView(R.id.btn_send);
        sendBtn.setOnClickListener(this);
    }

    private void bindPhone() {
    }

    private boolean isCanSave() {
        if (!StringUtil.isPhone(phoneET.getText().toString().trim())) {
            show("请输入正确的手机号码");
            return false;
        } else if (!numET.getText().toString().trim().equals(yzm)) {
            show("请输入正确的验证码");
            return false;
        }
        return true;
    }

    @Override
    public void getData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send:
                if (StringUtil.isPhone(phoneET.getText().toString().trim())) {
                    sendMsg();
                } else {
                    show("请输入正确的手机号码");
                }
                break;
        }
    }

    private void sendMsg() {
        HttpUtil.getYZM(mContext, phoneET.getText().toString().trim(), new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                Yzm num = new Gson().fromJson(response.get(), Yzm.class);
                if (num != null) {
                    if (YS.SUCCESE.equals(num.code)) {
                        yzm = num.data;
                    }
                    show(num.msg);
                } else {
                    show(YS.HTTP_TIP);
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.length() == 0 || StringUtil.isBlank(phoneET.getText().toString()) || StringUtil.isBlank(numET.getText().toString())) {
            titleView.setBtnClickable(false);
        } else {
            titleView.setBtnClickable(true);
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.et_phone:
                setFocusChange(hasFocus, getView(R.id.view_phone));
                break;
            case R.id.et_yzm:
                setFocusChange(hasFocus, getView(R.id.view_yzm));
                break;
        }
    }
}
