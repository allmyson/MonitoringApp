package com.ys.monitor.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yanzhenjie.nohttp.rest.Response;
import com.yongchun.library.view.ImageSelectorActivity;
import com.ys.monitor.R;
import com.ys.monitor.adapter.GridAdapter;
import com.ys.monitor.api.FunctionApi;
import com.ys.monitor.base.BaseActivity;
import com.ys.monitor.bean.BaseBean;
import com.ys.monitor.bean.FileUploadBean;
import com.ys.monitor.bean.KVBean;
import com.ys.monitor.bean.LoginBean;
import com.ys.monitor.dialog.DialogUtil;
import com.ys.monitor.dialog.ListDialogFragment;
import com.ys.monitor.http.HttpListener;
import com.ys.monitor.sp.UserSP;
import com.ys.monitor.ui.MyGridView;
import com.ys.monitor.util.DateUtil;
import com.ys.monitor.util.HttpUtil;
import com.ys.monitor.util.L;
import com.ys.monitor.util.StringUtil;
import com.ys.monitor.util.YS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddXHActivity extends BaseActivity {
    public static final int REQUEST_DELETE_CODE = 1001;
    private MyGridView myGridView;
    private GridAdapter mAdapter;
    private ArrayList<String> list;
    private String userId;
    private EditText descripET, wayET;
    private TextView statusTV, taskTV, nameTV, addressTV, commitTV;
    private String patrolPersonNo, patrolPlanName;
    private LinearLayout taskLL;
    private KVBean kvBean;
    private LoginBean loginBean;

    @Override
    public int getLayoutId() {
        return R.layout.activity_xh_add;
    }

    @Override
    public void initView() {
        taskLL = getView(R.id.ll_task);
        statusTV = getView(R.id.tv_status);
        statusTV.setOnClickListener(this);
        taskTV = getView(R.id.tv_task);
        nameTV = getView(R.id.tv_name);
        descripET = getView(R.id.et_description);
        wayET = getView(R.id.et_way);
        addressTV = getView(R.id.tv_address);
        commitTV = getView(R.id.tv_commit);
        commitTV.setOnClickListener(this);
        list = new ArrayList<>();
        setBarColor("#ffffff");
        titleView.setText("日常巡护");
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
        userId = UserSP.getUserId(mContext);
        loginBean = UserSP.getLoginBean(mContext);
        if (loginBean != null && loginBean.data != null) {
            nameTV.setText(loginBean.data.trueName);
        }
    }

    @Override
    public void getData() {
        patrolPersonNo = getIntent().getStringExtra("patrolPersonNo");
        patrolPlanName = getIntent().getStringExtra("patrolPlanName");
        L.e(patrolPersonNo + "---" + patrolPlanName);
        if (StringUtil.isBlank(patrolPersonNo)) {
            taskLL.setVisibility(View.GONE);
        } else {
            taskTV.setText(patrolPlanName);
            taskLL.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_commit:
                if (isCanCommit()) {
                    commitFile();
                }
                break;
            case R.id.tv_status:
                DialogUtil.showListFragment(mContext, KVBean.getXHStatusList(),
                        new ListDialogFragment.ItemClickListener() {
                            @Override
                            public void clickResult(KVBean listBean) {
                                kvBean = listBean;
                                statusTV.setText(kvBean.name);
                            }
                        });
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

    private void commitFile() {
        if (list == null || list.size() == 0) {
            addXH("");
            return;
        }
        //上传附件
        HttpUtil.uploadFile(mContext, userId, YS.FileType.FILE_XH, list,
                new HttpListener<String>() {
                    @Override
                    public void onSucceed(int what, Response<String> response) {
                        FileUploadBean fileUploadBean = new Gson().fromJson(response.get(),
                                FileUploadBean.class);
                        if (fileUploadBean != null && YS.SUCCESE.equals(fileUploadBean.code) && fileUploadBean.data != null) {
                            addXH(fileUploadBean.data.url);
                        } else {
                            show("附件上传失败!");
                        }
                    }

                    @Override
                    public void onFailed(int what, Response<String> response) {

                    }
                });
    }

    private void addXH(String fileUrls) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", nameTV.getText().toString());
        map.put("warnDesc", descripET.getText().toString());
        map.put("source", YS.source);
        map.put("siteSplicing", "尖顶坡");
        map.put("latitude", "29.54460611");
        map.put("longitude", "106.53063501");
        map.put("warnTime",
                DateUtil.changeTimeToYMDHMS(StringUtil.valueOf(System.currentTimeMillis())));
        map.put("imgUrl", fileUrls);
        map.put("videoUrl", "");
        String data = new Gson().toJson(map);
        L.e("data=" + data);
        HttpUtil.addXH(mContext, userId, data, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                try {
                    BaseBean baseBean = new Gson().fromJson(response.get(), BaseBean.class);
                    if (baseBean != null && YS.SUCCESE.equals(baseBean.code)) {
                        show("上报成功!");
                        finish();
                    } else {
                        show("上报失败!");
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

    public static void intentToXH(Context context, String taskNo, String taskName) {
        Intent intent = new Intent(context, AddXHActivity.class);
        intent.putExtra("patrolPersonNo", taskNo);
        intent.putExtra("patrolPlanName", taskName);
        context.startActivity(intent);
    }

    private boolean isCanCommit() {
        boolean isCan = true;
        if ("请选择".equals(statusTV.getText().toString())) {
            show("请选择巡护状态");
            isCan = false;
        } else if (StringUtil.isBlank(addressTV.getText().toString())) {
            show("请选择位置");
            isCan = false;
        } else if (StringUtil.isBlank(wayET.getText().toString())) {
            show("请填写路线");
            isCan = false;
        } else if ("存在隐患".equals(statusTV.getText().toString()) || "预警".equals(statusTV.getText().toString())) {
            if (StringUtil.isBlank(descripET.getText().toString())) {
                show("请填写详细描述");
                isCan = false;
            }
        }
        return isCan;
    }
}
