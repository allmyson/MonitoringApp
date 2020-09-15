package com.ys.monitor.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.ys.monitor.R;
import com.ys.monitor.bean.TaskBean;
import com.ys.monitor.dialog.DialogUtil;
import com.ys.monitor.dialog.TipFragment;
import com.ys.monitor.util.StringUtil;

import java.util.List;

public class TaskAdapter extends CommonAdapter<TaskBean.DataBean.RowsBean> {
    public TaskAdapter(Context context, List<TaskBean.DataBean.RowsBean> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, final TaskBean.DataBean.RowsBean item, int position) {
        helper.setText(R.id.tv_name, StringUtil.valueOf(item.name));
        helper.setText(R.id.tv_type, StringUtil.valueOf(item.typeName));
        helper.setText(R.id.tv_descrip, StringUtil.valueOf(item.content));
        TextView statusTV = helper.getView(R.id.tv_status);
        TextView typeTV = helper.getView(R.id.tv_type);
        if ("资源采集".equals(item.typeName)) {
            typeTV.setBackgroundColor(Color.parseColor("#FDF0EA"));
            typeTV.setTextColor(Color.parseColor("#F6692A"));
        } else if ("日常巡护".equals(item.typeName)) {
            typeTV.setBackgroundColor(Color.parseColor("#DFEDFF"));
            typeTV.setTextColor(Color.parseColor("#0D87F8"));
        } else {
            typeTV.setBackgroundColor(Color.parseColor("#D6F8E2"));
            typeTV.setTextColor(Color.parseColor("#10AC6E"));
        }
        if (item.isFinish == 0) {
            statusTV.setText("待处理");
            statusTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogUtil.showTip(mContext, "确认提交此任务?", new TipFragment.ClickListener() {
                        @Override
                        public void sure() {
                            if(doListener!=null){
                                doListener.doTask(item);
                            }
                        }
                    });
                }
            });
        } else {
            statusTV.setText("已完成");
            statusTV.setOnClickListener(null);
        }
    }

    private DoListener doListener;
    public interface DoListener{
        void doTask(TaskBean.DataBean.RowsBean bean);
    }

    public void setDoListener(DoListener doListener) {
        this.doListener = doListener;
    }
}
