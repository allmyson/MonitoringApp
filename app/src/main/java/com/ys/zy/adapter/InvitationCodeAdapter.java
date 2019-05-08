package com.ys.zy.adapter;

import android.content.Context;

import com.ys.zy.R;
import com.ys.zy.bean.YqmBean;
import com.ys.zy.util.DateUtil;
import com.ys.zy.util.StringUtil;

import java.util.List;

public class InvitationCodeAdapter extends CommonAdapter<YqmBean.DataBeanX.DataBean> {
    public InvitationCodeAdapter(Context context, List<YqmBean.DataBeanX.DataBean> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, YqmBean.DataBeanX.DataBean item, int position) {
        helper.setText(R.id.tv_yqm, StringUtil.valueOf(item.reg_code));
        helper.setText(R.id.tv_time, DateUtil.changeTimeToYMD(item.generate_time));
        helper.setText(R.id.tv_count, "" + StringUtil.StringToInt(item.reg_count));
    }
}
