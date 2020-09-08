package com.ys.monitor.adapter;

import android.content.Context;

import com.ys.monitor.R;
import com.ys.monitor.bean.MsgBean;
import com.ys.monitor.util.DateUtil;
import com.ys.monitor.util.StringUtil;

import java.util.List;

public class MsgAdapter extends CommonAdapter<MsgBean.DataBeanX.DataBean> {
    public MsgAdapter(Context context, List<MsgBean.DataBeanX.DataBean> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, MsgBean.DataBeanX.DataBean item, int position) {
        helper.setText(R.id.tv_time, DateUtil.changeTimeToYMD(item.publish_time));
        helper.setText(R.id.tv_title, StringUtil.valueOf(item.notice_title));
        helper.setText(R.id.tv_content, StringUtil.valueOf(item.notice_content));
    }
}
