package com.ys.zy.adapter;

import android.content.Context;

import com.ys.zy.R;
import com.ys.zy.bean.MyXfjlBean;
import com.ys.zy.bean.SubJYJL;
import com.ys.zy.util.DateUtil;
import com.ys.zy.util.StringUtil;

import java.util.List;

public class MyXfjlAdapter extends CommonAdapter<MyXfjlBean.DataBeanX.DataBean> {
    public MyXfjlAdapter(Context context, List<MyXfjlBean.DataBeanX.DataBean> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, MyXfjlBean.DataBeanX.DataBean item, int position) {
    }
}