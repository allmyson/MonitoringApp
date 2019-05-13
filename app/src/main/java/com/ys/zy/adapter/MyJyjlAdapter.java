package com.ys.zy.adapter;

import android.content.Context;

import com.ys.zy.R;
import com.ys.zy.bean.SubJYJL;
import com.ys.zy.util.DateUtil;
import com.ys.zy.util.StringUtil;

import java.util.List;

public class MyJyjlAdapter extends CommonAdapter<SubJYJL.DataBeanX.DataBean> {
    public MyJyjlAdapter(Context context, List<SubJYJL.DataBeanX.DataBean> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, SubJYJL.DataBeanX.DataBean item, int position) {
        helper.setText(R.id.tv_time, DateUtil.changeTimeToYMD(item.date_time));
        if ("1001".equals(item.type_code)) {
            //充值
            helper.setText(R.id.tv_name, StringUtil.valueOf(item.money));
            helper.setText(R.id.tv_odds, "0");
        } else {
            //提现
            helper.setText(R.id.tv_name, "0");
            helper.setText(R.id.tv_odds, StringUtil.valueOf(item.money));
        }
    }
}