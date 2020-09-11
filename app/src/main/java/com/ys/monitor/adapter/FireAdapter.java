package com.ys.monitor.adapter;

import android.content.Context;

import com.ys.monitor.R;
import com.ys.monitor.bean.FireBean;
import com.ys.monitor.util.DateUtil;
import com.ys.monitor.util.StringUtil;

import java.util.List;

public class FireAdapter extends CommonAdapter<FireBean.DataBean.RowsBean> {
    public FireAdapter(Context context, List<FireBean.DataBean.RowsBean> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, FireBean.DataBean.RowsBean item, int position) {
        helper.setText(R.id.tv_title, StringUtil.valueOf(item.name));
        helper.setText(R.id.tv_type, StringUtil.valueOf(item.sourceName));
        helper.setText(R.id.tv_address, StringUtil.valueOf(item.siteSplicing));
        helper.setText(R.id.tv_status, StringUtil.valueOf(item.statusName));
        helper.setText(R.id.tv_time, DateUtil.changeTimeToYMDHMS(StringUtil.valueOf(item.warnTime)));
    }
}
