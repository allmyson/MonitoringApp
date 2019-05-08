package com.ys.zy.update;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yanzhenjie.nohttp.Headers;
import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.download.DownloadListener;
import com.yanzhenjie.nohttp.error.NetworkError;
import com.yanzhenjie.nohttp.error.ServerError;
import com.yanzhenjie.nohttp.error.StorageReadWriteError;
import com.yanzhenjie.nohttp.error.StorageSpaceNotEnoughError;
import com.yanzhenjie.nohttp.error.TimeoutError;
import com.yanzhenjie.nohttp.error.URLError;
import com.yanzhenjie.nohttp.error.UnKnownHostError;
import com.yanzhenjie.nohttp.rest.Response;
import com.ys.zy.R;
import com.ys.zy.api.FunctionApi;
import com.ys.zy.bean.AppInfo;
import com.ys.zy.http.BaseHttp;
import com.ys.zy.http.HttpListener;
import com.ys.zy.util.Constant;
import com.ys.zy.util.HttpUtil;
import com.ys.zy.util.L;
import com.ys.zy.util.StringUtil;
import com.ys.zy.util.SystemUtil;
import com.ys.zy.util.ToastUtil;
import com.ys.zy.util.YS;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Locale;

/**
 * Created by Administrator on 2017/10/11.
 * 软件更新
 */

public class UpdateManager implements View.OnClickListener {

    private Context mContext;
    private boolean showToast = false;//是否显示提示文字
    private static final int DOWNLOAD = 1;//下载中
    private static final int DOWNLOAD_FINISH = 2;//下载结束
    private static final int DOWNLOAD_ERROR = 3;//下载失败
    private int versionCode;//当前软件版本号
    private ProgressBar mProgress;//进度条
    private TextView textView;//下载状态
    private boolean cancelUpdate = false;//取消更新
    private Dialog mDownloadDialog;//下载进度框
    private String mSavePath;//安装包保存路径
    private int progress;//进度条当前进度
    private String downloadJD;//下载进度及速度文本
    private TextView titleTV;//dialog标题+版本名称
    private TextView contentTV;//dialog更新内容
    private TextView cancelTV;//dialog取消
    private TextView sureTV;
    private AlertDialog.Builder updateBuilder;
    private Dialog noticeDialog;
    private String downLoadUrl = "";//APK下载地址
    private String packageName;//包名
    private String isForceUpdate = "0";//是否强制更新  1表示强制下载

    public UpdateManager(Context mContext, boolean showToast) {
        super();
        this.mContext = mContext;
        this.showToast = showToast;
        this.packageName = SystemUtil.PackgeName(mContext);
        this.versionCode = SystemUtil.VersionCode(mContext);
    }


    /**
     * 检查更新
     */
    public void checkVersion() {
        if ((ActivityCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED)) {
            ToastUtil.show(mContext, "请开启相关权限");
            ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission
                    .WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            checkUpdate();
        }
    }

