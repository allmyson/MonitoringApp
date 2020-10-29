package com.ys.monitor.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.yanzhenjie.nohttp.rest.Response;
import com.ys.monitor.R;
import com.ys.monitor.adapter.VideoListAdapter;
import com.ys.monitor.base.BaseActivity;
import com.ys.monitor.bean.VideoBean;
import com.ys.monitor.bean.VideoUrl;
import com.ys.monitor.http.HttpListener;
import com.ys.monitor.sp.UserSP;
import com.ys.monitor.ui.BlankView;
import com.ys.monitor.ui.NoNetView;
import com.ys.monitor.util.HttpUtil;
import com.ys.monitor.util.L;
import com.ys.monitor.util.NetWorkUtil;
import com.ys.monitor.util.StringUtil;
import com.ys.monitor.util.YS;

import java.util.ArrayList;
import java.util.List;

public class VideoListActivity extends BaseActivity implements NoNetView.ClickListener,
        SwipeRefreshLayout.OnRefreshListener {
    private GridView myGridView;
    private VideoListAdapter videoListAdapter;
    private List<VideoBean.DataBean.RowsBean> list;
    private String userId;
    private NoNetView noNetView;
    private BlankView blankView;
    private LinearLayout dataLL;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public int getLayoutId() {
        return R.layout.activity_video_list;
    }

    @Override
    public void initView() {
        setBarColor("#ffffff");
        titleView.setText("视频查看");
        swipeRefreshLayout = getView(R.id.srl_);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.main_color));
        dataLL = getView(R.id.ll_data);
        noNetView = getView(R.id.nnv_);
        blankView = getView(R.id.bv_);
        blankView.setImage(R.mipmap.blank_inf_img).setText("暂无视频");
        noNetView.setClickListener(this);
        myGridView = getView(R.id.gv_video);
        myGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        list = new ArrayList<>();
        videoListAdapter = new VideoListAdapter(mContext, list, R.layout.item_video);
        myGridView.setAdapter(videoListAdapter);
        myGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getVideoUrl(videoListAdapter.getItem(position).recNo);
            }
        });
        myGridView.setOnScrollListener(new AbsListView.OnScrollListener() {
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
        userId = UserSP.getUserId(mContext);
    }

    @Override
    public void getData() {
        if (NetWorkUtil.isNetworkAvailable(mContext)) {
            noNetView.setVisibility(View.GONE);
            dataLL.setVisibility(View.VISIBLE);
            HttpUtil.getVideoList(mContext, userId, new HttpListener<String>() {
                @Override
                public void onSucceed(int what, Response<String> response) {
                    try {
                        list.clear();
                        VideoBean videoBean = new Gson().fromJson(response.get(), VideoBean.class);
                        if (videoBean != null && YS.SUCCESE.equals(videoBean.code) && videoBean.data != null && videoBean.data.rows != null && videoBean.data.rows.size() > 0) {
                            list.addAll(videoBean.data.rows);
                        }
                        videoListAdapter.refresh(list);
                        if (videoListAdapter.getCount() > 0) {
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
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    @Override
    public void reload() {
        getData();
    }

    @Override
    public void onRefresh() {
        getData();
    }

    private void getVideoUrl(String recNo) {
//        PreviewActivity.playRtspVideo(mContext, "rtsp://10.66.165.243:655/EUrl/8ccdhg4");

        HttpUtil.getVideoUrl(mContext, userId, recNo,new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                try {
                    VideoUrl videoUrl = new Gson().fromJson(response.get(), VideoUrl.class);
                    if (videoUrl != null && YS.SUCCESE.equals(videoUrl.code) && videoUrl.data != null && videoUrl.data.size() > 0) {
                        VideoUrl.DataBeanX dataBean = videoUrl.data.get(0);
                        if (dataBean != null && dataBean.data != null) {
                            L.e("rtsp=" + dataBean.data.url);
                            if (!StringUtil.isBlank(dataBean.data.url)) {
                                PreviewActivity.playRtspVideo(mContext, dataBean.data.url);
                            }
                        }
                    } else {
                        show("获取视频流地址失败！");
                    }
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }
        });
    }
}
