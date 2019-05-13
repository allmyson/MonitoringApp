package com.ys.zy.activity;

import android.view.View;
import android.widget.ListView;

import com.google.gson.Gson;
import com.yanzhenjie.nohttp.rest.Response;
import com.ys.zy.R;
import com.ys.zy.adapter.SubDealHistoryAdapter;
import com.ys.zy.base.BaseActivity;
import com.ys.zy.bean.SubJYJL;
import com.ys.zy.http.HttpListener;
import com.ys.zy.sp.UserSP;
import com.ys.zy.util.HttpUtil;
import com.ys.zy.util.YS;

import java.util.ArrayList;
import java.util.List;

public class SubDealHistoryActivity extends BaseActivity {

    private ListView lv;
    private List<SubJYJL.DataBeanX.DataBean> list;
    private SubDealHistoryAdapter adapter;
    private String userId;

    @Override
    public int getLayoutId() {
        return R.layout.activity_sub_deal_history;
    }

    @Override
    public void initView() {
        setBarColor("#ededed");
        titleView.setText("下级交易记录");
        lv = getView(R.id.lv_);
        list = new ArrayList<>();
        adapter = new SubDealHistoryAdapter(mContext, list, R.layout.item_sub_manage);
        lv.setAdapter(adapter);
        userId = UserSP.getUserId(mContext);
    }

    @Override
    public void getData() {
        HttpUtil.getSubJYJL(mContext, userId, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                list.clear();
                SubJYJL subJYJL = new Gson().fromJson(response.get(), SubJYJL.class);
                if (subJYJL != null) {
                    if (YS.SUCCESE.equals(subJYJL.code) && subJYJL.data != null && subJYJL.data.data != null && subJYJL.data.data.size() > 0) {
                        list.addAll(subJYJL.data.data);
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

    }
}
