package com.ys.monitor.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.ys.monitor.R;

import java.io.File;
import java.util.List;

/**
 * @author lh
 * @version 1.0.0
 * @filename LocalGridAdapter
 * @description -------------------------------------------------------
 * @date 2017/7/7 16:19
 */
public class LocalGridAdapter extends CommonAdapter<String> {
    public LocalGridAdapter(Context context, List<String> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    //不管是加载本地文件还是资源文件 都必须用glide加载，测试结果当选择一张图的时候，80几率会出现
    //iv.setImageResource(R.mipmap.bg_my)无效
    @Override
    public void convert(ViewHolder helper, String item, final int position) {
        RelativeLayout deleteRL = helper.getView(R.id.rl_delete);
        ImageView iv = helper.getView(R.id.iv_);
        Log.e("lh", "执行次数");
        if (position < mDatas.size()) {
            iv.setVisibility(View.VISIBLE);
            if (item.startsWith("http://") || item.startsWith("https//")) {
                Glide.with(mContext).load(item).into(iv);
            } else {
                Glide.with(mContext).load(new File(item)).into(iv);
            }
        } else {
            iv.setVisibility(View.GONE);
        }
        deleteRL.setVisibility(View.GONE);
    }

    @Override
    public int getCount() {
        return super.getCount() + 1;
    }

    @Override
    public String getItem(int position) {
        if (position == mDatas.size()) {
            return "";
        }
        return super.getItem(position);
    }

}
