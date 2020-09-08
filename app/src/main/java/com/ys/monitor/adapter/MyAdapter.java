package com.ys.monitor.adapter;

import android.content.Context;
import android.view.View;

import com.ys.monitor.R;
import com.ys.monitor.bean.FourBean;
import com.ys.monitor.util.StringUtil;

import java.util.List;

public class MyAdapter extends CommonAdapter<FourBean> {
    public MyAdapter(Context context, List<FourBean> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, FourBean item, int position) {
        helper.setImageResource(R.id.iv_, item.id);
        helper.setText(R.id.tv_, StringUtil.valueOf(item.name));
        if (position == mDatas.size() - 1) {
            helper.getView(R.id.view_).setVisibility(View.GONE);
        }else {
            helper.getView(R.id.view_).setVisibility(View.VISIBLE);
        }
    }
}
