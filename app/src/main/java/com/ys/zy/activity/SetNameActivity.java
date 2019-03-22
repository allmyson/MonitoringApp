package com.ys.zy.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.ys.zy.R;
import com.ys.zy.base.BaseActivity;
import com.ys.zy.ui.TitleView;
import com.ys.zy.util.StringUtil;

public class SetNameActivity extends BaseActivity {
    private EditText et;

    @Override
    public int getLayoutId() {
        return R.layout.activity_set_name;
    }

    @Override
    public void initView() {
        et = getView(R.id.et_);
        String name = getIntent().getStringExtra("name");
        et.setText(StringUtil.valueOf(name));
        et.setSelection(et.getText().length());
        titleView.setText("设置昵称").showBtn(true).setBtnText("保存").setDoListener(new TitleView.DoListener() {
            @Override
            public void finish() {
                show("保存成功");
//                finish();
                SetNameActivity.this.finish();
            }
        });
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    titleView.setBtnClickable(false);
                } else {
                    titleView.setBtnClickable(true);
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
}
