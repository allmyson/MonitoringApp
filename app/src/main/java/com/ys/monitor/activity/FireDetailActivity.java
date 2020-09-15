package com.ys.monitor.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yanzhenjie.nohttp.rest.Response;
import com.yongchun.library.view.ImageSelectorActivity;
import com.ys.monitor.R;
import com.ys.monitor.adapter.FireImgAdapter;
import com.ys.monitor.adapter.GridAdapter;
import com.ys.monitor.api.FunctionApi;
import com.ys.monitor.base.BaseActivity;
import com.ys.monitor.bean.BaseBean;
import com.ys.monitor.bean.FileUploadBean;
import com.ys.monitor.bean.FireBean;
import com.ys.monitor.bean.KVBean;
import com.ys.monitor.dialog.DialogUtil;
import com.ys.monitor.dialog.ListDialogFragment;
import com.ys.monitor.fragment.FireFragment;
import com.ys.monitor.http.HttpListener;
import com.ys.monitor.sp.UserSP;
import com.ys.monitor.ui.HorizontalListView;
import com.ys.monitor.ui.MyGridView;
import com.ys.monitor.util.DateUtil;
import com.ys.monitor.util.HttpUtil;
import com.ys.monitor.util.L;
import com.ys.monitor.util.StringUtil;
import com.ys.monitor.util.YS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FireDetailActivity extends BaseActivity {
    public static final int REQUEST_DELETE_CODE = 1001;
    private HorizontalListView horizontalListView;
    private FireImgAdapter fireImgAdapter;
    private List<String> imgList;
    private MyGridView myGridView;
    private GridAdapter mAdapter;
    private ArrayList<String> list;
    private FireBean.DataBean.RowsBean rowsBean;
    private TextView typeTV, nameTV, descriptTV, addressTV, timeTV, resultTV, commitTV,
            warnPersonTV, warnerPhoneTV;
    private LinearLayout xcqkLL;
    private KVBean kvBean;
    private String userId;
    @Override
    public int getLayoutId() {
        return R.layout.activity_fire_detail;
    }

    @Override
    public void initView() {
        myGridView = getView(R.id.gv_image);
        typeTV = getView(R.id.tv_type);
        nameTV = getView(R.id.tv_name);
        descriptTV = getView(R.id.tv_descrip);
        addressTV = getView(R.id.tv_address);
        timeTV = getView(R.id.tv_time);
        warnPersonTV = getView(R.id.tv_warnPerson);
        warnerPhoneTV = getView(R.id.tv_warnerPhone);
        resultTV = getView(R.id.tv_result);
        commitTV = getView(R.id.tv_commit);
        commitTV.setOnClickListener(this);
        xcqkLL = getView(R.id.ll_xcqk);
        xcqkLL.setVisibility(View.GONE);
        commitTV.setVisibility(View.GONE);
        myGridView.setVisibility(View.GONE);
        list = new ArrayList<>();
        setBarColor("#ffffff");
        titleView.setText("火情详情");
        horizontalListView = getView(R.id.hlv_);
        imgList = new ArrayList<>();
//        imgList.add(YS.testImageUrl);
//        imgList.add(YS.testImageUrl);
//        imgList.add(YS.testImageUrl);
//        imgList.add(YS.testImageUrl);
        fireImgAdapter = new FireImgAdapter(mContext, imgList, R.layout.item_fire_img);
        horizontalListView.setAdapter(fireImgAdapter);
        horizontalListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FunctionApi.LargerImage(mContext, imgList, position);
            }
        });
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
    }


    @Override
    public void getData() {
        String data = getIntent().getStringExtra("data");
        L.e("data=" + data);
        if (StringUtil.isGoodJson(data)) {
            rowsBean = new Gson().fromJson(data, FireBean.DataBean.RowsBean.class);
            if (rowsBean != null) {
                if (YS.FireStatus.Status_DCL.equals(rowsBean.status)||YS.FireStatus.Status_HSZ.equals(rowsBean.status)) {
                    xcqkLL.setVisibility(View.VISIBLE);
                    commitTV.setVisibility(View.VISIBLE);
                    myGridView.setVisibility(View.VISIBLE);
                    resultTV.setOnClickListener(this);
                    resultTV.setText(StringUtil.valueOf(rowsBean.statusName));
                } else {
                    xcqkLL.setVisibility(View.GONE);
                    commitTV.setVisibility(View.GONE);
                    myGridView.setVisibility(View.GONE);
                    resultTV.setText(StringUtil.valueOf(rowsBean.statusName));
                    resultTV.setOnClickListener(null);
                }
                typeTV.setText(StringUtil.valueOf(rowsBean.sourceName));
                nameTV.setText(StringUtil.valueOf(rowsBean.name));
                descriptTV.setText(StringUtil.valueOf(rowsBean.warnDesc));
                addressTV.setText(StringUtil.valueOf(rowsBean.siteSplicing));
                warnPersonTV.setText(StringUtil.valueOf(rowsBean.warnPerson));
                warnerPhoneTV.setText(StringUtil.valueOf(rowsBean.warnerPhone));
                timeTV.setText(DateUtil.changeTimeToYMDHMS(StringUtil.valueOf(rowsBean.warnTime)));
                if (rowsBean.imgUrl != null && rowsBean.imgUrl.contains(";")) {
                    imgList.clear();
                    String[] a = rowsBean.imgUrl.split(";");
                    if (a != null && a.length > 0) {
                        for (String s : a) {
                            imgList.add(YS.IP + s);
                        }
                    }
                    fireImgAdapter.refresh(imgList);
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_result:
                DialogUtil.showListFragment(mContext, KVBean.getStatusTypeList(),
                        new ListDialogFragment.ItemClickListener() {
                            @Override
                            public void clickResult(KVBean listBean) {
                                kvBean = listBean;
                                resultTV.setText(kvBean.name);
                            }
                        });
                break;
            case R.id.tv_commit:
                if (isCanCommit()) {
                    commitFile();
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


    public static void intentToDetail(Context context, String json) {
        Intent intent = new Intent(context, FireDetailActivity.class);
        intent.putExtra("data", json);
        context.startActivity(intent);
    }

    private boolean isCanCommit() {
        if (kvBean != null && !YS.FireStatus.Status_DCL.equals(kvBean.id)&&!YS.FireStatus.Status_HSZ.equals(kvBean.id)) {
            return true;
        }
        show(" 请选择核查结果！");
        return false;
    }

    private void commitFile() {
        if (list == null || list.size() == 0) {
            updateFire("");
            return;
        }
        //上传附件
        HttpUtil.uploadFile(mContext, userId, YS.FileType.FILE_FIRE, list,
                new HttpListener<String>() {
                    @Override
                    public void onSucceed(int what, Response<String> response) {
                        FileUploadBean fileUploadBean = new Gson().fromJson(response.get(),
                                FileUploadBean.class);
                        if (fileUploadBean != null && YS.SUCCESE.equals(fileUploadBean.code) && fileUploadBean.data != null) {
                            updateFire(fileUploadBean.data.url);
                        } else {
                            show("附件上传失败!");
                        }
                    }

                    @Override
                    public void onFailed(int what, Response<String> response) {

                    }
                });
    }

    private void updateFire(String fileUrls) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", kvBean.id);
        map.put("recNo", rowsBean.recNo);
        map.put("imgUrl", rowsBean.imgUrl + fileUrls);
        String data = new Gson().toJson(map);
        L.e("data=" + data);
        HttpUtil.updateFire(mContext, userId, data, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                try {
                    BaseBean baseBean = new Gson().fromJson(response.get(), BaseBean.class);
                    if (baseBean != null && YS.SUCCESE.equals(baseBean.code)) {
                        show("提交成功!");
                        FireFragment.isRefresh = true;
                        finish();
                    } else {
                        show("提交失败!");
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
