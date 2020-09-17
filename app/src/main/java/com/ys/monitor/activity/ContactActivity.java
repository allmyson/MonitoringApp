package com.ys.monitor.activity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yanzhenjie.nohttp.rest.Response;
import com.ys.monitor.R;
import com.ys.monitor.adapter.ExpandableListAdapter;
import com.ys.monitor.base.BaseActivity;
import com.ys.monitor.bean.UserList;
import com.ys.monitor.http.HttpListener;
import com.ys.monitor.sp.UserSP;
import com.ys.monitor.ui.BlankView;
import com.ys.monitor.ui.NoNetView;
import com.ys.monitor.util.HttpUtil;
import com.ys.monitor.util.NetWorkUtil;
import com.ys.monitor.util.YS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContactActivity extends BaseActivity implements NoNetView.ClickListener {
    private String userId;
    private ExpandableListView expandableListView;
    private ExpandableListAdapter adapter;
    private NoNetView noNetView;
    private BlankView blankView;
    private LinearLayout dataLL;
    private Map<String, List<UserList.DataBean.RowsBean>> map;
    private Map<String, List<UserList.DataBean.RowsBean>> allMap;
    private List<UserList.DataBean.RowsBean> allList;
    private List<UserList.DataBean.RowsBean> searchList;
    private int sign = -1;// 控制列表的展开
    private EditText searchET;

    @Override
    public int getLayoutId() {
        return R.layout.activity_contact;
    }

    @Override
    public void initView() {
        searchList = new ArrayList<>();
        allList = new ArrayList<>();
        map = new HashMap<>();
        allMap = new HashMap<>();
        setBarColor("#ffffff");
        titleView.setText("通讯录");
        searchET = getView(R.id.et_search);
        dataLL = getView(R.id.ll_data);
        noNetView = getView(R.id.nnv_);
        blankView = getView(R.id.bv_);
        blankView.setImage(R.mipmap.blank_inf_img).setText("暂无记录");
        noNetView.setClickListener(this);
        expandableListView = getView(R.id.list);
        initModle();
        setListener();
        userId = UserSP.getUserId(mContext);
    }

    private void setListener() {
        searchET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    search();
                } else {
                    map.clear();
                    map.putAll(allMap);
                    adapter.refresh(map);
                    expandGroupFirst();
                }
            }
        });
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                if (sign == -1) {
                    // 展开被选的group
                    expandableListView.expandGroup(groupPosition);
                    // 设置被选中的group置于顶端
                    expandableListView.setSelectedGroup(groupPosition);
                    sign = groupPosition;
                } else if (sign == groupPosition) {
                    expandableListView.collapseGroup(sign);
                    sign = -1;
                } else {
                    expandableListView.collapseGroup(sign);
                    // 展开被选的group
                    expandableListView.expandGroup(groupPosition);
                    // 设置被选中的group置于顶端
                    expandableListView.setSelectedGroup(groupPosition);
                    sign = groupPosition;
                }
                return true;
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(mContext,
                        adapter.getChild(groupPosition, childPosition).trueName,
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    @Override
    public void getData() {
        if (NetWorkUtil.isNetworkAvailable(mContext)) {
            noNetView.setVisibility(View.GONE);
            dataLL.setVisibility(View.VISIBLE);
            HttpUtil.getUserList(mContext, userId, new HttpListener<String>() {
                @Override
                public void onSucceed(int what, Response<String> response) {
                    try {
                        allList.clear();
                        allMap.clear();
                        map.clear();
                        UserList userList = new Gson().fromJson(response.get(), UserList.class);
                        if (userList != null && YS.SUCCESE.equals(userList.code) && userList.data != null && userList.data.rows != null && userList.data.rows.size() > 0) {
                            Map<String, List<UserList.DataBean.RowsBean>> data =
                                    UserList.groupList(userList.data.rows);
                            allList.addAll(userList.data.rows);
                            allMap.putAll(data);
                            map.putAll(data);
                        } else {
                            show(YS.HTTP_TIP);
                        }
                        adapter.refresh(map);
                        expandGroupFirst();
                        if (adapter.getGroupCount() > 0) {
                            blankView.setVisibility(View.GONE);
                        } else {
                            blankView.setVisibility(View.VISIBLE);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailed(int what, Response<String> response) {
                    blankView.setVisibility(View.GONE);
                }
            });
        } else {
            noNetView.setVisibility(View.VISIBLE);
            blankView.setVisibility(View.GONE);
            dataLL.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    private void initModle() {
        adapter = new ExpandableListAdapter(this, map);
        expandableListView.setAdapter(adapter);
    }

    @Override
    public void reload() {
        getData();
    }


    private void search() {
        searchList.clear();
        map.clear();
        String content = searchET.getText().toString();
        if (allList != null && allList.size() > 0) {
            for (UserList.DataBean.RowsBean rowsBean : allList) {
                if (rowsBean.trueName != null && rowsBean.trueName.contains(content)) {
                    searchList.add(rowsBean);
                }
            }
        }
        if (searchList.size() > 0) {
            Map<String, List<UserList.DataBean.RowsBean>> data =
                    UserList.groupList(searchList);
            map.putAll(data);
        }
        adapter.refresh(map);

        expandGroupAll();
    }

    private void expandGroupAll() {
        for (int i = 0; i < adapter.getGroupCount(); i++) {
            expandableListView.expandGroup(i);
        }
    }

    private void expandGroupFirst() {
        for (int i = 0; i < adapter.getGroupCount(); i++) {
            if (i == 0) {
                sign = i;
                expandableListView.expandGroup(i);
            } else {
                expandableListView.collapseGroup(i);
            }
        }
    }

    private void collapseGroupAll() {
        for (int i = 0; i < adapter.getGroupCount(); i++) {
            expandableListView.collapseGroup(i);
        }
    }
}
