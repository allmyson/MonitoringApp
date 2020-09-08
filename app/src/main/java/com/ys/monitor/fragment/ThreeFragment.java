package com.ys.monitor.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.yanzhenjie.nohttp.rest.Response;
import com.ys.monitor.R;
import com.ys.monitor.activity.MsgDetailActivity;
import com.ys.monitor.adapter.MsgAdapter;
import com.ys.monitor.api.FunctionApi;
import com.ys.monitor.base.BaseFragment;
import com.ys.monitor.bean.MsgBean;
import com.ys.monitor.http.HttpListener;
import com.ys.monitor.ui.BlankView;
import com.ys.monitor.ui.NoNetView;
import com.ys.monitor.util.HttpUtil;
import com.ys.monitor.util.NetWorkUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lh
 * @version 1.0.0
 * @filename OneFragment
 * @description -------------------------------------------------------
 * @date 2018/10/23 17:09
 */
public class ThreeFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, NoNetView.ClickListener, View.OnClickListener {
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView listView;
    private NoNetView noNetView;
    private BlankView blankView;
    private List<MsgBean.DataBeanX.DataBean> list;
    private MsgAdapter adapter;

    public static ThreeFragment newInstance() {
        return new ThreeFragment();
    }

    @Override
    public void onRefresh() {
        getData();
    }

    @Override
    protected void init() {
        getView(R.id.iv_kf).setOnClickListener(this);
        noNetView = getView(R.id.nnv_);
        blankView = getView(R.id.bv_);
        blankView.setImage(R.mipmap.blank_inf_img).setText("暂无公告");
        noNetView.setClickListener(this);
        swipeRefreshLayout = getView(R.id.srl_);
        listView = getView(R.id.lv_);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.main_color));
        list = new ArrayList<>();
        adapter = new MsgAdapter(mContext, list, R.layout.item_msg);
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
                MsgDetailActivity.intentToMsgDetail(mContext, adapter.getItem(position));
            }
        });
    }

    @Override
    protected void getData() {
        if (NetWorkUtil.isNetworkAvailable(mContext)) {
            noNetView.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            HttpUtil.selectMsg(mContext, new HttpListener<String>() {
                @Override
                public void onSucceed(int what, Response<String> response) {
                    list.clear();
                    MsgBean msgBean = new Gson().fromJson(response.get(), MsgBean.class);
                    if (msgBean != null && msgBean.data != null&& msgBean.data.data != null && msgBean.data.data.size() > 0) {
                        list.addAll(msgBean.data.data);
                    }
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
            blankView.setVisibility(View.GONE);
            listView.setVisibility(View.GONE);
        }
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_three;
    }

    @Override
    public void reload() {
        swipeRefreshLayout.setRefreshing(true);
        onRefresh();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_kf) {
            FunctionApi.contactKF(mContext);
        }
    }
}
