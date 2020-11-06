package com.ys.monitor.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yanzhenjie.nohttp.rest.Response;
import com.yongchun.library.view.ImageSelectorActivity;
import com.ys.monitor.R;
import com.ys.monitor.adapter.GridAdapter;
import com.ys.monitor.adapter.LocalGridAdapter;
import com.ys.monitor.api.FunctionApi;
import com.ys.monitor.base.BaseActivity;
import com.ys.monitor.bean.KVBean;
import com.ys.monitor.bean.UpdateResource;
import com.ys.monitor.http.HttpListener;
import com.ys.monitor.sp.UserSP;
import com.ys.monitor.ui.LastInputEditText;
import com.ys.monitor.ui.MyGridView;
import com.ys.monitor.util.DateUtil;
import com.ys.monitor.util.HttpUtil;
import com.ys.monitor.util.L;
import com.ys.monitor.util.StringUtil;
import com.ys.monitor.util.YS;

import java.util.ArrayList;
import java.util.List;

public class NetResoureDetailActivity extends BaseActivity {
    private String userId;
    private TextView typeTV;
    private List<KVBean> typeList;
    private LinearLayout baseLL, extLL, imgLL;
    private TextView commitTV;
    private String address;
    private UpdateResource updateResource;
    private String recNo;

    @Override
    public int getLayoutId() {
        return R.layout.activity_net_resource_detail;
    }

    @Override
    public void initView() {
        setBarColor("#ffffff");
        titleView.setText("资源采集");
        typeTV = getView(R.id.tv_type);
        typeList = new ArrayList<>();
        baseLL = getView(R.id.ll_base);
        extLL = getView(R.id.ll_ext);
        imgLL = getView(R.id.ll_img);
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
//                        getZiduan(updateResource.data.elementBasic.elementType);
                        addBaseView("name", "名称",
                                StringUtil.valueOf(updateResource.data.elementBasic.name));
                        addBaseView("description", "描述",
                                StringUtil.valueOf(updateResource.data.elementBasic.description));
                        String imgUrl = updateResource.data.elementBasic.imgUrl;
                        if (!StringUtil.isBlank(imgUrl)) {
                            addImgView("imgUrl", "图片", imgUrl);
                        }
                    }
                    if (updateResource.data.elementBasicEx != null && updateResource.data.elementBasicEx.size() > 0) {
                        for (UpdateResource.DataBean.ElementBasicExBean bean :
                                updateResource.data.elementBasicEx) {
                            addExtView(bean.dataName, bean.name, bean.dataValue);
                        }
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
    }


    private void addBaseView(String id, String name, String value) {
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


    private void addExtView(String id, String name, String value) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_ext, null, false);
        TextView idTV = (TextView) view.findViewById(R.id.tv_id);
        TextView nameTV = (TextView) view.findViewById(R.id.tv_name);
        TextView typeTV = (TextView) view.findViewById(R.id.tv_type);
        ImageView mapIV = (ImageView) view.findViewById(R.id.iv_map);
        final LastInputEditText valueET = (LastInputEditText) view.findViewById(R.id.et_value);
        idTV.setText(StringUtil.valueOf(id));
        nameTV.setText(StringUtil.valueOf(name));
        valueET.setText(StringUtil.valueOf(value));
        extLL.addView(view);
    }

    private String currentImgViewId = "";

    private void addImgView(String id, String name, String imgUrl) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_images, null, false);
        TextView idTV = (TextView) view.findViewById(R.id.tv_id);
        final TextView uuidTV = (TextView) view.findViewById(R.id.tv_uuid);
        uuidTV.setText(StringUtil.getUUID());
        TextView nameTV = (TextView) view.findViewById(R.id.tv_name);
        MyGridView myGridView = (MyGridView) view.findViewById(R.id.gv_image);
        myGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        final List<String> list = new ArrayList<>();
        if (imgUrl != null && imgUrl.contains(";")) {
            String[] a = imgUrl.split(";");
            if (a != null && a.length > 0) {
                for (String s : a) {
                    list.add(YS.IP + s);
                }
            }
        }
        LocalGridAdapter mAdapter = new LocalGridAdapter(mContext, list, R.layout.item_grid_image);
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
        idTV.setText(id);
        nameTV.setText(name);
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

    public static void lookNetResourceActivity(Context context, String id) {
        Intent intent = new Intent(context, NetResoureDetailActivity.class);
        intent.putExtra("recNo", id);
        context.startActivity(intent);
    }
}
