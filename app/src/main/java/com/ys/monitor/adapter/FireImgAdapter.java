package com.ys.monitor.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.ys.monitor.R;
import com.ys.monitor.util.YS;

import java.util.List;

/**
 * @author lh
 * @version 1.0.0
 * @filename FireImgAdapter
 * @description -------------------------------------------------------
 * @date 2020/9/10 11:06
 */
public class FireImgAdapter extends CommonAdapter<String> {
    public FireImgAdapter(Context context, List<String> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, String item, int position) {
        ImageView iv = helper.getView(R.id.iv_);
        YS.showRoundImage(mContext, item, iv, 0);
    }
}
