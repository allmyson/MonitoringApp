package com.ys.zy.fragment;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.yanzhenjie.nohttp.rest.Response;
import com.ys.zy.R;
import com.ys.zy.adapter.WinnerListAdapter;
import com.ys.zy.base.BaseFragment;
import com.ys.zy.bean.WinnerBean;
import com.ys.zy.bean.WinnerUserBean;
import com.ys.zy.http.HttpListener;
import com.ys.zy.ui.BlankView;
import com.ys.zy.ui.NoNetView;
import com.ys.zy.util.HttpUtil;
import com.ys.zy.util.NetWorkUtil;

import java.util.ArrayList;
import java.util.List;

public class WinnerListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, NoNetView.ClickListener {
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView listView;
    private List<WinnerUserBean.DataBeanX.DataBean> list;
    private WinnerListAdapter adapter;
    private NoNetView noNetView;
    private BlankView blankView;

    public static WinnerListFragment newInstance() {
        return new WinnerListFragment();
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
        adapter = new WinnerListAdapter(mContext, list, R.layout.item_winner_list);
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
    }

    @Override
    protected void getData() {
        if (NetWorkUtil.isNetworkAvailable(mContext)) {
            noNetView.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            HttpUtil.getWinnerUserList(mContext, new HttpListener<String>() {
                @Override
                public void onSucceed(int what, Response<String> response) {
                    list.clear();
                    WinnerUserBean winnerUserBean = new Gson().fromJson(response.get(), WinnerUserBean.class);
                    if (winnerUserBean != null && winnerUserBean.data != null && winnerUserBean.data.data != null && winnerUserBean.data.data.size() > 0) {
                        list.addAll(winnerUserBean.data.data);
                    }
                    adapter.refresh(list);
                    swipeRefreshLayout.setRefreshing(false);
                    if (adapter.getCount() > 0) {
                        blankView.setVisibility(View.GONE);
                    } else {
                        blankView.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onFailed(int what, Response<String> response) {
                    swipeRefreshLayout.setRefreshing(false);
                    blankView.setVisibility(View.GONE);
                }
            });
        } else {
            noNetView.setVisibility(View.VISIBLE);
            blankView.setVisibility(View.GONE);
            listView.setVisibility(View.GONE);
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

    private List<WinnerBean> getList() {
        List<WinnerBean> list = new ArrayList<>();
        list.add(new WinnerBean("http://image.biaobaiju.com/uploads/20180211/00/1518281788-xmNoYwiyBV.jpg", "12442sasd", "124124.234"));
        list.add(new WinnerBean("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1553077764896&di=c27735668db9f876da1984e2e9fcca2a&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201512%2F13%2F20151213171012_AFGJn.jpeg", "12442sasd", "124124.234"));
        list.add(new WinnerBean("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1553077764897&di=c2ed634b27b9b978d0b06e29cf86addb&imgtype=0&src=http%3A%2F%2F5b0988e595225.cdn.sohucs.com%2Fimages%2F20171207%2F60918cbd27fe4642b2b68668f450abed.jpeg", "12442sasd", "124124.234"));
        list.add(new WinnerBean("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1553077764897&di=58c0bea1db48da4c7c0ff7e171ab0dbb&imgtype=0&src=http%3A%2F%2Fpic1.win4000.com%2Fpic%2F5%2F10%2Fd5a17c3837.jpg", "12442sasd", "124124.234"));
        list.add(new WinnerBean("", "12442sasd", "124124.234"));
        list.add(new WinnerBean("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1553077764896&di=54bf078595bffdf75d23ab48e2dea808&imgtype=0&src=http%3A%2F%2Fimg.tuzhaozhao.com%2F2018%2F05%2F28%2F43f54249c34f928c_600x600.jpg", "12442sasd", "124124.234"));
        return list;
    }
}

