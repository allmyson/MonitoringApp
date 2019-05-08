package com.ys.zy.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ys.zy.R;
import com.ys.zy.api.FunctionApi;
import com.ys.zy.bean.WinnerBean;
import com.ys.zy.bean.WinnerUserBean;
import com.ys.zy.util.StringUtil;

import java.util.List;

public class WinnerListAdapter extends CommonAdapter<WinnerUserBean.DataBeanX.DataBean> {
    public WinnerListAdapter(Context context, List<WinnerUserBean.DataBeanX.DataBean> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, WinnerUserBean.DataBeanX.DataBean item, int position) {
        Glide.with(mContext).load(FunctionApi.getImagePath(item.consumer_img)).placeholder(R.mipmap.bg_default_head2).error(R.mipmap.bg_default_head2).into((ImageView) helper.getView(R.id.iv_));
        helper.setText(R.id.tv_name, StringUtil.valueOf(item.login_name));
        helper.setText(R.id.tv_money, "￥"+StringUtil.StringToDoubleStr(item.win_money));
        helper.setText(R.id.tv_rank, StringUtil.valueOf((position + 1)));
        if(position==0){
            helper.setImageResource(R.id.iv_rank,R.mipmap.rside_img_sn1);
            helper.getView(R.id.iv_rank).setVisibility(View.VISIBLE);
            helper.getView(R.id.tv_rank).setVisibility(View.GONE);
        }else if(position == 1){
            helper.setImageResource(R.id.iv_rank,R.mipmap.rside_img_sn2);
            helper.getView(R.id.iv_rank).setVisibility(View.VISIBLE);
            helper.getView(R.id.tv_rank).setVisibility(View.GONE);
        }else if(position == 2){
            helper.setImageResource(R.id.iv_rank,R.mipmap.rside_img_sn3);
            helper.getView(R.id.iv_rank).setVisibility(View.VISIBLE);
            helper.getView(R.id.tv_rank).setVisibility(View.GONE);
        }else {
            helper.setImageResource(R.id.iv_rank,R.mipmap.rside_img_sn1);
            helper.getView(R.id.iv_rank).setVisibility(View.GONE);
            helper.getView(R.id.tv_rank).setVisibility(View.VISIBLE);
        }

    }
}
