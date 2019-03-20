package com.ys.zy.adapter;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

import com.ys.zy.R;
import com.ys.zy.bean.GameBean;
import com.ys.zy.util.StringUtil;

import java.util.List;

public class HotGameAdapter extends CommonAdapter<GameBean> {
    public HotGameAdapter(Context context, List<GameBean> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, GameBean item, int position) {
        helper.setImageResource(R.id.iv_, item.drawableId);
        helper.setText(R.id.tv_, StringUtil.valueOf(item.name));
        helper.setText(R.id.tv_num, StringUtil.valueOf((position + 1)));
        TextView numTV = helper.getView(R.id.tv_num);
        if (position == 0) {
            numTV.setBackgroundResource(R.drawable.circle1);
            numTV.setTextColor(Color.WHITE);
        } else if (position == 1) {
            numTV.setBackgroundResource(R.drawable.circle2);
            numTV.setTextColor(Color.WHITE);
        } else if (position == 2) {
            numTV.setBackgroundResource(R.drawable.circle3);
            numTV.setTextColor(Color.WHITE);
        } else {
            numTV.setBackgroundColor(Color.WHITE);
            numTV.setTextColor(Color.parseColor("#a5a5a5"));
        }
    }
}
