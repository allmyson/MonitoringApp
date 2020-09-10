package com.ys.monitor.activity;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.ys.monitor.R;
import com.ys.monitor.base.BaseActivity;
import com.ys.monitor.sp.UserSP;
import com.ys.monitor.util.KeyBoardUtils;
import com.ys.monitor.util.StringUtil;

public class ReSetLoginPsdActivity extends BaseActivity implements TextWatcher, View.OnFocusChangeListener {
    private EditText newPsdET, reNewPsdET;
    private String userId;
    private String phone, yzm;

    @Override
    public int getLayoutId() {
        return R.layout.activity_re_set_login_psd;
    }

    @Override
    public void initView() {
        phone = getIntent().getStringExtra("phone");
        yzm = getIntent().getStringExtra("yzm");
        setBarColor("#ededed");
//        titleView.setText("设置登录密码").showBtn(true).setBtnText("保存").setBtnClickable(false).setDoListener(new TitleView.DoListener() {
//            @Override
//            public void finish() {
//                if (isCanUpdate()) {
//                    updatePsd();
//                }
//            }
//        });
        newPsdET = getView(R.id.et_newPsd);
        reNewPsdET = getView(R.id.et_reNewPsd);

        newPsdET.addTextChangedListener(this);
        reNewPsdET.addTextChangedListener(this);


        newPsdET.setOnFocusChangeListener(this);
        reNewPsdET.setOnFocusChangeListener(this);
        userId = getIntent().getStringExtra("userId");
        if (StringUtil.isBlank(userId)) {
            userId = UserSP.getUserId(mContext);
        }
    }

    private void updatePsd() {
        KeyBoardUtils.closeKeybord(newPsdET, mContext);
        KeyBoardUtils.closeKeybord(reNewPsdET, mContext);
//        HttpUtil.findLoginPsd(mContext, phone, newPsdET.getText().toString().trim(), yzm, new HttpListener<String>() {
//            @Override
//            public void onSucceed(int what, Response<String> response) {
//                BaseBean baseBean = new Gson().fromJson(response.get(), BaseBean.class);
//                if (baseBean != null) {
//                    if (YS.SUCCESE.equals(baseBean.code)) {
//                        SPUtil.clear(mContext);
//                        ActivityUtil.finish();
//                        startActivity(new Intent(mContext, LoginActivity.class));
//                    }
//                    show(baseBean.msg);
//                } else {
//                    show(YS.HTTP_TIP);
//                }
//            }
//
//            @Override
//            public void onFailed(int what, Response<String> response) {
//
//            }
//        });
    }

    @Override
    public void getData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_forgetPsd:
//                show("忘记密码");
//                startActivity(new Intent(mContext, FindPsdActivity.class));
                break;
        }
    }

    private boolean isCanUpdate() {
        String newPsd = newPsdET.getText().toString().trim();
        String reNewPsd = reNewPsdET.getText().toString().trim();
        if (StringUtil.isBlank(newPsd) || StringUtil.isBlank(reNewPsd)) {
            show("密码不能为空");
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
//        if (s.length() == 0 || StringUtil.isBlank(newPsdET.getText().toString()) || StringUtil.isBlank(reNewPsdET.getText().toString())) {
//            titleView.setBtnClickable(false);
//        } else {
//            titleView.setBtnClickable(true);
//        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.et_newPsd:
                setFocusChange(hasFocus, getView(R.id.view_rePsd));
                break;
            case R.id.et_reNewPsd:
                setFocusChange(hasFocus, getView(R.id.view_reNewPsd));
                break;
        }
    }

    public static void intentToFindLoginPsd(Context context, String phone, String yzm) {
        Intent intent = new Intent(context, ReSetLoginPsdActivity.class);
        intent.putExtra("phone", phone);
        intent.putExtra("yzm", yzm);
        context.startActivity(intent);
    }

    public static void intentToFindLoginPsd(Context context, String userId, String phone, String yzm) {
        Intent intent = new Intent(context, ReSetLoginPsdActivity.class);
        intent.putExtra("phone", phone);
        intent.putExtra("yzm", yzm);
        intent.putExtra("userId", userId);
        context.startActivity(intent);
    }
}
