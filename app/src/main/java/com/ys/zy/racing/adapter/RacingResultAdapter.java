package com.ys.zy.racing.adapter;

import android.content.Context;

import com.ys.zy.R;
import com.ys.zy.adapter.CommonAdapter;
import com.ys.zy.adapter.ViewHolder;
import com.ys.zy.util.StringUtil;

import java.util.List;

public class RacingResultAdapter extends CommonAdapter<String> {
    public RacingResultAdapter(Context context, List<String> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, String item, int position) {
        helper.setText(R.id.tv_, StringUtil.valueOf(item));
    }
}
