package com.ys.zy.adapter;

import android.content.Context;

import java.util.List;

public class BankCardAdapter extends CommonAdapter<String> {
    public BankCardAdapter(Context context, List<String> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, String item, int position) {

    }
}
