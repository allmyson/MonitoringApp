package com.ys.monitor.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.ys.monitor.R;
import com.ys.monitor.base.BaseActivity;
import com.ys.monitor.bean.YjtzBean;
import com.ys.monitor.util.DateUtil;
import com.ys.monitor.util.DensityUtil;
import com.ys.monitor.util.StringUtil;
import com.ys.monitor.util.YS;

public class YjtzDetailActivity extends BaseActivity {
    private LinearLayout imageLL;
    private TextView titleTV, timeTV, contentTV;
    private YjtzBean.DataBean.RowsBean rowsBean;

    @Override
    public int getLayoutId() {
        return R.layout.activity_yjtz_detail;
    }

    @Override
    public void initView() {
        setBarColor("#ffffff");
        titleView.setText("预警通知详情");
        imageLL = getView(R.id.ll_image);
        titleTV = getView(R.id.tv_title);
        timeTV = getView(R.id.tv_time);
        contentTV = getView(R.id.tv_content);
    }

    @Override
    public void getData() {
        String json = getIntent().getStringExtra("data");
        if (StringUtil.isGoodJson(json)) {
            rowsBean = new Gson().fromJson(json, YjtzBean.DataBean.RowsBean.class);
            if (rowsBean != null) {
                titleTV.setText(StringUtil.valueOf(rowsBean.title));
                timeTV.setText(DateUtil.changeTimeToYMDHMS(StringUtil.valueOf(rowsBean.createTime)));
                contentTV.setText(StringUtil.valueOf(rowsBean.content));
                if (rowsBean.imgUrl != null && rowsBean.imgUrl.contains(";")) {
                    String[] a = rowsBean.imgUrl.split(";");
                    if (a != null && a.length > 0) {
                        for (String s : a) {
                            if (StringUtil.isBlank(s)) {
                                continue;
                            }
                            if (s.startsWith("http")) {
                                addImage(s);
                            } else {
                                addImage(YS.IP + s);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    public static void intentToDetail(Context context, String json) {
        Intent intent = new Intent(context, YjtzDetailActivity.class);
        intent.putExtra("data", json);
        context.startActivity(intent);
    }

    private void addImage(String url) {
        ImageView imageView = new ImageView(mContext);
        imageView.setAdjustViewBounds(true);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);//两
        imageView.setLayoutParams(params);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(mContext).load(url).into(imageView);
        imageLL.addView(imageView);
        ((ViewGroup.MarginLayoutParams) imageView.getLayoutParams()).bottomMargin = DensityUtil.dp2px(mContext,10);
    }
}
