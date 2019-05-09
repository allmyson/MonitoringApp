package com.ys.zy.ssc.adapter;

import android.content.Context;

import com.ys.zy.R;
import com.ys.zy.adapter.CommonAdapter;
import com.ys.zy.adapter.ViewHolder;
import com.ys.zy.ssc.bean.SscResultBean;
import com.ys.zy.util.DateUtil;
import com.ys.zy.util.StringUtil;

import java.util.List;

public class SscHistoryAdapter extends CommonAdapter<SscResultBean.DataBean> {
    public SscHistoryAdapter(Context context, List<SscResultBean.DataBean> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, SscResultBean.DataBean item, int position) {
        String periods;
        if(!StringUtil.isBlank(item.periodsNum)&&item.periodsNum.length()>4){
            periods = item.periodsNum.substring(4);
        }else {
            periods = StringUtil.valueOf(item.periodsNum);
        }
        helper.setText(R.id.tv_qh, periods);
        helper.setText(R.id.tv_result, StringUtil.valueOf(item.lotteryNum));
        helper.setText(R.id.tv_time, DateUtil.changeTimeToHMS(item.lotteryTime));
    }
}
