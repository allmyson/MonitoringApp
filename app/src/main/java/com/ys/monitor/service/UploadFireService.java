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
import java.util.Map;

/**
 * @author lh
 * @version 1.0.0
 * @filename UploadFireService
 * @description -------------------------------------------------------
 * @date 2020/10/26 13:58
 */
public class UploadFireService extends IntentService {
    private String userId;
    private static final String ACTION_UPLOAD_FIRE = "com.ys.monitor.intentservice.action" +
            ".UPLOAD_FIRE";

    public UploadFireService() {
        super("UploadFireService");
    }

    public UploadFireService(String name) {
        super(name);
    }

    public static void startUploadFire(Context context, ArrayList<String> imgs,
                                       ArrayList<String> videos, Map<String, String> map) {
        Intent intent = new Intent(context, UploadFireService.class);
        intent.setAction(ACTION_UPLOAD_FIRE);
        intent.putStringArrayListExtra("imgs", imgs);
        intent.putStringArrayListExtra("videos", videos);
        intent.putExtra("map", (Serializable) map);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPLOAD_FIRE.equals(action)) {
                ArrayList<String> imgs = intent.getStringArrayListExtra("imgs");
                ArrayList<String> videos = intent.getStringArrayListExtra("videos");
                Map<String, String> map = (Map<String, String>) intent.getSerializableExtra("map");
                handleUploadFire(imgs, videos, map);
            }
        }
    }

    private void handleUploadFire(ArrayList<String> imgs,
                                  ArrayList<String> videos, Map<String, String> map) {
        RecordBean recordBean = new RecordBean();
        recordBean.id = StringUtil.getUUID();
        recordBean.createTime = System.currentTimeMillis();
        recordBean.name = RecordBean.TYPE_FIRE;
        recordBean.address = map.get("siteSplicing");
        RecordDetail recordDetail = new RecordDetail();
        recordDetail.id = recordBean.id;
        recordDetail.imgs = imgs;
        recordDetail.videos = videos;
        recordDetail.map = map;
        String detailJson = new Gson().toJson(recordDetail);
        L.e("detailJson="+detailJson);
        SPUtil.put(getBaseContext(),recordBean.id,detailJson);
        try {
            Map<String, String> dataMap = map;
            String imageUrls = null;
            String videoUrls = null;
            if (imgs.size() > 0) {
                Response<String> response = HttpUtil.uploadFile(getBaseContext(), userId,
                        YS.FileType.FILE_FIRE, imgs);
                FileUploadBean fileUploadBean = new Gson().fromJson(response.get(),
                        FileUploadBean.class);
                if (fileUploadBean != null && YS.SUCCESE.equals(fileUploadBean.code) && fileUploadBean.data != null) {
                    imageUrls = fileUploadBean.data.url;
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
            dataMap.put("videoUrl", StringUtil.valueOf(videoUrls));
            String data = new Gson().toJson(map);
            L.e("data=" + data);
            Response<String> response = HttpUtil.addFireSync(getBaseContext(), userId, data);
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
                    ToastUtil.show(getBaseContext(), "火情添加失败");
                    break;
                case UPLOAD_SUCC:
                    ToastUtil.show(getBaseContext(), "火情添加成功");
                    break;
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        userId = UserSP.getUserId(getBaseContext());
        L.e(UploadFireService.this.getClass().getName() + "onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        L.e(UploadFireService.this.getClass().getName() + "onDestroy");
    }
}
