package com.ys.zy.activity;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.ys.zy.R;
import com.ys.zy.adapter.BankCardAdapter;
import com.ys.zy.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class BankCardActivity extends BaseActivity {
    private ListView lv;
    private List<String> list;
    private BankCardAdapter adapter;
    private LinearLayout addBankCardLL;

    @Override
    public int getLayoutId() {
        return R.layout.activity_bank_card;
    }

    @Override
    public void initView() {
        setBarColor("#ededed");
        titleView.setText("银行卡管理");
        lv = getView(R.id.lv_);
        list = new ArrayList<>();
        list.add(null);
        list.add(null);
        list.add(null);
        adapter = new BankCardAdapter(mContext, list, R.layout.item_bank);
        lv.setAdapter(adapter);
        addBankCardLL = getView(R.id.ll_addBankCard);
        addBankCardLL.setOnClickListener(this);
    }

    @Override
    public void getData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_addBankCard:
                startActivity(new Intent(mContext, AddBankCardActivity.class));
                break;
        }
    }
}
