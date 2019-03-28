package com.ys.zy.activity;

import android.view.View;
import android.widget.ListView;

import com.ys.zy.R;
import com.ys.zy.adapter.SubDealHistoryAdapter;
import com.ys.zy.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class SubDealHistoryActivity extends BaseActivity {

    private ListView lv;
    private List<Object> list;
    private SubDealHistoryAdapter adapter;
    @Override
    public int getLayoutId() {
        return R.layout.activity_sub_deal_history;
    }

    @Override
    public void initView() {
        titleView.setText("下级交易记录");
        lv = getView(R.id.lv_);
        list = new ArrayList<>();
        list.add(null);
        list.add(null);
        list.add(null);
        list.add(null);
        list.add(null);
        list.add(null);
        adapter = new SubDealHistoryAdapter(mContext,list,R.layout.item_sub_manage);
        lv.setAdapter(adapter);
    }

    @Override
    public void getData() {

    }

    @Override
    public void onClick(View v) {

    }
}
