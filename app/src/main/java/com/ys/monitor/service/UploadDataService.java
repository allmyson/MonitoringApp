package com.ys.monitor.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.yanzhenjie.nohttp.rest.Response;
import com.ys.monitor.bean.BaseBean;
import com.ys.monitor.bean.FileUploadBean;
import com.ys.monitor.bean.RecordBean;
import com.ys.monitor.bean.RecordDetail;
import com.ys.monitor.bean.ResourceTypeBean;
import com.ys.monitor.fragment.RecordFragment;
import com.ys.monitor.sp.RecordSP;
import com.ys.monitor.sp.UserSP;
import com.ys.monitor.util.HttpUtil;
import com.ys.monitor.util.L;
import com.ys.monitor.util.SPUtil;
import com.ys.monitor.util.StringUtil;
import com.ys.monitor.util.ToastUtil;
import com.ys.monitor.util.YS;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author lh
 * @version 1.0.0
 * @filename UploadFireService
 * @description -------------------------------------------------------
 * @date 2020/10/26 13:58
 */
public class UploadDataService extends IntentService {
    private String userId;
    private static final String ACTION_UPLOAD_FIRE = "com.ys.monitor.intentservice.action" +
            ".UPLOAD_FIRE";

    public UploadDataService() {
        super("UploadFireService");
    }

    public UploadDataService(String name) {
        super(name);
    }

    public static void startUploadFire(Context context, String uuid, ArrayList<String> imgs,
                                       ArrayList<String> videos, Map<String, String> map,
                                       String typeName) {
        Intent intent = new Intent(context, UploadDataService.class);
        intent.setAction(ACTION_UPLOAD_FIRE);
        intent.putExtra("uuid", uuid);
        intent.putExtra("type", typeName);
        intent.putStringArrayListExtra("imgs", imgs);
        intent.putStringArrayListExtra("videos", videos);
        intent.putExtra("map", (Serializable) map);
        context.startService(intent);
    }

    public static void startUploadFire(Context context, String uuid, ArrayList<String> imgs,
                                       ArrayList<String> videos, Map<String, String> map,
                                       String typeName, String handleType, String view,
                                       String address) {
        Intent intent = new Intent(context, UploadDataService.class);
        intent.setAction(ACTION_UPLOAD_FIRE);
        intent.putExtra("address", address);
        intent.putExtra("uuid", uuid);
        intent.putExtra("view", view);
        intent.putExtra("type", typeName);
        intent.putExtra("handleType", handleType);
        intent.putStringArrayListExtra("imgs", imgs);
        intent.putStringArrayListExtra("videos", videos);
        intent.putExtra("map", (Serializable) map);
        context.startService(intent);
    }


