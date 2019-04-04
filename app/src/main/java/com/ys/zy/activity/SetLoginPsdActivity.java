package com.ys.zy.activity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.ys.zy.R;
import com.ys.zy.base.BaseActivity;
import com.ys.zy.ui.TitleView;
import com.ys.zy.util.PwdCheckUtil;
import com.ys.zy.util.StringUtil;

public class SetLoginPsdActivity extends BaseActivity implements TextWatcher {
    private EditText psdET, newPsdET, reNewPsdET;
    private RelativeLayout forgetRL;

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
                if (isPsdValid()) {
                    show("保存成功");
                    SetLoginPsdActivity.this.finish();
                }
            }
        });
        psdET = getView(R.id.et_psd);
        newPsdET = getView(R.id.et_newPsd);
        reNewPsdET = getView(R.id.et_reNewPsd);

        psdET.addTextChangedListener(this);
        newPsdET.addTextChangedListener(this);
        reNewPsdET.addTextChangedListener(this);
        forgetRL = getView(R.id.rl_forgetPsd);
        forgetRL.setOnClickListener(this);
    }

    @Override
    public void getData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_forgetPsd:
                show("忘记密码");
                break;
        }
    }

    private boolean isPsdValid() {
        if (!PwdCheckUtil.isPsdValid(psdET.getText().toString().trim())) {
            show("原密码格式不正确");
            return false;
        } else if (!PwdCheckUtil.isPsdValid(newPsdET.getText().toString().trim())) {
            show("新密码格式不正确");
            return false;
        } else if (!PwdCheckUtil.isPsdValid(reNewPsdET.getText().toString().trim())) {
            show("确认密码格式不正确");
            return false;
        } else if (!newPsdET.getText().toString().trim().equals(reNewPsdET.getText().toString().trim())) {
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
        if (s.length() == 0 || StringUtil.isBlank(psdET.getText().toString()) || StringUtil.isBlank(newPsdET.getText().toString()) || StringUtil.isBlank(reNewPsdET.getText().toString())) {
            titleView.setBtnClickable(false);
        } else {
            titleView.setBtnClickable(true);
        }
    }
}
