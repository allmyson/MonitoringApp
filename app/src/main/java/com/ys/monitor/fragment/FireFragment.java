package com.ys.monitor.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.yanzhenjie.nohttp.rest.Response;
import com.ys.monitor.R;
import com.ys.monitor.activity.FireDetailActivity;
import com.ys.monitor.adapter.FireAdapter;
import com.ys.monitor.base.BaseFragment;
import com.ys.monitor.bean.FireBean;
import com.ys.monitor.http.HttpListener;
import com.ys.monitor.sp.UserSP;
import com.ys.monitor.ui.BlankView;
import com.ys.monitor.ui.NoNetView;
import com.ys.monitor.util.HttpUtil;
import com.ys.monitor.util.L;
import com.ys.monitor.util.NetWorkUtil;
import com.ys.monitor.util.YS;

import java.util.ArrayList;
import java.util.List;

public class FireFragment extends BaseFragment implements NoNetView.ClickListener,
        SwipeRefreshLayout.OnRefreshListener {
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView lv;
    private List<FireBean.DataBean.RowsBean> list;
    private FireAdapter adapter;
    private String userId;
    private NoNetView noNetView;
    private BlankView blankView;
    private LinearLayout dataLL;
    public static final int TYPE_UNDO = 0;//待处理
    public static final int TYPE_FINISH = 1;
    private int type = TYPE_UNDO;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    public static boolean isRefresh = false;

    public static Fragment newInstance(int type) {
        FireFragment fireFragment = new FireFragment();
        fireFragment.setType(type);
        return fireFragment;
    }

    @Override
    protected void init() {
        L.e("type=" + type);
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
        adapter = new FireAdapter(mContext, list, R.layout.item_fire);
        lv.setAdapter(adapter);
        userId = UserSP.getUserId(mContext);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FireBean.DataBean.RowsBean bean = adapter.getItem(position);
                String json = new Gson().toJson(bean);
                FireDetailActivity.intentToDetail(mContext, json);
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
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isRefresh) {
            isRefresh = false;
            getData();
        }
    }

    @Override
    public void getData() {
        if (NetWorkUtil.isNetworkAvailable(mContext)) {
            noNetView.setVisibility(View.GONE);
            dataLL.setVisibility(View.VISIBLE);
            HttpUtil.getFireList(mContext, userId, new HttpListener<String>() {
                @Override
                public void onSucceed(int what, Response<String> response) {
                    try {
                        list.clear();
                        FireBean fireBean = new Gson().fromJson(response.get(), FireBean.class);
                        if (fireBean != null) {
                            if (YS.SUCCESE.equals(fireBean.code) && fireBean.data != null && fireBean
                                    .data.rows != null && fireBean.data.rows.size() > 0) {
                                if (type == TYPE_UNDO) {
                                    for (FireBean.DataBean.RowsBean bean : fireBean.data.rows) {
                                        if (YS.FireStatus.Status_DCL.equals(bean.status)) {
                                            list.add(bean);
                                        }
                                    }
                                } else {
                                    for (FireBean.DataBean.RowsBean bean : fireBean.data.rows) {
                                        if (!YS.FireStatus.Status_DCL.equals(bean.status)) {
                                            list.add(bean);
                                        }
                                    }
                                }
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
            noNetView.setVisibility(View.VISIBLE);
            blankView.setVisibility(View.GONE);
            dataLL.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
        }
    }


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_fire;
    }

    @Override
    public void reload() {
        getData();
    }

    @Override
    public void onRefresh() {
        getData();
    }
}
