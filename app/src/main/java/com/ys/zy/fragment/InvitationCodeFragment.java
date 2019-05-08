package com.ys.zy.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.yanzhenjie.nohttp.rest.Response;
import com.ys.zy.R;
import com.ys.zy.adapter.InvitationCodeAdapter;
import com.ys.zy.base.BaseFragment;
import com.ys.zy.bean.YqmBean;
import com.ys.zy.http.HttpListener;
import com.ys.zy.sp.UserSP;
import com.ys.zy.util.HttpUtil;
import com.ys.zy.util.YS;

import java.util.ArrayList;
import java.util.List;

public class InvitationCodeFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    private ListView lv;
    private InvitationCodeAdapter adapter;
    private List<YqmBean.DataBeanX.DataBean> list;
    private String userId;

    public static InvitationCodeFragment newInstance() {
        return new InvitationCodeFragment();
    }

    @Override
    protected void init() {
        lv = getView(R.id.lv_);
        list = new ArrayList<>();
        adapter = new InvitationCodeAdapter(mContext, list, R.layout.item_invitation_code);
        lv.setAdapter(adapter);
        userId = UserSP.getUserId(mContext);
    }

    @Override
    protected void getData() {
        HttpUtil.getYqmList(mContext, userId, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                list.clear();
                YqmBean yqmBean = new Gson().fromJson(response.get(), YqmBean.class);
                if (yqmBean != null) {
                    if (yqmBean.data != null && yqmBean.data.data != null && yqmBean.data.data.size() > 0) {
                        list.addAll(yqmBean.data.data);
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
    protected int getLayoutResource() {
        return R.layout.fragment_nvitation_code;
    }

    @Override
    public void onRefresh() {
        getData();
    }
}
