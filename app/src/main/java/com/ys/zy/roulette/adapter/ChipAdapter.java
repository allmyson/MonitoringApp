package com.ys.zy.roulette.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;

import com.ys.zy.R;
import com.ys.zy.adapter.CommonAdapter;
import com.ys.zy.adapter.ViewHolder;
import com.ys.zy.roulette.bean.ChipBean;
import com.ys.zy.ui.HorizontalListView;

import java.util.List;

//筹码适配器
public class ChipAdapter extends CommonAdapter<ChipBean> {
    private int selectItem = -1;
    private HorizontalListView horizontalListView;
    public ChipAdapter(Context context, List<ChipBean> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, ChipBean item, int position) {
        helper.setImageResource(R.id.iv_, item.drawableId);
        if (selectItem == position) {
            helper.setImageResource(R.id.iv_, item.selectDrawableId);
        } else {
            helper.setImageResource(R.id.iv_, item.drawableId);
        }
    }

    public void setSelectItem(final int position) {
        this.selectItem = position;
        notifyDataSetChanged();
//        new Handler().postDelayed(new Runnable() {
//            public void run() {
//                horizontalListView.setSelection(position);
//            }
//        }, 350);
    }

    public void clear() {
        this.selectItem = -1;
        notifyDataSetChanged();
    }

    public ChipBean getChooseData() {
        if (selectItem == -1) {
            return null;
        } else {
            return mDatas.get(selectItem);
        }
    }

    public HorizontalListView getHorizontalListView() {
        return horizontalListView;
    }

    public void setHorizontalListView(HorizontalListView horizontalListView) {
        this.horizontalListView = horizontalListView;
    }
}
