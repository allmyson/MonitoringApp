package com.ys.monitor.activity;

import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.yanzhenjie.nohttp.rest.Response;
import com.ys.monitor.R;
import com.ys.monitor.adapter.ExpandableListAdapter;
import com.ys.monitor.base.BaseActivity;
import com.ys.monitor.http.HttpListener;
import com.ys.monitor.sp.UserSP;
import com.ys.monitor.util.HttpUtil;
import com.ys.monitor.util.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContactActivity extends BaseActivity {
    private String userId;
    private ExpandableListView expandableListView;
    private ExpandableListAdapter adapter;

    private List<Map<String, Object>> list;
    private String[][] child_text_array;
    @Override
    public int getLayoutId() {
        return R.layout.activity_contact;
    }

    @Override
    public void initView() {
        setBarColor("#ffffff");
        titleView.setText("通讯录");
        expandableListView = getView(R.id.list);
        child_text_array = Model.EXPANDABLE_MORELIST_TXT;
        initModle();
        setListener();
    }
    private void setListener() {
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                return false;
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(getApplicationContext(),
                        child_text_array[groupPosition][childPosition],
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }
    @Override
    public void getData() {
        userId = UserSP.getUserId(mContext);
        HttpUtil.getUserList(mContext, userId, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {

            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    private void initModle() {
        list = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < Model.EXPANDABLE_LISTVIEW_TXT.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("img", R.mipmap.about_logo);
            map.put("txt", Model.EXPANDABLE_LISTVIEW_TXT[i]);
            list.add(map);
        }
        adapter = new ExpandableListAdapter(this, list, child_text_array);
        expandableListView.setAdapter(adapter);
    }
}
