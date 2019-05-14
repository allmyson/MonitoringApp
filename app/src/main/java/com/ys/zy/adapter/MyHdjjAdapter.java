package com.ys.zy.adapter;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

import com.ys.zy.R;
import com.ys.zy.bean.HdjjBean;
import com.ys.zy.bean.SubJYJL;
import com.ys.zy.util.DateUtil;
import com.ys.zy.util.StringUtil;

import java.util.List;

public class MyHdjjAdapter extends CommonAdapter<HdjjBean.DataBeanX.DataBean> {
    public MyHdjjAdapter(Context context, List<HdjjBean.DataBeanX.DataBean> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, HdjjBean.DataBeanX.DataBean item, int position) {
        helper.setText(R.id.tv_name, StringUtil.valueOf(item.activity_name));
        helper.setText(R.id.tv_odds, StringUtil.StringToDoubleStr(item.bonus_num));
        ((TextView) helper.getView(R.id.tv_odds)).setTextColor(Color.parseColor("#dd2230"));
        helper.setText(R.id.tv_time, DateUtil.changeTimeToYMD(item.bonus_time));
    }
}