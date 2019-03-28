package com.ys.zy.activity;

import android.view.View;
import android.widget.ListView;

import com.ys.zy.R;
import com.ys.zy.adapter.SubManageAdapter;
import com.ys.zy.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

//下级管理
public class SubManageActivity extends BaseActivity {
    private ListView lv;
    private List<Object> list;
    private SubManageAdapter adapter;
    @Override
    public int getLayoutId() {
        return R.layout.activity_sub_manage;
    }

    @Override
    public void initView() {
        titleView.setText("下级管理");
        lv = getView(R.id.lv_);
        list = new ArrayList<>();
        list.add(null);
        list.add(null);
        list.add(null);
        list.add(null);
        list.add(null);
        list.add(null);
        adapter = new SubManageAdapter(mContext,list,R.layout.item_sub_manage);
        lv.setAdapter(adapter);
    }

    @Override
    public void getData() {

    }

    @Override
    public void onClick(View v) {

    }
}
