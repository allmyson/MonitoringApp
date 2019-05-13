package com.ys.zy.ttz.adapter;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

import com.ys.zy.R;
import com.ys.zy.adapter.CommonAdapter;
import com.ys.zy.adapter.ViewHolder;
import com.ys.zy.ssc.bean.SscResultBean;
import com.ys.zy.ttz.TtzUtil;
import com.ys.zy.util.StringUtil;

import java.util.List;

public class TtzHistoryAdapter extends CommonAdapter<SscResultBean.DataBean> {
    public TtzHistoryAdapter(Context context, List<SscResultBean.DataBean> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, SscResultBean.DataBean item, int position) {
        helper.setText(R.id.tv_qs, StringUtil.valueOf(item.periodsNum));
        helper.setText(R.id.tv_result, TtzUtil.getShowNum(item.lotteryNum));
        String[] str = TtzUtil.getAllResult(item.lotteryNum);
        TextView tv1 = helper.getView(R.id.tv_1);
        TextView tv2 = helper.getView(R.id.tv_2);
        TextView tv3 = helper.getView(R.id.tv_3);
        tv1.setText(str[0]);
        tv2.setText(str[1]);
        tv3.setText(str[2]);
        setColor(str[0], tv1);
        setColor(str[1], tv2);
        setColor(str[2], tv3);
    }

    private void setColor(String result, TextView tv) {
        if ("庄".equals(result)) {
            tv.setBackgroundColor(Color.parseColor("#dd2230"));
        } else if ("平".equals(result)) {
            tv.setBackgroundColor(Color.parseColor("#a5a5a5"));
        } else {
            tv.setBackgroundColor(Color.parseColor("#29abe2"));
        }
    }
}
