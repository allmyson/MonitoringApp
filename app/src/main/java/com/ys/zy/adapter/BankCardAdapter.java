package com.ys.zy.adapter;

import android.content.Context;

import com.ys.zy.R;
import com.ys.zy.bean.BankData;
import com.ys.zy.util.StringUtil;

import java.util.List;

public class BankCardAdapter extends CommonAdapter<BankData.DataBean> {
    public BankCardAdapter(Context context, List<BankData.DataBean> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, BankData.DataBean item, int position) {
        helper.setText(R.id.tv_card, StringUtil.valueOf(item.cardNumber));
        helper.setText(R.id.tv_name, StringUtil.valueOf(item.bankName));
    }
}
