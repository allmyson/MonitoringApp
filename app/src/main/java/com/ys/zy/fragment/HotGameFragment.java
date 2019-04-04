package com.ys.zy.fragment;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ys.zy.R;
import com.ys.zy.adapter.HotGameAdapter;
import com.ys.zy.base.BaseFragment;
import com.ys.zy.bean.GameBean;
import com.ys.zy.ui.NoNetView;
import com.ys.zy.util.NetWorkUtil;
import com.ys.zy.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class HotGameFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, NoNetView.ClickListener {
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView listView;
    private List<GameBean> list;
    private HotGameAdapter adapter;
    private NoNetView noNetView;

    public static HotGameFragment newInstance() {
        return new HotGameFragment();
    }

    @Override
    protected void init() {
        noNetView = getView(R.id.nnv_);
        noNetView.setClickListener(this);
        swipeRefreshLayout = getView(R.id.srl_);
        listView = getView(R.id.lv_);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.main_color));
        list = new ArrayList<>();
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ToastUtil.show(mContext, "ssa" + position);
            }
        });
    }

    @Override
    protected void getData() {
        if (NetWorkUtil.isNetworkAvailable(mContext)) {
            noNetView.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            list.addAll(GameBean.getDefaultList());
            adapter = new HotGameAdapter(mContext, list, R.layout.item_hot_game);
            listView.setAdapter(adapter);
        } else {
            noNetView.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        }
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_hot_game;
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (NetWorkUtil.isNetworkAvailable(mContext)) {
                    noNetView.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                    adapter.refresh(list);
                } else {
                    noNetView.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.GONE);
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 2000);
    }

    @Override
    public void reload() {
        swipeRefreshLayout.setRefreshing(true);
        onRefresh();
    }
}
