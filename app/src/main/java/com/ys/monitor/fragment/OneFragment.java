package com.ys.monitor.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.yanzhenjie.nohttp.rest.Response;
import com.ys.monitor.R;
import com.ys.monitor.activity.AddFireActivity;
import com.ys.monitor.activity.AddXHActivity;
import com.ys.monitor.activity.ContactActivity;
import com.ys.monitor.activity.ResoureActivity;
import com.ys.monitor.activity.VideoListActivity;
import com.ys.monitor.base.BaseFragment;
import com.ys.monitor.bean.WeatherBean;
import com.ys.monitor.http.HttpListener;
import com.ys.monitor.util.GlideRoundTransform;
import com.ys.monitor.util.HttpUtil;
import com.ys.monitor.util.StringUtil;

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
                startActivity(new Intent(mContext, AddXHActivity.class));
                break;
            case R.id.ll_zycj:
                startActivity(new Intent(mContext, ResoureActivity.class));
                break;
            case R.id.ll_spck:
                startActivity(new Intent(mContext, VideoListActivity.class));
                break;
            case R.id.ll_txl:
                startActivity(new Intent(mContext, ContactActivity.class));
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
        HttpUtil.getWeather(mContext, 105, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                try {
                    WeatherBean weatherBean = new Gson().fromJson(response.get(), WeatherBean.class);
                    if (weatherBean != null && "1".equals(weatherBean.success) && weatherBean.result != null) {
                        WeatherBean.ResultBean resultBean = weatherBean.result;
                        wdTV.setText(StringUtil.valueOf(resultBean.temperature_curr));
                        tianqiTV.setText(StringUtil.valueOf(resultBean.weather_curr));
                        aqiTV.setText(StringUtil.valueOf(resultBean.aqi) + StringUtil.getLevelByAqi(StringUtil.StringToInt(resultBean.aqi)));
                        aqiTV.setTextColor(Color.parseColor(StringUtil.getColorByAqi(StringUtil.StringToInt(resultBean.aqi))));
                        aqiTV.setBackgroundResource(StringUtil.getDrawableIdByAqi(StringUtil.StringToInt(resultBean.aqi)));
                        qixiangTV.setText(StringUtil.valueOf(resultBean.wind) + StringUtil.valueOf(resultBean.winp)+"\t\t湿度"+StringUtil.valueOf(resultBean.humidity));
    //                    Glide.with(mContext)
    //                            .load(StringUtil.valueOf(resultBean.weather_icon))
    //                            .asGif()
    //                            .into(tianqiIV);
                    }
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
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
