package com.ys.monitor.adapter;

import android.content.Context;

import com.ys.monitor.R;
import com.ys.monitor.bean.YjtzBean;
import com.ys.monitor.util.DateUtil;
import com.ys.monitor.util.StringUtil;

import java.util.List;

/**
 * @author lh
 * @version 1.0.0
 * @filename YjtzAdapter
 * @description -------------------------------------------------------
 * @date 2020/9/10 16:34
 */
public class YjtzAdapter extends CommonAdapter<YjtzBean.DataBean.RowsBean> {
    public YjtzAdapter(Context context, List<YjtzBean.DataBean.RowsBean> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, YjtzBean.DataBean.RowsBean item, int position) {
        helper.setText(R.id.tv_name, StringUtil.valueOf(item.title));
        helper.setText(R.id.tv_time, DateUtil.changeTimeToYMDHMS(StringUtil.valueOf(item.createTime)));
    }
}
