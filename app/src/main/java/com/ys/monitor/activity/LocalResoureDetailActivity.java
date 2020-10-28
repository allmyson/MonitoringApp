package com.ys.monitor.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.google.gson.Gson;
import com.yanzhenjie.nohttp.rest.Response;
import com.yongchun.library.view.ImageSelectorActivity;
import com.ys.monitor.R;
import com.ys.monitor.adapter.GridAdapter;
import com.ys.monitor.adapter.LocalGridAdapter;
import com.ys.monitor.api.FunctionApi;
import com.ys.monitor.base.BaseActivity;
import com.ys.monitor.bean.BaseBean;
import com.ys.monitor.bean.FileUploadBean;
import com.ys.monitor.bean.KVBean;
import com.ys.monitor.bean.RecordBean;
import com.ys.monitor.bean.RecordDetail;
import com.ys.monitor.bean.ResourceBean;
import com.ys.monitor.bean.ResourceTypeBean;
import com.ys.monitor.dialog.WaitDialog;
import com.ys.monitor.http.HttpListener;
import com.ys.monitor.service.UploadFireService;
import com.ys.monitor.ui.LastInputEditText;
import com.ys.monitor.ui.MyGridView;
import com.ys.monitor.util.GPSUtil;
import com.ys.monitor.util.HttpUtil;
import com.ys.monitor.util.L;
import com.ys.monitor.util.SPUtil;
import com.ys.monitor.util.StringUtil;
import com.ys.monitor.util.YS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocalResoureDetailActivity extends BaseActivity {
    private String userId;
    private TextView typeTV;
    private List<KVBean> typeList;
    private KVBean currentKVBean;
    private LinearLayout baseLL, extLL, imgLL;
    private TextView commitTV;
    private List<ResourceBean.DataBean.RowsBean> rowsBeanList;
    private ResourceBean.DataBean.RowsBean currentRowsBean;
    private String number;
    private String gis_jd;
    private String gis_wd;

    @Override
    public int getLayoutId() {
        return R.layout.activity_resource;
    }

    @Override
    public void initView() {
        waitDialog = new WaitDialog(mContext);
        resultMap = new HashMap<>();
        setBarColor("#ffffff");
        titleView.setText("资源采集");
        typeTV = getView(R.id.tv_type);
//        typeTV.setOnClickListener(this);
        typeList = new ArrayList<>();
        rowsBeanList = new ArrayList<>();
        baseLL = getView(R.id.ll_base);
        extLL = getView(R.id.ll_ext);
        imgLL = getView(R.id.ll_img);
        commitTV = getView(R.id.tv_commit);
        commitTV.setOnClickListener(this);
    }

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
                createView(recordDetail.resourceTypeBean);
            }
        }
    }

    private void createView(ResourceTypeBean resourceTypeBean) {
        typeTV.setText(StringUtil.valueOf(recordDetail.resourceTypeBean.resourceType));
        Map<String, String> baseMap = resourceTypeBean.baseMap;
        if (baseMap != null && baseMap.size() > 0) {
            for (Map.Entry<String, String> entry : baseMap.entrySet()) {
                L.e("Key = " + entry.getKey() + ", Value = " + entry.getValue());
                addBaseView(StringUtil.valueOf(entry.getKey()),
                        StringUtil.valueOf(entry.getValue()));
            }
        }
        Map<String, String> extMap = resourceTypeBean.extMap;
        if (extMap != null && extMap.size() > 0) {
            for (Map.Entry<String, String> entry : extMap.entrySet()) {
                L.e("Key = " + entry.getKey() + ", Value = " + entry.getValue());
                addExtView(StringUtil.valueOf(entry.getKey()),
                        StringUtil.valueOf(entry.getValue()));
            }
        }
        List<String> imgList = resourceTypeBean.imgList;
        if (imgList != null && imgList.size() > 0) {
            addImgView(imgList);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_commit:
                if (isCanAdd()) {
//                    addResource();
                    commitByService();
                }
                break;
        }
    }

    private void getZiduan(String id) {
        HttpUtil.getResourceZiduan(mContext, userId, id, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
//                try {
//                    ResourceZDBean resourceZDBean = new Gson().fromJson(response.get(),
//                            ResourceZDBean.class);
//                    if (resourceZDBean != null && YS.SUCCESE.equals(resourceZDBean.code)) {
//                        if (resourceZDBean.data.ElementBasic != null) {
//                            boolean hasImg = false;
//                            Class cls = resourceZDBean.data.ElementBasic.getClass();
//                            Field[] fields = cls.getDeclaredFields();
//                            for (int i = 0; i < fields.length; i++) {
//                                Field f = fields[i];
//                                f.setAccessible(true);
//                                L.e("属性名:" + f.getName() + " 属性值:" + f.get(resourceZDBean.data
//                                .ElementBasic));
//                                if (!"imgUrl".equals(f.getName())) {
//                                    addBaseView(StringUtil.valueOf(f.getName()),
//                                            StringUtil.valueOf(f.get(resourceZDBean.data
//                                            .ElementBasic)));
//                                } else {
//                                    hasImg = true;
//                                }
//                            }
//                            if (hasImg) {
//                                ResourceZDBean.DataBean.BaseElementExtBean baseElementExtBean =
//                                        new ResourceZDBean.DataBean.BaseElementExtBean();
//                                baseElementExtBean.dataName = "imgUrl";
//                                baseElementExtBean.name = "图片";
//                                addImgView(baseElementExtBean);
//                            }
//                        }
//
//                        if (resourceZDBean.data.BaseElementExt != null && resourceZDBean.data
//                        .BaseElementExt.size() > 0) {
//                            for (ResourceZDBean.DataBean.BaseElementExtBean baseElementExtBean :
//                                    resourceZDBean.data.BaseElementExt) {
//                                if (baseElementExtBean != null) {
//                                    if ("img".equals(baseElementExtBean.dataName)) {
//                                        //图片
//                                        addImgView(baseElementExtBean);
//                                    } else {
//                                        //文本
//                                        addExtView(baseElementExtBean);
//                                    }
//                                }
//                            }
//                        }
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }
        });
    }

    private void addBaseView(String key, String name) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_base, null, false);
        TextView nameTV = (TextView) view.findViewById(R.id.tv_name);
        LastInputEditText valueET = (LastInputEditText) view.findViewById(R.id.et_value);
        nameTV.setText(StringUtil.valueOf(key));
        valueET.setText(StringUtil.valueOf(name));
        valueET.setEnabled(false);
        if ("社".equals(key) || "行政区域代码".equals(key) || "地址".equals(key) ||
                "创建人".equals(key) || "编号".equals(key) || "资源类型".equals(key) || "创建时间".equals(key) || "小班号".equals(key) || "小地名".equals(key) || "资源类型名称".equals(key)) {
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
        baseLL.addView(view);
    }

    private ResourceBean.DataBean.RowsBean getCurrentRowsBean() {
        if (currentKVBean == null || rowsBeanList.size() == 0) {
            return null;
        }
        ResourceBean.DataBean.RowsBean rowsBean = null;
        for (int i = 0; i < rowsBeanList.size(); i++) {
            if (currentKVBean.id.equals(rowsBeanList.get(i).recNo)) {
                rowsBean = rowsBeanList.get(i);
                break;
            }
        }
        return rowsBean;
    }


    private void addExtView(String key, String value) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_ext, null, false);
        TextView nameTV = (TextView) view.findViewById(R.id.tv_name);
        final LastInputEditText valueET = (LastInputEditText) view.findViewById(R.id.et_value);
        nameTV.setText(StringUtil.valueOf(key));
        valueET.setText(StringUtil.valueOf(value));
        valueET.setEnabled(false);
