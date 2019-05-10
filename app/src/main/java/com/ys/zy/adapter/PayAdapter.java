package com.ys.zy.adapter;

import android.content.Context;
import android.view.View;

import com.ys.zy.R;
import com.ys.zy.bean.PayBean;
import com.ys.zy.bean.RechargePlatform;
import com.ys.zy.util.StringUtil;

import java.util.List;

public class PayAdapter extends CommonAdapter<RechargePlatform.DataBean> {
    public PayAdapter(Context context, List<RechargePlatform.DataBean> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, RechargePlatform.DataBean item, int position) {
        if ("WX".equals(item.accountTypeCode)) {
            helper.setImageResource(R.id.iv_, R.mipmap.recharge_icon_wx);
            helper.setText(R.id.tv_description, "单笔最低10元，最高20000元");
        } else if ("ZFB".equals(item.accountTypeCode)) {
            helper.setImageResource(R.id.iv_, R.mipmap.recharge_icon_z);
            helper.setText(R.id.tv_description, "单笔最低10元，最高50000元");
        } else if ("UNION_WALLET".equals(item.accountTypeCode)) {
            helper.setImageResource(R.id.iv_, R.mipmap.recharge_icon_yl);
            helper.setText(R.id.tv_description, "单笔最低10元，最高500000元");
        } else {
            helper.setImageResource(R.id.iv_, R.mipmap.platform_default);
            helper.setText(R.id.tv_description, "");
        }
        helper.getView(R.id.tv_description).setVisibility(View.GONE);
        helper.setText(R.id.tv_name, StringUtil.valueOf(item.accountTypeName));
    }
}
