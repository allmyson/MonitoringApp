package com.ys.zy.winner.adapter;

import android.content.Context;
import android.graphics.Color;

import com.ys.zy.R;
import com.ys.zy.adapter.CommonAdapter;
import com.ys.zy.adapter.ViewHolder;
import com.ys.zy.util.StringUtil;

import java.util.List;

public class MySNAdapter extends CommonAdapter<String> {
    public MySNAdapter(Context context, List<String> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, String item, int position) {
        helper.setText(R.id.tv_, StringUtil.valueOf(item));
        if (position == 0) {
            helper.getView(R.id.tv_).setBackgroundResource(R.drawable.rect_cornor_yellow3);
        } else if (position == 3) {
            helper.getView(R.id.tv_).setBackgroundResource(R.drawable.rect_cornor_yellow4);
        } else if (position == mDatas.size() - 4) {
            helper.getView(R.id.tv_).setBackgroundResource(R.drawable.rect_cornor_yellow5);
        } else if (position == mDatas.size() - 1) {
            helper.getView(R.id.tv_).setBackgroundResource(R.drawable.rect_cornor_yellow6);
        } else {
            helper.getView(R.id.tv_).setBackgroundColor(Color.parseColor("#f86e00"));
        }
    }
}