//        if ("经度".equals(key) || "纬度".equals(key)) {
//            view.setVisibility(View.GONE);
//        } else {
//            view.setVisibility(View.VISIBLE);
//        }
        extLL.addView(view);
    }

    private String currentImgViewId = "";

    private void addImgView(List<String> imgList) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_images, null, false);
        TextView idTV = (TextView) view.findViewById(R.id.tv_id);
        final TextView uuidTV = (TextView) view.findViewById(R.id.tv_uuid);
        uuidTV.setText(StringUtil.getUUID());
        TextView nameTV = (TextView) view.findViewById(R.id.tv_name);
        MyGridView myGridView = (MyGridView) view.findViewById(R.id.gv_image);
        myGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        final List<String> list = new ArrayList<>();
        list.addAll(imgList);
        LocalGridAdapter mAdapter = new LocalGridAdapter(mContext, list, R.layout.item_grid_image);
        myGridView.setAdapter(mAdapter);
        myGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == list.size()) {
//                    FunctionApi.takePicture(mContext, 9 - list.size(), 1, true, false, false);
//                    currentImgViewId = uuidTV.getText().toString();
//                    L.e("currentImgViewId" + currentImgViewId);
                } else {
                    FunctionApi.LargerImage(mContext, list, position);

                }
            }
        });
        nameTV.setText("图片");
        imgLL.addView(view);
    }

    private void removeAllView() {
        baseLL.removeAllViews();
        extLL.removeAllViews();
        imgLL.removeAllViews();
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
                        int count = imgLL.getChildCount();
                        if (count == 0) {
                            return;
                        }
                        for (int i = 0; i < count; i++) {
                            View view = imgLL.getChildAt(i);
                            TextView uuidTV = (TextView) view.findViewById(R.id.tv_uuid);
                            if (currentImgViewId.equals(uuidTV.getText().toString())) {
                                MyGridView myGridView =
                                        (MyGridView) view.findViewById(R.id.gv_image);
                                GridAdapter gridAdapter = (GridAdapter) myGridView.getAdapter();
                                List<String> list = gridAdapter.getmDatas();
                                if (list == null) {
                                    list = new ArrayList<>();
                                }
                                list.addAll(images);
                                ((GridAdapter) myGridView.getAdapter()).refresh(list);
                                break;
                            }
                        }
                    }
                    break;
            }
        }
    }

    //提交校验
    private boolean isCanAdd() {
        boolean isCan = true;
        if ("请选择".equals(typeTV.getText().toString())) {
            show("请选择资源类型！");
            isCan = false;
        }
        if (!isCan) {
            return isCan;
        }
        int baseCount = baseLL.getChildCount();
        for (int i = 0; i < baseCount; i++) {
            View view = baseLL.getChildAt(i);
            TextView nameTV = (TextView) view.findViewById(R.id.tv_name);
            LastInputEditText valueET = (LastInputEditText) view.findViewById(R.id.et_value);
            String value = valueET.getText().toString();
            if (StringUtil.isBlank(value)) {
                show(nameTV.getText().toString() + "不能为空！");
                isCan = false;
                break;
            }
        }
        if (!isCan) {
            return isCan;
        }
        int extCount = extLL.getChildCount();
        for (int j = 0; j < extCount; j++) {
            View view = extLL.getChildAt(j);
            TextView nameTV = (TextView) view.findViewById(R.id.tv_name);
            TextView typeTV = (TextView) view.findViewById(R.id.tv_type);
            LastInputEditText valueET = (LastInputEditText) view.findViewById(R.id.et_value);
            String value = valueET.getText().toString();
            if (StringUtil.isBlank(value)) {
                show(nameTV.getText().toString() + "不能为空！");
                isCan = false;
                break;
            }
            if ("int".equals(typeTV.getText().toString())) {
                if (!StringUtil.isInteger(value)) {
                    show(nameTV.getText().toString() + "必须是整数！");
                    isCan = false;
                    break;
                }
            }
        }
        if (!isCan) {
            return isCan;
        }
        int imgCount = imgLL.getChildCount();
        for (int k = 0; k < imgCount; k++) {
            View view = imgLL.getChildAt(k);
            TextView nameTV = (TextView) view.findViewById(R.id.tv_name);
            MyGridView myGridView = (MyGridView) view.findViewById(R.id.gv_image);
            LocalGridAdapter gridAdapter = (LocalGridAdapter) myGridView.getAdapter();
            List<String> list = gridAdapter.getmDatas();
            if (list == null || list.size() == 0) {
                show(nameTV.getText().toString() + "不能为空！");
                isCan = false;
                break;
            }
        }
        return isCan;
    }

    private Map<String, String> resultMap;
    private WaitDialog waitDialog;

    private void addResource() {
        waitDialog.show();
        resultMap.clear();
        resultMap.put(" isDelete", "0");
        resultMap.put("elementType", currentKVBean.id);
//        double[] gps = GPSUtil.bd09_To_gps84(StringUtil.StringToDouble(gis_wd),
//                StringUtil.StringToDouble(gis_jd));
//        resultMap.put("latitude", "" + gps[0]);
//        resultMap.put("longitude", "" + gps[1]);
        L.e("参数齐全,可以提交");
        int baseCount = baseLL.getChildCount();
        for (int i = 0; i < baseCount; i++) {
            View view = baseLL.getChildAt(i);
            TextView idTV = (TextView) view.findViewById(R.id.tv_id);
            LastInputEditText valueET = (LastInputEditText) view.findViewById(R.id.et_value);
            String id = idTV.getText().toString();
            String value = valueET.getText().toString();
            if ("areaCode".equals(id) || "agency".equals(id) || "investigationAddr".equals(id)) {
                value = "";
            }
            resultMap.put(id, value);
        }
        int extCount = extLL.getChildCount();
        for (int j = 0; j < extCount; j++) {
            View view = extLL.getChildAt(j);
            TextView idTV = (TextView) view.findViewById(R.id.tv_id);
            TextView typeTV = (TextView) view.findViewById(R.id.tv_type);
            LastInputEditText valueET = (LastInputEditText) view.findViewById(R.id.et_value);
            String value = valueET.getText().toString();
            resultMap.put(idTV.getText().toString(), value);
        }
        int imgCount = imgLL.getChildCount();
        final List<List<String>> imageList = new ArrayList<>();
        final List<String> imageKey = new ArrayList<>();
        for (int k = 0; k < imgCount; k++) {
            View view = imgLL.getChildAt(k);
            TextView idTV = (TextView) view.findViewById(R.id.tv_id);
            MyGridView myGridView = (MyGridView) view.findViewById(R.id.gv_image);
            LocalGridAdapter gridAdapter = (LocalGridAdapter) myGridView.getAdapter();
            List<String> list = gridAdapter.getmDatas();
            imageKey.add(idTV.getText().toString());
            imageList.add(list);
        }
        //开始同步传文件
        new Thread() {
            @Override
            public void run() {
                try {
                    for (int k = 0; k < imageList.size(); k++) {
                        Response<String> response = HttpUtil.uploadFile(mContext, userId,
                                YS.FileType.FILE_ZY, imageList.get(k));
                        FileUploadBean fileUploadBean = new Gson().fromJson(response.get(),
                                FileUploadBean.class);
                        if (fileUploadBean != null && YS.SUCCESE.equals(fileUploadBean.code) && fileUploadBean.data != null) {
                            resultMap.put(imageKey.get(k), fileUploadBean.data.url);
                        } else {
                            L.e("图片上传失败,请稍后重试！");
                            handler.sendEmptyMessage(UPLOAD_FAIL);
                            break;
                        }
                    }
                } catch (Exception e) {
                    L.e("图片上传失败,请稍后重试！");
                    handler.sendEmptyMessage(UPLOAD_FAIL);
                    e.printStackTrace();
                }
                L.e("图片上传成功！");
                handler.sendEmptyMessage(UPLOAD_SUCC);
            }
        }.start();
    }

    public static final int UPLOAD_SUCC = 100;//成功
    public static final int UPLOAD_FAIL = 101;//失败
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPLOAD_SUCC:
                    waitDialog.dismiss();
                    add();
                    break;
                case UPLOAD_FAIL:
                    waitDialog.dismiss();
                    show("图片上传失败,请稍后重试！");
                    break;
            }
        }
    };

    private void add() {
        String json = new Gson().toJson(resultMap);
        L.e("json=" + json);
        HttpUtil.addResource(mContext, userId, json, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                try {
                    BaseBean baseBean = new Gson().fromJson(response.get(), BaseBean.class);
                    if (baseBean != null && YS.SUCCESE.equals(baseBean.code)) {
                        show(StringUtil.valueOf(baseBean.msg));
                        finish();
                    } else {
                        show("采集资源上报失败!");
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
                double[] gps = GPSUtil.bd09_To_gps84(latitude, longitude);
                gis_jd = StringUtil.valueOf(gps[1]);
                gis_wd = StringUtil.valueOf(gps[0]);
            } else {
                L.e("定位失败==" + location.getLocType());
            }

        }
    }


    private ResourceTypeBean getViewData() {
        Map<String, String> baseMap = new HashMap<>();
        int baseCount = baseLL.getChildCount();
        for (int i = 0; i < baseCount; i++) {
            View view = baseLL.getChildAt(i);
            TextView idTV = (TextView) view.findViewById(R.id.tv_id);
            TextView nameTV = (TextView) view.findViewById(R.id.tv_name);
            LastInputEditText valueET = (LastInputEditText) view.findViewById(R.id.et_value);
            String name = nameTV.getText().toString();
            String value = valueET.getText().toString();
            baseMap.put(name, value);
        }
        Map<String, String> extMap = new HashMap<>();
        int extCount = extLL.getChildCount();
        for (int j = 0; j < extCount; j++) {
            View view = extLL.getChildAt(j);
            TextView idTV = (TextView) view.findViewById(R.id.tv_id);
            TextView nameTV = (TextView) view.findViewById(R.id.tv_name);
            TextView typeTV = (TextView) view.findViewById(R.id.tv_type);
            LastInputEditText valueET = (LastInputEditText) view.findViewById(R.id.et_value);
            String value = valueET.getText().toString();
            extMap.put(nameTV.getText().toString(), value);
        }
        int imgCount = imgLL.getChildCount();
        final List<String> imageList = new ArrayList<>();
        for (int k = 0; k < imgCount; k++) {
            View view = imgLL.getChildAt(k);
            TextView idTV = (TextView) view.findViewById(R.id.tv_id);
            MyGridView myGridView = (MyGridView) view.findViewById(R.id.gv_image);
            LocalGridAdapter gridAdapter = (LocalGridAdapter) myGridView.getAdapter();
            List<String> list = gridAdapter.getmDatas();
            imageList.addAll(list);
        }
        ResourceTypeBean resourceTypeBean = new ResourceTypeBean();
        resourceTypeBean.baseMap = baseMap;
        resourceTypeBean.extMap = extMap;
        resourceTypeBean.imgList = imageList;
        resourceTypeBean.resourceType = currentKVBean.name;
        return resourceTypeBean;
    }


    private void commitByService() {
        String json = new Gson().toJson(recordDetail.resourceTypeBean);
        UploadFireService.startUploadFire(mContext, uuid, recordDetail.imgs, recordDetail.videos,
                recordDetail.map, RecordBean.TYPE_ZIYUAN, "", json,"");
        finish();
    }

    public static void lookLocalResoure(Context context, String uuid, boolean isSucc) {
        Intent intent = new Intent(context, LocalResoureDetailActivity.class);
        intent.putExtra("uuid", uuid);
        intent.putExtra("isSucc", isSucc);
        context.startActivity(intent);
    }
}
