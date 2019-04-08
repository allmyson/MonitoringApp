package com.ys.zy.activity;

import android.view.View;
import android.widget.ListView;

import com.ys.zy.R;
import com.ys.zy.adapter.PayAdapter;
import com.ys.zy.base.BaseActivity;
import com.ys.zy.bean.PayBean;

import java.util.ArrayList;
import java.util.List;

//充值
public class RechargeActivity extends BaseActivity {
    private ListView lv;
    private List<PayBean> list;
    private PayAdapter payAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_recharge;
    }

    @Override
    public void initView() {
        setBarColor("#ededed");
        titleView.setText("充值");
        lv = getView(R.id.lv_);
        list = new ArrayList<>();
        list.addAll(PayBean.getList());
        payAdapter = new PayAdapter(mContext, list, R.layout.item_recharge);
        lv.setAdapter(payAdapter);
    }

    @Override
    public void getData() {

    }

    @Override
    public void onClick(View v) {

    }
}
