package com.ys.zy.activity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.yanzhenjie.nohttp.rest.Response;
import com.ys.zy.R;
import com.ys.zy.base.BaseActivity;
import com.ys.zy.bean.BaseBean;
import com.ys.zy.http.HttpListener;
import com.ys.zy.sp.User;
import com.ys.zy.sp.UserSP;
import com.ys.zy.ui.TitleView;
import com.ys.zy.util.HttpUtil;
import com.ys.zy.util.PwdCheckUtil;
import com.ys.zy.util.StringUtil;
import com.ys.zy.util.YS;

public class SetJyPsdActivity extends BaseActivity implements TextWatcher, View.OnFocusChangeListener {
    private EditText psdET, rePsdET;
    private String userId;
    private String psd;

    @Override
    public int getLayoutId() {
        return R.layout.activity_set_jy_psd;
    }

    @Override
    public void initView() {
        setBarColor("#ededed");
        psdET = getView(R.id.et_psd);
        rePsdET = getView(R.id.et_rePsd);
        psdET.addTextChangedListener(this);
        rePsdET.addTextChangedListener(this);
        psdET.setOnFocusChangeListener(this);
        rePsdET.setOnFocusChangeListener(this);
        titleView.setText("设置交易密码").showBtn(true).setBtnText("完成").setBtnClickable(false).setDoListener(new TitleView.DoListener() {
            @Override
            public void finish() {
                if (isPsdValid()) {
                    updateJymm();
                }
            }
        });
        userId = UserSP.getUserId(mContext);
        psd = UserSP.getPassword(mContext);
    }

    private void updateJymm() {
        HttpUtil.updateZJMM(mContext, userId, psd, psdET.getText().toString().trim(), new HttpListener<String>() {
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

    @Override
    public void getData() {

    }

    @Override
    public void onClick(View v) {

    }

    private boolean isPsdValid() {
        String psd = psdET.getText().toString().trim();
        String rePsd = rePsdET.getText().toString().trim();
        if (psd.length() < 8 || psd.length() > 16 || !StringUtil.isLetterDigit(psd)) {
            show("密码格式不正确");
            return false;
        } else if (rePsd.length() < 8 || rePsd.length() > 16 || !StringUtil.isLetterDigit(rePsd)) {
            show("确认密码格式不正确");
            return false;
        } else if (!psd.equals(rePsd)) {
            show("两次输入新密码不一致");
            return false;
        }
        return true;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.length() == 0 || StringUtil.isBlank(psdET.getText().toString()) || StringUtil.isBlank(rePsdET.getText().toString())) {
            titleView.setBtnClickable(false);
        } else {
            titleView.setBtnClickable(true);
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.et_psd:
                setFocusChange(hasFocus, getView(R.id.view_psd));
                break;
            case R.id.et_rePsd:
                setFocusChange(hasFocus, getView(R.id.view_rePsd));
                break;
        }
    }
}
