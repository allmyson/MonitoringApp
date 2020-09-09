package com.ys.monitor.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.ys.monitor.R;
import com.ys.monitor.bean.Msg;
import com.ys.monitor.util.StringUtil;

import java.util.List;

public class NoticeAdapter extends CommonAdapter<Msg> {
    public NoticeAdapter(Context context, List<Msg> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, Msg item, int position) {
        helper.setText(R.id.tv_time, item.time);
        helper.setText(R.id.tv_title, StringUtil.valueOf(item.title));
        helper.setText(R.id.tv_content, StringUtil.valueOf(item.content));
        helper.setImageResource(R.id.iv_, item.drawableId);
        int num = item.num;
        TextView numTV = helper.getView(R.id.tv_num);
        numTV.setText("" + num);
        if (num == 0) {
            numTV.setVisibility(View.GONE);
        } else {
            numTV.setVisibility(View.VISIBLE);
        }
    }
}
