package com.ys.zy.ttz.adapter;

import android.content.Context;

import com.ys.zy.R;
import com.ys.zy.adapter.CommonAdapter;
import com.ys.zy.adapter.ViewHolder;
import com.ys.zy.ssc.bean.SscResultBean;
import com.ys.zy.util.StringUtil;

import java.util.List;

public class TtzHistoryAdapter extends CommonAdapter<SscResultBean.DataBean> {
    public TtzHistoryAdapter(Context context, List<SscResultBean.DataBean> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, SscResultBean.DataBean item, int position) {
        helper.setText(R.id.tv_qs, StringUtil.valueOf(item.periodsNum));

    }
}
