package com.ys.zy.web;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;

import com.google.gson.Gson;
import com.yanzhenjie.nohttp.rest.Response;
import com.yongchun.library.view.ImageSelectorActivity;
import com.ys.zy.api.FunctionApi;
import com.ys.zy.util.L;
import com.ys.zy.util.SPUtil;
import com.ys.zy.util.StringUtil;
import com.ys.zy.util.ToastUtil;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author lh
 * @version 1.0.0
 * @filename JSApi
 * @description -------------------------------------------------------
 * @date 2018/9/10 15:55
 */
public class JSApi {

    private Context mContext;
    //    private SQLiteOpenHelper mSqLiteOpenHelper;
    private String resultStr;
    private UpsoftWebview mWebView;
    public static final int SCANCODE_REQUST = 141;
    public static final int SCANNFC_REQUST = 142;
    public static final int REQUEST_FOR_PHOTO = 101; //相机返回
    public static final int REQUEST_FOR_VIDEO = 103; //摄像返回
    public static final int REQUEST_FOR_AUDIO = 105;//录音返回地址


    public JSApi(Context mContext, UpsoftWebview mWebView) {
        this.mContext = mContext;
        this.mWebView = mWebView;
//        this.mSqLiteOpenHelper = mSqLiteOpenHelper;
    }


    /**
     * 键值对存
     */
    @JavascriptInterface
    public String saveKeyValue(String key, String value) {
        L.e("键值对存", "key--" + key + "《《》》value-----" + value);
        boolean result = SPUtil.put(this.mContext, key, value);
        return result ? "1" : "0";
    }


    @JavascriptInterface
    public String saveKeyValue(String type, String key, String value) {
        L.e("存》》》》》》》》》》》", "key--" + type+"_"+key + "《《》》value----" + value);
        boolean result = SPUtil.put(this.mContext, type + "_" + key, value);
        return result ? "1" : "0";
    }


    /**
     * 键值对取
     */
    @JavascriptInterface
    public String searchKeyValue(String key) {
        String result = SPUtil.get(this.mContext, key, "") + "";
        L.e("键值对取", "key--" + key + "《《》》value---" + result);
        return result + "";
    }

    @JavascriptInterface
    public String searchKeyValue(String type, String key) {
        String result = SPUtil.get(mContext, type + "_" + key, "") + "";
        L.e("取》》》》》》》》》》》", "key--" + key + "《《》》value----" + result);
        return result;
    }


    @JavascriptInterface
    public String delKeyValue(String key) {
        boolean result = SPUtil.deleteKey(this.mContext, key);
        L.e("xxxxxxx"+key+"删除过后=1=="+searchKeyValue(key));
        return result ? "1" : "0";
    }

    @JavascriptInterface
    public String delKeyValue(String type, String key) {
        boolean result = SPUtil.deleteKey(this.mContext, type + "_" + key);
        L.e(type+"xxxxxxx"+key+"删除过后2==="+searchKeyValue(type + "_" + key));
        return result ? "1" : "0";
    }


