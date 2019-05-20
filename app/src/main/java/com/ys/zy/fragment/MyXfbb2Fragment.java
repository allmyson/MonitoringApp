package com.ys.zy.fragment;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.yanzhenjie.nohttp.rest.Response;
import com.ys.zy.R;
import com.ys.zy.adapter.MyXfjlAdapter;
import com.ys.zy.base.BaseFragment;
import com.ys.zy.bean.MyXfjlBean;
import com.ys.zy.http.HttpListener;
import com.ys.zy.sp.UserSP;
import com.ys.zy.ui.BlankView;
import com.ys.zy.ui.NoNetView;
import com.ys.zy.util.HttpUtil;
import com.ys.zy.util.NetWorkUtil;
import com.ys.zy.util.StringUtil;
import com.ys.zy.util.YS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyXfbb2Fragment extends BaseFragment implements NoNetView.ClickListener {
    private ListView lv;
    private List<MyXfjlBean.DataBeanX.DataBean> list;
    private MyXfjlAdapter adapter;
    private String userId;
    private NoNetView noNetView;
    private BlankView blankView;
    private LinearLayout dataLL;

    public static Fragment newInstance() {
        return new MyXfbb2Fragment();
    }

    @Override
    protected void init() {
        dataLL = getView(R.id.ll_data);
        noNetView = getView(R.id.nnv_);
        blankView = getView(R.id.bv_);
        blankView.setImage(R.mipmap.blank_inf_img).setText("暂无记录");
        noNetView.setClickListener(this);
        lv = getView(R.id.lv_);
        list = new ArrayList<>();
        adapter = new MyXfjlAdapter(mContext, list, R.layout.item_xfbb);
        lv.setAdapter(adapter);
        userId = UserSP.getUserId(mContext);
    }

    @Override
    public void getData() {
        if (NetWorkUtil.isNetworkAvailable(mContext)) {
            noNetView.setVisibility(View.GONE);
            dataLL.setVisibility(View.VISIBLE);
            HttpUtil.getMyXFJL(mContext, userId, new HttpListener<String>() {
                @Override
                public void onSucceed(int what, Response<String> response) {
                    list.clear();
                    MyXfjlBean xfjlBean = new Gson().fromJson(response.get(), MyXfjlBean.class);
                    if (xfjlBean != null && YS.SUCCESE.equals(xfjlBean.code) && xfjlBean.data != null && xfjlBean.data.data != null && xfjlBean.data.data.size() > 0) {
                        list.addAll(xfjlBean.data.data);
                    }
                    adapter.refresh(list);
                    if (adapter.getCount() > 0) {
                        blankView.setVisibility(View.GONE);
                    } else {
                        blankView.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onFailed(int what, Response<String> response) {
                    blankView.setVisibility(View.GONE);
                }
            });
        } else {
            noNetView.setVisibility(View.VISIBLE);
            blankView.setVisibility(View.GONE);
            dataLL.setVisibility(View.GONE);
        }
    }


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_my_xfbb;
    }

    @Override
    public void reload() {
        getData();
    }
}
