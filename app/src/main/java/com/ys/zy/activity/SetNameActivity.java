package com.ys.zy.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.ys.zy.sp.UserSP;
import com.ys.zy.ui.TitleView;
import com.ys.zy.util.HttpUtil;
import com.ys.zy.util.KeyBoardUtils;
import com.ys.zy.util.StringUtil;
import com.ys.zy.util.YS;

public class SetNameActivity extends BaseActivity {
    private EditText et;
    private String userId;

    @Override
    public int getLayoutId() {
        return R.layout.activity_set_name;
    }

    @Override
    public void initView() {
        setBarColor("#ededed");
        et = getView(R.id.et_);
        String name = getIntent().getStringExtra("name");
        et.setText(StringUtil.valueOf(name));
        et.setSelection(et.getText().length());
        titleView.setText("设置昵称").showBtn(true).setBtnText("保存").setDoListener(new TitleView.DoListener() {
            @Override
            public void finish() {
                save();
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
        userId = UserSP.getUserId(mContext);
    }

    @Override
    public void getData() {

    }

    @Override
    public void onClick(View v) {

    }

    private void save() {
        KeyBoardUtils.closeKeybord(et,mContext);
        HttpUtil.updateUserInfo(mContext, userId, et.getText().toString().trim(), null, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                BaseBean baseBean = new Gson().fromJson(response.get(), BaseBean.class);
                if (baseBean != null) {
                    if (YS.SUCCESE.equals(baseBean.code)) {
                        finish();
                    }
                    show(StringUtil.valueOf(baseBean.msg));
                } else {
                    show(YS.HTTP_TIP);
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }
        });
    }
}
