package com.ys.monitor.activity;

import android.content.Context;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yanzhenjie.nohttp.rest.Response;
import com.ys.monitor.R;
import com.ys.monitor.base.BaseActivity;
import com.ys.monitor.bean.AppInfo;
import com.ys.monitor.http.HttpListener;
import com.ys.monitor.util.HttpUtil;
import com.ys.monitor.util.StringUtil;
import com.ys.monitor.util.SystemUtil;
import com.ys.monitor.util.ToastUtil;
import com.ys.monitor.util.YS;

public class UpdateInfoActivity extends BaseActivity {
    private int versionCode;//当前软件版本号
    private String versionName;//当前软件版本号
    private TextView hasTV;
    private Button btn;
    private String downloadUrl;

    @Override
    public int getLayoutId() {
        return R.layout.activity_update_info;
    }

    @Override
    public void initView() {
        setBarColor("#ededed");
        titleView.setText("更新版本");
        versionCode = SystemUtil.VersionCode(mContext);
        versionName = SystemUtil.VersionName(mContext);
        hasTV = getView(R.id.tv_has);
        btn = getView(R.id.btn_update);
        btn.setOnClickListener(this);
        hasTV.setText("已是最新版本：Version " + versionName);
        setBtnClickable(true,btn);
    }

    @Override
    public void getData() {
        HttpUtil.getAppVersion(mContext, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                AppInfo appInfo = new Gson().fromJson(response.get(), AppInfo.class);
                if (appInfo != null && YS.SUCCESE.equals(appInfo.code) && appInfo.data != null) {
                    int serverVersion = StringUtil.StringToInt(appInfo.data.versionNum);
                    if (serverVersion > versionCode) {
                        //有更新
                        hasTV.setText("已有新版本：Version " + StringUtil.valueOf(appInfo.data.versionName));
                        btn.setVisibility(View.VISIBLE);
                    } else {
                        //无更新
                        hasTV.setText("已是最新版本：Version " + versionName);
                        btn.setVisibility(View.GONE);
                    }
                    downloadUrl = StringUtil.valueOf(appInfo.data.downloadUrl);
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
            case R.id.btn_update:
                if (hasPermission(mContext, permissionName)) {
                    //用系统浏览器下载
                    StringUtil.openBrowser(mContext, downloadUrl);
                } else {
                    ToastUtil.show(mContext, "无存储权限");
                }
                break;
        }
    }

    public static String permissionName = "android.permission.WRITE_EXTERNAL_STORAGE";

    public static boolean hasPermission(Context context, String permission) {
        int perm = context.checkCallingOrSelfPermission(permission);
        return perm == PackageManager.PERMISSION_GRANTED;
    }
}
