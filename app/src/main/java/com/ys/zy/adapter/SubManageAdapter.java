package com.ys.zy.adapter;

import android.content.Context;

import com.ys.zy.R;
import com.ys.zy.bean.SubUserBean;
import com.ys.zy.util.DateUtil;
import com.ys.zy.util.StringUtil;

import java.util.List;

public class SubManageAdapter extends CommonAdapter<SubUserBean.DataBeanX.DataBean>{
    public SubManageAdapter(Context context, List<SubUserBean.DataBeanX.DataBean> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, SubUserBean.DataBeanX.DataBean item, int position) {
        helper.setText(R.id.tv_name, StringUtil.valueOf(item.login_name));
        helper.setText(R.id.tv_odds, StringUtil.valueOf(item.back_num));
        if(StringUtil.isBlank(item.online_time)){
            helper.setText(R.id.tv_time,"");
        }else {
            helper.setText(R.id.tv_time, DateUtil.changeTimeToYMD(item.online_time));
        }
    }
}
