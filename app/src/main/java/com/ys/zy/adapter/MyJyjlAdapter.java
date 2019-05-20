package com.ys.zy.adapter;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

import com.ys.zy.R;
import com.ys.zy.bean.MyJYBB;
import com.ys.zy.bean.SubJYJL;
import com.ys.zy.util.DateUtil;
import com.ys.zy.util.StringUtil;

import java.util.List;

public class MyJyjlAdapter extends CommonAdapter<MyJYBB.DataBeanX.DataBean> {
    public MyJyjlAdapter(Context context, List<MyJYBB.DataBeanX.DataBean> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, MyJYBB.DataBeanX.DataBean item, int position) {
        helper.setText(R.id.tv_time, DateUtil.changeTimeToYMDHMS(item.date_time));
        if ("1001".equals(item.type_code)) {
            //充值
            helper.setText(R.id.tv_type, StringUtil.valueOf(item.method_name));
        } else {
            //提现
            helper.setText(R.id.tv_type, StringUtil.valueOf(item.type_name));
        }
        helper.setText(R.id.tv_money, StringUtil.StringToDoubleStr(item.money));
        helper.setText(R.id.tv_status, StringUtil.valueOf(item.status_name));
        if("1000".equals(item.status_code)){
            //等待
            ((TextView) helper.getView(R.id.tv_status)).setTextColor(Color.parseColor("#a5a5a5"));
        }else if("1001".equals(item.status_code)){
            //失败
            ((TextView) helper.getView(R.id.tv_status)).setTextColor(Color.parseColor("#dd2230"));
        }else if("1002".equals(item.status_code)){
            //成功
            ((TextView) helper.getView(R.id.tv_status)).setTextColor(Color.parseColor("#27c427"));
        }
    }
}