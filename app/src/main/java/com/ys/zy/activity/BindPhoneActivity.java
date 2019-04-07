package com.ys.zy.activity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ys.zy.R;
import com.ys.zy.base.BaseActivity;
import com.ys.zy.ui.TitleView;
import com.ys.zy.util.StringUtil;

public class BindPhoneActivity extends BaseActivity implements TextWatcher {
    private EditText phoneET, numET;
    private Button sendBtn;

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
                    show("保存成功");
                    BindPhoneActivity.this.finish();
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

    private boolean isCanSave() {
        return true;
    }

    @Override
    public void getData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send:
                show("发短信");
                break;
        }
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
}
