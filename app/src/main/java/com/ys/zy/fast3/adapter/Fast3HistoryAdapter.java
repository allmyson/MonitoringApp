package com.ys.zy.fast3.adapter;

import android.content.Context;
import android.graphics.Color;

import com.ys.zy.R;
import com.ys.zy.adapter.CommonAdapter;
import com.ys.zy.adapter.ViewHolder;
import com.ys.zy.fast3.Fast3Util;
import com.ys.zy.ssc.bean.SscResultBean;
import com.ys.zy.util.StringUtil;

import java.util.List;

public class Fast3HistoryAdapter extends CommonAdapter<SscResultBean.DataBean> {
    public Fast3HistoryAdapter(Context context, List<SscResultBean.DataBean> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, SscResultBean.DataBean item, int position) {
        helper.setText(R.id.tv_qh, StringUtil.valueOf(item.periodsNum));
        String lotteryNum = item.lotteryNum;
        String[] str = item.lotteryNum.split(",");
        if (str != null && str.length == 3) {
            helper.setImageResource(R.id.iv01, Fast3Util.getDrawableIdByNum(StringUtil.StringToInt(str[0])));
            helper.setImageResource(R.id.iv02, Fast3Util.getDrawableIdByNum(StringUtil.StringToInt(str[1])));
            helper.setImageResource(R.id.iv03, Fast3Util.getDrawableIdByNum(StringUtil.StringToInt(str[2])));
            int sum =(StringUtil.StringToInt(str[0])+StringUtil.StringToInt(str[1])+StringUtil.StringToInt(str[2]));
            helper.setText(R.id.tv_sum,""+sum);
            if(sum>=3&&sum<=10){
                helper.setText(R.id.tv_bigOrSmall,"小");
                helper.getView(R.id.tv_bigOrSmall).setBackgroundColor(Color.parseColor("#1288d8"));
            }else {
                helper.setText(R.id.tv_bigOrSmall,"大");
                helper.getView(R.id.tv_bigOrSmall).setBackgroundColor(Color.parseColor("#fbae17"));
            }
            if(sum%2==0){
                helper.setText(R.id.tv_singleOrDouble,"双");
                helper.getView(R.id.tv_singleOrDouble).setBackgroundColor(Color.parseColor("#1288d8"));
            }else {
                helper.setText(R.id.tv_singleOrDouble,"单");
                helper.getView(R.id.tv_singleOrDouble).setBackgroundColor(Color.parseColor("#fbae17"));
            }
        }
    }
}
