package com.ys.zy.adapter;

import android.content.Context;
import android.view.View;

import com.ys.zy.R;
import com.ys.zy.bean.GameBean;
import com.ys.zy.util.StringUtil;

import java.util.List;

public class GameAdapter extends CommonAdapter<GameBean> {
    public GameAdapter(Context context, List<GameBean> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, GameBean item, int position) {
        helper.setImageResource(R.id.iv_, item.drawableId);
        helper.setText(R.id.tv_name, StringUtil.valueOf(item.name));
        helper.setText(R.id.tv_des, StringUtil.valueOf(item.des));
        if ("1000".equals(item.status)) {
            helper.getView(R.id.tv_status).setVisibility(View.GONE);
        } else {
            helper.getView(R.id.tv_status).setVisibility(View.VISIBLE);
        }
    }
}
