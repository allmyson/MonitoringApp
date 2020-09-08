package com.ys.monitor.activity;

import android.view.View;
import android.widget.TextView;

import com.ys.monitor.R;
import com.ys.monitor.api.FunctionApi;
import com.ys.monitor.base.BaseActivity;
import com.ys.monitor.util.ClipboardUtils;
import com.ys.monitor.util.YS;

public class KefuActivity extends BaseActivity {
    private TextView emailTV, phoneTV;

    @Override
    public int getLayoutId() {
        return R.layout.activity_kefu;
    }

    @Override
    public void initView() {
        setBarColor("#FFFFFF");
        titleView.setText("客服中心");
        phoneTV = getView(R.id.tv_kefuPhone);
        emailTV = getView(R.id.tv_email);
        phoneTV.setText(YS.kefuPhone);
        emailTV.setText(YS.kefuEmail);
        getView(R.id.btn_call).setOnClickListener(this);
        getView(R.id.btn_fuzhi).setOnClickListener(this);
    }

    @Override
    public void getData() {
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_call:
                FunctionApi.diallPhone(mContext, YS.kefuPhone);
                break;
            case R.id.btn_fuzhi:
                ClipboardUtils.copyText(mContext, YS.kefuEmail);
                show("复制成功!");
                break;
        }
    }

}
