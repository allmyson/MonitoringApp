package com.ys.zy.adapter;

import android.content.Context;

import com.ys.zy.R;
import com.ys.zy.bean.MsgBean;
import com.ys.zy.util.DateUtil;
import com.ys.zy.util.StringUtil;

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
