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
 * @filename GridAdapter
 * @description -------------------------------------------------------
 * @date 2017/7/7 16:19
 */
public class GridAdapter extends CommonAdapter<String> {
    public GridAdapter(Context context, List<String> mDatas, int itemLayoutId) {
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
            if (item.startsWith("http://") || item.startsWith("https//")) {
                Glide.with(mContext).load(item).into(iv);
            } else {
                Glide.with(mContext).load(new File(item)).into(iv);
            }
            deleteRL.setVisibility(View.VISIBLE);
        } else {
            deleteRL.setVisibility(View.GONE);
            Glide.with(mContext).load(R.mipmap.ic_add_dt).into(iv);
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