    public static void startUploadFire(Context context, String uuid, ArrayList<String> imgs,
                                       ArrayList<String> videos, Map<String, String> map,
                                       String typeName, String handleType, String view,
                                       String address, ArrayList<String> netImgs) {
        Intent intent = new Intent(context, UploadDataService.class);
        intent.setAction(ACTION_UPLOAD_FIRE);
        intent.putExtra("address", address);
        intent.putExtra("uuid", uuid);
        intent.putExtra("view", view);
        intent.putExtra("type", typeName);
        intent.putExtra("handleType", handleType);
        intent.putStringArrayListExtra("imgs", imgs);
        intent.putStringArrayListExtra("netImgs", netImgs);
        intent.putStringArrayListExtra("videos", videos);
        intent.putExtra("map", (Serializable) map);
        context.startService(intent);
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPLOAD_FIRE.equals(action)) {
                String uuid = intent.getStringExtra("uuid");
                String address = intent.getStringExtra("address");
                String view = intent.getStringExtra("view");
                ResourceTypeBean resourceTypeBean = null;
                if (StringUtil.isGoodJson(view)) {
                    resourceTypeBean = new Gson().fromJson(view, ResourceTypeBean.class);
                }
                String handleType = intent.getStringExtra("handleType");
                String typeName = intent.getStringExtra("type");
                ArrayList<String> imgs = intent.getStringArrayListExtra("imgs");
                ArrayList<String> netImgs = intent.getStringArrayListExtra("netImgs");
                ArrayList<String> videos = intent.getStringArrayListExtra("videos");
                Map<String, String> map = (Map<String, String>) intent.getSerializableExtra("map");
                handleUploadFire(uuid, imgs, videos, map, typeName, handleType, resourceTypeBean,
                        address,netImgs);
            }
        }
    }

    private void handleUploadFire(String uuid, ArrayList<String> imgs,
                                  ArrayList<String> videos, Map<String, String> map,
                                  String typeName) {
        handleUploadFire(uuid, imgs, videos, map, typeName, "", null, "");
    }

    private void handleUploadFire(String uuid, ArrayList<String> imgs,
                                  ArrayList<String> videos, Map<String, String> map,
                                  String typeName, String handleType,
                                  ResourceTypeBean resourceTypeBean, String address) {
        handleUploadFire(uuid, imgs, videos, map, typeName, handleType, resourceTypeBean, address
                , new ArrayList<>());
    }

    private void handleUploadFire(String uuid, ArrayList<String> imgs,
                                  ArrayList<String> videos, Map<String, String> map,
                                  String typeName, String handleType,
                                  ResourceTypeBean resourceTypeBean, String address,
                                  ArrayList<String> netImgs) {
        RecordBean recordBean = new RecordBean();
        L.e("typeName=" + typeName);
        if (StringUtil.isBlank(uuid)) {
            L.e("uuid == null,add fire");
            recordBean.id = StringUtil.getUUID();
            recordBean.handleType = StringUtil.valueOf(handleType);
            recordBean.createTime = System.currentTimeMillis();
            recordBean.name = typeName;
            if (!StringUtil.isBlank(address)) {
                recordBean.address = address;
            } else {
                recordBean.address = map.get("siteSplicing");
//                if (StringUtil.isBlank(recordBean.address)) {
//                    recordBean.address = map.get("investigationAddr");
//                }
            }
            RecordDetail recordDetail = new RecordDetail();
            recordDetail.id = recordBean.id;
            recordDetail.imgs = imgs;
            recordDetail.videos = videos;
            recordDetail.map = map;
            recordDetail.resourceTypeBean = resourceTypeBean;
            String detailJson = new Gson().toJson(recordDetail);
            L.e("detailJson=" + detailJson);
            SPUtil.put(getBaseContext(), recordBean.id, detailJson);
        } else {
            List<RecordBean> list = RecordSP.getRecordList(getBaseContext());
            if (list != null && list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    if (uuid.equals(list.get(i).id)) {
                        recordBean.id = list.get(i).id;
                        recordBean.handleType = list.get(i).handleType;
                        recordBean.createTime = list.get(i).createTime;
                        recordBean.name = list.get(i).name;
                        recordBean.address = list.get(i).address;
                        break;
                    }
                }
            }
//            recordBean.id = uuid;
            L.e("uuid != null,reAdd fire");
        }

        try {
            Map<String, String> dataMap = map;
            String imageUrls = "";
            String videoUrls = "";
            if (netImgs != null && netImgs.size() > 0) {
                for (String net : netImgs) {
                    imageUrls += net + ";";
                }
            }
            if (imgs.size() > 0) {
                Response<String> response = HttpUtil.uploadFile(getBaseContext(), userId,
                        YS.FileType.FILE_FIRE, imgs);
                FileUploadBean fileUploadBean = new Gson().fromJson(response.get(),
                        FileUploadBean.class);
                if (fileUploadBean != null && YS.SUCCESE.equals(fileUploadBean.code) && fileUploadBean.data != null) {
                    imageUrls += fileUploadBean.data.url;
                    L.e("图片上传成功！imageUrls=" + imageUrls);
                } else {
                    L.e("图片上传失败！");
                    recordBean.isSucc = false;
                    RecordSP.addRecord(getBaseContext(), recordBean);
                    handler.sendEmptyMessage(UPLOAD_IMAGE_FAIL);
                    return;
                }
            } else {
                L.e("暂无图片！");
            }
            if (videos.size() > 0) {
                Response<String> response = HttpUtil.uploadFile(getBaseContext(), userId,
                        YS.FileType.FILE_FIRE, videos);
                FileUploadBean fileUploadBean = new Gson().fromJson(response.get(),
                        FileUploadBean.class);
                if (fileUploadBean != null && YS.SUCCESE.equals(fileUploadBean.code) && fileUploadBean.data != null) {
                    videoUrls = fileUploadBean.data.url;
                    L.e("视频上传失败！videoUrls=" + videoUrls);
                } else {
                    L.e("视频上传失败！");
                    recordBean.isSucc = false;
                    RecordSP.addRecord(getBaseContext(), recordBean);
                    handler.sendEmptyMessage(UPLOAD_VIDEO_FAIL);
                    return;
                }
            } else {
                L.e("暂无视频！");
            }
            dataMap.put("imgUrl", StringUtil.valueOf(imageUrls));
            if (!RecordBean.TYPE_ZIYUAN.equals(recordBean.name)) {
                dataMap.put("videoUrl", StringUtil.valueOf(videoUrls));
            }
            String data = new Gson().toJson(map);
            L.e("data=" + data);
            Response<String> response = HttpUtil.addDataSync(getBaseContext(), userId, data,
                    recordBean.name, recordBean.handleType);
            BaseBean baseBean = new Gson().fromJson(response.get(), BaseBean.class);
            if (baseBean != null && YS.SUCCESE.equals(baseBean.code)) {
                L.e("上报成功!");
                recordBean.isSucc = true;
                RecordSP.addRecord(getBaseContext(), recordBean);
                handler.sendEmptyMessage(UPLOAD_SUCC);
            } else {
                L.e("上报失败!");
                recordBean.isSucc = false;
                RecordSP.addRecord(getBaseContext(), recordBean);
                handler.sendEmptyMessage(UPLOAD_FAIL);
            }
        } catch (Exception e) {
            e.printStackTrace();
            L.e("上报异常" + e.getMessage());
            recordBean.isSucc = false;
            RecordSP.addRecord(getBaseContext(), recordBean);
        }
    }

    private static final int UPLOAD_SUCC = 8;
    private static final int UPLOAD_FAIL = 9;
    private static final int UPLOAD_IMAGE_FAIL = 10;
    private static final int UPLOAD_VIDEO_FAIL = 11;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case UPLOAD_IMAGE_FAIL:
//                    ToastUtil.show(getBaseContext(), "图片上传失败");
//                    ToastUtil.show(getBaseContext(), "火情添加失败");
//                    break;
                case UPLOAD_VIDEO_FAIL:
//                    ToastUtil.show(getBaseContext(),"视频上传失败");
//                    ToastUtil.show(getBaseContext(), "火情添加失败");
                case UPLOAD_FAIL:
                    ToastUtil.show(getBaseContext(), "上报失败");
//                    sendMsg();
                    break;
                case UPLOAD_SUCC:
                    ToastUtil.show(getBaseContext(), "上报成功");
                    sendMsg();
                    break;
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        userId = UserSP.getUserId(getBaseContext());
        L.e(UploadDataService.this.getClass().getName() + "onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        L.e(UploadDataService.this.getClass().getName() + "onDestroy");
    }

    private void sendMsg() {
        Intent intent = new Intent(RecordFragment.UPLOAD_RESULT);
        sendBroadcast(intent);
    }
}
