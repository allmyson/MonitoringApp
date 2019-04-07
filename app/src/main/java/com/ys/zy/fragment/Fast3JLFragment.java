package com.ys.zy.fragment;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.ys.zy.R;
import com.ys.zy.adapter.Fast3TZJLAdapter;
import com.ys.zy.base.BaseFragment;
import com.ys.zy.ui.BlankView;

import java.util.ArrayList;
import java.util.List;

public class Fast3JLFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    private ListView lv;
    private List<Object> list;
    private Fast3TZJLAdapter adapter;
    private LinearLayout dataLL;
    private BlankView blankView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int type;
    public static Fast3JLFragment newInstance(int type) {
        Fast3JLFragment fast3JLFragment = new Fast3JLFragment();
        fast3JLFragment.type = type;
        return fast3JLFragment;
    }

    @Override
    protected void init() {
        swipeRefreshLayout = getView(R.id.srl_);
        swipeRefreshLayout.setOnRefreshListener(this);
        dataLL = getView(R.id.ll_data);
        blankView = getView(R.id.bv_);
        blankView.setImage(R.mipmap.blank_inf_img).setText("暂无记录");
        lv = getView(R.id.lv_);
        list = new ArrayList<>();
        adapter = new Fast3TZJLAdapter(mContext, list, R.layout.item_fast3_tzjl);
        lv.setAdapter(adapter);
        checkList();
    }

    @Override
    protected void getData() {

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_fast3_jl;
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                list.clear();
                list.add(null);
                list.add(null);
                list.add(null);
                list.add(null);
                list.add(null);
                list.add(null);
                adapter.refresh(list);
                checkList();
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 2000);
    }

    private void checkList() {
        if (adapter.getCount() == 0) {
            blankView.setVisibility(View.VISIBLE);
            dataLL.setVisibility(View.GONE);
        } else {
            blankView.setVisibility(View.GONE);
            dataLL.setVisibility(View.VISIBLE);
        }
    }
}
