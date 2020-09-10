package com.ys.monitor.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.ys.monitor.R;
import com.ys.monitor.adapter.YjtzAdapter;
import com.ys.monitor.base.BaseActivity;
import com.ys.monitor.ui.BlankView;
import com.ys.monitor.ui.NoNetView;

import java.util.ArrayList;
import java.util.List;

//预警通知
public class YjtzActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener,
        NoNetView.ClickListener {
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView lv;
    private List<Object> list;
    private YjtzAdapter adapter;
    private NoNetView noNetView;
    private BlankView blankView;
    private LinearLayout dataLL;

    @Override
    public int getLayoutId() {
        return R.layout.activity_yjtz;
    }

    @Override
    public void initView() {
        setBarColor("#ffffff");
        titleView.setText("预警通知");
        swipeRefreshLayout = getView(R.id.srl_);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.main_color));
        dataLL = getView(R.id.ll_data);
        noNetView = getView(R.id.nnv_);
        blankView = getView(R.id.bv_);
        blankView.setImage(R.mipmap.blank_inf_img).setText("暂无记录");
        noNetView.setClickListener(this);
        lv = getView(R.id.lv_);
        list = new ArrayList<>();
        list.add(null);
        list.add(null);
        list.add(null);
        adapter = new YjtzAdapter(mContext, list, R.layout.item_yjtz);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(mContext, YjtzDetailActivity.class));
            }
        });
    }

    @Override
    public void getData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    @Override
    public void onRefresh() {
        getData();
    }

    @Override
    public void reload() {
        getData();
    }
}
