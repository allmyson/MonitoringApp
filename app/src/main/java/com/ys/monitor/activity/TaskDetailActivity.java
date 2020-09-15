package com.ys.monitor.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yanzhenjie.nohttp.rest.Response;
import com.yongchun.library.view.ImageSelectorActivity;
import com.ys.monitor.R;
import com.ys.monitor.adapter.GridAdapter;
import com.ys.monitor.api.FunctionApi;
import com.ys.monitor.base.BaseActivity;
import com.ys.monitor.bean.BaseBean;
import com.ys.monitor.bean.TaskBean;
import com.ys.monitor.fragment.TaskFragment;
import com.ys.monitor.http.HttpListener;
import com.ys.monitor.sp.UserSP;
import com.ys.monitor.ui.MyGridView;
import com.ys.monitor.util.HttpUtil;
import com.ys.monitor.util.StringUtil;
import com.ys.monitor.util.YS;

import java.util.ArrayList;

public class TaskDetailActivity extends BaseActivity {
    public static final int REQUEST_DELETE_CODE = 1001;
    private MyGridView myGridView;
    private GridAdapter mAdapter;
    private ArrayList<String> list;
    private EditText contentET;
    private TextView commitTV;
    private TaskBean.DataBean.RowsBean rowsBean;
    private String userId;

    @Override
    public int getLayoutId() {
        return R.layout.activity_task_detail;
    }

    @Override
    public void initView() {
        contentET = getView(R.id.et_content);
        commitTV = getView(R.id.tv_commit);
        commitTV.setOnClickListener(this);
        commitTV.setVisibility(View.GONE);
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

    public static void intentToDetail(Context context, String json) {
        Intent intent = new Intent(context, TaskDetailActivity.class);
        intent.putExtra("data", json);
        context.startActivity(intent);
    }

    @Override
    public void getData() {
        userId = UserSP.getUserId(mContext);
        String json = getIntent().getStringExtra("data");
        if (StringUtil.isGoodJson(json)) {
            rowsBean = new Gson().fromJson(json, TaskBean.DataBean.RowsBean.class);
            if (rowsBean != null) {
                if (rowsBean.isFinish == 0) {
                    //未完成
                    commitTV.setVisibility(View.VISIBLE);
                } else if (rowsBean.isFinish == 1) {
                    //已完成
                    contentET.setText(StringUtil.valueOf(rowsBean.content));
                    contentET.setSelection(contentET.length());//将光标移至文字末尾
                    contentET.setFocusable(false);
                    commitTV.setVisibility(View.GONE);
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_commit:
                if (isCanCommit()) {
                    commit();
                }
                break;
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

    private boolean isCanCommit() {
        if (contentET.getText().length() > 0) {
            return true;
        }
        show("请填写任务内容!");
        return false;
    }

    private void commit() {
        HttpUtil.updateTask(mContext, userId, rowsBean.recNo, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                try {
                    BaseBean baseBean = new Gson().fromJson(response.get(), BaseBean.class);
                    if (baseBean != null && YS.SUCCESE.equals(baseBean.code)) {
                        show(StringUtil.valueOf(baseBean.msg));
                        TaskFragment.isRefresh = true;
                        finish();
                    } else {
                        show("任务汇报失败!");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }
        });
    }

}
