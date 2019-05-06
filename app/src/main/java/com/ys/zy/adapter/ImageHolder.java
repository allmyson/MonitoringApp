package com.ys.zy.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.ys.zy.api.FunctionApi;
import com.ys.zy.bean.ADBean;

public class ImageHolder implements Holder<ADBean.DataBean> {
    private ImageView imageView;

    @Override
    public View createView(Context context) {
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return imageView;
    }

    @Override
    public void UpdateUI(Context context, int position, ADBean.DataBean data) {
        Glide.with(context).load(FunctionApi.getImagePath(data.activity_image)).into(imageView);
    }
}
