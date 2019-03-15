package com.ys.zy.http;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;

import com.yanzhenjie.nohttp.Binary;
import com.yanzhenjie.nohttp.FileBinary;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.download.DownloadListener;
import com.yanzhenjie.nohttp.download.DownloadRequest;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.StringRequest;
import com.ys.zy.sp.CookieSP;
import com.ys.zy.util.L;
import com.ys.zy.util.StringUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author lh
 * @version 1.0.0
 * @filename BaseHttp
 * @description -------------------------------------------------------
 * @date 2017/10/9 10:42
 */
public class BaseHttp {
    private static BaseHttp instance;
    private int threadPoolSize = 5;
    /**
     * 用来标记取消。
     */
    private Object object = new Object();

    /**
     * 请求队列。
     */
    private RequestQueue mQueue;

    private BaseHttp() {
        mQueue = NoHttp.newRequestQueue(threadPoolSize);
    }

    public static BaseHttp getInstance() {
        if (instance == null)
            synchronized (BaseHttp.class) {
                if (instance == null)
                    instance = new BaseHttp();
            }
        return instance;
    }

    /**
     * 发起请求。
     *
     * @param what      what.
     * @param request   请求对象。
     * @param callback  回调函数。
     * @param canCancel 是否能被用户取消。
     * @param isLoading 实现显示加载框。
     * @param <T>       想请求到的数据类型。
     */
    public <T> void request(Activity activity, int what, Request<T> request, HttpListener<T> callback, boolean
            canCancel, boolean
                                    isLoading) {
        request.setCancelSign(object);
//        request.addHeader("Cookie", "PHPSESSID=" + UserSP.getCookie(mContext));
        mQueue.add(what, request, new HttpResponseListener<>(activity, request, callback, canCancel, isLoading));
    }

    public <T> void request(int what, Request<T> request, HttpListener<T> callback, boolean canCancel, boolean
            isLoading, Context context) {
        request.setCancelSign(object);
//        request.addHeader("Cookie", "PHPSESSID=" + UserSP.getCookie(mContext));
        mQueue.add(what, request, new HttpResponseListener<>((Activity) context, request, callback, canCancel,
                isLoading));

    }

    public <T> void request(Request<T> request, HttpListener<T> callback, Context context) {
        request(0, request, callback, false, true, context);
    }

    public <T> void request(int what, Request<T> request, HttpListener<T> callback, Context context) {
        request(what, request, callback, false, true, context);
    }

    public <T> void request(Activity activity, Request<T> request, HttpListener<T> callback) {
        request(activity, 0, request, callback, false, true);
    }

    public <T> void request(Activity activity, int what, Request<T> request, HttpListener<T> callback) {
        request(activity, what, request, callback, false, true);
    }

    public void postJson(Activity activity, int what, String url, String postJson, HttpListener<String> callback,
                         boolean canCancel,
                         boolean isLoading) {
        L.e("url=" + url);
        L.e("postJson=" + postJson);
        Request<String> request = NoHttp.createStringRequest(url, RequestMethod.POST);
        request.setDefineRequestBodyForJson(postJson);
        String token = CookieSP.getCookie(activity);
        if (token != null && !"".equals(token)) {
            request.addHeader("Cookie", token);
        }
        request(activity, what, request, callback, canCancel, isLoading);
    }

    public void postJson(Activity activity, String url, String postJson, HttpListener<String> callback) {
        postJson(activity, url, postJson, callback, true);
    }

    public void postJson(Activity activity, String url, String postJson, HttpListener<String> callback, boolean
            isShowDialog) {
        postJson(activity, 0, url, postJson, callback, false, isShowDialog);
    }

    public void cancelAll() {
        mQueue.cancelAll();
    }

    public void cancelBySign(Object object) {
        mQueue.cancelBySign(object);
    }

    public void exitQueue() {
        if (mQueue != null) {
            mQueue.cancelBySign(object);
            mQueue.stop();
        }
    }


    public <T> void request(Context context, int what, Request<T> request, HttpListener<T> callback) {
        request.setCancelSign(object);
        mQueue.add(what, request, new SimpleResponseListener<T>(context, request, callback));
    }

    public <T> void request(Context context, Request<T> request, HttpListener<T> callback) {
        request(context, 0, request, callback);
    }

    public void postSimpleJson(Context context, String url, String postJson, HttpListener<String> callback) {
//        L.e("url=" + url);
//        L.e("params=" + postJson);
//        Request<String> request = NoHttp.createStringRequest(url, RequestMethod.POST);
//        request.setContentType("application/json");
//        if (!StringUtil.isBlank(postJson)) {
//            request.setDefineRequestBodyForJson(postJson);
//        }
//        String token = CookieSP.getCookie(context);
//        if (token != null && !"".equals(token)) {
//            request.addHeader("Cookie", token);
//        }
//        request(context, request, callback);
        postJson((Activity) context, url, postJson, callback);
    }
    public void postSimpleJson2(Context context, String url, String postJson, HttpListener<String> callback) {
        L.e("url=" + url);
        L.e("params=" + postJson);
        Request<String> request = NoHttp.createStringRequest(url, RequestMethod.POST);
        request.setContentType("application/json");
        if (!StringUtil.isBlank(postJson)) {
            request.setDefineRequestBodyForJson(postJson);
        }
        String token = CookieSP.getCookie(context);
        if (token != null && !"".equals(token)) {
            request.addHeader("Cookie", token);
        }
        request(context, request, callback);
    }

