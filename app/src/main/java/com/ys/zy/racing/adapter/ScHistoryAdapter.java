package com.ys.zy.racing.adapter;

import android.content.Context;

import com.ys.zy.R;
import com.ys.zy.adapter.CommonAdapter;
import com.ys.zy.adapter.ViewHolder;
import com.ys.zy.racing.RacingUtil;
import com.ys.zy.ssc.bean.SscResultBean;
import com.ys.zy.util.DateUtil;
import com.ys.zy.util.StringUtil;

import java.util.List;

public class ScHistoryAdapter extends CommonAdapter<SscResultBean.DataBean> {
    public ScHistoryAdapter(Context context, List<SscResultBean.DataBean> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, SscResultBean.DataBean item, int position) {
        helper.setText(R.id.tv_qh, StringUtil.valueOf(item.periodsNum));
        String lotteryNum = StringUtil.valueOf(item.lotteryNum);
        String[] str = lotteryNum.split(",");
        String result="";
        if (str != null & str.length > 0) {
            for (String s : str) {
                result +=RacingUtil.getNumber(StringUtil.StringToInt(s))+" ";
            }
        }
        helper.setText(R.id.tv_result, result);
        helper.setText(R.id.tv_time, DateUtil.changeTimeToHMS(item.lotteryTime));
    }
}
