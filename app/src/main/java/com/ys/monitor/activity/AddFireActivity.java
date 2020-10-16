package com.ys.monitor.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.huamai.poc.PocEngine;
import com.huamai.poc.PocEngineFactory;
import com.yanzhenjie.nohttp.rest.Response;
import com.yongchun.library.view.ImageSelectorActivity;
import com.ys.monitor.R;
import com.ys.monitor.adapter.GridAdapter;
import com.ys.monitor.adapter.VideoGridAdapter;
import com.ys.monitor.api.FunctionApi;
import com.ys.monitor.base.BaseActivity;
import com.ys.monitor.bean.BaseBean;
import com.ys.monitor.bean.FileUploadBean;
import com.ys.monitor.dialog.WaitDialog;
import com.ys.monitor.http.HttpListener;
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

public class AddFireActivity extends BaseActivity {
    public static final int CAMERA = 444;
    public static final int REQUEST_DELETE_CODE = 1001;
    private MyGridView myGridView, videoGV;
    private GridAdapter mAdapter;
    private VideoGridAdapter videoGridAdapter;
    private ArrayList<String> list;
    private ArrayList<String> videoList;
    private String userId;
    private TextView commitTV;
    private EditText nameET, descripET;
    private TextView addressTV;
    private PocEngine pocEngine;
    private GeoCoder mCoder;
    private String currentVideoName;
    private WaitDialog waitDialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_fire_add;
    }

    @Override
    public void initView() {
        waitDialog = new WaitDialog(mContext);
        nameET = getView(R.id.et_name);
        descripET = getView(R.id.et_description);
        addressTV = getView(R.id.tv_address);
        commitTV = getView(R.id.tv_commit);
        commitTV.setOnClickListener(this);
        list = new ArrayList<>();
        videoList = new ArrayList<>();
        setBarColor("#ffffff");
        titleView.setText("火情上报");
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
        userId = UserSP.getUserId(mContext);
        pocEngine = PocEngineFactory.get();
        mCoder = GeoCoder.newInstance();
        mCoder.setOnGetGeoCodeResultListener(listener);
    }

    private String number;
    private String gis_jd;
    private String gis_wd;

    @Override
    public void getData() {
        getLocation();
//        if (pocEngine.hasServiceConnected()) {
//            if (!pocEngine.isDisableInternalGpsFunc()) {
//                User user = pocEngine.getCurrentUser();
//                number = "" + user.getNumber();
//                L.e("user=" + user.toString());
//                List<User> list = new ArrayList<>();
//                list.add(user);
//                pocEngine.getUserGPS(list, new IPocEngineEventHandler.Callback<String>() {
//                    @Override
//                    public void onResponse(final String json) {
//                        //回调在子线程
//                        new Handler().post(new Runnable() {
//                            @Override
//                            public void run() {
//                                L.e("json=" + json);
//                                if (StringUtil.isGoodJson(json)) {
//                                    List<GPSBean> ll = new Gson().fromJson(json,
//                                            new TypeToken<List<GPSBean>>() {
//                                            }.getType());
//                                    if (ll != null && ll.size() > 0) {
//                                        for (GPSBean gpsBean : ll) {
//                                            if (number.equals(gpsBean.exten)) {
//                                                gis_jd = gpsBean.gis_jd;
//                                                gis_wd = gpsBean.gis_wd;
//                                                geoAddress(StringUtil.StringToDouble(gis_wd),
//                                                        StringUtil.StringToDouble(gis_jd));
////                                                addressTV.setText(gis_jd + "-" + gis_wd);
//                                                break;
//                                            }
//                                        }
//                                    }
//
//                                }
//                            }
//                        });
//                    }
//                });
//            }
//        }
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

    private void geoAddress(double lat, double lon) {

        mCoder.reverseGeoCode(new ReverseGeoCodeOption()
                .location(new LatLng(lat, lon))
                // 设置是否返回新数据 默认值0不返回，1返回
                .newVersion(1)
                // POI召回半径，允许设置区间为0-1000米，超过1000米按1000米召回。默认值为1000
                .radius(500));
    }

    OnGetGeoCoderResultListener listener = new OnGetGeoCoderResultListener() {

        @Override
        public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

        }

        @Override
        public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
            if (reverseGeoCodeResult == null || reverseGeoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
                //没有找到检索结果
                L.e("onGetReverseGeoCodeResult---没有检索到结果");
                return;
            } else {
                //详细地址
                String address = reverseGeoCodeResult.getAddress();
                //行政区号
                int adCode = reverseGeoCodeResult.getCityCode();
                addressTV.setText(address);
                L.e("address=" + address + "--adCode=" + adCode);

            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_commit:
                if (isCan()) {
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
                case CAMERA:
                    L.e("视频录制成功");
                    videoList.add(Constant.VIDEO_PATH + "/" + currentVideoName);
                    videoGridAdapter.refresh(videoList);
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
            addFire("", "");
            return;
        }
        waitDialog.show();
        new Thread() {
            @Override
            public void run() {
                try {
                    if (list.size() > 0) {
                        Response<String> response = HttpUtil.uploadFile(mContext, userId,
                                YS.FileType.FILE_FIRE, list);
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
//        //上传附件
//        HttpUtil.uploadFile(mContext, userId, YS.FileType.FILE_FIRE, list,
//                new HttpListener<String>() {
//                    @Override
//                    public void onSucceed(int what, Response<String> response) {
//                        FileUploadBean fileUploadBean = new Gson().fromJson(response.get(),
//                                FileUploadBean.class);
//                        if (fileUploadBean != null && YS.SUCCESE.equals(fileUploadBean.code) &&
//                        fileUploadBean.data != null) {
//                            addFire(fileUploadBean.data.url);
//                        } else {
//                            show("附件上传失败!");
//                        }
//                    }
//
//                    @Override
//                    public void onFailed(int what, Response<String> response) {
//
//                    }
//                });
    }

    private void addFire(String imageUrls, String videoUrls) {
        Map<String, Object> map = new HashMap<>();
        double[] gps = GPSUtil.bd09_To_gps84(StringUtil.StringToDouble(gis_wd),
                StringUtil.StringToDouble(gis_jd));
        map.put("name", nameET.getText().toString());
        map.put("warnDesc", descripET.getText().toString());
        map.put("source", YS.source);
        map.put("siteSplicing", addressTV.getText().toString());
        map.put("latitude", "" + gps[0]);
        map.put("longitude", "" + gps[1]);
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
        if (mCoder != null) {
            mCoder.destroy();
        }
    }

    private boolean isCan() {
        if (StringUtil.isBlank(nameET.getText().toString())) {
            show("名称不能为空");
            return false;
        } else if (StringUtil.isBlank(descripET.getText().toString())) {
            show("描述不能为空");
            return false;
        } else if (StringUtil.isBlank(addressTV.getText().toString())) {
            show("获取位置信息失败,请检查是否开启GPS权限！");
            return false;
        }
        return true;
    }
}
