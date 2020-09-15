package com.ys.monitor.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.yanzhenjie.nohttp.rest.Response;
import com.ys.monitor.R;
import com.ys.monitor.activity.ResoureActivity;
import com.ys.monitor.activity.TaskDetailActivity;
import com.ys.monitor.adapter.TaskAdapter;
import com.ys.monitor.base.BaseFragment;
import com.ys.monitor.bean.BaseBean;
import com.ys.monitor.bean.TaskBean;
import com.ys.monitor.http.HttpListener;
import com.ys.monitor.sp.UserSP;
import com.ys.monitor.ui.BlankView;
import com.ys.monitor.ui.NoNetView;
import com.ys.monitor.util.HttpUtil;
import com.ys.monitor.util.NetWorkUtil;
import com.ys.monitor.util.StringUtil;
import com.ys.monitor.util.ToastUtil;
import com.ys.monitor.util.YS;

import java.util.ArrayList;
import java.util.List;

public class TaskFragment extends BaseFragment implements NoNetView.ClickListener,
        SwipeRefreshLayout.OnRefreshListener, TaskAdapter.DoListener {
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView lv;
    private List<TaskBean.DataBean.RowsBean> list;
    private TaskAdapter adapter;
    private String userId;
    private NoNetView noNetView;
    private BlankView blankView;
    private LinearLayout dataLL;
    public static final int TYPE_UNDO = 0;
    public static final int TYPE_FINISH = 1;
    private int type = TYPE_UNDO;
    public static boolean isRefresh = false;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public static Fragment newInstance(int type) {
        TaskFragment taskFragment = new TaskFragment();
        taskFragment.setType(type);
        return taskFragment;
    }

    @Override
    protected void init() {
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
        adapter = new TaskAdapter(mContext, list, R.layout.item_task);
        adapter.setDoListener(this);
        lv.setAdapter(adapter);
        userId = UserSP.getUserId(mContext);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TaskBean.DataBean.RowsBean rowsBean = adapter.getItem(position);
                String json = new Gson().toJson(rowsBean);
                if (rowsBean.isFinish == 0) {
                    //待办
                    if ("资源采集".equals(rowsBean.typeName)) {
                        startActivity(new Intent(mContext, ResoureActivity.class));
                    } else if ("日常巡护".equals(rowsBean.typeName)) {

                    } else {
                        //其他任务
                        TaskDetailActivity.intentToDetail(mContext, json);
                    }
                } else {
                    TaskDetailActivity.intentToDetail(mContext, json);
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
            HttpUtil.getTaskList(mContext, userId, new HttpListener<String>() {
                @Override
                public void onSucceed(int what, Response<String> response) {
                    try {
                        list.clear();
                        TaskBean taskBean = new Gson().fromJson(response.get(), TaskBean.class);
                        if (taskBean != null) {
                            if (YS.SUCCESE.equals(taskBean.code) && taskBean.data != null && taskBean
                                    .data.rows != null && taskBean.data.rows.size() > 0) {
                                if (type == TYPE_UNDO) {
                                    for (TaskBean.DataBean.RowsBean bean : taskBean.data.rows) {
                                        if (bean.isFinish == 0) {
                                            list.add(bean);
                                        }
                                    }
                                } else {
                                    for (TaskBean.DataBean.RowsBean bean : taskBean.data.rows) {
                                        if (bean.isFinish == 1) {
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

    @Override
    public void doTask(TaskBean.DataBean.RowsBean bean) {
        HttpUtil.updateTask(mContext, UserSP.getUserId(mContext), bean.recNo, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                try {
                    BaseBean baseBean = new Gson().fromJson(response.get(), BaseBean.class);
                    if (baseBean != null && YS.SUCCESE.equals(baseBean.code)) {
                        ToastUtil.show(mContext, StringUtil.valueOf(baseBean.msg));
                        getData();
                    } else {
                        ToastUtil.show(mContext,"任务汇报失败!");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }
        });
    }

}
