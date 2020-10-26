package com.ys.monitor.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.ys.monitor.R;
import com.ys.monitor.bean.RecordBean;
import com.ys.monitor.util.DateUtil;
import com.ys.monitor.util.StringUtil;

import java.util.List;

/**
 * @author lh
 * @version 1.0.0
 * @filename RecordAdapter
 * @description -------------------------------------------------------
 * @date 2020/10/26 18:55
 */
public class RecordAdapter extends CommonAdapter<RecordBean> {
    public RecordAdapter(Context context, List<RecordBean> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, RecordBean item, int position) {
        TextView nameTV = helper.getView(R.id.tv_name);
        nameTV.setText(StringUtil.valueOf(item.name));
        if ("火情上报".equals(item.name)) {
            nameTV.setBackgroundColor(Color.parseColor("#FDF0EA"));
            nameTV.setTextColor(Color.parseColor("#F6692A"));
        } else if ("资源采集".equals(item.name)) {
            nameTV.setBackgroundColor(Color.parseColor("#D6F8E2"));
            nameTV.setTextColor(Color.parseColor("#10AC6E"));
        } else if ("日常巡护".equals(item.name)) {
            nameTV.setBackgroundColor(Color.parseColor("#DFEDFF"));
            nameTV.setTextColor(Color.parseColor("#0D87F8"));
        }
        if ("".equals(StringUtil.valueOf(item.handleType))) {
            helper.getView(R.id.tv_type).setVisibility(View.GONE);
        } else {
            helper.getView(R.id.tv_type).setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_type, StringUtil.valueOf(item.handleType));
        }
        helper.setText(R.id.tv_address, StringUtil.valueOf(item.address));
        helper.setText(R.id.tv_time, DateUtil.changeTimeToYMDHMS(StringUtil.valueOf(item.createTime)));
    }
}
