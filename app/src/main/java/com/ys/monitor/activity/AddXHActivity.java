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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.lcw.library.imagepicker.ImagePicker;
import com.yanzhenjie.nohttp.rest.Response;
import com.ys.monitor.R;
import com.ys.monitor.adapter.GridAdapter;
import com.ys.monitor.adapter.VideoGridAdapter;
import com.ys.monitor.api.FunctionApi;
import com.ys.monitor.base.BaseActivity;
import com.ys.monitor.bean.BaseBean;
import com.ys.monitor.bean.FileUploadBean;
import com.ys.monitor.bean.KVBean;
import com.ys.monitor.bean.LoginBean;
import com.ys.monitor.bean.RecordBean;
import com.ys.monitor.dialog.DialogUtil;
import com.ys.monitor.dialog.ListDialogFragment;
import com.ys.monitor.dialog.WaitDialog;
import com.ys.monitor.http.HttpListener;
import com.ys.monitor.service.UploadDataService;
import com.ys.monitor.sp.LocationSP;
import com.ys.monitor.sp.UserSP;
import com.ys.monitor.ui.MyGridView;
import com.ys.monitor.util.Constant;
import com.ys.monitor.util.DateUtil;
import com.ys.monitor.util.GPSUtil;
import com.ys.monitor.util.HttpUtil;
import com.ys.monitor.util.L;
import com.ys.monitor.util.StringUtil;
import com.ys.monitor.util.YS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddXHActivity extends BaseActivity {
    public static final int CAMERA = 555;
    public static final int REQUEST_DELETE_CODE = 1001;
    private MyGridView myGridView, videoGV;
    private GridAdapter mAdapter;
    private VideoGridAdapter videoGridAdapter;
    private ArrayList<String> list;
    private ArrayList<String> videoList;
    private String userId;
    private EditText descripET, wayET;
    private TextView statusTV, taskTV, nameTV, addressTV, commitTV;
    private String patrolPersonNo, patrolPlanName;
    private LinearLayout taskLL;
    private KVBean kvBean;
    private LoginBean loginBean;
    private String gis_jd;
    private String gis_wd;
    private String currentVideoName;
    private WaitDialog waitDialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_xh_add;
    }

    @Override
    public void initView() {
        kvBean = KVBean.getXHStatusList().get(1);
        waitDialog = new WaitDialog(mContext);
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
        videoList = new ArrayList<>();
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
        videoGV = getView(R.id.gv_video);
        videoGV.setSelector(new ColorDrawable(Color.TRANSPARENT));
        videoGridAdapter = new VideoGridAdapter(mContext, videoList, R.layout.item_grid_video);
        videoGV.setAdapter(videoGridAdapter);
        videoGV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == videoList.size()) {
                    currentVideoName = StringUtil.getNowTimeStr(3);
                    FunctionApi.startVideo(mActivity, CAMERA, currentVideoName);
                } else {
                    FunctionApi.playVideo(mContext, videoGridAdapter.getItem(position));
                }
            }
        });
        initLocationOption();
        getView(R.id.iv_map).setOnClickListener(this);
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
//        getAddress();
//        getLocation();
//        initLocationOption();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_commit:
                if (isCanCommit()) {
                    commitByService();
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
            case R.id.iv_map:
                startActivityForResult(new Intent(mContext, BaiduMapSelectAddressActivity.class),
                        1002);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case YS.REQUEST_SELECT_IMAGES_CODE://相册图片选取返回
//                    ArrayList<String> images =
//                            (ArrayList<String>) data.getSerializableExtra(ImageSelectorActivity
//                                    .REQUEST_OUTPUT);
                    ArrayList<String> images =
                            data.getStringArrayListExtra(ImagePicker.EXTRA_SELECT_IMAGES);
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
                case 1002:
                    String address = data.getStringExtra("address");
                    String lon = data.getStringExtra("lon");
                    String lat = data.getStringExtra("lat");
                    L.e("address=" + address + "--lon=" + lon + "--lat=" + lat);
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

    private void commitFile() {
        if (list.size() == 0 && videoList.size() == 0) {
            addXH("", "");
            return;
        }
        waitDialog.show();
        new Thread() {
            @Override
            public void run() {
                try {
                    if (list.size() > 0) {
                        Response<String> response = HttpUtil.uploadFile(mContext, userId,
                                YS.FileType.FILE_XH, list);
                        FileUploadBean fileUploadBean = new Gson().fromJson(response.get(),
                                FileUploadBean.class);
                        if (fileUploadBean != null && YS.SUCCESE.equals(fileUploadBean.code) && fileUploadBean.data != null) {
                            imageUrls = fileUploadBean.data.url;
                        } else {
                            L.e("图片上传失败,请稍后重试！");
                            handler.sendEmptyMessage(UPLOAD_IMAGE_FAIL);
                            return;
                        }
                    }
                    if (videoList.size() > 0) {
                        Response<String> response = HttpUtil.uploadFile(mContext, userId,
                                YS.FileType.FILE_FIRE, videoList);
                        FileUploadBean fileUploadBean = new Gson().fromJson(response.get(),
                                FileUploadBean.class);
                        if (fileUploadBean != null && YS.SUCCESE.equals(fileUploadBean.code) && fileUploadBean.data != null) {
                            videoUrls = fileUploadBean.data.url;
                        } else {
                            L.e("视频上传失败,请稍后重试！");
                            handler.sendEmptyMessage(UPLOAD_VIDEO_FAIL);
                            return;
                        }
                    }
                    handler.sendEmptyMessage(UPLOAD_SUCC);
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                    handler.sendEmptyMessage(UPLOAD_IMAGE_FAIL);
                }
            }
        }.start();
    }

    private void addXH(String imageUrls, String videoUrls) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", nameTV.getText().toString());
        map.put("patrolStatus", kvBean.id);
        map.put("warnDesc", descripET.getText().toString());
        map.put("source", YS.source);
        map.put("siteSplicing", addressTV.getText().toString());
        map.put("latitude", gis_wd);
        map.put("longitude", gis_jd);
        map.put("warnTime",
                DateUtil.changeTimeToYMDHMS(StringUtil.valueOf(System.currentTimeMillis())));
        map.put("imgUrl", imageUrls);
        map.put("videoUrl", videoUrls);
        map.put("way", wayET.getText().toString());
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
                    addXH(imageUrls, videoUrls);
                    break;
            }
        }
    };

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
            show("获取位置信息失败,请检查是否开启GPS权限！");
            isCan = false;
        } else if (StringUtil.isBlank(wayET.getText().toString())) {
            show("请填写路线");
            isCan = false;
        } else if (list.size() == 0) {
            show("至少上传一张图片！");
            return false;
        }
