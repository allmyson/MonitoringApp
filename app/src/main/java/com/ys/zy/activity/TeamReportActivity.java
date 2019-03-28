package com.ys.zy.activity;

import android.view.View;
import android.widget.ListView;

import com.ys.zy.R;
import com.ys.zy.adapter.TeamReportAdapter;
import com.ys.zy.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

//团队报表
public class TeamReportActivity extends BaseActivity {
    private ListView lv;
    private List<Object> list;
    private TeamReportAdapter adapter;
    @Override
    public int getLayoutId() {
        return R.layout.activity_team_report;
    }

    @Override
    public void initView() {
        titleView.setText("团队报表");
        lv = getView(R.id.lv_);
        list = new ArrayList<>();
        list.add(null);
        list.add(null);
        list.add(null);
        list.add(null);
        list.add(null);
        list.add(null);
        adapter = new TeamReportAdapter(mContext,list,R.layout.item_team_report);
        lv.setAdapter(adapter);
    }

    @Override
    public void getData() {

    }

    @Override
    public void onClick(View v) {

    }
}
