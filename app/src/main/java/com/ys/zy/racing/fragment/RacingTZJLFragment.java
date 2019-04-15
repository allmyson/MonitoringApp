package com.ys.zy.racing.fragment;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.ys.zy.R;
import com.ys.zy.base.BaseFragment;
import com.ys.zy.fast3.adapter.Fast3TZJLAdapter;
import com.ys.zy.racing.adapter.RacingTZJLAdapter;
import com.ys.zy.ui.BlankView;

import java.util.ArrayList;
import java.util.List;

public class RacingTZJLFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    private int type;
    private int play;
    private ListView lv;
    private List<Object> list;
    private RacingTZJLAdapter adapter;
    private LinearLayout dataLL;
    private BlankView blankView;
    private SwipeRefreshLayout swipeRefreshLayout;
    public static RacingTZJLFragment newInstance(int type, int play) {
        RacingTZJLFragment racingTZJLFragment = new RacingTZJLFragment();
        racingTZJLFragment.setType(type);
        racingTZJLFragment.setPlay(play);
        return racingTZJLFragment;
    }
    @Override
    protected void init() {
        swipeRefreshLayout = getView(R.id.srl_);
        swipeRefreshLayout.setOnRefreshListener(this);
        dataLL = getView(R.id.ll_data);
        blankView = getView(R.id.bv_);
        blankView.setImage(R.mipmap.blank_inf_img2).setText("暂无记录");
        lv = getView(R.id.lv_);
        list = new ArrayList<>();
        adapter = new RacingTZJLAdapter(mContext, list, R.layout.item_fast3_tzjl);
        lv.setAdapter(adapter);
        checkList();
        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                View firstView = view.getChildAt(firstVisibleItem);
                if (firstVisibleItem == 0 && (firstView == null || firstView.getTop() == 0)) {
                    /*上滑到listView的顶部时，下拉刷新组件可见*/
                    swipeRefreshLayout.setEnabled(true);
                } else {
                    /*不是listView的顶部时，下拉刷新组件不可见*/
                    swipeRefreshLayout.setEnabled(false);
                }
            }
        });
    }

    @Override
    protected void getData() {

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_racing_tzjl;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setPlay(int play) {
        this.play = play;
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
