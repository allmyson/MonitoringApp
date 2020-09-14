package com.ys.monitor.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ys.monitor.R;
import com.ys.monitor.base.BaseActivity;
import com.ys.monitor.bean.YjtzBean;
import com.ys.monitor.util.DateUtil;
import com.ys.monitor.util.StringUtil;

public class YjtzDetailActivity extends BaseActivity {
    private TextView titleTV,timeTV,contentTV;
    private YjtzBean.DataBean.RowsBean rowsBean;
    @Override
    public int getLayoutId() {
        return R.layout.activity_yjtz_detail;
    }

    @Override
    public void initView() {
        setBarColor("#ffffff");
        titleView.setText("预警通知详情");
        titleTV = getView(R.id.tv_title);
        timeTV = getView(R.id.tv_time);
        contentTV = getView(R.id.tv_content);
    }

    @Override
    public void getData() {
        String json = getIntent().getStringExtra("data");
        if(StringUtil.isGoodJson(json)){
            rowsBean = new Gson().fromJson(json,YjtzBean.DataBean.RowsBean.class);
            if(rowsBean!=null){
                titleTV.setText(StringUtil.valueOf(rowsBean.title));
                timeTV.setText(DateUtil.changeTimeToYMDHMS(StringUtil.valueOf(rowsBean.createTime)));
                contentTV.setText(StringUtil.valueOf(rowsBean.content));
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    public static void intentToDetail(Context context, String json){
        Intent intent = new Intent(context,YjtzDetailActivity.class);
        intent.putExtra("data",json);
        context.startActivity(intent);
    }
}
