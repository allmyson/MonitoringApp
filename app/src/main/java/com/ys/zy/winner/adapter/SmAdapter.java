package com.ys.zy.winner.adapter;

import android.content.Context;
import android.text.Html;
import android.widget.TextView;

import com.ys.zy.R;
import com.ys.zy.adapter.CommonAdapter;
import com.ys.zy.adapter.ViewHolder;
import com.ys.zy.util.StringUtil;
import com.ys.zy.util.YS;

import java.util.List;

public class SmAdapter extends CommonAdapter<String> {
    public SmAdapter(Context context, List<String> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, String item, int position) {
        String sm = String.format("<font color=\"#dd2230\">%s</font>," + item, position + 1);
        TextView smTV = helper.getView(R.id.tv_);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            smTV.setText(Html.fromHtml(sm, Html.FROM_HTML_MODE_LEGACY));
        } else {
            smTV.setText(Html.fromHtml(sm));
        }
    }
}
