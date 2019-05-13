package com.ys.zy.adapter;

import android.content.Context;

import com.ys.zy.R;
import com.ys.zy.bean.TeamBB;
import com.ys.zy.util.StringUtil;

import java.util.List;

public class TeamReportAdapter extends CommonAdapter<TeamBB.DataBeanX.DataBean> {
    public TeamReportAdapter(Context context, List<TeamBB.DataBeanX.DataBean> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, TeamBB.DataBeanX.DataBean item, int position) {
        helper.setText(R.id.tv_name, StringUtil.valueOf(item.login_name));
        helper.setText(R.id.tv_tzNum, StringUtil.StringToDoubleStr(item.bets_money));
        helper.setText(R.id.tv_backNum, StringUtil.StringToDoubleStr(item.back_moeny));
    }
}
