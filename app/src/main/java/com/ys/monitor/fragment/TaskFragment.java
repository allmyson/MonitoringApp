package com.ys.monitor.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.ys.monitor.R;
import com.ys.monitor.activity.TaskDetailActivity;
import com.ys.monitor.adapter.TaskAdapter;
import com.ys.monitor.base.BaseFragment;
import com.ys.monitor.sp.UserSP;
import com.ys.monitor.ui.BlankView;
import com.ys.monitor.ui.NoNetView;

import java.util.ArrayList;
import java.util.List;

public class TaskFragment extends BaseFragment implements NoNetView.ClickListener,
        SwipeRefreshLayout.OnRefreshListener {
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView lv;
    private List<Object> list;
    private TaskAdapter adapter;
    private String userId;
    private NoNetView noNetView;
    private BlankView blankView;
    private LinearLayout dataLL;
    public static final int TYPE_UNDO = 0;
    public static final int TYPE_FINISH = 1;
    private int type = TYPE_UNDO;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public static Fragment newInstance(int type) {
        TaskFragment taskFragment = new TaskFragment();
        taskFragment.setType(type);
        return new TaskFragment();
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
        list.add(null);
        list.add(null);
        list.add(null);
        adapter = new TaskAdapter(mContext, list, R.layout.item_task);
        lv.setAdapter(adapter);
        userId = UserSP.getUserId(mContext);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(mContext, TaskDetailActivity.class));
            }
        });
    }


    @Override
    public void getData() {
//        if (NetWorkUtil.isNetworkAvailable(mContext)) {
//            noNetView.setVisibility(View.GONE);
//            dataLL.setVisibility(View.VISIBLE);
//            HttpUtil.getMyJYJL(mContext, userId, new HttpListener<String>() {
//                @Override
//                public void onSucceed(int what, Response<String> response) {
//                    list.clear();
//                    MyJYBB myJybb = new Gson().fromJson(response.get(), MyJYBB.class);
//                    if (myJybb != null) {
//                        if (YS.SUCCESE.equals(myJybb.code) && myJybb.data != null && myJybb
//                        .data.data != null && myJybb.data.data.size() > 0) {
//                            list.addAll(myJybb.data.data);
//                        }
//                    } else {
//                        show(YS.HTTP_TIP);
//                    }
//                    adapter.refresh(list);
//                    if (adapter.getCount() > 0) {
//                        blankView.setVisibility(View.GONE);
//                    } else {
//                        blankView.setVisibility(View.VISIBLE);
//                    }
//                }
//
//                @Override
//                public void onFailed(int what, Response<String> response) {
//                    blankView.setVisibility(View.GONE);
//                }
//            });
//        } else {
//            noNetView.setVisibility(View.VISIBLE);
//            blankView.setVisibility(View.GONE);
//            dataLL.setVisibility(View.GONE);
//        }
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
