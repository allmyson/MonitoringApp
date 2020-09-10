package com.ys.monitor.adapter;

import android.content.Context;

import java.util.List;

public class TaskAdapter extends CommonAdapter<Object> {
    public TaskAdapter(Context context, List<Object> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, Object item, int position) {
    }
}