    /**
     * 检查更新
     */
    public void checkUpdate() {
        HttpUtil.getAppVersion(mContext, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                AppInfo appInfo = new Gson().fromJson(response.get(), AppInfo.class);
                if (appInfo != null && YS.SUCCESE.equals(appInfo.code) && appInfo.data != null) {
                    int serverVersion = StringUtil.StringToInt(appInfo.data.versionNum);
                    downLoadUrl = StringUtil.valueOf(appInfo.data.downloadUrl);
                    compareVersionCode(versionCode,
                            serverVersion,
                            isForceUpdate,
                            StringUtil.valueOf(appInfo.data.versionName),
                            StringUtil.valueOf(appInfo.data.versionRemark));
                } else {
                    if (showToast) {
                        ToastUtil.show(mContext, "当前已是最新版本", 1);
                    }
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }
        });
    }


    /**
     * 手动更新或者强制更新
     */
    private void compareVersionCode(int oldVersionCode, int newVersionCode, String isForcedUpdate, String title,
                                    String content) {

        L.e("code比较》》》》", oldVersionCode + "<<>>" + newVersionCode);
        if (oldVersionCode < newVersionCode) {
            if ("1".equals(isForcedUpdate)) {//强制下载
                showDownloadDialog();
            } else {//手动下载
                if (null != content) {
                    showNoticeDialog(title, content);
                } else {
                    showNoticeDialog(title, "");
                }
            }
        } else {
            if (showToast) {
                ToastUtil.show(mContext, "已是最新版本", 1);
            }
        }
    }


    /**
     * 更新详情框
     *
     * @param title
     * @param content
     */
    private void showNoticeDialog(String title, String content) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.update_dialog, null);
        titleTV = (TextView) view.findViewById(R.id.textview_titleId);
        contentTV = (TextView) view.findViewById(R.id.textview_contentId);
        cancelTV = (TextView) view.findViewById(R.id.textview_cancelId);
        sureTV = (TextView) view.findViewById(R.id.textview_sureId);
        cancelTV.setOnClickListener(this);
        sureTV.setOnClickListener(this);
        contentTV.setMovementMethod(ScrollingMovementMethod.getInstance());
        String t1 = content.replace("\\n", "\n");
        String t2 = t1.replace("\\r", "\r");
        contentTV.setText("有新版本可供升级下载！\n版本名：" + title + "\n更新内容：" + t2);
        updateBuilder = new AlertDialog.Builder(mContext, R.style.dialog_update);
        updateBuilder.setView(view);
        updateBuilder.setCancelable(false);
        noticeDialog = updateBuilder.create();
        noticeDialog.show();
    }


    /**
     * 下载进度框
     */
    private void showDownloadDialog() {
        // 构造软件下载对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setCancelable(false);
        // 给下载对话框增加进度条
        final LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.update_progress, null);
        mProgress = (ProgressBar) v.findViewById(R.id.update_progress);
        textView = (TextView) v.findViewById(R.id.update_textViewId);
        builder.setView(v);
        // 取消更新
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                // 设置取消状态
                BaseHttp.getInstance().cancleCurrentDownload();
            }
        });
        mDownloadDialog = builder.create();
        mDownloadDialog.show();
        // 下载文件
        downloadApk();
    }

    /**
     * 文件下载
     */
    private void downloadApk() {
//        downLoadUrl = "http://app-global.pgyer.com/bc42ed7161875418f551b321073652dd.apk?attname=app-release" +
//                ".apk&sign=f36c7db67dbaa3031a1268f5f5284c3e&t=5bc98626";
//        downLoadUrl = "http://apk.taohuichang.com/apk/1534217470188/taohuichang.apk";
        BaseHttp.getInstance().downloadFile(mContext, downLoadUrl, Constant.DOWNLOAD_PATH, "YS.apk", new
                DownloadListener() {
                    @Override
                    public void onDownloadError(int what, Exception exception) {
                        Logger.e(exception);

                        String message = mContext.getString(R.string.download_error);
                        String messageContent;
                        if (exception instanceof ServerError) {
                            messageContent = mContext.getString(R.string.download_error_server);
                        } else if (exception instanceof NetworkError) {
                            messageContent = mContext.getString(R.string.download_error_network);
                        } else if (exception instanceof StorageReadWriteError) {
                            messageContent = mContext.getString(R.string.download_error_storage);
                        } else if (exception instanceof StorageSpaceNotEnoughError) {
                            messageContent = mContext.getString(R.string.download_error_space);
                        } else if (exception instanceof TimeoutError) {
                            messageContent = mContext.getString(R.string.download_error_timeout);
                        } else if (exception instanceof UnKnownHostError) {
                            messageContent = mContext.getString(R.string.download_error_un_know_host);
                        } else if (exception instanceof URLError) {
                            messageContent = mContext.getString(R.string.download_error_url);
                        } else {
                            messageContent = mContext.getString(R.string.download_error_un);
                        }
                        message = String.format(Locale.getDefault(), message, messageContent);
                        L.e(message);
                        mHandler.sendEmptyMessage(DOWNLOAD_ERROR);
                    }

                    @Override
                    public void onStart(int what, boolean isResume, long rangeSize, Headers responseHeaders, long
                            allCount) {

                    }

                    @Override
                    public void onProgress(int what, int progress, long fileCount, long speed) {
                        L.e("progress=" + progress);
                        double newSpeed = speed / 1024D;
                        DecimalFormat decimalFormat = new DecimalFormat("###0.00");
                        String sProgress = mContext.getString(R.string.download_progress);
                        downloadJD = String.format(Locale.getDefault(), sProgress, progress, decimalFormat.format
                                (newSpeed));
//                        downloadJD = "已下载：" + progress + "%,速度：" + decimalFormat.format(newSpeed);
                        // 计算进度条位置
                        UpdateManager.this.progress = progress;
                        // 更新进度
                        mHandler.sendEmptyMessage(DOWNLOAD);
                    }

                    @Override
                    public void onFinish(int what, String filePath) {
                        L.e(filePath);
                        mSavePath = filePath;
                        mHandler.sendEmptyMessage(DOWNLOAD_FINISH);
                    }

                    @Override
                    public void onCancel(int what) {
                        ToastUtil.showToast(mContext, "已取消下载");
                    }
                });
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DOWNLOAD:
                    // 设置进度条位置
                    mProgress.setProgress(progress);
                    textView.setText(downloadJD);
                    break;
                case DOWNLOAD_FINISH:
                    mDownloadDialog.dismiss();
                    installApk(mSavePath);

                    break;
                case DOWNLOAD_ERROR:
                    mDownloadDialog.dismiss();
                    ToastUtil.show(mContext, "更新失败", 1);
                    break;
            }
        }
    };


    private void installApk(String path) {
        File apkfile = new File(path);//地址
        // 通过Intent安装APK文件
        Intent i = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            L.e(">>7.0", ">>7.0");
            i.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri contentUri = FileProvider.getUriForFile(mContext, SystemUtil.PackgeName(mContext) + FunctionApi
                    .AUTHORITY, apkfile);
            i.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            L.e("<<7.0", "<<7.0");
            i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        mContext.startActivity(i);
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.textview_cancelId) {
            //取消的时候  也要暂停下载
            noticeDialog.dismiss();
        } else if (i == R.id.textview_sureId) {
            noticeDialog.dismiss();

            if (hasPermission(mContext, permissionName)) {
                // 显示下载对话框
//                showDownloadDialog();
//               用系统浏览器下载
                StringUtil.openBrowser(mContext, downLoadUrl);
            } else {
                ToastUtil.show(mContext, "无存储权限");
            }
        }
    }


    public static String permissionName = "android.permission.WRITE_EXTERNAL_STORAGE";

    public static boolean hasPermission(Context context, String permission) {
        int perm = context.checkCallingOrSelfPermission(permission);
        return perm == PackageManager.PERMISSION_GRANTED;
    }
}








