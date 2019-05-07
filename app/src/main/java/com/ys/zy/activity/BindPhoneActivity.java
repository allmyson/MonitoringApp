package com.ys.zy.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.yanzhenjie.nohttp.rest.Response;
import com.ys.zy.R;
import com.ys.zy.base.BaseActivity;
import com.ys.zy.bean.BaseBean;
import com.ys.zy.bean.Yzm;
import com.ys.zy.http.HttpListener;
import com.ys.zy.sp.UserSP;
import com.ys.zy.ui.TitleView;
import com.ys.zy.util.HttpUtil;
import com.ys.zy.util.KeyBoardUtils;
import com.ys.zy.util.PwdCheckUtil;
import com.ys.zy.util.StringUtil;
import com.ys.zy.util.YS;

public class BindPhoneActivity extends BaseActivity implements TextWatcher, View.OnFocusChangeListener {
    private EditText phoneET, numET;
    private Button sendBtn;
    private String yzm;
    private String userId;
    public static final int TYPE_BIND_PHONE = 10;//绑定手机
    public static final int TYPE_FIND_LOGIN_PSD = 11;//找回登录密码
    public static final int TYPE_FIND_JY_PSD = 12;//找回交易密码

    private int type = TYPE_BIND_PHONE;
    private static final String KEY = "key_phone";

    @Override
    public int getLayoutId() {
        return R.layout.activity_bind_phone;
    }

    @Override
    public void initView() {
        type = getIntent().getIntExtra(KEY, TYPE_BIND_PHONE);
        setBarColor("#ededed");
        if (type == TYPE_BIND_PHONE) {
            //绑定手机
            titleView.setText("绑定手机");
        } else if (type == TYPE_FIND_LOGIN_PSD) {
            titleView.setText("通过手机找回登录密码");
        } else if (type == TYPE_FIND_JY_PSD) {
            titleView.setText("通过手机找回交易密码");
        }
        titleView.showBtn(true).setBtnText("完成").setBtnClickable(false).setDoListener(new TitleView.DoListener() {
            @Override
            public void finish() {
                if (isCanSave()) {
                    if (type == TYPE_BIND_PHONE) {
                        //绑定手机
                        bindPhone();
                    } else if (type == TYPE_FIND_LOGIN_PSD) {
                        //找回登录密码
                        ReSetLoginPsdActivity.intentToFindLoginPsd(mContext, phoneET.getText().toString().trim(), numET.getText().toString().trim());
                        BindPhoneActivity.this.finish();
                    } else if (type == TYPE_FIND_JY_PSD) {
                        //找回交易密码
                        ReSetJyPsdActivity.intentToFindJyPsd(mContext, phoneET.getText().toString().trim(), numET.getText().toString().trim());
                        BindPhoneActivity.this.finish();
                    }
                }
            }
        });
        phoneET = getView(R.id.et_phone);
        numET = getView(R.id.et_num);
        phoneET.addTextChangedListener(this);
        numET.addTextChangedListener(this);
        sendBtn = getView(R.id.btn_send);
        sendBtn.setOnClickListener(this);
        userId = UserSP.getUserId(mContext);
    }

    private void bindPhone() {
        KeyBoardUtils.closeKeybord(phoneET, mContext);
        KeyBoardUtils.closeKeybord(numET, mContext);
        HttpUtil.bindPhone(mContext, userId, phoneET.getText().toString().trim(), new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                BaseBean baseBean = new Gson().fromJson(response.get(), BaseBean.class);
                if (baseBean != null) {
                    if (YS.SUCCESE.equals(baseBean.code)) {
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
                    start();
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


    private CountDownTimer countDownTimer;

    private void initTimer() {
        countDownTimer = new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                sendBtn.setText(millisUntilFinished / 1000 + "s");
                sendBtn.setTextColor(Color.GRAY);
                sendBtn.setClickable(false);
            }

            @Override
            public void onFinish() {
                sendBtn.setText("重新发送");
                sendBtn.setTextColor(Color.parseColor("#dd2230"));
                sendBtn.setClickable(true);
            }
        };
    }

    /**
     * 开启倒计时
     */
    public void start() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        initTimer();
        countDownTimer.start();
    }


    /**
     * destroy
     */
    public void cancel() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cancel();
    }

    public static void intentToVerifyPhone(Context context, int type) {
        Intent intent = new Intent(context, BindPhoneActivity.class);
        intent.putExtra(KEY, type);
        context.startActivity(intent);
    }
}
