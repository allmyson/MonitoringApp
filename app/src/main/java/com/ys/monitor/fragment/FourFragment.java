package com.ys.monitor.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.huamai.poc.PocEngineFactory;
import com.ys.monitor.R;
import com.ys.monitor.activity.AboutActivity;
import com.ys.monitor.activity.KefuActivity;
import com.ys.monitor.activity.LoginActivity;
import com.ys.monitor.activity.RtspVideoActivity;
import com.ys.monitor.activity.SetActivity;
import com.ys.monitor.activity.UserInfoActivity;
import com.ys.monitor.base.BaseFragment;
import com.ys.monitor.bean.LoginBean;
import com.ys.monitor.sp.UserSP;
import com.ys.monitor.util.ActivityUtil;
import com.ys.monitor.util.StringUtil;
import com.ys.monitor.util.YS;


/**
 * @author lh
 * @version 1.0.0
 * @filename OneFragment
 * @description -------------------------------------------------------
 * @date 2018/10/23 17:09
 */
public class FourFragment extends BaseFragment implements View.OnClickListener,
        SwipeRefreshLayout.OnRefreshListener {
    private ImageView headIV;
    private SwipeRefreshLayout srl;
    private String userId;
    private TextView userNameTV, orgTV;
    private LoginBean loginBean;

    public static FourFragment newInstance() {
        return new FourFragment();
    }

    @Override
    public void onRefresh() {
        getPhoto();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_info:
                startActivity(new Intent(mContext, UserInfoActivity.class));
                break;
            case R.id.ll_kefu:
                startActivity(new Intent(mContext, KefuActivity.class));
                break;
            case R.id.ll_about:
                startActivity(new Intent(mContext, AboutActivity.class));
                break;
            case R.id.ll_set:
                startActivity(new Intent(mContext, SetActivity.class));
                break;
            case R.id.tv_exit:
//                exit();
                RtspVideoActivity.playRtspVideo(mContext,"rtsp://wowzaec2demo.streamlock.net/vod/mp4:BigBuckBunny_115k.mov");
                break;
        }
    }

    @Override
    protected void init() {
        headIV = getView(R.id.iv_photo);
        userNameTV = getView(R.id.tv_name);
        orgTV = getView(R.id.tv_org);
        srl = (SwipeRefreshLayout) mView.findViewById(R.id.srl);
        srl.setOnRefreshListener(this);
        srl.setColorSchemeColors(getResources().getColor(R.color.main_color));
        userId = UserSP.getUserId(mContext);
        loginBean = UserSP.getLoginBean(mContext);
        getView(R.id.ll_info).setOnClickListener(this);
        getView(R.id.ll_kefu).setOnClickListener(this);
        getView(R.id.ll_about).setOnClickListener(this);
        getView(R.id.ll_set).setOnClickListener(this);
        getView(R.id.tv_exit).setOnClickListener(this);
    }

    @Override
    protected void getData() {
        if (loginBean != null && loginBean.data != null) {
            userNameTV.setText(StringUtil.valueOf(loginBean.data.trueName));
            orgTV.setText(StringUtil.valueOf(loginBean.data.dutyName));
            if (!StringUtil.isBlank(loginBean.data.icon)) {
                YS.showRoundImage(mContext, loginBean.data.icon, headIV);
//                Glide.with(mContext).load(FunctionApi.getImagePath(loginBean.data.icon)).into
//                (headIV);
            } else {
//                YS.showRoundImage(mContext, YS.testImageUrl, headIV);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getPhoto();
    }

    private void getPhoto() {
//        HttpUtil.getUserInfoById(mContext, userId, new HttpListener<String>() {
//            @Override
//            public void onSucceed(int what, Response<String> response) {
//                User user = new Gson().fromJson(response.get(), User.class);
//                if (user != null && YS.SUCCESE.equals(user.code) && user.data != null) {
//                    nickNameTV.setText(StringUtil.valueOf(user.data.consumerName));
//                    userNameTV.setText("账号:" + StringUtil.valueOf(user.data.loginName));
//                    yueTV.setText(StringUtil.StringToDoubleStr(user.data.balance));
//                    if (!StringUtil.isBlank(user.data.consumerImg)) {
//                        Glide.with(mContext).load(FunctionApi.getImagePath(user.data
//                        .consumerImg)).into(headIV);
//                    }
//                    UserSP.saveUser(mContext, response.get());
//                }
//                srl.setRefreshing(false);
//            }
//
//            @Override
//            public void onFailed(int what, Response<String> response) {
//                srl.setRefreshing(false);
//            }
//        });
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_four;
    }


    private void exit() {
        UserSP.clear(mContext);
        ActivityUtil.finish();
        PocEngineFactory.get().logout();
        startActivity(new Intent(mContext, LoginActivity.class));
    }
}
