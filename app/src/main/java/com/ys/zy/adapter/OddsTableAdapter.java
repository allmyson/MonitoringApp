package com.ys.zy.adapter;

import android.content.Context;

import com.ys.zy.R;
import com.ys.zy.bean.KeyValue;
import com.ys.zy.util.StringUtil;

import java.util.List;
//返点赔率表
public class OddsTableAdapter extends CommonAdapter<KeyValue> {
    public OddsTableAdapter(Context context, List<KeyValue> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, KeyValue item, int position) {
        helper.setText(R.id.tv_key, StringUtil.valueOf(item.fd));
        helper.setText(R.id.tv_value,StringUtil.valueOf(item.odd));
    }
}
