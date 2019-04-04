package com.ys.zy.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ys.zy.R;
import com.ys.zy.base.BaseActivity;
import com.ys.zy.ui.ImaginaryLineView;

//代理说明
public class ProxyExplainActivity extends BaseActivity {
    private ImaginaryLineView ilv;

    @Override
    public int getLayoutId() {
        return R.layout.activity_proxy_explain;
    }

    @Override
    public void initView() {
        setBarColor("#ededed");
        titleView.setText("代理说明");
        ilv = getView(R.id.ilv_);
        ilv.setLineAttribute(Color.parseColor("#d1d1d1"), 5, new float[]{10,5});
    }

    @Override
    public void getData() {

    }

    @Override
    public void onClick(View v) {

    }
}
