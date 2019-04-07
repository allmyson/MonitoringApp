package com.ys.zy.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yongchun.library.view.ImageSelectorActivity;
import com.ys.zy.R;
import com.ys.zy.api.FunctionApi;
import com.ys.zy.base.BaseActivity;
import com.ys.zy.ui.TitleView;

import java.util.ArrayList;

public class UserInfoActivity extends BaseActivity {
    private LinearLayout headLL, nickLL;
    private ImageView headIV;
    private String photoUrl;
    private TextView nickTV;
    @Override
    public int getLayoutId() {
        return R.layout.activity_user_info;
    }

    @Override
    public void initView() {
        setBarColor("#ededed");
        titleView.setText("个人信息");
        nickTV = getView(R.id.tv_nick);
        headIV = getView(R.id.iv_head);
        headLL = getView(R.id.ll_head);
        nickLL = getView(R.id.ll_nick);
        headLL.setOnClickListener(this);
        nickLL.setOnClickListener(this);
    }

    @Override
    public void getData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_head:
                FunctionApi.takePicture(mContext,1,2,true,true,true);
                break;
            case R.id.ll_nick:
                startActivity(new Intent(mContext,SetNameActivity.class).putExtra("name",nickTV.getText().toString()));
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
                    Glide.with(mContext).load(photoUrl).placeholder(R.mipmap.bg_default_head2).error(R.mipmap
                            .bg_default_head2).into(headIV);
                    break;
            }
        }
    }
}
