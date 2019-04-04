package com.ys.zy.activity;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.ys.zy.R;
import com.ys.zy.adapter.SubDealHistoryAdapter;
import com.ys.zy.base.BaseActivity;

//代理中心
public class ProxyCenterActivity extends BaseActivity {
    private LinearLayout dlsmLL, xjkhLL, tdbbLL, xjglLL, xjjyjlLL;

    @Override
    public int getLayoutId() {
        return R.layout.activity_proxy_center;
    }

    @Override
    public void initView() {
        setBarColor("#ededed");
        titleView.setText("代理中心");
        dlsmLL = getView(R.id.ll_dlsm);
        xjkhLL = getView(R.id.ll_xjkh);
        tdbbLL = getView(R.id.ll_tdbb);
        xjglLL = getView(R.id.ll_xjgl);
        xjjyjlLL = getView(R.id.ll_xjjyjl);
        dlsmLL.setOnClickListener(this);
        xjkhLL.setOnClickListener(this);
        tdbbLL.setOnClickListener(this);
        xjglLL.setOnClickListener(this);
        xjjyjlLL.setOnClickListener(this);
    }

    @Override
    public void getData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_dlsm:
                startActivity(new Intent(mContext, ProxyExplainActivity.class));
                break;
            case R.id.ll_xjkh:
                startActivity(new Intent(mContext, SubOpenAccountActivity.class));
                break;
            case R.id.ll_tdbb:
                startActivity(new Intent(mContext, TeamReportActivity.class));
                break;
            case R.id.ll_xjgl:
                startActivity(new Intent(mContext, SubManageActivity.class));
                break;
            case R.id.ll_xjjyjl:
                startActivity(new Intent(mContext, SubDealHistoryActivity.class));
                break;
        }
    }
}
