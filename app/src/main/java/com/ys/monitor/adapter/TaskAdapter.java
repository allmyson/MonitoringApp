package com.ys.monitor.adapter;

import android.content.Context;

import com.ys.monitor.R;
import com.ys.monitor.bean.TaskBean;
import com.ys.monitor.util.DateUtil;
import com.ys.monitor.util.StringUtil;

import java.util.List;

public class TaskAdapter extends CommonAdapter<TaskBean.DataBean.RowsBean> {
    public TaskAdapter(Context context, List<TaskBean.DataBean.RowsBean> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, TaskBean.DataBean.RowsBean item, int position) {
        helper.setText(R.id.tv_name, StringUtil.valueOf(item.name));
        helper.setText(R.id.tv_type, StringUtil.valueOf(item.typeName));
        helper.setText(R.id.tv_descrip, StringUtil.valueOf(item.description));
        if(item.isFinish==0) {
            helper.setText(R.id.tv_timeName, "计划完成时间:");
            helper.setText(R.id.tv_time, DateUtil.changeTimeToYMDHMS(StringUtil.valueOf(item.planTime)));
        }else {
            helper.setText(R.id.tv_timeName, "完成时间:");
            helper.setText(R.id.tv_time, DateUtil.changeTimeToYMDHMS(StringUtil.valueOf(item.finishTime)));
        }
    }
}
