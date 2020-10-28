package com.ys.monitor.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.ys.monitor.R;
import com.ys.monitor.activity.LocalFireDetailActivity;
import com.ys.monitor.activity.LocalResoureDetailActivity;
import com.ys.monitor.activity.LocalXHDetailActivity;
import com.ys.monitor.adapter.RecordAdapter;
import com.ys.monitor.base.BaseFragment;
import com.ys.monitor.bean.RecordBean;
import com.ys.monitor.sp.RecordSP;
import com.ys.monitor.sp.UserSP;
import com.ys.monitor.ui.BlankView;
import com.ys.monitor.util.L;
import com.ys.monitor.util.SPUtil;

import java.util.ArrayList;
import java.util.List;

public class RecordFragment extends BaseFragment implements
        SwipeRefreshLayout.OnRefreshListener {
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView lv;
    private List<RecordBean> recordBeanList;
    private RecordAdapter adapter;
    private String userId;
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
        RecordFragment recordFragment = new RecordFragment();
        recordFragment.setType(type);
        return recordFragment;
    }

    @Override
    protected void init() {
        L.e("type=" + type);
        swipeRefreshLayout = getView(R.id.srl_);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.main_color));
        dataLL = getView(R.id.ll_data);
        blankView = getView(R.id.bv_);
        blankView.setImage(R.mipmap.blank_inf_img).setText("暂无记录");
        lv = getView(R.id.lv_);
        recordBeanList = new ArrayList<>();
        adapter = new RecordAdapter(mContext, recordBeanList, R.layout.item_record);
        lv.setAdapter(adapter);
        userId = UserSP.getUserId(mContext);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String uuid = adapter.getItem(position).id;
                L.e("uuid=" + uuid);
                String detailJson = (String) SPUtil.get(mContext, uuid, "");
                L.e("detailJson=" + detailJson);
                String name = adapter.getItem(position).name;
                L.e("name=" + name);

                boolean isSucc = adapter.getItem(position).isSucc;
                if (RecordBean.TYPE_FIRE.equals(name)) {
                    if (isSucc) {
                        //查看
                        LocalFireDetailActivity.lookLocalFire(mContext, uuid, isSucc);
                    } else {
                        //不可编辑提交
                        LocalFireDetailActivity.lookLocalFire(mContext, uuid, isSucc);
                    }
                } else if (RecordBean.TYPE_XUHU.equals(name)) {
                    LocalXHDetailActivity.lookLocalXh(mContext, uuid, isSucc);
                } else if (RecordBean.TYPE_ZIYUAN.equals(name)) {
                    LocalResoureDetailActivity.lookLocalResoure(mContext,uuid,isSucc);
                }
//                FireBean.DataBean.RowsBean bean = adapter.getItem(position);
//                String json = new Gson().toJson(bean);
//                FireDetailActivity.intentToDetail(mContext, json);
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
        register();
    }

    @Override
    public void onResume() {
        super.onResume();
//        if (isRefresh) {
//            isRefresh = false;
//            getData();
//        }
        getDataSp();
    }

    @Override
    public void getData() {
    }

    private void getDataSp() {
        dataLL.setVisibility(View.VISIBLE);
        List<RecordBean> list = RecordSP.getRecordList(mContext);
        recordBeanList.clear();
        if (list != null && list.size() > 0) {
            for (RecordBean recordBean : list) {
                if (type == TYPE_UNDO) {
                    if (!recordBean.isSucc) {
                        recordBeanList.add(recordBean);
                    }
                } else if (type == TYPE_FINISH) {
                    if (recordBean.isSucc) {
                        recordBeanList.add(recordBean);
                    }
                }
            }
        }
        adapter.refresh(recordBeanList);
        if (adapter.getCount() > 0) {
            blankView.setVisibility(View.GONE);
        } else {
            blankView.setVisibility(View.VISIBLE);
        }
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_record;
    }

    @Override
    public void onRefresh() {
        getDataSp();
    }

    public static final String UPLOAD_RESULT = "com.ys.monitor.intentservice.UPLOAD_RESULT";

    private BroadcastReceiver uploadReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() == UPLOAD_RESULT) {
                L.e("收到上报结果广播");
                getDataSp();
            }

        }
    };

    private void register() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(UPLOAD_RESULT);
        getActivity().registerReceiver(uploadReceiver, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (uploadReceiver != null) {
            getActivity().unregisterReceiver(uploadReceiver);
        }
    }
}
