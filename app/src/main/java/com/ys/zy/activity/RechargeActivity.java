package com.ys.zy.activity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.yanzhenjie.nohttp.rest.Response;
import com.ys.zy.R;
import com.ys.zy.adapter.PayAdapter;
import com.ys.zy.base.BaseActivity;
import com.ys.zy.bean.PayBean;
import com.ys.zy.bean.RechargePlatform;
import com.ys.zy.dialog.CzFragment;
import com.ys.zy.dialog.DialogUtil;
import com.ys.zy.http.HttpListener;
import com.ys.zy.sp.UserSP;
import com.ys.zy.util.HttpUtil;
import com.ys.zy.util.StringUtil;
import com.ys.zy.util.YS;

import java.util.ArrayList;
import java.util.List;

//充值
public class RechargeActivity extends BaseActivity {
    private ListView lv;
    private List<RechargePlatform.DataBean> list;
    private PayAdapter payAdapter;
    private String userId;
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
//        list.addAll(PayBean.getList());
        payAdapter = new PayAdapter(mContext, list, R.layout.item_recharge);
        lv.setAdapter(payAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String accountId = payAdapter.getItem(position).accountId;
                DialogUtil.showCZ(mContext, new CzFragment.ClickListener() {
                    @Override
                    public void sure(String money) {
                        DialogUtil.removeDialog(mContext);
                        cz(money,accountId);
                    }
                });
            }
        });
        userId = UserSP.getUserId(mContext);
    }

    private void cz(String money,String accountId) {
        HttpUtil.cz(mContext, userId, StringUtil.StringToDoubleStr(money), accountId, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {

            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }
        });
    }

    @Override
    public void getData() {
        HttpUtil.getCZList(mContext, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                list.clear();
                RechargePlatform rechargePlatform = new Gson().fromJson(response.get(), RechargePlatform.class);
                if (rechargePlatform != null && YS.SUCCESE.equals(rechargePlatform.code) && rechargePlatform.data != null && rechargePlatform.data.size() > 0) {
                    list.addAll(rechargePlatform.data);
                }
                payAdapter.refresh(list);
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}
