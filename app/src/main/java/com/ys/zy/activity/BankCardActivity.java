package com.ys.zy.activity;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.yanzhenjie.nohttp.rest.Response;
import com.ys.zy.R;
import com.ys.zy.adapter.BankCardAdapter;
import com.ys.zy.base.BaseActivity;
import com.ys.zy.bean.Bank;
import com.ys.zy.bean.BankData;
import com.ys.zy.http.HttpListener;
import com.ys.zy.sp.UserSP;
import com.ys.zy.ui.MyListView;
import com.ys.zy.util.HttpUtil;
import com.ys.zy.util.YS;

import java.util.ArrayList;
import java.util.List;

public class BankCardActivity extends BaseActivity {
    private MyListView lv;
    private List<BankData.DataBean> list;
    private BankCardAdapter adapter;
    private LinearLayout addBankCardLL;
    private String userId;

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
        adapter = new BankCardAdapter(mContext, list, R.layout.item_bank);
        lv.setAdapter(adapter);
        addBankCardLL = getView(R.id.ll_addBankCard);
        addBankCardLL.setOnClickListener(this);
        userId = UserSP.getUserId(mContext);
    }

    @Override
    public void getData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        HttpUtil.getBankList(mContext, userId, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                list.clear();
                BankData bankData = new Gson().fromJson(response.get(), BankData.class);
                if (bankData != null) {
                    if (YS.SUCCESE.equals(bankData.code) && bankData.data != null && bankData.data.size() > 0) {
                        list.addAll(bankData.data);
                    }
                } else {
                    show(YS.HTTP_TIP);
                }
                adapter.refresh(list);
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }
        });
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
