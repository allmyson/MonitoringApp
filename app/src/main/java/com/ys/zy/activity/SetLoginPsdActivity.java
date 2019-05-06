package com.ys.zy.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.yanzhenjie.nohttp.rest.Response;
import com.ys.zy.R;
import com.ys.zy.base.BaseActivity;
import com.ys.zy.bean.BaseBean;
import com.ys.zy.http.HttpListener;
import com.ys.zy.sp.UserSP;
import com.ys.zy.ui.TitleView;
import com.ys.zy.util.ActivityUtil;
import com.ys.zy.util.HttpUtil;
import com.ys.zy.util.KeyBoardUtils;
import com.ys.zy.util.PwdCheckUtil;
import com.ys.zy.util.SPUtil;
import com.ys.zy.util.StringUtil;
import com.ys.zy.util.YS;

import java.security.Key;

public class SetLoginPsdActivity extends BaseActivity implements TextWatcher, View.OnFocusChangeListener {
    private EditText psdET, newPsdET, reNewPsdET;
    private RelativeLayout forgetRL;
    private String userId;

    @Override
    public int getLayoutId() {
        return R.layout.activity_set_login_psd;
    }

    @Override
    public void initView() {
        setBarColor("#ededed");
        titleView.setText("设置登录密码").showBtn(true).setBtnText("保存").setBtnClickable(false).setDoListener(new TitleView.DoListener() {
            @Override
            public void finish() {
                if (isCanUpdate()) {
                    updatePsd();
                }
            }
        });
        psdET = getView(R.id.et_psd);
        newPsdET = getView(R.id.et_newPsd);
        reNewPsdET = getView(R.id.et_reNewPsd);

        psdET.addTextChangedListener(this);
        newPsdET.addTextChangedListener(this);
        reNewPsdET.addTextChangedListener(this);


        psdET.setOnFocusChangeListener(this);
        newPsdET.setOnFocusChangeListener(this);
        reNewPsdET.setOnFocusChangeListener(this);
        forgetRL = getView(R.id.rl_forgetPsd);
        forgetRL.setOnClickListener(this);
        userId = UserSP.getUserId(mContext);
    }

    private void updatePsd() {
        KeyBoardUtils.closeKeybord(psdET, mContext);
        KeyBoardUtils.closeKeybord(newPsdET, mContext);
        KeyBoardUtils.closeKeybord(reNewPsdET, mContext);
        HttpUtil.updatePsd(mContext, userId, psdET.getText().toString().trim(), newPsdET.getText().toString().trim(), new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                BaseBean baseBean = new Gson().fromJson(response.get(), BaseBean.class);
                if (baseBean != null) {
                    if (YS.SUCCESE.equals(baseBean.code)) {
                        SPUtil.clear(mContext);
                        ActivityUtil.finish();
                        startActivity(new Intent(mContext, LoginActivity.class));
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
        switch (v.getId()) {
            case R.id.rl_forgetPsd:
//                show("忘记密码");
                startActivity(new Intent(mContext, FindPsdActivity.class));
                break;
        }
    }

    private boolean isCanUpdate() {
        String psd = psdET.getText().toString().trim();
        String newPsd = newPsdET.getText().toString().trim();
        String reNewPsd = reNewPsdET.getText().toString().trim();
        if (StringUtil.isBlank(psd) || StringUtil.isBlank(newPsd) || StringUtil.isBlank(reNewPsd)) {
            show("密码不能为空");
            return false;
        } else if (psd.length() < 8 || psd.length() > 16 || !StringUtil.isLetterDigit(psd)) {
            show("密码由8-16个字母或数字组成");
            return false;
        } else if (newPsd.length() < 8 || newPsd.length() > 16 || !StringUtil.isLetterDigit(newPsd)) {
            show("密码由8-16个字母或数字组成");
            return false;
        } else if (reNewPsd.length() < 8 || reNewPsd.length() > 16 || !StringUtil.isLetterDigit(reNewPsd)) {
            show("密码由8-16个字母或数字组成");
            return false;
        } else if (!newPsd.equals(reNewPsd)) {
            show("两次输入密码不一致");
            return false;
        } else if (psd.equals(newPsd)) {
            show("新密码不能与旧密码一致");
            return false;
        }
        return true;
    }

//    private boolean isPsdValid() {
//        if (!PwdCheckUtil.isPsdValid(psdET.getText().toString().trim())) {
//            show("原密码格式不正确");
//            return false;
//        } else if (!PwdCheckUtil.isPsdValid(newPsdET.getText().toString().trim())) {
//            show("新密码格式不正确");
//            return false;
//        } else if (!PwdCheckUtil.isPsdValid(reNewPsdET.getText().toString().trim())) {
//            show("确认密码格式不正确");
//            return false;
//        } else if (!newPsdET.getText().toString().trim().equals(reNewPsdET.getText().toString().trim())) {
//            show("两次输入新密码不一致");
//            return false;
//        }
//        return true;
//    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.length() == 0 || StringUtil.isBlank(psdET.getText().toString()) || StringUtil.isBlank(newPsdET.getText().toString()) || StringUtil.isBlank(reNewPsdET.getText().toString())) {
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
            case R.id.et_newPsd:
                setFocusChange(hasFocus, getView(R.id.view_rePsd));
                break;
            case R.id.et_reNewPsd:
                setFocusChange(hasFocus, getView(R.id.view_reNewPsd));
                break;
        }
    }
}