//        else if ("存在隐患".equals(statusTV.getText().toString()) || "预警".equals(statusTV.getText()
//        .toString())) {
//            if (StringUtil.isBlank(descripET.getText().toString())) {
//                show("请填写详细描述");
//                isCan = false;
//            }
//        }
        return isCan;
    }

    private void getLocation() {
        Map<String, Object> locationMap = LocationSP.getLocationData(mContext);
        if (locationMap != null) {
            double lat = (double) locationMap.get("lat");
            double lon = (double) locationMap.get("lon");
            String address = (String) locationMap.get("address");
            double[] gps = GPSUtil.bd09_To_gps84(lat, lon);
            gis_jd = StringUtil.valueOf(gps[1]);
            gis_wd = StringUtil.valueOf(gps[0]);
            addressTV.setText(address);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initLocationOption() {
        //定位服务的客户端。宿主程序在客户端声明此类，并调用，目前只支持在主线程中启动
        MyLocationListener myLocationListener = new MyLocationListener();
        LocationClient locationClient = StringUtil.getDefaultLocationClient(mContext,
                myLocationListener);
        //开始定位
        locationClient.start();
    }

    /**
     * 实现定位回调
     */
    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取经纬度相关（常用）的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明

            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                //获取纬度信息
                double latitude = location.getLatitude();
                //获取经度信息
                double longitude = location.getLongitude();
                String address = location.getAddress().address;
                //获取定位精度，默认值为0.0f
                float radius = location.getRadius();
                //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准
                String coorType = location.getCoorType();
                L.e("定位成功==latitude=" + latitude + "---longtitude=" + longitude + "--address=" + address);
                addressTV.setText(StringUtil.valueOf(address));
                double[] gps = GPSUtil.bd09_To_gps84(latitude, longitude);
                gis_jd = StringUtil.valueOf(gps[1]);
                gis_wd = StringUtil.valueOf(gps[0]);
            } else {
                L.e("定位失败==" + location.getLocType());
            }

        }
    }

    private void commitByService() {
        Map<String, String> map = new HashMap<>();
        map.put("name", nameTV.getText().toString());
        map.put("patrolStatus", kvBean.id);
        map.put("description", descripET.getText().toString());
        map.put("source", YS.source);
        map.put("siteSplicing", addressTV.getText().toString());
        map.put("latitude", gis_wd);
        map.put("longitude", gis_jd);
        map.put("patrolTime",
                DateUtil.changeTimeToYMDHMS(StringUtil.valueOf(System.currentTimeMillis())));
        map.put("imgUrl", imageUrls);
        map.put("videoUrl", videoUrls);
        map.put("way", wayET.getText().toString());
        UploadDataService.startUploadFire(mContext, "", list, videoList, map,
                RecordBean.TYPE_XUHU);
        finish();
    }
}
