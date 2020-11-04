package com.ys.monitor.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.yanzhenjie.nohttp.rest.Response;
import com.ys.monitor.R;
import com.ys.monitor.activity.AddXHActivity;
import com.ys.monitor.activity.ContactActivity;
import com.ys.monitor.activity.RecordListActivity;
import com.ys.monitor.activity.ResoureActivity;
import com.ys.monitor.activity.UpdateResoureActivity;
import com.ys.monitor.activity.VideoListActivity;
import com.ys.monitor.activity.YjtzDetailActivity;
import com.ys.monitor.base.BaseFragment;
import com.ys.monitor.bean.HFAqi;
import com.ys.monitor.bean.HFWeather;
import com.ys.monitor.bean.YjtzBean;
import com.ys.monitor.http.HttpListener;
import com.ys.monitor.sp.MsgSP;
import com.ys.monitor.sp.UserSP;
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
    private String userId;
    private YjtzBean.DataBean.RowsBean currentYjtz;

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
//                startActivity(new Intent(mContext, AddFireActivity.class));
                UpdateResoureActivity.updateResourceActivity(mContext,"");
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
            case R.id.ll_msg:
                if (currentYjtz != null) {
                    YjtzDetailActivity.intentToDetail(mContext, new Gson().toJson(currentYjtz));
                } else {
                    show("暂无预警通知");
                }
                break;
            case R.id.ll_history:
                startActivity(new Intent(mContext, RecordListActivity.class));
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
        msgLL.setOnClickListener(this);
        getView(R.id.ll_hqsb).setOnClickListener(this);
        getView(R.id.ll_rcxh).setOnClickListener(this);
        getView(R.id.ll_zycj).setOnClickListener(this);
        getView(R.id.ll_spck).setOnClickListener(this);
        getView(R.id.ll_txl).setOnClickListener(this);
        getView(R.id.ll_history).setOnClickListener(this);
        userId = UserSP.getUserId(mContext);
        String json = MsgSP.getFirstMsg(mContext);
        if (StringUtil.isGoodJson(json)) {
            currentYjtz = new Gson().fromJson(json, YjtzBean.DataBean.RowsBean.class);
            if (currentYjtz != null) {
                msgTV.setText(StringUtil.valueOf(currentYjtz.content));
            }
        }
    }

    @Override
    protected void getData() {
//        HttpUtil.getWeather(mContext, 105, new HttpListener<String>() {
//            @Override
//            public void onSucceed(int what, Response<String> response) {
//                try {
//                    WeatherBean weatherBean = new Gson().fromJson(response.get(), WeatherBean
//                    .class);
//                    if (weatherBean != null && "1".equals(weatherBean.success) && weatherBean
//                    .result != null) {
//                        WeatherBean.ResultBean resultBean = weatherBean.result;
//                        wdTV.setText(StringUtil.valueOf(resultBean.temperature_curr));
//                        tianqiTV.setText(StringUtil.valueOf(resultBean.weather_curr));
//                        aqiTV.setText(StringUtil.valueOf(resultBean.aqi) + StringUtil
//                        .getLevelByAqi(StringUtil.StringToInt(resultBean.aqi)));
//                        aqiTV.setTextColor(Color.parseColor(StringUtil.getColorByAqi(StringUtil
//                        .StringToInt(resultBean.aqi))));
//                        aqiTV.setBackgroundResource(StringUtil.getDrawableIdByAqi(StringUtil
//                        .StringToInt(resultBean.aqi)));
//                        qixiangTV.setText(StringUtil.valueOf(resultBean.wind) + StringUtil
//                        .valueOf(resultBean.winp)+"\t\t湿度"+StringUtil.valueOf(resultBean
//                        .humidity));
//    //                    Glide.with(mContext)
//    //                            .load(StringUtil.valueOf(resultBean.weather_icon))
//    //                            .asGif()
//    //                            .into(tianqiIV);
//                    }
//                } catch (JsonSyntaxException e) {
//                    e.printStackTrace();
//                }
//                swipeRefreshLayout.setRefreshing(false);
//            }
//
//            @Override
//            public void onFailed(int what, Response<String> response) {
//                swipeRefreshLayout.setRefreshing(false);
//            }
//        });
        HttpUtil.getYjtzListWithNoDialog(mContext, userId, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                try {
                    YjtzBean yjtzBean = new Gson().fromJson(response.get(), YjtzBean.class);
                    if (yjtzBean != null && yjtzBean.data != null && yjtzBean.data.rows != null && yjtzBean.data.rows.size() > 0) {
                        if (yjtzBean.data.rows.get(0) != null) {
                            currentYjtz = yjtzBean.data.rows.get(0);
                            msgTV.setText(StringUtil.valueOf(currentYjtz.content));
                            MsgSP.saveFirstMsg(mContext, new Gson().toJson(currentYjtz));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                swipeRefreshLayout.setRefreshing(false);

            }
        });


        HttpUtil.getHFWeather(mContext, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                try {
                    HFWeather hfWeather = new Gson().fromJson(response.get(),
                            HFWeather.class);
                    if (hfWeather != null && hfWeather.HeWeather6 != null && hfWeather.HeWeather6.size() > 0) {
                        HFWeather.HeWeather6Bean resultBean = hfWeather.HeWeather6.get(0);
                        if (resultBean != null && "ok".equals(resultBean.status) && resultBean.now != null) {
                            wdTV.setText(StringUtil.valueOf(resultBean.now.tmp + "℃"));
                            tianqiTV.setText(StringUtil.valueOf(resultBean.now.cond_txt));
                            qixiangTV.setText(StringUtil.valueOf(resultBean.now.wind_dir) + StringUtil.valueOf(resultBean.now.wind_sc) + "级\t\t湿度" + StringUtil.valueOf(resultBean.now.hum) + "%\t\t气压" + StringUtil.valueOf(resultBean.now.pres) + "hPa");
                            Bitmap bitmap = StringUtil.getImageFromAssetsFile(mContext, "weather" +
                                    "/" + StringUtil.valueOf(resultBean.now.cond_code) + ".png");
                            if (bitmap != null) {
                                tianqiIV.setImageBitmap(bitmap);
                            }
                        }
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

        HttpUtil.getHFAQI(mContext, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                try {
                    HFAqi hfAqi = new Gson().fromJson(response.get(),
                            HFAqi.class);
                    if (hfAqi != null && hfAqi.HeWeather6 != null && hfAqi.HeWeather6.size() > 0) {
                        HFAqi.HeWeather6Bean resultBean = hfAqi.HeWeather6.get(0);
                        if (resultBean != null && "ok".equals(resultBean.status) && resultBean.air_now_station != null && resultBean.air_now_station.size() > 0) {
                            for (HFAqi.HeWeather6Bean.AirNowStationBean airNowStationBean :
                                    resultBean.air_now_station) {
                                if (airNowStationBean != null && "缙云山".equals(airNowStationBean.air_sta)) {
                                    aqiTV.setText(StringUtil.valueOf(airNowStationBean.aqi) + StringUtil.valueOf(airNowStationBean.qlty));
                                    aqiTV.setTextColor(Color.parseColor(StringUtil.getColorByAqi
                                            (StringUtil.StringToInt(airNowStationBean.aqi))));
                                    aqiTV.setBackgroundResource(StringUtil.getDrawableIdByAqi
                                            (StringUtil.StringToInt(airNowStationBean.aqi)));
                                    break;
                                }
                            }
                        }
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
