package com.ys.monitor.adapter;

import android.content.Context;

import com.ys.monitor.R;
import com.ys.monitor.util.StringUtil;

import java.util.List;

/**
 * @author lh
 * @version 1.0.0
 * @filename DataAdapter
 * @description -------------------------------------------------------
 * @date 2020/10/13 15:13
 */
public class DataAdapter extends CommonAdapter<String> {
    public DataAdapter(Context context, List<String> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, String item, int position) {
        helper.setText(R.id.tv_content, StringUtil.valueOf(item));
    }
}
