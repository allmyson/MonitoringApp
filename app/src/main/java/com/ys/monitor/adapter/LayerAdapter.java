package com.ys.monitor.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.ys.monitor.R;
import com.ys.monitor.bean.LayerBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lh
 * @version 1.0.0
 * @filename LayerAdapter
 * @description -------------------------------------------------------
 * @date 2020/9/30 14:58
 */
public class LayerAdapter extends CommonAdapter<LayerBean> {
    private List<Boolean> selectList;

    public LayerAdapter(Context context, List<LayerBean> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
        selectList = new ArrayList<>();
        for (LayerBean layerBean : mDatas) {
            selectList.add(false);
        }
    }

    @Override
    public void convert(ViewHolder helper, LayerBean item, int position) {
        ImageView statusIV = helper.getView(R.id.iv_status);
        if (selectList.get(position)) {
            statusIV.setVisibility(View.VISIBLE);
        } else {
            statusIV.setVisibility(View.GONE);
        }
    }

    @Override
    public void refresh(List<LayerBean> mDatas) {
        selectList.clear();
        for (LayerBean layerBean : mDatas) {
            selectList.add(false);
        }
        super.refresh(mDatas);
    }

    public void selectPosition(int position) {
        boolean isSelect = selectList.get(position);
        isSelect = !isSelect;
        selectList.set(position, isSelect);
        notifyDataSetChanged();
    }

    public boolean isSelect(int position) {
        return selectList.get(position);
    }
}