    @JavascriptInterface
    public String getAllKey() {
        String result = "";
        List keyList = SPUtil.getAllKey(this.mContext);

        try {
            JSONArray jsonArray = new JSONArray();
            Iterator var4 = keyList.iterator();

            while (var4.hasNext()) {
                String str = (String) var4.next();

                L.e(str+"==顺便取值=="+searchKeyValue(str));

                jsonArray.put(str);
            }

            result = jsonArray.toString();
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        return result;
    }


    /**
     * 设备信息获取
     */
    @JavascriptInterface
    public String getBaseData(String key) {
        L.e("getBaseData", key + "");
        String resut = "";
        if (!TextUtils.isEmpty(key)) {
            if (key.equals("system_type")) {//获取平台
                resut = "android";
            } else if (key.equals("ip")) {//获取Ip
                resut = SPUtil.get(mContext, "ip", "") + "";
            }
//            else if (key.equals("user_id")) {//用户id
//                resut = JsDataSp.getUserId(mContext);
//            }
        } else {
            resut = "数据获取失败";
        }
        L.e("getBaseDataBack", resut);
        return resut;
    }


    /**
     * 日志打印
     */
    @JavascriptInterface
    public void writeLog(String type, String text) {
        if (TextUtils.isEmpty(type)) {
            L.e("JsLog", text);
        } else {
            L.e(type, text);
        }
    }


    /**
     * 对话框
     */
    @JavascriptInterface
    public void showDialog(String title, String message, final String buttonA, final String buttonB) {
        L.e("窗口", title + "<<>>" + message + "<<>>" + buttonA + "<<>>" + buttonB);
        AlertDialog.Builder mDialog = new AlertDialog.Builder(mContext);
        if (!TextUtils.isEmpty(title)) {
            mDialog.setTitle(StringUtil.valueOf(title));
        }
        if (!TextUtils.isEmpty(message)) {
            mDialog.setMessage(StringUtil.valueOf(message));
        }
        if (!TextUtils.isEmpty(buttonA)) {
            mDialog.setNegativeButton(buttonA, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    jsResult("yxapp.ui.dialogBack.onClickBack", buttonA);
                }
            });
        }
        //设置第二个按钮
        if (!TextUtils.isEmpty(buttonB)) {
            mDialog.setPositiveButton(buttonB, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    jsResult("yxapp.ui.dialogBack.onClickBack", buttonB);
                }
            });
        }
        mDialog.create().show();
    }


    /**
     * 电话
     *
     * @param phone
     * @param type
     */
    @JavascriptInterface
    public void call(String phone, String type) {
        NativeUtil.playTel(this.mContext, phone, type);
    }


