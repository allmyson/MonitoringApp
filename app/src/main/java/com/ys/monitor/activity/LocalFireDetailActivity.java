package com.ys.monitor.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yanzhenjie.nohttp.rest.Response;
import com.yongchun.library.view.ImageSelectorActivity;
import com.ys.monitor.R;
import com.ys.monitor.adapter.LocalGridAdapter;
import com.ys.monitor.adapter.LocalVideoGridAdapter;
import com.ys.monitor.api.FunctionApi;
import com.ys.monitor.base.BaseActivity;
import com.ys.monitor.bean.BaseBean;
import com.ys.monitor.bean.RecordBean;
import com.ys.monitor.bean.RecordDetail;
import com.ys.monitor.dialog.WaitDialog;
import com.ys.monitor.http.HttpListener;
import com.ys.monitor.service.UploadDataService;
import com.ys.monitor.sp.UserSP;
import com.ys.monitor.ui.MyGridView;
import com.ys.monitor.util.Constant;
import com.ys.monitor.util.DateUtil;
import com.ys.monitor.util.GPSUtil;
import com.ys.monitor.util.HttpUtil;
import com.ys.monitor.util.L;
import com.ys.monitor.util.SPUtil;
import com.ys.monitor.util.StringUtil;
import com.ys.monitor.util.YS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LocalFireDetailActivity extends BaseActivity {
    public static final int CAMERA = 444;
    public static final int REQUEST_DELETE_CODE = 1001;
    private MyGridView myGridView, videoGV;
    private LocalGridAdapter mAdapter;
    private LocalVideoGridAdapter videoGridAdapter;
    private ArrayList<String> list;
    private ArrayList<String> videoList;
    private String userId;
    private TextView commitTV;
    private EditText nameET, descripET;
    private TextView addressTV;
    private String currentVideoName;
    private WaitDialog waitDialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_fire_local;
    }

    @Override
    public void initView() {
        waitDialog = new WaitDialog(mContext);
        nameET = getView(R.id.et_name);
        descripET = getView(R.id.et_description);
        descripET.setEnabled(false);
        commitTV = getView(R.id.tv_commit);
        commitTV.setOnClickListener(this);
        addressTV = getView(R.id.tv_address);
        list = new ArrayList<>();
        videoList = new ArrayList<>();
        setBarColor("#ffffff");
        titleView.setText("火情上报");
        myGridView = getView(R.id.gv_image);
        myGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        mAdapter = new LocalGridAdapter(mContext, list, R.layout.item_grid_image);
        myGridView.setAdapter(mAdapter);
        myGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == list.size()) {
//                    FunctionApi.takePicture(mContext, 9 - list.size(), 1, true, false, false);
                } else {
                    PhotoActivity.intentToPhotoActivity(mActivity, REQUEST_DELETE_CODE, list,
                            position);
                }
            }
        });
        videoGV = getView(R.id.gv_video);
        videoGV.setSelector(new ColorDrawable(Color.TRANSPARENT));
        videoGridAdapter = new LocalVideoGridAdapter(mContext, videoList, R.layout.item_grid_video);
        videoGV.setAdapter(videoGridAdapter);
        videoGV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == videoList.size()) {
//                    currentVideoName = StringUtil.getNowTimeStr(3);
//                    FunctionApi.startVideo(mActivity, CAMERA, currentVideoName);
                } else {
                    FunctionApi.playVideo(mContext, videoGridAdapter.getItem(position));
                }
            }
        });
        userId = UserSP.getUserId(mContext);
        getView(R.id.iv_map).setVisibility(View.GONE);
        getView(R.id.iv_map).setOnClickListener(this);
    }

    private String gis_jd;
    private String gis_wd;
    private RecordDetail recordDetail;
    private String uuid;
    private boolean isSucc;

    @Override
    public void getData() {
        uuid = getIntent().getStringExtra("uuid");
        isSucc = getIntent().getBooleanExtra("isSucc", false);
        if (isSucc) {
            commitTV.setVisibility(View.GONE);
        } else {
            commitTV.setVisibility(View.VISIBLE);
        }
        String detailJson = (String) SPUtil.get(mContext, uuid, "");
        if (StringUtil.isGoodJson(detailJson)) {
            recordDetail = new Gson().fromJson(detailJson, RecordDetail.class);
            if (recordDetail != null) {
                Map<String, String> map = recordDetail.map;
                if (map == null) {
                    return;
                }
                descripET.setText(StringUtil.valueOf(map.get("warnDesc")));
                addressTV.setText(StringUtil.valueOf(map.get("siteSplicing")));
                ArrayList<String> imgs = recordDetail.imgs;
                ArrayList<String> videos = recordDetail.videos;
                if (imgs != null && imgs.size() > 0) {
                    list.addAll(imgs);
                    mAdapter.refresh(list);
                }
                if (videos != null && videos.size() > 0) {
                    videoList.addAll(videos);
                    videoGridAdapter.refresh(videoList);
                }

            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_commit:
                if (isCan()) {
//                    commitFile();
                    commitByService();
                }
                break;
            case R.id.iv_map:
                startActivityForResult(new Intent(mContext, BaiduMapSelectAddressActivity.class),
                        1000);
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
                case CAMERA:
                    L.e("视频录制成功");
                    videoList.add(Constant.VIDEO_PATH + "/" + currentVideoName);
                    videoGridAdapter.refresh(videoList);
                    break;
                case 1000:
                    String address = data.getStringExtra("address");
                    String lon = data.getStringExtra("lon");
                    String lat = data.getStringExtra("lat");
                    L.e("address=" + address + "--lon=" + lon + "--lat=" + lat);
                    nameET.setText("(" + address + ")+火情");
                    addressTV.setText(StringUtil.valueOf(address));
                    double[] gps = GPSUtil.bd09_To_gps84(StringUtil.StringToDouble(lat),
                            StringUtil.StringToDouble(lon));
                    gis_jd = StringUtil.valueOf(gps[1]);
                    gis_wd = StringUtil.valueOf(gps[0]);
                    break;
            }
        }
    }

    private String imageUrls, videoUrls;
    private static final int UPLOAD_SUCC = 9;
    private static final int UPLOAD_IMAGE_FAIL = 10;
    private static final int UPLOAD_VIDEO_FAIL = 11;


    private void addFire(String imageUrls, String videoUrls) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", nameET.getText().toString());
        map.put("warnDesc", descripET.getText().toString());
        map.put("source", YS.source);
        map.put("siteSplicing", addressTV.getText().toString());
        map.put("latitude", "" + gis_wd);
        map.put("longitude", "" + gis_jd);
        map.put("warnTime",
                DateUtil.changeTimeToYMDHMS(StringUtil.valueOf(System.currentTimeMillis())));
        map.put("imgUrl", StringUtil.valueOf(imageUrls));
        map.put("videoUrl", StringUtil.valueOf(videoUrls));
        String data = new Gson().toJson(map);
        L.e("data=" + data);
        HttpUtil.addFire(mContext, userId, data, new HttpListener<String>() {
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

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case UPLOAD_IMAGE_FAIL:
                    show("图片上传失败");
                    waitDialog.dismiss();
                    break;
                case UPLOAD_VIDEO_FAIL:
                    show("视频上传失败");
                    waitDialog.dismiss();
                    break;
                case UPLOAD_SUCC:
                    L.e("附件上传成功");
                    waitDialog.dismiss();
                    addFire(imageUrls, videoUrls);
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private boolean isCan() {
        if (StringUtil.isBlank(addressTV.getText().toString())) {
            show("位置不能为空！");
            return false;
        } else if (list.size() == 0) {
            show("至少上传一张图片！");
            return false;
        }
        return true;
    }


    private void commitByService() {
//        Map<String, String> map = new HashMap<>();
//        map.put("name", nameET.getText().toString());
//        map.put("warnDesc", descripET.getText().toString());
//        map.put("source", YS.source);
//        map.put("siteSplicing", addressTV.getText().toString());
//        map.put("latitude", "" + gis_wd);
//        map.put("longitude", "" + gis_jd);
//        map.put("warnTime",
//                DateUtil.changeTimeToYMDHMS(StringUtil.valueOf(System.currentTimeMillis())));
//        map.put("imgUrl", StringUtil.valueOf(imageUrls));
//        map.put("videoUrl", StringUtil.valueOf(videoUrls));
        UploadDataService.startUploadFire(mContext, uuid, recordDetail.imgs, recordDetail.videos,
                recordDetail.map, RecordBean.TYPE_FIRE);
        finish();
    }

    public static void lookLocalFire(Context context, String uuid, boolean isSucc) {
        Intent intent = new Intent(context, LocalFireDetailActivity.class);
        intent.putExtra("uuid", uuid);
        intent.putExtra("isSucc", isSucc);
        context.startActivity(intent);
    }
}
