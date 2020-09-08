package com.ys.monitor.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ys.monitor.R;
import com.ys.monitor.api.FunctionApi;
import com.ys.monitor.base.BaseActivity;
import com.ys.monitor.bean.MsgBean;
import com.ys.monitor.util.DateUtil;
import com.ys.monitor.util.StringUtil;

public class MsgDetailActivity extends BaseActivity {
    private static final String DATA = "data";
    private RelativeLayout backRL;
    private ImageView kfIV;
    private TextView titleTV, contentTV, timeTV;
    private MsgBean.DataBeanX.DataBean dataBean;

    @Override
    public int getLayoutId() {
        return R.layout.activity_msg_detail;
    }

    @Override
    public void initView() {
        backRL = getView(R.id.rl_back);
        kfIV = getView(R.id.iv_kf);
        backRL.setOnClickListener(this);
        kfIV.setOnClickListener(this);
        titleTV = getView(R.id.tv_title);
        contentTV = getView(R.id.tv_content);
        timeTV = getView(R.id.tv_time);
        dataBean = (MsgBean.DataBeanX.DataBean) getIntent().getSerializableExtra(DATA);
        if (dataBean != null) {
            titleTV.setText(StringUtil.valueOf(dataBean.notice_title));
            contentTV.setText("\t\t"+StringUtil.valueOf(dataBean.notice_content));
            timeTV.setText(DateUtil.changeTimeToYMD(dataBean.publish_time));
        }
    }

    @Override
    public void getData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.iv_kf:
                FunctionApi.contactKF(mContext);
                break;
        }
    }

    public static void intentToMsgDetail(Context context, MsgBean.DataBeanX.DataBean dataBean) {
        Intent intent = new Intent(context, MsgDetailActivity.class);
        intent.putExtra(DATA, dataBean);
        context.startActivity(intent);
    }
}
