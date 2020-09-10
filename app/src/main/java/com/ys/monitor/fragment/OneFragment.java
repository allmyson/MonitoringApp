package com.ys.monitor.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ys.monitor.R;
import com.ys.monitor.activity.AddFireActivity;
import com.ys.monitor.base.BaseFragment;

/**
 * @author lh
 * @version 1.0.0
 * @filename OneFragment
 * @description -------------------------------------------------------
 * @date 2018/10/23 17:09
 */
public class OneFragment extends BaseFragment implements View.OnClickListener,
        SwipeRefreshLayout.OnRefreshListener {
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView wdTV, tianqiTV, aqiTV, qixiangTV, msgTV;
    private ImageView tianqiIV;
    private LinearLayout msgLL;

    public static OneFragment newInstance() {
        return new OneFragment();
    }

    @Override
    public void onRefresh() {
        getData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_hqsb:
                startActivity(new Intent(mContext, AddFireActivity.class));
                break;
            case R.id.ll_rcxh:
                break;
            case R.id.ll_zycj:
                break;
            case R.id.ll_spck:
                break;
            case R.id.ll_txl:
                break;
        }
    }

    @Override
    protected void init() {
        swipeRefreshLayout = getView(R.id.srl_);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.main_color));
        wdTV = getView(R.id.tv_wd);
        tianqiTV = getView(R.id.tv_tianqi);
        aqiTV = getView(R.id.tv_aqi);
        qixiangTV = getView(R.id.tv_qixiang);
        msgTV = getView(R.id.tv_msg);
        tianqiIV = getView(R.id.iv_tianqi);
        msgLL = getView(R.id.ll_msg);
        getView(R.id.ll_hqsb).setOnClickListener(this);
        getView(R.id.ll_rcxh).setOnClickListener(this);
        getView(R.id.ll_zycj).setOnClickListener(this);
        getView(R.id.ll_spck).setOnClickListener(this);
        getView(R.id.ll_txl).setOnClickListener(this);
    }

    @Override
    protected void getData() {
//        HttpUtil.getGameList(mContext, new HttpListener<String>() {
//            @Override
//            public void onSucceed(int what, Response<String> response) {
////                swipeRefreshLayout.setRefreshing(false);
//            }
//
//            @Override
//            public void onFailed(int what, Response<String> response) {
////                swipeRefreshLayout.setRefreshing(false);
//            }
//        });
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_one;
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
