package com.ys.zy.fragment;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.yanzhenjie.nohttp.rest.Response;
import com.ys.zy.R;
import com.ys.zy.adapter.HotGameAdapter;
import com.ys.zy.base.BaseFragment;
import com.ys.zy.bean.GameBean;
import com.ys.zy.bean.HotGameBean;
import com.ys.zy.http.HttpListener;
import com.ys.zy.ui.BlankView;
import com.ys.zy.ui.NoNetView;
import com.ys.zy.util.HttpUtil;
import com.ys.zy.util.NetWorkUtil;
import com.ys.zy.util.ToastUtil;
import com.ys.zy.util.YS;

import java.util.ArrayList;
import java.util.List;

public class HotGameFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, NoNetView.ClickListener {
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView listView;
    private List<GameBean> list;
    private HotGameAdapter adapter;
    private NoNetView noNetView;
    private BlankView blankView;

    public static HotGameFragment newInstance() {
        return new HotGameFragment();
    }

    @Override
    protected void init() {
        blankView = getView(R.id.bv_);
        blankView.setText("暂无数据");
        noNetView = getView(R.id.nnv_);
        noNetView.setClickListener(this);
        swipeRefreshLayout = getView(R.id.srl_);
        listView = getView(R.id.lv_);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.main_color));
        list = new ArrayList<>();
        adapter = new HotGameAdapter(mContext, list, R.layout.item_hot_game);
        listView.setAdapter(adapter);
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
            HttpUtil.getHotGameList(mContext, new HttpListener<String>() {
                @Override
                public void onSucceed(int what, Response<String> response) {
                    list.clear();
                    HotGameBean hotGameBean = new Gson().fromJson(response.get(), HotGameBean.class);
                    List<GameBean> newList = new ArrayList<>();
                    if (hotGameBean != null && YS.SUCCESE.equals(hotGameBean.code) && hotGameBean.data != null) {
                        for (HotGameBean.DataBean dataBean : hotGameBean.data) {
                            GameBean gameBean = new GameBean();
                            gameBean.code = dataBean.game_code;
                            gameBean.name = GameBean.getNameByCode(dataBean.game_code);
                            gameBean.drawableId = GameBean.getImageByCode(dataBean.game_code);
                            newList.add(gameBean);
                        }
                    }
                    list.addAll(newList);
                    adapter.refresh(list);
                    if (adapter.getCount() > 0) {
                        blankView.setVisibility(View.GONE);
                    } else {
                        blankView.setVisibility(View.VISIBLE);
                    }
                    swipeRefreshLayout.setRefreshing(false);
                }

                @Override
                public void onFailed(int what, Response<String> response) {
                    swipeRefreshLayout.setRefreshing(false);
                    blankView.setVisibility(View.GONE);
                }
            });
        } else {
            noNetView.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
            blankView.setVisibility(View.GONE);
        }
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_hot_game;
    }

    @Override
    public void onRefresh() {
        getData();
    }

    @Override
    public void reload() {
        swipeRefreshLayout.setRefreshing(true);
        onRefresh();
    }

}
