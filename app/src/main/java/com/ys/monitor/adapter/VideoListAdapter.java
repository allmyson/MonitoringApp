package com.ys.monitor.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ys.monitor.R;
import com.ys.monitor.bean.VideoBean;
import com.ys.monitor.util.StringUtil;

import java.util.List;

/**
 * @author lh
 * @version 1.0.0
 * @filename VideoListAdapter
 * @description -------------------------------------------------------
 * @date 2020/9/14 14:45
 */
public class VideoListAdapter extends CommonAdapter<VideoBean.DataBean.RowsBean>{
    public VideoListAdapter(Context context, List<VideoBean.DataBean.RowsBean> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, VideoBean.DataBean.RowsBean item, int position) {
        ImageView iv = helper.getView(R.id.iv_bg);
        Glide.with(mContext).load(StringUtil.valueOf(item.imgUrl)).into(iv);
        helper.setText(R.id.tv_name,StringUtil.valueOf(item.devName));
    }
}
