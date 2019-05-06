package com.ys.zy.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.yanzhenjie.nohttp.rest.Response;
import com.yongchun.library.view.ImageSelectorActivity;
import com.ys.zy.R;
import com.ys.zy.api.FunctionApi;
import com.ys.zy.base.BaseActivity;
import com.ys.zy.bean.BaseBean;
import com.ys.zy.http.HttpListener;
import com.ys.zy.sp.User;
import com.ys.zy.sp.UserSP;
import com.ys.zy.ui.TitleView;
import com.ys.zy.util.HttpUtil;
import com.ys.zy.util.StringUtil;
import com.ys.zy.util.YS;

import java.util.ArrayList;

public class UserInfoActivity extends BaseActivity {
    private LinearLayout headLL, nickLL;
    private ImageView headIV;
    private String photoUrl;
    private TextView nickTV, usernameTV, typeTV, fdTV;
    private String userId;
    private User user;

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_info;
    }

    @Override
    public void initView() {
        setBarColor("#ededed");
        titleView.setText("个人信息");
        nickTV = getView(R.id.tv_nick);
        usernameTV = getView(R.id.tv_name);
        typeTV = getView(R.id.tv_type);
        fdTV = getView(R.id.tv_fd);
        headIV = getView(R.id.iv_head);
        headLL = getView(R.id.ll_head);
        nickLL = getView(R.id.ll_nick);
        headLL.setOnClickListener(this);
        nickLL.setOnClickListener(this);
        userId = UserSP.getUserId(mContext);
        user = UserSP.getUserInfo(mContext);
    }

    @Override
    public void getData() {
        if (user != null && user.data != null) {
            nickTV.setText(StringUtil.valueOf(user.data.consumerName));
            usernameTV.setText(StringUtil.valueOf(user.data.loginName));
            typeTV.setText(StringUtil.valueOf(user.data.levelName));
            fdTV.setText(StringUtil.StringToDoubleStr(user.data.backNum));
            Glide.with(mContext).load(FunctionApi.getImagePath(user.data.consumerImg)).placeholder(R.mipmap.bg_default_head2).error(R.mipmap.bg_default_head2).into(headIV);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        HttpUtil.getUserInfoById(mContext, userId, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                User user = new Gson().fromJson(response.get(), User.class);
                if (user != null && YS.SUCCESE.equals(user.code) && user.data != null) {
                    nickTV.setText(StringUtil.valueOf(user.data.consumerName));
                    usernameTV.setText(StringUtil.valueOf(user.data.loginName));
                    typeTV.setText(StringUtil.valueOf(user.data.levelName));
                    fdTV.setText(StringUtil.StringToDoubleStr(user.data.backNum));
                    Glide.with(mContext).load(FunctionApi.getImagePath(user.data.consumerImg)).placeholder(R.mipmap.bg_default_head2).error(R.mipmap.bg_default_head2).into(headIV);
                    UserSP.saveUser(mContext, response.get());
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_head:
                FunctionApi.takePicture(mContext, 1, 2, true, true, true);
                break;
            case R.id.ll_nick:
                startActivity(new Intent(mContext, SetNameActivity.class).putExtra("name", nickTV.getText().toString()));
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
                    updatePhoto();
                    break;
            }
        }
    }

    private void updatePhoto() {
        HttpUtil.updateUserInfo(mContext, userId, nickTV.getText().toString().trim(), photoUrl, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                BaseBean baseBean = new Gson().fromJson(response.get(), BaseBean.class);
                if (baseBean != null) {
                    if (YS.SUCCESE.equals(baseBean.code)) {
                        Glide.with(mContext).load(photoUrl).placeholder(R.mipmap.bg_default_head2).error(R.mipmap
                                .bg_default_head2).into(headIV);
                    }
                    show(StringUtil.valueOf(baseBean.msg));
                } else {
                    show(YS.HTTP_TIP);
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }
        });
    }
}
