package com.ys.monitor.dialog;

import android.content.Context;

import com.ys.monitor.R;
import com.ys.monitor.adapter.CommonAdapter;
import com.ys.monitor.adapter.ViewHolder;
import com.ys.monitor.bean.KVBean;
import com.ys.monitor.util.StringUtil;

import java.util.List;

/**
 * @author lh
 * @version 1.0.0
 * @filename ListAdapter
 * @description -------------------------------------------------------
 * @date 2017/6/28 14:07
 */
public class ListAdapter extends CommonAdapter<KVBean> {
    public ListAdapter(Context context, List<KVBean> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, KVBean item, int position) {
        helper.setText(R.id.tv, StringUtil.valueOf(item.name));
    }
}
