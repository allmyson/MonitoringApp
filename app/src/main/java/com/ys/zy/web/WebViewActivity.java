package com.ys.zy.web;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import com.yongchun.library.view.ImageSelectorActivity;
import com.ys.zy.R;
import com.ys.zy.base.BaseActivity;
import com.ys.zy.util.Constant;
import com.ys.zy.util.L;

import java.util.ArrayList;
import java.util.List;


public abstract class WebViewActivity extends BaseActivity {
    public static final String FUNCTION_NAME = "AndroidJsInterface";
    protected UpsoftWebview upsoftWebview;
    public JSApi jsInterface;
    private static final String TAG = "WebViewActivity";

    @Override
    public int getLayoutId() {
        return R.layout.activity_webactivity;
    }

    @Override
    public void initView() {
        upsoftWebview = getView(R.id.uw);
        initWebViewSet();
        initWebListener();
        setExtra();
    }

    @Override
    public void getData() {

    }

    private void initWebViewSet() {
        // 设置编码
        upsoftWebview.getSettings().setDefaultTextEncodingName("utf-8");
        upsoftWebview.getSettings().setTextZoom(100);
        // 设置背景颜色 透明
        upsoftWebview.setBackgroundColor(Color.argb(0, 0, 0, 0));
        // 设置可以支持缩放
        upsoftWebview.getSettings().setSupportZoom(true);
        // //添加Javascript调用java对象
        upsoftWebview.getSettings().setJavaScriptEnabled(true);
        // 设置出现缩放工具
        upsoftWebview.getSettings().setBuiltInZoomControls(true);
        upsoftWebview.getSettings().setDisplayZoomControls(false);
        upsoftWebview.getSettings().setAllowFileAccessFromFileURLs(true);// 跨域
        upsoftWebview.getSettings().setAllowUniversalAccessFromFileURLs(true);// 跨域
        upsoftWebview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);//缓存模式
        // 扩大比例的缩放设置此属性，可任意比例缩放。
        upsoftWebview.getSettings().setLoadWithOverviewMode(true);
        upsoftWebview.getSettings().setBlockNetworkImage(false);
        // 不启用硬件加速
//        mWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        // 自适应屏幕
        upsoftWebview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            upsoftWebview.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(upsoftWebview, true);
        }
        /******支持html5* -调试模式-***必须大于4.4(KitKat)***************/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            upsoftWebview.setWebContentsDebuggingEnabled(true);
        }
        /******支持html5* -videoview-**************************/
        upsoftWebview.getSettings().setUseWideViewPort(true); // 关键点
        upsoftWebview.getSettings().setSaveFormData(true);
        upsoftWebview.getSettings().setAllowFileAccess(true);
    }

    protected void initWebListener() {
        // 重新WebView加载URL的方法
        upsoftWebview.setWebViewClient(new WebViewClient() {

            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse
                    errorResponse) {
                super.onReceivedHttpError(view, request, errorResponse);
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    int code = errorResponse.getStatusCode();
                    L.e("code=" + code);
//                    if (code == 404) {
//                        view.loadUrl("about:blank");// 避免出现默认的错误界面
//                        view.loadUrl("file:///android_asset/error.html");
//                    }
                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                L.e("重新加载", "重新加载");
                return true;
            }

            /**
             *  连接错误
             * @param view
             * @param errorCode
             * @param description
             * @param failingUrl
             */
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
//                view.loadUrl("about:blank");// 避免出现默认的错误界面
                L.e("errorCode=" + errorCode);
                if (errorCode == ERROR_HOST_LOOKUP || errorCode == ERROR_CONNECT || errorCode == ERROR_TIMEOUT) {
                    view.loadUrl("about:blank"); // 避免出现默认的错误界面
                    view.loadUrl("file:///android_asset/error.html");
                }
//                view.loadUrl("file:///android_asset/index.html");
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // TODO Auto-generated method stub
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                // TODO Auto-generated method stub
                super.onPageFinished(view, url);
            }
        });


        upsoftWebview.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                // android 6.0 以下通过title获取
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                    if (title.contains("404") || title.contains("500") || title.contains("Error")) {
                        view.loadUrl("about:blank");// 避免出现默认的错误界面
                        view.loadUrl("file:///android_asset/error.html");
                    }
                }
            }

            @SuppressLint("NewApi")
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(WebViewActivity.this);
                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        result.confirm();
                    }
                });
                dialog.setMessage(message);
                dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        result.confirm();
                    }
                }).create().show();
                return true;
            }

            /*** 视频播放相关的方法 **/
            @Override
            public View getVideoLoadingProgressView() {
                FrameLayout frameLayout = new FrameLayout(mContext);
                frameLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup
                        .LayoutParams.MATCH_PARENT));
                return frameLayout;
            }

            @Override
            public void onShowCustomView(View view, CustomViewCallback callback) {
                showCustomView(view, callback);
            }

            @Override
            public void onHideCustomView() {
                hideCustomView();
            }
        });
    }


    protected boolean goBack() {
        /** 回退键 事件处理 优先级:视频播放全屏-网页回退-关闭页面 */
        if (customView != null) {
            hideCustomView();
            return true;
        }
        return false;
    }


    /**
     * 视频全屏参数
     */
    protected static final FrameLayout.LayoutParams COVER_SCREEN_PARAMS = new FrameLayout.LayoutParams(FrameLayout
            .LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
    private View customView;
    private FrameLayout fullscreenContainer;
    private WebChromeClient.CustomViewCallback customViewCallback;


    /**
     * 视频播放全屏
     **/
    private void showCustomView(View view, WebChromeClient.CustomViewCallback callback) {
        if (customView != null) {
            callback.onCustomViewHidden();
            return;
        }
        this.getWindow().getDecorView();
        FrameLayout decor = (FrameLayout) getWindow().getDecorView();
        fullscreenContainer = new FullscreenHolder(this);
        fullscreenContainer.addView(view, COVER_SCREEN_PARAMS);
        decor.addView(fullscreenContainer, COVER_SCREEN_PARAMS);
        customView = view;
        setStatusBarVisibility(false);
        customViewCallback = callback;
    }

    /**
     * 隐藏视频全屏
     */
    private void hideCustomView() {
        if (customView == null) {
            return;
        }
        setStatusBarVisibility(true);
        FrameLayout decor = (FrameLayout) getWindow().getDecorView();
        decor.removeView(fullscreenContainer);
        fullscreenContainer = null;
        customView = null;
        customViewCallback.onCustomViewHidden();
        upsoftWebview.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {

    }


    /**
     * 全屏容器界面
     */
    static class FullscreenHolder extends FrameLayout {

        public FullscreenHolder(Context ctx) {
            super(ctx);
            setBackgroundColor(ctx.getResources().getColor(android.R.color.black));
        }

        @Override
        public boolean onTouchEvent(MotionEvent evt) {
            return true;
        }
    }

    private void setStatusBarVisibility(boolean visible) {
        int flag = visible ? 0 : WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setFlags(flag, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }


    /**
     * 权限判断
     */
    public void getPermission() {
        if (
                (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.READ_PHONE_STATE) != PackageManager
                        .PERMISSION_GRANTED) ||
                        (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.RECORD_AUDIO) !=
                                PackageManager.PERMISSION_GRANTED) ||
                        (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                                PackageManager.PERMISSION_GRANTED) ||
                        (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                                PackageManager.PERMISSION_GRANTED) ||
                        (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA) != PackageManager
                                .PERMISSION_GRANTED)
                ) {
            ActivityCompat.requestPermissions((Activity) mContext, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA
            }, 1);
        }
    }


    @SuppressLint("JavascriptInterface")
    public void addJavascriptInterface(Object object, String name) {
        upsoftWebview.addJavascriptInterface(object, name);
    }

    public void setWebUri(String url) {
        upsoftWebview.loadUrl(url);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (upsoftWebview.canGoBack()) {
                upsoftWebview.goBack();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case ImageSelectorActivity.REQUEST_IMAGE://相册图片选取返回
                    ArrayList<String> images = (ArrayList<String>) data.getSerializableExtra(ImageSelectorActivity
                            .REQUEST_OUTPUT);
                    jsResult("yxapp.device.defaultTakePictureParams.getPictureUrl", images.toString());
                    break;
                case JSApi.REQUEST_FOR_PHOTO://拍照返回
//                    String camPath = NativeUtil.imgPath;


                    List<String> list = data.getStringArrayListExtra(ImageSelectorActivity.REQUEST_OUTPUT);
                    if(list.size()>0){
                        jsResult("yxapp.device.defaultTakePictureParams.getPictureUrl", list.get(0));
                    }
                    break;
                case JSApi.REQUEST_FOR_VIDEO://视频返回

                    Uri uriVideo = data.getData();
                    String path = uriVideo.getPath();
                    String fileName = path.substring(path.lastIndexOf("/"), path.length());
                    String viPath = Constant.VIDEO_PATH + fileName;
                    L.e("视频地址》》》", viPath);
                    jsResult("yxapp.device.defaultTakeVideoParams.getVideoUrl", viPath);

                    break;

//                case JSApi.REQUEST_FOR_AUDIO://录音返回
//                    String filepath = data.getStringExtra("filePath");
//                    jsResult("yxapp.device.defaultRecordParams.getRecordUrl", filepath);
//                    break;
//                case JSApi.SCANCODE_REQUST://扫描二维码数据返回
//                    String result = data.getExtras().getString(CaptureActivity.SCAN_RESULT);
//                    jsResult("yxapp.device.scanCodeBack.onClickBack", result);
//                    break;
//                case JSApi.SCANNFC_REQUST://扫描NFC数据返回
//                    String nfcResult = data.getStringExtra(NFCScanActivity.NFC_RESULT);
//                    jsResult("yxapp.device.defaultNfcScanParams.getNfcId", nfcResult);
//                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //Js参数回调
    public void jsResult(String methodName, String data) {
        String uri = "javascript:" + methodName + "('" + data + "')";
        L.e("jsBack", "回调的地址==" + uri);
        setWebUri(uri);
    }

    protected abstract void setExtra();
}
