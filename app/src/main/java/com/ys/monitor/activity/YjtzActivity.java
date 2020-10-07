package com.ys.monitor.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.yanzhenjie.nohttp.rest.Response;
import com.ys.monitor.R;
import com.ys.monitor.adapter.YjtzAdapter;
import com.ys.monitor.base.BaseActivity;
import com.ys.monitor.bean.YjtzBean;
import com.ys.monitor.http.HttpListener;
import com.ys.monitor.sp.MsgSP;
import com.ys.monitor.sp.UserSP;
import com.ys.monitor.ui.BlankView;
import com.ys.monitor.ui.NoNetView;
import com.ys.monitor.util.HttpUtil;
import com.ys.monitor.util.NetWorkUtil;
import com.ys.monitor.util.YS;

import java.util.ArrayList;
import java.util.List;

//预警通知
public class YjtzActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener,
        NoNetView.ClickListener {
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView lv;
    private List<YjtzBean.DataBean.RowsBean> list;
    private YjtzAdapter adapter;
    private NoNetView noNetView;
    private BlankView blankView;
    private LinearLayout dataLL;
    private String userId;

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
        list.addAll(MsgSP.getYJList(mContext));
        adapter = new YjtzAdapter(mContext, list, R.layout.item_yjtz);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                YjtzBean.DataBean.RowsBean rowsBean = adapter.getItem(position);
                String json = new Gson().toJson(rowsBean);
                YjtzDetailActivity.intentToDetail(mContext, json);
            }
        });
        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                                 int totalItemCount) {
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
        userId = UserSP.getUserId(mContext);
    }

    @Override
    public void getData() {
        if (NetWorkUtil.isNetworkAvailable(mContext)) {
            noNetView.setVisibility(View.GONE);
            dataLL.setVisibility(View.VISIBLE);
            HttpUtil.getYjtzList(mContext, userId, new HttpListener<String>() {
                @Override
                public void onSucceed(int what, Response<String> response) {
                    try {
                        list.clear();
                        YjtzBean yjtzBean = new Gson().fromJson(response.get(), YjtzBean.class);
                        if (yjtzBean != null) {
                            if (YS.SUCCESE.equals(yjtzBean.code) && yjtzBean.data != null && yjtzBean
                                    .data.rows != null && yjtzBean.data.rows.size() > 0) {
                                list.addAll(yjtzBean.data.rows);
                            }
                        } else {
                            show(YS.HTTP_TIP);
                        }
                        adapter.refresh(list);
                        if (adapter.getCount() > 0) {
                            blankView.setVisibility(View.GONE);
                        } else {
                            blankView.setVisibility(View.VISIBLE);
                        }
                        MsgSP.saveYJTZ(mContext,list);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    swipeRefreshLayout.setRefreshing(false);
                }

                @Override
                public void onFailed(int what, Response<String> response) {
                    blankView.setVisibility(View.GONE);
                    swipeRefreshLayout.setRefreshing(false);
                }
            });
        } else {
//            noNetView.setVisibility(View.VISIBLE);
//            blankView.setVisibility(View.GONE);
//            dataLL.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
        }
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
