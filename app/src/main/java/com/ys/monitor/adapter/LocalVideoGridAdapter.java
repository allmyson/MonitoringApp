package com.ys.monitor.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ys.monitor.R;
import com.ys.monitor.util.StringUtil;

import java.util.List;

/**
 * @author lh
 * @version 1.0.0
 * @filename LocalVideoGridAdapter
 * @description -------------------------------------------------------
 * @date 2017/7/7 16:19
 */
public class LocalVideoGridAdapter extends CommonAdapter<String> {
    public LocalVideoGridAdapter(Context context, List<String> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    //不管是加载本地文件还是资源文件 都必须用glide加载，测试结果当选择一张图的时候，80几率会出现
    //iv.setImageResource(R.mipmap.bg_my)无效
    @Override
    public void convert(ViewHolder helper, String item, final int position) {
        RelativeLayout deleteRL = helper.getView(R.id.rl_delete);
        deleteRL.setVisibility(View.GONE);
        ImageView iv = helper.getView(R.id.iv_);
        Log.e("lh", "执行次数");
        if (position < mDatas.size()) {
            iv.setVisibility(View.VISIBLE);
            if (!StringUtil.isBlank(item)) {
                if (item.startsWith("http://") || item.startsWith("https//")) {
                    iv.setImageBitmap(StringUtil.getNetVideoBitmap(item));
                } else {
                    iv.setImageBitmap(StringUtil.getVideoThumb(item));
                }
            }
        } else {
            iv.setVisibility(View.GONE);
        }
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
