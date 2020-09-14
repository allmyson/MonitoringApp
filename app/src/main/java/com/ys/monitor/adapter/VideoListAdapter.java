package com.ys.monitor.adapter;

import android.content.Context;

import com.ys.monitor.bean.VideoBean;

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

    }
}