    public void postSimpleJson(Context context, String url, String postJson, List<String> files, HttpListener<String>
            callback) {
        L.e("url=" + url);
        L.e("params=" + postJson);
        Request<String> request = NoHttp.createStringRequest(url, RequestMethod.POST);
        request.setContentType("application/json");
        List<Binary> binaries = new ArrayList<>();
        if (files != null && files.size() > 0) {
            Binary binary = null;
            for (String path : files) {
                binary = new FileBinary(new File(path));
                binaries.add(binary);
            }
        }
        request.add("file", binaries);
        if (!StringUtil.isBlank(postJson)) {
            request.setDefineRequestBodyForJson(postJson);
        }
        String token = CookieSP.getCookie(context);
        if (token != null && !"".equals(token)) {
            request.addHeader("Cookie", token);
        }
        request(context, request, callback);
    }


    /**
     * 上传文件 多key
     *
     * @param context
     * @param url
     * @param fileMap
     * @param callback
     */
    public void postFile(Context context, String url, Map<String, File> fileMap, HttpListener<String> callback) {
        Request<String> request = new StringRequest(url, RequestMethod.POST);
        if (fileMap != null) {
            for (String key : fileMap.keySet()) {
                request.add(StringUtil.valueOf(key), new FileBinary(fileMap.get(key)));
            }
        }
        String token = CookieSP.getCookie(context);
        if (token != null && !"".equals(token)) {
            request.addHeader("Cookie", token);
        }
//        request(context, request, callback);
        request((Activity) context, 0, request, callback, false, true);
    }


    /**
     * 简单get请求
     *
     * @param context
     * @param url
     * @param callback
     */
    public void simpleGet(Context context, String url, HttpListener<String> callback) {
        L.e("url=" + url);
        Request<String> request = NoHttp.createStringRequest(url, RequestMethod.GET);
        String token = CookieSP.getCookie(context);
        if (token != null && !"".equals(token)) {
            request.addHeader("Cookie", token);
        }
        request(context, request, callback);
    }

    public void get(Activity activity, int what, String url, HttpListener<String> callback,
                    boolean canCancel,
                    boolean isLoading) {
        L.e("url=" + url);
        Request<String> request = NoHttp.createStringRequest(url, RequestMethod.GET);
        String token = CookieSP.getCookie(activity);
        if (token != null && !"".equals(token)) {
            request.addHeader("Cookie", token);
        }
        request(activity, what, request, callback, canCancel, isLoading);
    }

    /**
     * get
     *
     * @param activity
     * @param url
     * @param callback
     * @param isShowDialog
     */
    public void get(Activity activity, String url, HttpListener<String> callback, boolean
            isShowDialog) {
        get(activity, 0, url, callback, false, isShowDialog);
    }

    /**
     * 默认显示进度框   get
     *
     * @param activity
     * @param url
     * @param callback
     */
    public void get(Activity activity, String url, HttpListener<String> callback) {
        get(activity, url, callback, true);
    }


    public void loadImag(Context context, String url, HttpListener<Bitmap> callback) {
        //第二步：创建请求对象（url是请求路径， RequestMethod.POST是请求方式）
        Request<Bitmap> imageRequest = NoHttp.createImageRequest(url);//这里 RequestMethod
        imageRequest.setContentType("image/*");
        String token = CookieSP.getCookie(context);
        if (token != null && !"".equals(token)) {
            imageRequest.addHeader("Cookie", token);
        }
        request(context, imageRequest, callback);
    }

    public Request<String> getStringPostRequst(String url) {
        Request<String> request = NoHttp.createStringRequest(url, RequestMethod.POST);
        return request;
    }

    /**
     * 下载文件
     *
     * @param context
     * @param url
     * @param savaPath
     * @param fileName
     * @param downloadListener
     */
    public void downloadFile(Context context, String url, String savaPath, String fileName, DownloadListener
            downloadListener) {
        downloadRequest = NoHttp.createDownloadRequest(
                url, savaPath, fileName, true, true);
        String token = CookieSP.getCookie(context);
//        downloadRequest.getHeaders().remove(Headers.HEAD_KEY_CONTENT_TYPE);
        if (token != null && !"".equals(token)) {
            downloadRequest.addHeader("Cookie", token);
        }
        NoHttp.getDownloadQueueInstance().add(0, downloadRequest, downloadListener);
    }

    private DownloadRequest downloadRequest;

    public void cancleCurrentDownload() {
        if (downloadRequest != null) {
            downloadRequest.cancel();
        }
    }
}
