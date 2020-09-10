package com.ys.monitor.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ys.monitor.R;
import com.ys.monitor.activity.FireListActivity;
import com.ys.monitor.activity.TaskListActivity;
import com.ys.monitor.activity.YjtzActivity;
import com.ys.monitor.adapter.NoticeAdapter;
import com.ys.monitor.api.FunctionApi;
import com.ys.monitor.base.BaseFragment;
import com.ys.monitor.bean.Msg;
import com.ys.monitor.ui.NoNetView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lh
 * @version 1.0.0
 * @filename OneFragment
 * @description -------------------------------------------------------
 * @date 2018/10/23 17:09
 */
public class ThreeFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,
        NoNetView.ClickListener, View.OnClickListener {
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView listView;
    private List<Msg> list;
    private NoticeAdapter adapter;

    public static ThreeFragment newInstance() {
        return new ThreeFragment();
    }

    @Override
    public void onRefresh() {
        getData();
    }

    @Override
    protected void init() {
        swipeRefreshLayout = getView(R.id.srl_);
        listView = getView(R.id.lv_);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.main_color));
        list = new ArrayList<>();
        list.addAll(Msg.getDefaultMsgList());
        adapter = new NoticeAdapter(mContext, list, R.layout.item_notice);
        listView.setAdapter(adapter);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(mContext, FireListActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(mContext, YjtzActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(mContext, TaskListActivity.class));
                        break;
                }
            }
        });
    }

    @Override
    protected void getData() {
//            HttpUtil.selectMsg(mContext, new HttpListener<String>() {
//                @Override
//                public void onSucceed(int what, Response<String> response) {
//                    list.clear();
//                    MsgBean msgBean = new Gson().fromJson(response.get(), MsgBean.class);
//                    if (msgBean != null && msgBean.data != null&& msgBean.data.data != null &&
//                    msgBean.data.data.size() > 0) {
//                        list.addAll(msgBean.data.data);
//                    }
//                    adapter.refresh(list);
//                    swipeRefreshLayout.setRefreshing(false);
//                }
//
//                @Override
//                public void onFailed(int what, Response<String> response) {
//                    swipeRefreshLayout.setRefreshing(false);
//                }
//            });
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
