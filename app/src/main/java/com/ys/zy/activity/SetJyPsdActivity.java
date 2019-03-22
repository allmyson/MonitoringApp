package com.ys.zy.activity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.ys.zy.R;
import com.ys.zy.base.BaseActivity;
import com.ys.zy.ui.TitleView;
import com.ys.zy.util.PwdCheckUtil;
import com.ys.zy.util.StringUtil;

public class SetJyPsdActivity extends BaseActivity implements TextWatcher {
    private EditText psdET, rePsdET;

    @Override
    public int getLayoutId() {
        return R.layout.activity_set_jy_psd;
    }

    @Override
    public void initView() {
        psdET = getView(R.id.et_psd);
        rePsdET = getView(R.id.et_rePsd);
        psdET.addTextChangedListener(this);
        rePsdET.addTextChangedListener(this);
        titleView.setText("设置交易密码").showBtn(true).setBtnText("完成").setBtnClickable(false).setDoListener(new TitleView.DoListener() {
            @Override
            public void finish() {
                if (isPsdValid()) {
                    show("保存成功");
                    SetJyPsdActivity.this.finish();
                }
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
        if (!PwdCheckUtil.isPsdValid(psdET.getText().toString().trim())) {
            show("密码格式不正确");
            return false;
        } else if (!PwdCheckUtil.isPsdValid(rePsdET.getText().toString().trim())) {
            show("确认密码格式不正确");
            return false;
        } else if (!psdET.getText().toString().trim().equals(rePsdET.getText().toString().trim())) {
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
}
