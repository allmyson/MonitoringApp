package com.ys.monitor.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
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
import com.ys.monitor.bean.FileUploadBean;
import com.ys.monitor.bean.KVBean;
import com.ys.monitor.bean.ResourceBean;
import com.ys.monitor.bean.ResourceZDBean;
import com.ys.monitor.dialog.DialogUtil;
import com.ys.monitor.dialog.ListDialogFragment;
import com.ys.monitor.dialog.WaitDialog;
import com.ys.monitor.http.HttpListener;
import com.ys.monitor.sp.UserSP;
import com.ys.monitor.ui.LastInputEditText;
import com.ys.monitor.ui.MyGridView;
import com.ys.monitor.util.DateUtil;
import com.ys.monitor.util.HttpUtil;
import com.ys.monitor.util.L;
import com.ys.monitor.util.StringUtil;
import com.ys.monitor.util.YS;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResoureActivity extends BaseActivity {
    private String userId;
    private TextView typeTV;
    private List<KVBean> typeList;
    private KVBean currentKVBean;
    private LinearLayout baseLL, extLL, imgLL;
    private TextView commitTV;
    private List<ResourceBean.DataBean.RowsBean> rowsBeanList;
    private ResourceBean.DataBean.RowsBean currentRowsBean;

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
        typeTV.setOnClickListener(this);
        typeList = new ArrayList<>();
        rowsBeanList = new ArrayList<>();
        baseLL = getView(R.id.ll_base);
        extLL = getView(R.id.ll_ext);
        imgLL = getView(R.id.ll_img);
        commitTV = getView(R.id.tv_commit);
        commitTV.setOnClickListener(this);
    }

    @Override
    public void getData() {
        userId = UserSP.getUserId(mContext);
        //获取资源类型
        HttpUtil.getResourceTypeList(mContext, userId, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                typeList.clear();
                rowsBeanList.clear();
                try {
                    ResourceBean resourceBean = new Gson().fromJson(response.get(),
                            ResourceBean.class);
                    if (resourceBean != null && YS.SUCCESE.equals(resourceBean.code) && resourceBean.data != null && resourceBean.data.rows != null && resourceBean.data.rows.size() > 0) {
                        rowsBeanList.addAll(resourceBean.data.rows);
                        for (ResourceBean.DataBean.RowsBean rowsBean : resourceBean.data.rows) {
                            KVBean kvBean = new KVBean(rowsBean.recNo,
                                    rowsBean.resourcetypeName + "_" + rowsBean.name);
                            typeList.add(kvBean);
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
                                    currentKVBean = listBean;
                                    typeTV.setText(listBean.name);
                                    currentRowsBean = getCurrentRowsBean();
                                    getZiduan(listBean.id);
                                }
                            });
                }
                break;
            case R.id.tv_commit:
                if (isCanAdd()) {
                    addResource();
                }
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
                            Class cls = resourceZDBean.data.ElementBasic.getClass();
                            Field[] fields = cls.getDeclaredFields();
                            for (int i = 0; i < fields.length; i++) {
                                Field f = fields[i];
                                f.setAccessible(true);
                                L.e("属性名:" + f.getName() + " 属性值:" + f.get(resourceZDBean.data.ElementBasic));
                                addBaseView(StringUtil.valueOf(f.getName()),
                                        StringUtil.valueOf(f.get(resourceZDBean.data.ElementBasic)));
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
        if ("createUserNo".equals(id)) {
            valueET.setText(userId);
        }
        if (currentRowsBean != null) {
            if ("resourcetype".equals(id)) {
//                L.e("resourcetype=" + currentRowsBean.resourcetype);
                valueET.setText(StringUtil.valueOf(currentRowsBean.resourcetype));
            } else if ("resourcetypeName".equals(id)) {
                valueET.setText(StringUtil.valueOf(currentRowsBean.resourcetypeName));
            }
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


    private void addExtView(ResourceZDBean.DataBean.BaseElementExtBean bean) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_ext, null, false);
        TextView idTV = (TextView) view.findViewById(R.id.tv_id);
        TextView nameTV = (TextView) view.findViewById(R.id.tv_name);
        TextView typeTV = (TextView) view.findViewById(R.id.tv_type);
        final LastInputEditText valueET = (LastInputEditText) view.findViewById(R.id.et_value);
        idTV.setText(StringUtil.valueOf(bean.dataName));
        nameTV.setText(StringUtil.valueOf(bean.name));
        typeTV.setText(StringUtil.valueOf(bean.datatype));
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
                                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
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

    private Map<String, Object> resultMap;
    private WaitDialog waitDialog;

    private void addResource() {
        waitDialog.show();
        resultMap.clear();
        resultMap.put("elementType",currentKVBean.id);
        L.e("参数齐全,可以提交");
        int baseCount = baseLL.getChildCount();
        for (int i = 0; i < baseCount; i++) {
            View view = baseLL.getChildAt(i);
            TextView idTV = (TextView) view.findViewById(R.id.tv_id);
            LastInputEditText valueET = (LastInputEditText) view.findViewById(R.id.et_value);
            String value = valueET.getText().toString();
            resultMap.put(idTV.getText().toString(), value);
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
            GridAdapter gridAdapter = (GridAdapter) myGridView.getAdapter();
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
}
