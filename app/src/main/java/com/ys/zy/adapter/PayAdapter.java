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
        } else if ("ZFB".equals(item.accountTypeCode)) {
            helper.setImageResource(R.id.iv_, R.mipmap.recharge_icon_z);
        } else if ("UNION_WALLET".equals(item.accountTypeCode)) {
            helper.setImageResource(R.id.iv_, R.mipmap.recharge_icon_yl);
        } else {
            helper.setImageResource(R.id.iv_, R.mipmap.platform_default);
        }
        helper.setText(R.id.tv_description, StringUtil.valueOf(item.remark));
        helper.setText(R.id.tv_name, StringUtil.valueOf(item.accountTypeName));
        if (StringUtil.isBlank(item.remark)) {
            helper.getView(R.id.tv_description).setVisibility(View.GONE);
        } else {
            helper.getView(R.id.tv_description).setVisibility(View.VISIBLE);
        }
    }
}