//    /**
//     * 显示列表框
//     *
//     * @param list
//     */
//    @JavascriptInterface
//    public void showListDialog(String[] list) {
//
//        L.e("列表框》》", list.toString());
//
//        List<String> li = new ArrayList<>();
//        for (int i = 0; i < list.length; i++) {
//            li.add(list[i]);
//        }
//        ListDialog listDialog = new ListDialog(mContext, li, new ListDialog.MyListDialogListener() {
//            @Override
//            public void back(String name) {
//                jsResult("yxapp.ui.dialogListBack.onClickBack", name);
//            }
//        });
//        listDialog.show();
//    }


    @JavascriptInterface
    public void getPicture(int type) {//type--0拍照
        if (0 == type) {
            //拍照
            Intent intent= new Intent(mContext, ImageSelectorActivity.class);
            intent.putExtra(ImageSelectorActivity.EXTRA_MAX_SELECT_NUM,1);
            intent.putExtra(ImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
            ((Activity)mContext).startActivityForResult(intent, REQUEST_FOR_PHOTO);
        } else if (1 == type) {
            FunctionApi.takePicture(mContext, 9, 1, false, false, false);
        }
    }

    @JavascriptInterface
    public void choosePicture(int max, int mode, boolean showCamera, boolean enablePreview, boolean enableCrop) {
        FunctionApi.takePicture(mContext, max, mode, showCamera, enablePreview, enableCrop);
    }

    @JavascriptInterface
    public void openCamera() {
        NativeUtil.intentToCamera(mContext, REQUEST_FOR_PHOTO);
    }

//    @JavascriptInterface
//    public void ntNfc(int intentOrReturn, String className) {
//        FunctionApi.intentToNFC((Activity) mContext, SCANNFC_REQUST);
//    }
//
//    @JavascriptInterface
//    public void nfcScan(int intentOrReturn, String className) {
//        FunctionApi.intentToNFC((Activity) mContext, SCANNFC_REQUST);
//    }


//    /**
//     * 跳转原生界面
//     *
//     * @param activityClass
//     * @param action
//     * @param params
//     * @return
//     */
//    @JavascriptInterface
//    public boolean openActivity(String activityClass, String action, String params) {
//        boolean success = false;
//        try {
//            ToActivityUtil.startIntentClassName(mContext, activityClass, action, params);
//            success = true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            L.e("start_activity", "类名称错误-Class-" + activityClass);
//            success = false;
//        }
//        return success;
//    }


//    /**
//     * 扫描二维码
//     */
//    @JavascriptInterface
//    public void scanCode() {
//        FunctionApi.intentToCapture((Activity) mContext, SCANCODE_REQUST);
//    }


    /**
     * 关闭当前页面
     */
    @JavascriptInterface
    public void close() {
        L.e("Js", "关闭页面");
        ((Activity) mContext).finish();
    }

    /**
     * 返回
     */
    @JavascriptInterface
    public void goBack() {
        L.e("Js", "返回");
        Message message = new Message();
        message.what = 9;
        mHandler.sendMessage(message);

    }

//    /**
//     * 返回
//     */
//    @JavascriptInterface
//    public void uploadFile(String filePath, String catalogId, String desc) {
//        Message message = new Message();
//        message.what = 100;
//        final String catalogIdStr = StringUtil.isBlank(catalogId) ? StringUtil.getUUID() : catalogId;
//        FunctionApi.uploadFile(mContext, filePath, catalogIdStr,desc, new HttpListener<String>() {
//            @Override
//            public void onSucceed(int what, Response<String> response) {
//                final UploadFileBean bean = new Gson().fromJson(response.get(), UploadFileBean.class);
//                if (bean != null && "1".equals(bean.status)) {
//                    String fileId = bean.fileId;
//                    Bundle bundle = new Bundle();
//                    bundle.putString("fileId", fileId);
//                    bundle.putString("catalogId", catalogIdStr);
//                    message.setData(bundle);
//                    mHandler.sendMessage(message);
//                } else {
//                    ToastUtil.show(mContext, "文件上传失败!");
//                }
//            }
//
//            @Override
//            public void onFailed(int what, Response<String> response) {
//                ToastUtil.show(mContext, "文件上传失败!");
//            }
//        });


//    }


//    /**
//     * 返回
//     */
//    @JavascriptInterface
//    public void uploadFile(String filePath, String catalogId) {
//        Message message = new Message();
//        message.what = 100;
//        final String catalogIdStr = StringUtil.isBlank(catalogId) ? StringUtil.getUUID() : catalogId;
//        FunctionApi.uploadFile(mContext, filePath, catalogIdStr, new HttpListener<String>() {
//            @Override
//            public void onSucceed(int what, Response<String> response) {
//                final UploadFileBean bean = new Gson().fromJson(response.get(), UploadFileBean.class);
//                if (bean != null && "1".equals(bean.status)) {
//                    String fileId = bean.fileId;
//                    Bundle bundle = new Bundle();
//                    bundle.putString("fileId", fileId);
//                    bundle.putString("catalogId", catalogIdStr);
//                    message.setData(bundle);
//                    mHandler.sendMessage(message);
//                } else {
//                    ToastUtil.show(mContext, "文件上传失败!");
//                }
//            }
//
//            @Override
//            public void onFailed(int what, Response<String> response) {
//                ToastUtil.show(mContext, "文件上传失败!");
//            }
//        });
//
//
//    }
//
//
//    /**
//     * @return void
//     * @version 1.0
//     * @Description: 开始录音
//     * @time： 2016/11/3
//     */
//    @JavascriptInterface
//    public boolean startRecord() {
//        return NativeUtil.takeAudio2(mContext);
//    }
//
//    /**
//     * 播放录音
//     *
//     * @param url
//     * @return
//     */
//    @JavascriptInterface
//    public boolean playRecord(String url) {
//        return NativeUtil.playAduio(mContext, url);
//    }

    /**
     * @return void
     * @version 1.0
     * @Description: 开始摄像
     * @time： 2016/11/7
     */
    @JavascriptInterface
    public void startVideo() {
        NativeUtil.startVideo(mContext, REQUEST_FOR_VIDEO);
    }


    /**
     * @return void
     * @version 1.0
     * @Description: 播放视频
     * @time： 2016/11/7
     */
    @JavascriptInterface
    public void playVideo(String url) {
        NativeUtil.playVideo(mContext, url);
    }


//    /**
//     * 查看大图
//     *
//     * @param s
//     * @param position
//     */
//    @JavascriptInterface
//    public void intentToPicDetail(String[] s, int position) {
//        FunctionApi.LargerImage(mContext, s, position);
//    }


    //Js参数回调
    public void jsResult(String methodName, String data) {
        String uri = "javascript:" + methodName + "('" + data + "')";
        L.e("jsBack", "回调的地址==" + uri);
        Message message = new Message();
        message.what = 6;
        message.obj = uri;
        mHandler.sendMessage(message);
    }


    //Js无参数回调
    public void jsResult(String methodName) {
        String uri = "javascript:" + methodName + "( )";
        Message message = new Message();
        message.what = 6;
        message.obj = uri;
        mHandler.sendMessage(message);
    }


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 20:
//                    Map<String, Object> values = (Map<String, Object>) msg.obj;
//                    //设置标题
//                    if (TextUtils.isEmpty(StringUtil.valueOf(values.get("title")))) {
//                        mWebView.setTitleText(StringUtil.valueOf(values.get("title")));
//                    }
//                    //设置标题字颜色
//                    if (TextUtils.isEmpty(StringUtil.valueOf(values.get("titleTextColor")))) {
//                        mWebView.setTitleTextColor(StringUtil.valueOf(values.get("titleTextColor")));
//                    }
//                    //设置标题背景颜色
//                    if (TextUtils.isEmpty(StringUtil.valueOf(values.get("titleBackColor")))) {
//                        mWebView.setTitleBackground(StringUtil.valueOf(values.get("titleBackColor")));
//                    }
//                    //设置标题字体大小
//                    if (TextUtils.isEmpty(StringUtil.valueOf(values.get("titleTextSize")))) {
//                        mWebView.setTitleTextSize(StringUtil.valueOf(values.get("titleTextSize")));
//                    }
//                    //设置状态
//                    if (TextUtils.isEmpty(StringUtil.valueOf(values.get("titleState")))) {
//                        mWebView.setTitleState(StringUtil.valueOf(values.get("titleState")));
//                    }
//
//                    //加载网页地址
//                    if (TextUtils.isEmpty(StringUtil.valueOf(values.get("webUrl")))) {
//                        mWebView.setWebUri(StringUtil.valueOf(values.get("webUrl")));
//                    }
//                    //右侧目录
//                    if (values.get("menuList") != null) {
//                        String[] data = (String[]) values.get("menuList");
//                        StrongWebView.setWebMeun(data, new StrongWebView.IWebViewMenuBack() {
//                            @Override
//                            public void menuBack(String data) {
//                                jsResult("yxapp.webview.buttonOnClickListener.menuOnclick", data);
//                            }
//                        });
//                    }
                    break;
                case 9://返回设置
                    if (mWebView.canGoBack()) {
                        mWebView.goBack();
                    } else {
                        ((Activity) mContext).finish();
                    }
                    break;
                case 10://刷新设置
                    mWebView.reload();
                    break;
                case 6://加载地址设置
                    mWebView.loadUrl(msg.obj + "");
                    break;
                case 123:
                    ((Activity) mContext).finish();
                    break;
                case 100:
                    //文件上传成功
                    Bundle bundle = msg.getData();
                    String fileId = bundle.getString("fileId");
                    String catalogId = bundle.getString("catalogId");
                    jsResult("yxapp.device.defaultTakeUploadFileParams.getFileInfo", catalogId + "," + fileId);
                    break;
            }
        }
    };

    public static boolean isJSHandleBack = false;

    public boolean isIsJSHandleBack() {
        return isJSHandleBack;
    }

    @JavascriptInterface
    public void setIsJSHandleBack(boolean isJSHandleBack) {
        JSApi.isJSHandleBack = isJSHandleBack;
    }
