package com.ys.zy.activity;

import android.view.View;
import android.widget.ListView;

import com.google.gson.Gson;
import com.yanzhenjie.nohttp.rest.Response;
import com.ys.zy.R;
import com.ys.zy.adapter.SubManageAdapter;
import com.ys.zy.base.BaseActivity;
import com.ys.zy.bean.SubUserBean;
import com.ys.zy.http.HttpListener;
import com.ys.zy.sp.UserSP;
import com.ys.zy.util.HttpUtil;
import com.ys.zy.util.YS;

import java.util.ArrayList;
import java.util.List;

//下级管理
public class SubManageActivity extends BaseActivity {
    private ListView lv;
    private List<SubUserBean.DataBeanX.DataBean> list;
    private SubManageAdapter adapter;
    private String userId;

    @Override
    public int getLayoutId() {
        return R.layout.activity_sub_manage;
    }

    @Override
    public void initView() {
        setBarColor("#ededed");
        titleView.setText("下级管理");
        lv = getView(R.id.lv_);
        list = new ArrayList<>();
        adapter = new SubManageAdapter(mContext, list, R.layout.item_sub_manage);
        lv.setAdapter(adapter);
        userId = UserSP.getUserId(mContext);
    }

    @Override
    public void getData() {
        HttpUtil.getSubUserList(mContext, userId, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                list.clear();
                SubUserBean subUserBean = new Gson().fromJson(response.get(), SubUserBean.class);
                if (subUserBean != null) {
                    if (subUserBean.data != null && subUserBean.data.data != null && subUserBean.data.data.size() > 0) {
                        list.addAll(subUserBean.data.data);
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
