package com.ys.zy.adapter;

import android.content.Context;

import com.ys.zy.R;
import com.ys.zy.bean.PayBean;
import com.ys.zy.util.StringUtil;

import java.util.List;

public class PayAdapter extends CommonAdapter<PayBean> {
    public PayAdapter(Context context, List<PayBean> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, PayBean item, int position) {
        helper.setImageResource(R.id.iv_, item.drawableId);
        helper.setText(R.id.tv_name, StringUtil.valueOf(item.name));
        helper.setText(R.id.tv_description, StringUtil.valueOf(item.description));
    }
}
