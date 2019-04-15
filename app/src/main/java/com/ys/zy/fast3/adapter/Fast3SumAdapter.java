package com.ys.zy.fast3.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;

import com.ys.zy.R;
import com.ys.zy.adapter.CommonAdapter;
import com.ys.zy.adapter.ViewHolder;
import com.ys.zy.fast3.bean.Fast3Bean;
import com.ys.zy.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class Fast3SumAdapter extends CommonAdapter<Fast3Bean> {
    private List<Boolean> booleanList;

    public Fast3SumAdapter(Context context, List<Fast3Bean> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
        booleanList = new ArrayList<>();
        for (Fast3Bean fast3Bean : mDatas) {
            booleanList.add(false);
        }
    }

    @Override
    public void convert(ViewHolder helper, Fast3Bean item, final int position) {
        LinearLayout ll = helper.getView(R.id.ll_);
        helper.setText(R.id.tv_value, StringUtil.valueOf(item.value));
        helper.setText(R.id.tv_odds, "赔" + StringUtil.valueOf(item.odds));
        if (booleanList.get(position)) {
            ll.setBackgroundResource(R.drawable.rect_cornor_red3);
            helper.setTextColor(R.id.tv_value, Color.WHITE);
            helper.setTextColor(R.id.tv_odds, Color.WHITE);
        } else {
            ll.setBackgroundResource(R.drawable.rect_cornor_gray3);
            helper.setTextColor(R.id.tv_value, Color.parseColor("#dd2230"));
            helper.setTextColor(R.id.tv_odds, Color.parseColor("#a5a5a5"));
        }
        helper.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (booleanList.get(position)) {
                    booleanList.set(position, false);
                } else {
                    booleanList.set(position, true);
                }
                notifyDataSetInvalidated();
                if (chooseListener != null) {
                    chooseListener.chooseResult(getTZList());
                }
            }
        });
    }

    public List<Fast3Bean> getTZList() {
        List<Fast3Bean> list = new ArrayList<>();
        for (int i = 0; i < mDatas.size(); i++) {
            if (booleanList.get(i)) {
                list.add(mDatas.get(i));
            }
        }
        return list;
    }

    public String getTZResult() {
        List<Fast3Bean> list = getTZList();
        String result = "";
        for (int i = 0; i < list.size(); i++) {
            if (i != list.size() - 1) {
                result += list.get(i).value + ",";
            } else {
                result += list.get(i).value;
            }
        }
        return result;
    }

    @Override
    public void refresh(List<Fast3Bean> mDatas) {
        booleanList = new ArrayList<>();
        for (Fast3Bean fast3Bean : mDatas) {
            booleanList.add(false);
        }
        super.refresh(mDatas);
    }

    public interface ChooseListener {
        void chooseResult(List<Fast3Bean> list);
    }

    private ChooseListener chooseListener;

    public void setChooseListener(ChooseListener chooseListener) {
        this.chooseListener = chooseListener;
    }

    public void clear() {
        booleanList = new ArrayList<>();
        for (Fast3Bean fast3Bean : mDatas) {
            booleanList.add(false);
        }
        notifyDataSetInvalidated();
        chooseListener.chooseResult(getTZList());
    }
}
