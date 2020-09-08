package com.ys.monitor.http;

import android.content.Context;

import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.error.NetworkError;
import com.yanzhenjie.nohttp.error.NotFoundCacheError;
import com.yanzhenjie.nohttp.error.TimeoutError;
import com.yanzhenjie.nohttp.error.URLError;
import com.yanzhenjie.nohttp.error.UnKnownHostError;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;
import com.ys.monitor.sp.CookieSP;
import com.ys.monitor.util.GsonUtil;
import com.ys.monitor.util.L;
import com.ys.monitor.util.StringUtil;
import com.ys.monitor.util.ToastUtil;

import java.net.HttpCookie;
import java.util.List;

/**
 * @author lh
 * @version 1.0.0
 * @filename SimpleResponseListener
 * @description -------------------------------------------------------
 * @date 2017/10/11 16:04
 */
public class SimpleResponseListener<T> implements OnResponseListener<T> {
    private Context mContext;
    /**
     * Request.
     */
    private Request<?> mRequest;
    /**
     * 结果回调.
     */
    private HttpListener<T> callback;
    private String cookie;

    public SimpleResponseListener(Context context, Request<?> mRequest, HttpListener<T> callback) {
        mContext = context;
        this.mRequest = mRequest;
        this.callback = callback;
    }

    @Override
    public void onStart(int what) {

    }

    @Override
    public void onSucceed(int what, Response<T> response) {
        Object obj = response.get();
        L.e(response.get() + "");
        int code = response.getHeaders().getResponseCode();
        L.e("code=" + code);
        if (code == 200) {
            if (cookie == null) {
                cookie = CookieSP.getCookie(mContext);
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
                        CookieSP.saveCookie(mContext, cookieStore);
                    }
                }
            }
            if (obj != null && obj instanceof String && GsonUtil.isJSONValid3(StringUtil.valueOf(obj))) {
//                BaseBean baseBean = new Gson().fromJson(StringUtil.valueOf(response.get()), BaseBean.class);
//                if (baseBean != null && "0".equals(baseBean.status)) {
//                    if (Constant.SESSION_EXPIRED.equals(baseBean.errorCode) || Constant.SESSION_INVALID.equals(baseBean
//                            .errorCode)) {
//                        //session过期或者无效弹出跳转登录界面弹窗
////                        FunctionApi.showLoginDialog(mContext);
//                        Intent intent = new Intent(Constant.Action.ACTION_SESSION_INVALID);
//                        intent.putExtra("msg", baseBean.errorCode);
//                        mContext.sendBroadcast(intent);
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
            ToastUtil.show(mContext, "服务器错误,请稍后再试,错误码：" + code);
            if (callback != null) {
                callback.onFailed(what, response);
            }
        }
    }

    @Override
    public void onFailed(int what, Response<T> response) {
        Exception exception = response.getException();
        if (exception instanceof NetworkError) {// 网络不好
        } else if (exception instanceof TimeoutError) {// 请求超时
//            ToastUtil.showShort(mActivity, R.string.error_timeout);
        } else if (exception instanceof UnKnownHostError) {// 找不到服务器
//            ToastUtil.showShort(mActivity, R.string.error_not_found_server);
        } else if (exception instanceof URLError) {// URL是错的
//            ToastUtil.showShort(mActivity, R.string.error_url_error);
        } else if (exception instanceof NotFoundCacheError) {
            // 这个异常只会在仅仅查找缓存时没有找到缓存时返回
//            ToastUtil.showShort(mActivity, R.string.error_not_found_cache);
        } else {
//            ToastUtil.showShort(mActivity, R.string.error_unknow);
        }
        Logger.e("错误：" + exception.getMessage());
        if (callback != null) {
            callback.onFailed(what, response);
        }
    }

    @Override
    public void onFinish(int what) {

    }
}
