/*
 * Copyright 2015 Yan Zhenjie
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ys.monitor.http;

import android.app.Activity;
import android.content.DialogInterface;

import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.error.NetworkError;
import com.yanzhenjie.nohttp.error.NotFoundCacheError;
import com.yanzhenjie.nohttp.error.TimeoutError;
import com.yanzhenjie.nohttp.error.URLError;
import com.yanzhenjie.nohttp.error.UnKnownHostError;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;
import com.ys.monitor.R;
import com.ys.monitor.dialog.WaitDialog;
import com.ys.monitor.sp.CookieSP;
import com.ys.monitor.util.GsonUtil;
import com.ys.monitor.util.L;
import com.ys.monitor.util.StringUtil;
import com.ys.monitor.util.ToastUtil;

import java.net.HttpCookie;
import java.util.List;

/**
 * Created in Nov 4, 2015 12:02:55 PM.
 *
 * @author Yan Zhenjie.
 */
public class HttpResponseListener<T> implements OnResponseListener<T> {

    private Activity mActivity;
    /**
     * Dialog.
     */
    private WaitDialog mWaitDialog;
    /**
     * Request.
     */
    private Request<?> mRequest;
    /**
     * 结果回调.
     */
    private HttpListener<T> callback;
    private String cookie;

    /**
     * @param activity     context用来实例化dialog.
     * @param request      请求对象.
     * @param httpCallback 回调对象.
     * @param canCancel    是否允许用户取消请求.
     * @param isLoading    是否显示dialog.
     */
    public HttpResponseListener(Activity activity, Request<?> request, HttpListener<T> httpCallback, boolean
            canCancel, boolean isLoading) {
        this.mActivity = activity;
        this.mRequest = request;
        if (activity != null && isLoading) {
            mWaitDialog = new WaitDialog(activity);
            mWaitDialog.setCancelable(canCancel);
            mWaitDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    mRequest.cancel();
                }
            });
        }
        this.callback = httpCallback;
    }

    public HttpResponseListener(Activity activity, Request<?> request, HttpListener<T> httpCallback) {
        this(activity, request, httpCallback, false, true);
    }

    /**
     * 开始请求, 这里显示一个dialog.
     */
    @Override
    public void onStart(int what) {
        if (mWaitDialog != null && !mActivity.isFinishing() && !mWaitDialog.isShowing())
            mWaitDialog.show();
    }

    /**
     * 结束请求, 这里关闭dialog.
     */
    @Override
    public void onFinish(int what) {
        if (mWaitDialog != null && mWaitDialog.isShowing())
            mWaitDialog.dismiss();
    }

    /**
     * 成功回调.
     */
    @Override
    public void onSucceed(int what, Response<T> response) {
        Object obj = response.get();
        L.e(response.get() + "");
        int code = response.getHeaders().getResponseCode();
        L.e("code=" + code);
        if (code == 200) {
            if (cookie == null) {
                cookie = CookieSP.getCookie(mActivity);
            }
            if (cookie == null || "".equals(cookie)) {

                List<HttpCookie> cookies = null;
                try {
                    cookies = response.getHeaders().getCookies();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (cookies != null && cookies.size() > 0) {
                    HttpCookie httpCookie = cookies.get(0);
                    if ("jsessionid".equals(httpCookie.getName())) {
                        // 设置有效期为最大。
//                        httpCookie.setMaxAge(HeaderUtil.getMaxExpiryMillis());
                        String cookieStore = httpCookie.getName().concat("=").concat(httpCookie.getValue()).concat(";");
                        L.e(cookieStore);
                        CookieSP.saveCookie(mActivity, cookieStore);
                    }
                }
            }
            if (obj != null && obj instanceof String && GsonUtil.isJSONValid3(StringUtil.valueOf(obj))) {
//                BaseBean baseBean = new Gson().fromJson(StringUtil.valueOf(response.get()), BaseBean.class);
//                if (baseBean != null && "0".equals(baseBean.status)) {
//                    if (Constant.SESSION_EXPIRED.equals(baseBean.errorCode) || Constant.SESSION_INVALID.equals(baseBean
//                            .errorCode)) {
//                        //session过期或者无效弹出跳转登录界面弹窗
////                        FunctionApi.showLoginDialog(mActivity);
//                        Intent intent = new Intent(Constant.Action.ACTION_SESSION_INVALID);
//                        intent.putExtra("msg",baseBean.errorCode);
//                        mActivity.sendBroadcast(intent);
//                        return;
//                    }
//                }
                if (callback != null) {
                    // 这里判断一下http响应码，这个响应码问下你们的服务端你们的状态有几种，一般是200成功。
                    // w3c标准http响应码：http://www.w3school.com.cn/tags/html_ref_httpmessages.asp

                    callback.onSucceed(what, response);
                }
            } else {
                callback.onSucceed(what, response);
            }
        } else {
            ToastUtil.show(mActivity, "服务器错误,请稍后再试,错误码：" + code);
            if (callback != null) {
                callback.onFailed(what, response);
            }
        }
    }

    /**
     * 失败回调.
     */
    @Override
    public void onFailed(int what, Response<T> response) {
        Exception exception = response.getException();
        if (exception instanceof NetworkError) {// 网络不好
            ToastUtil.showShort(mActivity, R.string.error_please_check_network);
        } else if (exception instanceof TimeoutError) {// 请求超时
            ToastUtil.showShort(mActivity, R.string.error_timeout);
        } else if (exception instanceof UnKnownHostError) {// 找不到服务器
            ToastUtil.showShort(mActivity, R.string.error_not_found_server);
        } else if (exception instanceof URLError) {// URL是错的
            ToastUtil.showShort(mActivity, R.string.error_url_error);
        } else if (exception instanceof NotFoundCacheError) {
            // 这个异常只会在仅仅查找缓存时没有找到缓存时返回
            ToastUtil.showShort(mActivity, R.string.error_not_found_cache);
        } else {
            ToastUtil.showShort(mActivity, R.string.error_unknow);
        }
        Logger.e("错误：" + exception.getMessage());
        if (callback != null)
            callback.onFailed(what, response);
    }

}
