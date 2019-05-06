package com.ys.zy.fragment;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.yanzhenjie.nohttp.rest.Response;
import com.ys.zy.R;
import com.ys.zy.activity.AboutActivity;
import com.ys.zy.activity.MyFormActivity;
import com.ys.zy.activity.ProxyCenterActivity;
import com.ys.zy.activity.RechargeActivity;
import com.ys.zy.activity.SafeActivity;
import com.ys.zy.activity.TXActivity;
import com.ys.zy.activity.UserInfoActivity;
import com.ys.zy.adapter.MyAdapter;
import com.ys.zy.api.FunctionApi;
import com.ys.zy.base.BaseFragment;
import com.ys.zy.bean.FourBean;
import com.ys.zy.http.HttpListener;
import com.ys.zy.sp.User;
import com.ys.zy.sp.UserSP;
import com.ys.zy.ui.MyListView;
import com.ys.zy.util.HttpUtil;
import com.ys.zy.util.StringUtil;
import com.ys.zy.util.YS;

import java.util.ArrayList;
import java.util.List;


/**
 * @author lh
 * @version 1.0.0
 * @filename OneFragment
 * @description -------------------------------------------------------
 * @date 2018/10/23 17:09
 */
public class FourFragment extends BaseFragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    private MyListView mlv;
    private List<FourBean> myList;
    private MyAdapter myAdapter;
    private ImageView headIV;
    private SwipeRefreshLayout srl;
    private RelativeLayout rechargeRL, txRL;
    private String userId;
    private TextView nickNameTV, userNameTV, yueTV;
    private User user;

    public static FourFragment newInstance() {
        return new FourFragment();
    }

    @Override
    public void onRefresh() {
        getData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_recharge:
                startActivity(new Intent(mContext, RechargeActivity.class));
                break;
            case R.id.rl_tx:
                startActivity(new Intent(mContext, TXActivity.class));
                break;
        }
    }

    @Override
    protected void init() {
        headIV = getView(R.id.iv_head);
        nickNameTV = getView(R.id.tv_nickName);
        userNameTV = getView(R.id.tv_userName);
        yueTV = getView(R.id.tv_yue);
        mlv = getView(R.id.mlv_my);
        myList = new ArrayList<>();
        myList.addAll(FourBean.getDefaultList());
        myAdapter = new MyAdapter(mContext, myList, R.layout.item_my);
        mlv.setAdapter(myAdapter);
        mlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(mContext, UserInfoActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(mContext, SafeActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(mContext, ProxyCenterActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(mContext, MyFormActivity.class));
                        break;
                    case 4:
                        startActivity(new Intent(mContext, AboutActivity.class));
                        break;
                }
            }
        });
        srl = (SwipeRefreshLayout) mView.findViewById(R.id.srl);
        srl.setOnRefreshListener(this);
        srl.setColorSchemeColors(getResources().getColor(R.color.main_color));
        rechargeRL = getView(R.id.rl_recharge);
        txRL = getView(R.id.rl_tx);
        rechargeRL.setOnClickListener(this);
        txRL.setOnClickListener(this);
        userId = UserSP.getUserId(mContext);
        user = UserSP.getUserInfo(mContext);
    }

    @Override
    protected void getData() {
        if (user != null && user.data != null) {
            nickNameTV.setText(StringUtil.valueOf(user.data.consumerName));
            userNameTV.setText("账号:" + StringUtil.valueOf(user.data.loginName));
            yueTV.setText(StringUtil.StringToDoubleStr(user.data.balance));
            Glide.with(mContext).load(FunctionApi.getImagePath(user.data.consumerImg)).placeholder(R.mipmap.bg_default_head2).error(R.mipmap.bg_default_head2).into(headIV);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        HttpUtil.getUserInfoById(mContext, userId, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                User user = new Gson().fromJson(response.get(), User.class);
                if (user != null && YS.SUCCESE.equals(user.code) && user.data != null) {
                    nickNameTV.setText(StringUtil.valueOf(user.data.consumerName));
                    userNameTV.setText("账号:" + StringUtil.valueOf(user.data.loginName));
                    yueTV.setText(StringUtil.StringToDoubleStr(user.data.balance));
                    Glide.with(mContext).load(FunctionApi.getImagePath(user.data.consumerImg)).placeholder(R.mipmap.bg_default_head2).error(R.mipmap.bg_default_head2).into(headIV);
                    UserSP.saveUser(mContext, response.get());
                }
                srl.setRefreshing(false);
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                srl.setRefreshing(false);
            }
        });
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_four;
    }

}
