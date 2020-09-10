package com.ys.monitor.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.AdapterView;

import com.yongchun.library.view.ImageSelectorActivity;
import com.ys.monitor.R;
import com.ys.monitor.adapter.GridAdapter;
import com.ys.monitor.api.FunctionApi;
import com.ys.monitor.base.BaseActivity;
import com.ys.monitor.ui.MyGridView;

import java.util.ArrayList;

public class TaskDetailActivity extends BaseActivity {
    public static final int REQUEST_DELETE_CODE = 1001;
    private MyGridView myGridView;
    private GridAdapter mAdapter;
    private ArrayList<String> list;

    @Override
    public int getLayoutId() {
        return R.layout.activity_task_detail;
    }

    @Override
    public void initView() {
        list = new ArrayList<>();
        setBarColor("#ffffff");
        titleView.setText("任务详情");
        myGridView = getView(R.id.gv_image);
        myGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        mAdapter = new GridAdapter(mContext, list, R.layout.item_grid_image);
        myGridView.setAdapter(mAdapter);
        myGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == list.size()) {
                    FunctionApi.takePicture(mContext, 9 - list.size(), 1, true, false, false);
                } else {
                    PhotoActivity.intentToPhotoActivity(mActivity, REQUEST_DELETE_CODE, list,
                            position);
                }
            }
        });
    }

    @Override
    public void getData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case ImageSelectorActivity.REQUEST_IMAGE://相册图片选取返回
                    ArrayList<String> images =
                            (ArrayList<String>) data.getSerializableExtra(ImageSelectorActivity
                                    .REQUEST_OUTPUT);
                    if (images != null && images.size() > 0) {
                        list.addAll(images);
                    }
                    mAdapter.refresh(list);

//                    headIV.setImageResource(R.mipmap.about_logo_icon);
//                    Glide.with(mContext).load("https://ss2.bdstatic
//                    .com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1908239510,917370588&fm=26&gp=0.jpg")
//                    .into(headIV);
                    break;
                case REQUEST_DELETE_CODE:
                    int deletePosition = data.getIntExtra(PhotoActivity.KEY_DELETE_POSITION, 0);
                    list.remove(deletePosition);
                    mAdapter.refresh(list);
                    break;
            }
        }
    }
}
