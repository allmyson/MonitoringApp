package com.ys.zy.adapter;

import android.content.Context;

import com.ys.zy.R;
import com.ys.zy.util.StringUtil;

import java.util.List;

public class GameOrPlayAdapter extends CommonAdapter<String> {
    public GameOrPlayAdapter(Context context, List<String> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, String item, int position) {
        helper.setText(R.id.tv_, StringUtil.valueOf(item));
    }
}
