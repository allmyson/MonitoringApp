package com.ys.monitor.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yongchun.library.view.ImageSelectorActivity;
import com.ys.monitor.R;
import com.ys.monitor.api.FunctionApi;
import com.ys.monitor.base.BaseActivity;
import com.ys.monitor.bean.LoginBean;
import com.ys.monitor.sp.UserSP;
import com.ys.monitor.util.L;
import com.ys.monitor.util.StringUtil;
import com.ys.monitor.util.YS;

import java.util.ArrayList;

public class UserInfoActivity extends BaseActivity {
    private LinearLayout headLL;
    private ImageView headIV;
    private String photoUrl;
    private TextView usernameTV, phoneTV, orgTV;
    private String userId;
    private LoginBean user;

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_info;
    }

    @Override
    public void initView() {
        setBarColor("#FFFFFF");
        titleView.setText("个人信息");
        usernameTV = getView(R.id.tv_name);
        phoneTV = getView(R.id.tv_phone);
        orgTV = getView(R.id.tv_org);
        headIV = getView(R.id.iv_head);
        headLL = getView(R.id.ll_head);
        headLL.setOnClickListener(this);
        userId = UserSP.getUserId(mContext);
        user = UserSP.getLoginBean(mContext);
    }

    @Override
    public void getData() {
        if (user != null && user.data != null) {
            usernameTV.setText(StringUtil.valueOf(user.data.trueName));
            phoneTV.setText(StringUtil.valueOf(user.data.mobilePhoneNumber));
            orgTV.setText(StringUtil.valueOf(user.data.deptName));
            if (!StringUtil.isBlank(user.data.icon)) {
//                Glide.with(mContext).load(FunctionApi.getImagePath(user.data.icon)).into(headIV);
                YS.showRoundImage(mContext,FunctionApi.getImagePath(user.data.icon),headIV);
            }else {
//                YS.showRoundImage(mContext,YS.testImageUrl,headIV);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        HttpUtil.getUserInfoById(mContext, userId, new HttpListener<String>() {
//            @Override
//            public void onSucceed(int what, Response<String> response) {
//                User user = new Gson().fromJson(response.get(), User.class);
//                if (user != null && YS.SUCCESE.equals(user.code) && user.data != null) {
//                    nickTV.setText(StringUtil.valueOf(user.data.consumerName));
//                    usernameTV.setText(StringUtil.valueOf(user.data.loginName));
//                    typeTV.setText(StringUtil.valueOf(user.data.levelName));
//                    fdTV.setText(StringUtil.StringToDoubleStr(user.data.backNum));
//                    if (!StringUtil.isBlank(user.data.consumerImg)) {
//                        Glide.with(mContext).load(FunctionApi.getImagePath(user.data.consumerImg)).into(headIV);
//                    }
//                    UserSP.saveUser(mContext, response.get());
//                }
//            }
//
//            @Override
//            public void onFailed(int what, Response<String> response) {
//            }
//        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_head:
                FunctionApi.takePicture(mContext, 1, 2, true, true, true);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case ImageSelectorActivity.REQUEST_IMAGE://相册图片选取返回
                    ArrayList<String> images = (ArrayList<String>) data.getSerializableExtra(ImageSelectorActivity
                            .REQUEST_OUTPUT);
                    photoUrl = images.get(0);
                    L.e("photoUrl=" + photoUrl);
                    updatePhoto();
//                    headIV.setImageResource(R.mipmap.about_logo_icon);
//                    Glide.with(mContext).load("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1908239510,917370588&fm=26&gp=0.jpg").into(headIV);
                    break;
            }
        }
    }

    private void updatePhoto() {
//        HttpUtil.updateUserInfo(mContext, userId, nickTV.getText().toString().trim(), photoUrl, new HttpListener<String>() {
//            @Override
//            public void onSucceed(int what, Response<String> response) {
//                BaseBean baseBean = new Gson().fromJson(response.get(), BaseBean.class);
//                if (baseBean != null) {
//                    if (YS.SUCCESE.equals(baseBean.code)) {
//                        Glide.with(mContext).load(photoUrl).into(headIV);
//                    }
//                    show(StringUtil.valueOf(baseBean.msg));
//                } else {
//                    show(YS.HTTP_TIP);
//                }
//            }
//
//            @Override
//            public void onFailed(int what, Response<String> response) {
//
//            }
//        });
    }
}
