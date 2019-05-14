package com.ys.zy.adapter;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

import com.ys.zy.R;
import com.ys.zy.bean.MyXfjlBean;
import com.ys.zy.bean.SubJYJL;
import com.ys.zy.util.DateUtil;
import com.ys.zy.util.StringUtil;

import java.util.List;

public class MyXfjlAdapter extends CommonAdapter<MyXfjlBean.DataBeanX.DataBean> {
    public MyXfjlAdapter(Context context, List<MyXfjlBean.DataBeanX.DataBean> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, MyXfjlBean.DataBeanX.DataBean item, int position) {
        helper.setText(R.id.tv_gameName, StringUtil.valueOf(item.game_name));
        helper.setText(R.id.tv_periods_num, StringUtil.valueOf(item.periods_num));
        helper.setText(R.id.tv_betMoney, StringUtil.StringToDoubleStr(item.bets_money));
        if (StringUtil.StringToDouble(item.win_money) == 0) {
            helper.setText(R.id.tv_isZJ, "未中奖");
            ((TextView) helper.getView(R.id.tv_isZJ)).setTextColor(Color.parseColor("#1e1e1e"));
        } else {
            helper.setText(R.id.tv_isZJ, StringUtil.StringToDoubleStr(item.win_money));
            ((TextView) helper.getView(R.id.tv_isZJ)).setTextColor(Color.parseColor("#dd2230"));
        }

    }
}