//
//    @JavascriptInterface
//    public String getDeviceInfo() {
//        String info = FunctionApi.getDeviceInfoByJson(mContext);
//        ViseLog.json(info);
//        return info;
//    }

    @JavascriptInterface
    public void toast(String str) {
        ToastUtil.show(mContext, StringUtil.valueOf(str));
    }

//    @JavascriptInterface
//    public void scanNfc() {
//        FunctionApi.intentToNFC((Activity) mContext, SCANNFC_REQUST);
//    }
//
//    @JavascriptInterface
//    public String getColor() {
//        String barColor = FunctionApi.getCurrentBarColor(mContext);
//        String titleColor = FunctionApi.getCurrentTitleColor(mContext);
//        String btnColor = FunctionApi.getCurrentBtnColor(mContext);
//        String textIconColor = FunctionApi.getCurrentTextIconColor(mContext);
//        int alpha = FunctionApi.getCurrentAlpha(mContext);
//        Map<String, Object> result = new HashMap<>();
//        result.put("barColor", barColor);
//        result.put("titleColor", titleColor);
//        result.put("btnColor", btnColor);
//        result.put("textIconColor", textIconColor);
//        result.put("alpha", alpha);
//        String json = new Gson().toJson(result);
//        ViseLog.json(json);
//        return json;
//    }
}
