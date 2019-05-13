package com.ys.zy.activity;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yanzhenjie.nohttp.rest.Response;
import com.ys.zy.R;
import com.ys.zy.adapter.TeamReportAdapter;
import com.ys.zy.base.BaseActivity;
import com.ys.zy.bean.MsgBean;
import com.ys.zy.bean.TeamBB;
import com.ys.zy.bean.TeamBBTotal;
import com.ys.zy.http.HttpListener;
import com.ys.zy.sp.UserSP;
import com.ys.zy.ui.BlankView;
import com.ys.zy.ui.NoNetView;
import com.ys.zy.util.HttpUtil;
import com.ys.zy.util.NetWorkUtil;
import com.ys.zy.util.StringUtil;
import com.ys.zy.util.YS;

import java.util.ArrayList;
import java.util.List;

//团队报表
public class TeamReportActivity extends BaseActivity implements NoNetView.ClickListener {
    private ListView lv;
    private List<TeamBB.DataBeanX.DataBean> list;
    private TeamReportAdapter adapter;
    private String userId;
    private TextView numTV, tzNumTV, backNumTV;
    private LinearLayout dataLL;
    private NoNetView noNetView;
    private BlankView blankView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_team_report;
    }

    @Override
    public void initView() {
        setBarColor("#ededed");
        titleView.setText("团队报表");
        noNetView = getView(R.id.nnv_);
        blankView = getView(R.id.bv_);
        blankView.setImage(R.mipmap.blank_inf_img).setText("暂无记录");
        noNetView.setClickListener(this);
        dataLL = getView(R.id.ll_data);
        numTV = getView(R.id.tv_count);
        tzNumTV = getView(R.id.tv_tzNum);
        backNumTV = getView(R.id.tv_backNum);
        lv = getView(R.id.lv_);
        list = new ArrayList<>();
        adapter = new TeamReportAdapter(mContext, list, R.layout.item_team_report);
        lv.setAdapter(adapter);
        userId = UserSP.getUserId(mContext);
    }

    @Override
    public void getData() {
        HttpUtil.getTotalTeamData(mContext, userId, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                TeamBBTotal teamBBTotal = new Gson().fromJson(response.get(), TeamBBTotal.class);
                if (teamBBTotal != null && YS.SUCCESE.equals(teamBBTotal.code) && teamBBTotal.data != null) {
                    numTV.setText("" + StringUtil.StringToInt(teamBBTotal.data.user_count));
                    tzNumTV.setText(StringUtil.StringToDoubleStr(teamBBTotal.data.bets_money));
                    backNumTV.setText(StringUtil.StringToDoubleStr(teamBBTotal.data.back_moeny));
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }
        });

        if (NetWorkUtil.isNetworkAvailable(mContext)) {
            noNetView.setVisibility(View.GONE);
            dataLL.setVisibility(View.VISIBLE);
            HttpUtil.getTeamBBList(mContext, userId, new HttpListener<String>() {
                @Override
                public void onSucceed(int what, Response<String> response) {
                    list.clear();
                    TeamBB teamBB = new Gson().fromJson(response.get(), TeamBB.class);
                    if (teamBB != null && teamBB.data != null && teamBB.data.data != null && teamBB.data.data.size() > 0) {
                        list.addAll(teamBB.data.data);
                    }
                    adapter.refresh(list);
                    if (adapter.getCount() > 0) {
                        blankView.setVisibility(View.GONE);
                    } else {
                        blankView.setVisibility(View.VISIBLE);
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

    }

    @Override
    public void reload() {
        getData();
    }
}
