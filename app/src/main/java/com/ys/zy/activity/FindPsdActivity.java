package com.ys.zy.activity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ys.zy.R;
import com.ys.zy.api.FunctionApi;
import com.ys.zy.base.BaseActivity;

public class FindPsdActivity extends BaseActivity {
    private LinearLayout phoneLL, kfLL;
    private ImageView phoneIV, kfIV;
    private Button nextBtn;
    private boolean isPhone = false;

    @Override
    public int getLayoutId() {
        return R.layout.activity_find_psd;
    }

    @Override
    public void initView() {
        setBarColor("#ededed");
        titleView.setText("忘记登录密码").showBtn(false);
        phoneLL = getView(R.id.ll_phone);
        kfLL = getView(R.id.ll_kf);
        phoneIV = getView(R.id.iv_phone);
        kfIV = getView(R.id.iv_kf);
        nextBtn = getView(R.id.btn_next);
        phoneLL.setOnClickListener(this);
        kfLL.setOnClickListener(this);
        nextBtn.setOnClickListener(this);
        isPhone = true;
        phoneIV.setImageResource(R.mipmap.checkbox_icon_select);
        kfIV.setImageResource(R.mipmap.checkbox_icon_normal);
        setBtnClickable(true, nextBtn);
    }

    @Override
    public void getData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_phone:
                if (!isPhone) {
                    isPhone = true;
                    phoneIV.setImageResource(R.mipmap.checkbox_icon_select);
                    kfIV.setImageResource(R.mipmap.checkbox_icon_normal);
                }
                break;
            case R.id.ll_kf:
                if (isPhone) {
                    isPhone = false;
                    kfIV.setImageResource(R.mipmap.checkbox_icon_select);
                    phoneIV.setImageResource(R.mipmap.checkbox_icon_normal);
                }
                break;
            case R.id.btn_next:
                if (isPhone) {
                    //通过手机找回
                } else {
                    //通过客服找回
                    FunctionApi.contactKF(mContext);
                }
                break;
        }
    }
}
