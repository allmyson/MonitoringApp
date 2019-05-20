package com.ys.zy.adapter;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

import com.ys.zy.R;
import com.ys.zy.bean.MyXfjlBean;
import com.ys.zy.bean.SubJYJL;
import com.ys.zy.util.DateUtil;
import com.ys.zy.util.DensityUtil;
import com.ys.zy.util.StringUtil;

import java.util.List;

public class MyXfjlAdapter extends CommonAdapter<MyXfjlBean.DataBeanX.DataBean> {
    public MyXfjlAdapter(Context context, List<MyXfjlBean.DataBeanX.DataBean> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, MyXfjlBean.DataBeanX.DataBean item, int position) {
        helper.setText(R.id.tv_game, StringUtil.valueOf(item.game_name));
        helper.setText(R.id.tv_tzMoney, "￥" + StringUtil.StringToDoubleStr(item.bets_money));
        if ("1001".equals(item.game_status)) {
            //未中奖
            helper.setText(R.id.tv_zjMoney, "未中奖");
            ((TextView) helper.getView(R.id.tv_zjMoney)).setTextColor(Color.parseColor("#a5a5a5"));
            ((TextView) helper.getView(R.id.tv_zjMoney)).setTextSize(12);
        } else if ("1002".equals(item.game_status)) {
            //待开奖
            helper.setText(R.id.tv_zjMoney, "待开奖");
            ((TextView) helper.getView(R.id.tv_zjMoney)).setTextColor(Color.parseColor("#27c427"));
            ((TextView) helper.getView(R.id.tv_zjMoney)).setTextSize(12);
        } else {
            //1000--中奖
            helper.setText(R.id.tv_zjMoney, StringUtil.StringToDoubleStr(item.win_money));
            ((TextView) helper.getView(R.id.tv_zjMoney)).setTextColor(Color.parseColor("#1e1e1e"));
            ((TextView) helper.getView(R.id.tv_zjMoney)).setTextSize(16);
        }
        helper.setText(R.id.tv_time, DateUtil.changeTimeToYMDHMS(item.bets_time));
    }
}