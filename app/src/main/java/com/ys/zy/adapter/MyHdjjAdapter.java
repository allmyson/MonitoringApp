package com.ys.zy.adapter;

import android.content.Context;

import com.ys.zy.R;
import com.ys.zy.bean.HdjjBean;
import com.ys.zy.bean.SubJYJL;
import com.ys.zy.util.DateUtil;
import com.ys.zy.util.StringUtil;

import java.util.List;

public class MyHdjjAdapter extends CommonAdapter<HdjjBean.DataBeanX.DataBean> {
    public MyHdjjAdapter(Context context, List<HdjjBean.DataBeanX.DataBean> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, HdjjBean.DataBeanX.DataBean item, int position) {

    }
}