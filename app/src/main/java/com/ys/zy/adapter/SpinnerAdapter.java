package com.ys.zy.adapter;

import android.content.Context;

import com.ys.zy.bean.PatformBankBean;
import com.ys.zy.util.StringUtil;

import java.util.List;

public class SpinnerAdapter extends CommonAdapter<PatformBankBean.DataBean> {
    public SpinnerAdapter(Context context, List<PatformBankBean.DataBean> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, PatformBankBean.DataBean item, int position) {
        helper.setText(android.R.id.text1, StringUtil.valueOf(item.bankName));
    }
}
