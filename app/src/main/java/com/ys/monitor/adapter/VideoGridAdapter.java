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
 * @filename VideoGridAdapter
 * @description -------------------------------------------------------
 * @date 2017/7/7 16:19
 */
public class VideoGridAdapter extends CommonAdapter<String> {
    public VideoGridAdapter(Context context, List<String> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    //不管是加载本地文件还是资源文件 都必须用glide加载，测试结果当选择一张图的时候，80几率会出现
    //iv.setImageResource(R.mipmap.bg_my)无效
    @Override
    public void convert(ViewHolder helper, String item, final int position) {
        RelativeLayout deleteRL = helper.getView(R.id.rl_delete);
        deleteRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatas.remove(position);
                notifyDataSetChanged();
            }
        });
        ImageView iv = helper.getView(R.id.iv_);
        Log.e("lh", "执行次数");
        if (position < mDatas.size()) {
//            iv.setImageResource(R.mipmap.ic_add_dt);
//            Glide.with(mContext).load(R.mipmap.ic_add_dt).into(iv);
            if (!StringUtil.isBlank(item)) {
                if (item.startsWith("http://") || item.startsWith("https//")) {
                    iv.setImageBitmap(StringUtil.getNetVideoBitmap(item));
                } else {
                    iv.setImageBitmap(StringUtil.getVideoThumb(item));
                }
            }
            deleteRL.setVisibility(View.VISIBLE);
        } else {
            iv.setImageResource(R.mipmap.ic_add_dt);
            deleteRL.setVisibility(View.GONE);
            if (position == 9) {
                iv.setVisibility(View.GONE);
            } else {
                iv.setVisibility(View.VISIBLE);
            }
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
