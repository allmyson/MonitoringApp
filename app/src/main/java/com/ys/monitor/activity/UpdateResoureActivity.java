package com.ys.monitor.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.gson.Gson;
import com.yanzhenjie.nohttp.rest.Response;
import com.yongchun.library.view.ImageSelectorActivity;
import com.ys.monitor.R;
import com.ys.monitor.adapter.GridAdapter;
import com.ys.monitor.api.FunctionApi;
import com.ys.monitor.base.BaseActivity;
import com.ys.monitor.bean.BaseBean;
import com.ys.monitor.bean.KVBean;
import com.ys.monitor.bean.RecordBean;
import com.ys.monitor.bean.ResourceBean;
import com.ys.monitor.bean.ResourceTypeBean;
import com.ys.monitor.bean.ResourceZDBean;
import com.ys.monitor.bean.UpdateResource;
import com.ys.monitor.dialog.DialogUtil;
import com.ys.monitor.dialog.ListDialogFragment;
import com.ys.monitor.dialog.TipFragment;
import com.ys.monitor.dialog.WaitDialog;
import com.ys.monitor.http.HttpListener;
import com.ys.monitor.service.UploadDataService;
import com.ys.monitor.sp.UserSP;
import com.ys.monitor.ui.LastInputEditText;
import com.ys.monitor.ui.MyGridView;
import com.ys.monitor.util.DateUtil;
import com.ys.monitor.util.GPSUtil;
import com.ys.monitor.util.HttpUtil;
import com.ys.monitor.util.L;
import com.ys.monitor.util.StringUtil;
import com.ys.monitor.util.YS;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpdateResoureActivity extends BaseActivity {
    private String userId;
    private TextView typeTV;
    private List<KVBean> typeList;
    //    private KVBean currentKVBean;
    private LinearLayout baseLL, extLL, imgLL;
    private TextView commitTV;
    private List<ResourceBean.DataBean.RowsBean> rowsBeanList;
    private String number;
    private String gis_jd;
    private String gis_wd;
    private String address;
    private UpdateResource updateResource;
    private String recNo;
    @Override
    public int getLayoutId() {
        return R.layout.activity_resource_update;
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
        getView(R.id.rl_delete).setOnClickListener(this);
        recNo = getIntent().getStringExtra("recNo");
    }

    @Override
    public void getData() {
        userId = UserSP.getUserId(mContext);
        HttpUtil.getResourceValue(mContext, userId, recNo, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                updateResource = new Gson().fromJson(response.get(),
                        UpdateResource.class);
                if (updateResource != null && YS.SUCCESE.equals(updateResource.code) && updateResource.data != null) {
                    if (updateResource.data.elementBasic != null) {
                        typeTV.setText(StringUtil.valueOf(updateResource.data.elementBasic.resourceTypeName) + "_" + StringUtil.valueOf(updateResource.data.elementBasic.elementTypeName));
                        getZiduan(updateResource.data.elementBasic.elementType);
                    }
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
            case R.id.tv_type:
                if (typeList.size() > 0) {
                    DialogUtil.showListFragment(mContext, typeList,
                            new ListDialogFragment.ItemClickListener() {
                                @Override
                                public void clickResult(KVBean listBean) {
                                    removeAllView();
//                                    currentKVBean = listBean;
                                    typeTV.setText(listBean.name);
                                    getZiduan(listBean.id);
                                }
                            });
                }
                break;
            case R.id.tv_commit:
                if (isCanAdd()) {
//                    addResource();
                    commitByService();
                }
                break;
            case R.id.rl_delete:
                DialogUtil.showTip(mContext, "删除确认!", new TipFragment.ClickListener() {
                    @Override
                    public void sure() {
                        deleteByService();
                    }
                });
                break;
        }
    }

    private void getZiduan(String id) {
        HttpUtil.getResourceZiduan(mContext, userId, id, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                try {
                    ResourceZDBean resourceZDBean = new Gson().fromJson(response.get(),
                            ResourceZDBean.class);
                    if (resourceZDBean != null && YS.SUCCESE.equals(resourceZDBean.code)) {
                        if (resourceZDBean.data.ElementBasic != null) {
                            boolean hasImg = false;
                            Class cls = resourceZDBean.data.ElementBasic.getClass();
                            Field[] fields = cls.getDeclaredFields();
                            for (int i = 0; i < fields.length; i++) {
                                Field f = fields[i];
                                f.setAccessible(true);
                                L.e("属性名:" + f.getName() + " 属性值:" + f.get(resourceZDBean.data.ElementBasic));
                                if (!"imgUrl".equals(f.getName()) && !StringUtil.isBlank(f.getName())) {
                                    addBaseView(StringUtil.valueOf(f.getName()),
                                            StringUtil.valueOf(f.get(resourceZDBean.data.ElementBasic)));
                                } else {
                                    hasImg = true;
                                }
                            }
                            if (hasImg) {
                                ResourceZDBean.DataBean.BaseElementExtBean baseElementExtBean =
                                        new ResourceZDBean.DataBean.BaseElementExtBean();
                                baseElementExtBean.dataName = "imgUrl";
                                baseElementExtBean.name = "图片";
                                addImgView(baseElementExtBean);
                            }
                        }

                        if (resourceZDBean.data.BaseElementExt != null && resourceZDBean.data.BaseElementExt.size() > 0) {
                            for (ResourceZDBean.DataBean.BaseElementExtBean baseElementExtBean :
                                    resourceZDBean.data.BaseElementExt) {
                                if (baseElementExtBean != null) {
                                    if ("img".equals(baseElementExtBean.dataName)) {
                                        //图片
                                        addImgView(baseElementExtBean);
                                    } else {
                                        //文本
                                        addExtView(baseElementExtBean);
                                    }
                                }
                            }
                        }
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

    private void addBaseView(String id, String name) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_base, null, false);
        TextView idTV = (TextView) view.findViewById(R.id.tv_id);
        TextView nameTV = (TextView) view.findViewById(R.id.tv_name);
        LastInputEditText valueET = (LastInputEditText) view.findViewById(R.id.et_value);
        idTV.setText(StringUtil.valueOf(id));
        nameTV.setText(StringUtil.valueOf(name));
        if (name != null && name.contains("时间")) {
            valueET.setText(DateUtil.getLongDate3(System.currentTimeMillis()));
        }
        if ("name".equals(id)) {
            valueET.setText(StringUtil.valueOf(updateResource.data.elementBasic.name));
        }
        if ("description".equals(id)) {
            valueET.setText(StringUtil.valueOf(updateResource.data.elementBasic.description));
        }
        if ("createUserNo".equals(id)) {
            valueET.setText(StringUtil.valueOf(updateResource.data.elementBasic.createUserNo));
        }
        if ("recNo".equals(id)) {
            valueET.setText(StringUtil.valueOf(updateResource.data.elementBasic.recNo));
        }
        if ("resourcetypeName".equals(id)) {
            valueET.setText(StringUtil.valueOf(updateResource.data.elementBasic.resourceTypeName));
        }
        if ("createTime".equals(id)) {
            valueET.setText(DateUtil.getLongDate3(updateResource.data.elementBasic.createTime));
        }
        if ("areaCode".equals(id) || "agency".equals(id) || "investigationAddr".equals(id) ||
                "createUserNo".equals(id) || "recNo".equals(id) || "resourcetype".equals(id) ||
                "smallClassNo".equals(id) || "smallPlaceName".equals(id) || "resourcetypeName".equals(id) || "createTime".equals(id)) {
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
        baseLL.addView(view);
    }


    private void addExtView(ResourceZDBean.DataBean.BaseElementExtBean bean) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_ext, null, false);
        TextView idTV = (TextView) view.findViewById(R.id.tv_id);
        TextView nameTV = (TextView) view.findViewById(R.id.tv_name);
        TextView typeTV = (TextView) view.findViewById(R.id.tv_type);
        ImageView mapIV = (ImageView) view.findViewById(R.id.iv_map);
        final LastInputEditText valueET = (LastInputEditText) view.findViewById(R.id.et_value);
        idTV.setText(StringUtil.valueOf(bean.dataName));
        nameTV.setText(StringUtil.valueOf(bean.name));
        typeTV.setText(StringUtil.valueOf(bean.datatype));
        List<UpdateResource.DataBean.ElementBasicExBean> list = updateResource.data.elementBasicEx;
        if (list != null && list.size() > 0) {
            for (UpdateResource.DataBean.ElementBasicExBean elementBasicExBean : list) {
                if (elementBasicExBean.dataName != null && elementBasicExBean.dataName.equals(bean.dataName)) {
                    valueET.setText(StringUtil.valueOf(elementBasicExBean.dataValue));
                }
                if ("investigationAddr".equals(elementBasicExBean.dataName)) {
                    address = StringUtil.valueOf(elementBasicExBean.dataValue);
                }
            }
        }
        if ("longitude".equals(bean.dataName)) {
            mapIV.setVisibility(View.VISIBLE);
            mapIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivityForResult(new Intent(mContext,
                                    BaiduMapSelectAddressActivity.class),
                            1000);
                }
            });
        } else {
            mapIV.setVisibility(View.GONE);
        }
        if ("datetime".equals(bean.datatype)) {
            valueET.setFocusable(false);
            valueET.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogUtil.showDateDialog(mContext, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month,
                                              int dayOfMonth) {
                            String monthStr = (month + 1) > 9 ? String.valueOf((month + 1)) :
                                    "0" + String.valueOf((month
                                            + 1));
                            String dayStr = dayOfMonth > 9 ? String.valueOf(dayOfMonth) :
                                    "0" + String.valueOf(dayOfMonth);
                            final String result = year + "-" + monthStr + "-" + dayStr;
                            L.e(result);
//                            valueET.setText(result);
                            DialogUtil.showDateDialogSF(mContext,
                                    new TimePickerDialog.OnTimeSetListener() {
                                        @Override
                                        public void onTimeSet(TimePicker view, int hourOfDay,
                                                              int minute) {
                                            String hourOfDayStr = hourOfDay > 9 ?
                                                    String.valueOf(hourOfDay) :
                                                    "0" + String.valueOf(hourOfDay);
                                            String minuteStr = minute > 9 ? String.valueOf(minute) :
                                                    "0" + String.valueOf(minute);
                                            String time = hourOfDayStr + ":" + minuteStr;
                                            valueET.setText(result + " " + time);
                                        }
                                    });
                        }
                    });
                }
            });
        }
        extLL.addView(view);
    }

    private String currentImgViewId = "";

    private void addImgView(ResourceZDBean.DataBean.BaseElementExtBean bean) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_images, null, false);
        TextView idTV = (TextView) view.findViewById(R.id.tv_id);
        final TextView uuidTV = (TextView) view.findViewById(R.id.tv_uuid);
        uuidTV.setText(StringUtil.getUUID());
        TextView nameTV = (TextView) view.findViewById(R.id.tv_name);
        MyGridView myGridView = (MyGridView) view.findViewById(R.id.gv_image);
        myGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        final List<String> list = new ArrayList<>();
        String imgUrl = updateResource.data.elementBasic.imgUrl;
        if (imgUrl != null && imgUrl.contains(";")) {
            String[] a = imgUrl.split(";");
            if (a != null && a.length > 0) {
                for (String s : a) {
                    list.add(YS.IP + s);
                }
            }
        }
        GridAdapter mAdapter = new GridAdapter(mContext, list, R.layout.item_grid_image);
        myGridView.setAdapter(mAdapter);
        myGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == list.size()) {
                    FunctionApi.takePicture(mContext, 9 - list.size(), 1, true, false, false);
                    currentImgViewId = uuidTV.getText().toString();
                    L.e("currentImgViewId" + currentImgViewId);
                } else {
                    FunctionApi.LargerImage(mContext, list, position);

                }
            }
        });
        idTV.setText(StringUtil.valueOf(bean.dataName));
        nameTV.setText(StringUtil.valueOf(bean.name));
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
                case 1000:
                    String address = data.getStringExtra("address");
                    String lon = data.getStringExtra("lon");
                    String lat = data.getStringExtra("lat");
                    L.e("address=" + address + "--lon=" + lon + "--lat=" + lat);
                    double[] gps = GPSUtil.bd09_To_gps84(StringUtil.StringToDouble(lat),
                            StringUtil.StringToDouble(lon));
                    gis_jd = StringUtil.valueOf(gps[1]);
                    gis_wd = StringUtil.valueOf(gps[0]);
                    int extCount = extLL.getChildCount();
                    for (int j = 0; j < extCount; j++) {
                        View view = extLL.getChildAt(j);
                        TextView nameTV = (TextView) view.findViewById(R.id.tv_name);
                        TextView idTV = (TextView) view.findViewById(R.id.tv_id);
                        LastInputEditText valueET =
                                (LastInputEditText) view.findViewById(R.id.et_value);
                        String value = idTV.getText().toString();
                        if ("longitude".equals(value)) {
                            valueET.setText(gis_jd);
                        }
                        if ("latitude".equals(value)) {
                            valueET.setText(gis_wd);
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
            if (StringUtil.isBlank(value) && !"描述".equals(nameTV.getText().toString()) && !"资源类型".equals(nameTV.getText().toString())) {
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
            if ("经度".equals(nameTV.getText().toString()) || "纬度".equals(nameTV.getText().toString())) {
                if (StringUtil.isBlank(value)) {
                    show(nameTV.getText().toString() + "不能为空！");
                    isCan = false;
                    break;
                }
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
            GridAdapter gridAdapter = (GridAdapter) myGridView.getAdapter();
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


    private ResourceTypeBean getViewData() {
        Map<String, String> baseMap = new HashMap<>();
        int baseCount = baseLL.getChildCount();
        for (int i = 0; i < baseCount; i++) {
            View view = baseLL.getChildAt(i);
            if (view.getVisibility() == View.VISIBLE) {
                TextView idTV = (TextView) view.findViewById(R.id.tv_id);
                TextView nameTV = (TextView) view.findViewById(R.id.tv_name);
                LastInputEditText valueET = (LastInputEditText) view.findViewById(R.id.et_value);
                String name = nameTV.getText().toString();
                String value = valueET.getText().toString();
                baseMap.put(name, value);
            }
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
            GridAdapter gridAdapter = (GridAdapter) myGridView.getAdapter();
            List<String> list = gridAdapter.getmDatas();
            imageList.addAll(list);
        }
        ResourceTypeBean resourceTypeBean = new ResourceTypeBean();
        resourceTypeBean.baseMap = baseMap;
        resourceTypeBean.extMap = extMap;
        resourceTypeBean.imgList = imageList;
        resourceTypeBean.resourceType = typeTV.getText().toString();
        return resourceTypeBean;
    }


    private void commitByService() {
        resultMap.clear();
        resultMap.put(" isDelete", "0");
        resultMap.put(" recNo", updateResource.data.elementBasic.recNo);
        resultMap.put("elementType", updateResource.data.elementBasic.elementType);
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
        final ArrayList<String> imageList = new ArrayList<>();
        final List<String> imageKey = new ArrayList<>();
        for (int k = 0; k < imgCount; k++) {
            View view = imgLL.getChildAt(k);
            TextView idTV = (TextView) view.findViewById(R.id.tv_id);
            MyGridView myGridView = (MyGridView) view.findViewById(R.id.gv_image);
            GridAdapter gridAdapter = (GridAdapter) myGridView.getAdapter();
            List<String> list = gridAdapter.getmDatas();
            imageKey.add(idTV.getText().toString());
            imageList.addAll(list);
        }
        ResourceTypeBean resourceTypeBean = getViewData();
        String json = new Gson().toJson(resourceTypeBean);
        UploadDataService.startUploadFire(mContext, "", imageList, new ArrayList<>(), resultMap,
                RecordBean.TYPE_ZIYUAN, RecordBean.DO_UPDATE, json, address);
        finish();
    }



    private void deleteByService() {
        resultMap.clear();
        resultMap.put(" isDelete", "0");
        resultMap.put(" recNo", updateResource.data.elementBasic.recNo);
        resultMap.put("elementType", updateResource.data.elementBasic.elementType);
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
        final ArrayList<String> imageList = new ArrayList<>();
        final List<String> imageKey = new ArrayList<>();
        for (int k = 0; k < imgCount; k++) {
            View view = imgLL.getChildAt(k);
            TextView idTV = (TextView) view.findViewById(R.id.tv_id);
            MyGridView myGridView = (MyGridView) view.findViewById(R.id.gv_image);
            GridAdapter gridAdapter = (GridAdapter) myGridView.getAdapter();
            List<String> list = gridAdapter.getmDatas();
            imageKey.add(idTV.getText().toString());
            imageList.addAll(list);
        }
        ResourceTypeBean resourceTypeBean = getViewData();
        String json = new Gson().toJson(resourceTypeBean);
        UploadDataService.startUploadFire(mContext, "", imageList, new ArrayList<>(), resultMap,
                RecordBean.TYPE_ZIYUAN, RecordBean.DO_DELETE, json, address);
        finish();
    }

    public static void updateResourceActivity(Context context, String id) {
        Intent intent = new Intent(context, UpdateResoureActivity.class);
        intent.putExtra("recNo", id);
        context.startActivity(intent);
    }
}
