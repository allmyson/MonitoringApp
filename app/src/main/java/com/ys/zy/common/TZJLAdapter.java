package com.ys.zy.common;

import android.content.Context;
import android.graphics.Color;

import com.ys.zy.R;
import com.ys.zy.adapter.CommonAdapter;
import com.ys.zy.adapter.ViewHolder;
import com.ys.zy.bean.TzjlBean;
import com.ys.zy.util.StringUtil;
import com.ys.zy.util.YS;

import java.util.List;

public class TZJLAdapter extends CommonAdapter<TzjlBean.DataBeanX.DataBean> {
    public TZJLAdapter(Context context, List<TzjlBean.DataBeanX.DataBean> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, TzjlBean.DataBeanX.DataBean item, int position) {
        helper.setText(R.id.tv_qs, StringUtil.valueOf(item.periods_num));
        helper.setText(R.id.tv_money, StringUtil.StringToDoubleStr(item.bets_money) + YS.UNIT);
        if ("1000".equals(item.is_win_code)) {
//            helper.setText(R.id.tv_isZJ, "已中奖");
            helper.setText(R.id.tv_isZJ, StringUtil.StringToDoubleStr(item.win_money) + YS.UNIT);
            helper.setTextColor(R.id.tv_isZJ, Color.parseColor("#dd2230"));
        } else if ("1001".equals(item.is_win_code)) {
//            helper.setText(R.id.tv_isZJ, "未中奖");
            helper.setText(R.id.tv_isZJ, StringUtil.valueOf(item.is_win_name));
            helper.setTextColor(R.id.tv_isZJ, Color.parseColor("#000000"));
        } else if ("1002".equals(item.is_win_code)) {
//            helper.setText(R.id.tv_isZJ, "未开奖");
            helper.setText(R.id.tv_isZJ, StringUtil.valueOf(item.is_win_name));
            helper.setTextColor(R.id.tv_isZJ, Color.parseColor("#27c427"));
        }
    }
}
