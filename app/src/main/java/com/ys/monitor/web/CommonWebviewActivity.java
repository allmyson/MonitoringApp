package com.ys.monitor.web;

import android.content.Context;
import android.content.Intent;

import com.ys.monitor.util.L;
import com.ys.monitor.util.YS;

public class CommonWebviewActivity extends WebViewActivity {
    public static final int REQUEST_PATH = 107;
    /**
     * WEB打开图片
     */
    public static final int REQUEST_CODE_WEB_IMAGE = 666;
    public static final String KEY_URL = "url";
    public static final String KEY_TYPE = "type";
    public static final String FUNCTION_NAME = "AndroidJsInterface";
    private String url;
    public static final int TYPE_URL = 0;// 加载url
    public static final int TYPE_CONTENT = 1;//加载富文本
    private int currentType = TYPE_URL;

    @Override
    protected void setExtra() {
        jsInterface = new JSApi(mContext, upsoftWebview);
        addJavascriptInterface(jsInterface, FUNCTION_NAME);
        addJavascriptInterface(new JSAndroid(this),"Android");
//        addJavascriptInterface(new ProjectJSMethod(mContext, jsInterface), "AndroidProjectJSMethod");

        url = getIntent().getStringExtra(KEY_URL);
        currentType = getIntent().getIntExtra(KEY_TYPE, TYPE_URL);
        if (currentType == TYPE_URL) {
            L.e("url==" + url);
            upsoftWebview.loadUrl(url);
        } else {
            upsoftWebview.loadDataWithBaseURL(YS.IP, url, "text/html", "utf-8", null);
        }
    }

    public static void openWebUrl(Context context, String url) {
        Intent intent = new Intent(context, CommonWebviewActivity.class);
        intent.putExtra(KEY_URL, url);
        context.startActivity(intent);
    }

    /**
     * 加载富文本
     *
     * @param context
     * @param content
     */
    public static void openWebContent(Context context, String content) {
        Intent intent = new Intent(context, CommonWebviewActivity.class);
        intent.putExtra(KEY_URL, content);
        intent.putExtra(KEY_TYPE, TYPE_CONTENT);
        context.startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
//                case FunctionApi.GET_MAP_COMPANY:
//                    //获取到公司了
//                    if (data != null) {
//                        CompanyBean selectCompany = (CompanyBean) data.getSerializableExtra("company");
//                        jsResult("yxapp.device.defaultCompanyParams.getpollution", new Gson().toJson(selectCompany));
//                    }
//                    break;
//                case FunctionApi.GET_MAP_POINT:
//                    //获取到点位信息了
//                    if (data != null) {
//                        String addressStr = data.getStringExtra("address");
//                        String lon = data.getStringExtra("lon");
//                        String lat = data.getStringExtra("lat");
//                        Map<String, String> map = new HashMap<>();
//                        map.put("addressStr", addressStr);
//                        map.put("lon", lon);
//                        map.put("lat", lat);
//                        jsInterface.jsResult("yxapp.device.defaultPointParams.setPoint", new Gson().toJson(map));
//                    }
//                    break;
            }
        }
    }
}
