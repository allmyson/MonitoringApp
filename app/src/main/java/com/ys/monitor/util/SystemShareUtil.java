package com.ys.monitor.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Build;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lh
 * @version 1.0.0
 * @filename SystemShareUtil
 * @description -------------------------------------------------------
 * @date 2018/9/17 18:57
 */
public class SystemShareUtil {
    /**
     * 拉起微信好友发送单张图片
     */
    public static void shareweixin(Context context, String path) {
        Uri uriToImage = Uri.fromFile(new File(path));
        Intent shareIntent = new Intent();
        //发送图片到朋友圈
        //ComponentName comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI");
        //发送图片给好友。
        ComponentName comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI");
        shareIntent.setComponent(comp);
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uriToImage);
        shareIntent.setType("image/jpeg");
        context.startActivity(Intent.createChooser(shareIntent, "分享图片"));
    }

    /**
     * 拉起微信朋友圈发送单张图片
     */
    public static void shareweipyq(Context context, String path, String content) {
        Uri uriToImage = Uri.fromFile(new File(path));
        Intent shareIntent = new Intent();
        //发送图片到朋友圈
        ComponentName comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI");
        //发送图片给好友。
//        ComponentName comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI");
        shareIntent.setComponent(comp);
        shareIntent.putExtra("Kdescription", content);
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uriToImage);
        shareIntent.setType("image/jpeg");
        context.startActivity(Intent.createChooser(shareIntent, "分享图片"));
    }

    /**
     * 拉起微信朋友圈发送多张图片
     */
    public static void shareweipyqSomeImg(Context context, Uri[] uri) {
        Intent shareIntent = new Intent();
        //1调用系统分析
        shareIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        //2添加图片数组
        ArrayList<Uri> imageUris = new ArrayList<>();
        for (int i = 0; i < uri.length; i++) {
            imageUris.add(uri[i]);
        }

        shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
        shareIntent.setType("image/*");

        //3指定选择微信
        ComponentName componentName = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI");
        shareIntent.setComponent(componentName);

        //4开始分享
        context.startActivity(Intent.createChooser(shareIntent, "分享图片"));
    }

    /**
     * 拉起微信发送多张图片给好友
     */
    public static void shareWXSomeImg(Context context, Uri[] uri) {
        Intent shareIntent = new Intent();
        //1调用系统分析
        shareIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        //2添加图片数组
        ArrayList<Uri> imageUris = new ArrayList<>();
        for (int i = 0; i < uri.length; i++) {
            imageUris.add(uri[i]);
        }

        shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
        shareIntent.setType("image/*");

        //3指定选择微信
        ComponentName componentName = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI");
        shareIntent.setComponent(componentName);

        //4开始分享
        context.startActivity(Intent.createChooser(shareIntent, "分享图片"));
    }


    /**
     * Android原生分享功能
     *
     * @param appName:要分享的应用程序名称
     */
    public static void share(Context context, String appName) {
        Intent share_intent = new Intent();
        share_intent.setAction(Intent.ACTION_SEND);
        share_intent.setType("text/plain");
        share_intent.putExtra(Intent.EXTRA_SUBJECT, "f分享");
        share_intent.putExtra(Intent.EXTRA_TEXT, "HI 推荐您使用一款软件:" + appName);
        share_intent = Intent.createChooser(share_intent, "分享");
        context.startActivity(share_intent);
    }


    //判断是否安装微信
    private static boolean isInstallWeChat(Context context) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo("com.tencent.mm", 0);
        } catch (Exception e) {
            packageInfo = null;
            e.printStackTrace();
        }
        if (packageInfo == null)
            return false;
        else
            return true;

    }

    //分享图片到微信,分享的是本地图片

    /**
     * 0 微信  1朋友圈 2 QQ  3 SINA 4 QZONE
     *
     * @param context
     * @param paths
     * @param type
     */
    public static void shareImagesToWeiXin(Context context, List<String> paths, int type) {
        if (!isInstallWeChat(context.getApplicationContext())) {
            ToastUtil.showToast(context.getApplicationContext(), "沒有安裝微信");
            return;
        }
        try {
            Intent weChatIntent = new Intent();
            //com.tencent.mm.ui.tools.ShareImgUI  是分享到微信好友,//com.tencent.mm.ui.tools.ShareToTimeLineUI
            // 是分享到微信朋友圈，最多可以分享九张图片到微信朋友圈
            if (type == 0) {
                //微信
                weChatIntent.setComponent(new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI"));
            } else if (type == 1) {
                //微信朋友圈
                weChatIntent.setComponent(new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools" +
                        ".ShareToTimeLineUI"));
            } else if (type == 2) {
                //QQ
                weChatIntent.setComponent(new ComponentName("com.tencent.mobileqq", "com.tencent.mobileqq.activity" +
                        ".JumpActivity"));
            } else if (type == 3) {
                //sina
                weChatIntent.setComponent(new ComponentName("com.sina.weibo", "com.sina.weibo.composerinde" +
                        ".ComposerDispatchActivity"));
            } else if (type == 4) {
                //QQ zone
                weChatIntent.setComponent(new ComponentName("com.qzone", "com.qzonex.module.operation.ui" +
                        ".QZonePublishMoodActivity"));
            } else {
                weChatIntent.setComponent(new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI"));
            }
            weChatIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
            ArrayList<Uri> imageList = new ArrayList();
            List<File> list = new ArrayList<>();
            for (String path : paths) {
                list.add(new File(path));
            }
            if (list != null) {
                for (File file : list) {
                    L.e(file.getAbsolutePath());
                    File f = new File(file.getAbsolutePath());
                    if (f.exists()) {
                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {//android 7.0以下
                            imageList.add(Uri.fromFile(f));
                        } else {//android 7.0及以上
                            Uri uri = Uri.parse(android.provider.MediaStore.Images.Media.insertImage(context
                                    .getContentResolver(), f.getAbsolutePath(), f.getName(), null));
                            imageList.add(uri);
                        }
                    }
                }
            }
            weChatIntent.setType("image/*");
            weChatIntent.putExtra(Intent.EXTRA_STREAM, imageList);
//            weChatIntent.putExtra("Kdescription", "aaaa"); //分享描述
            context.startActivity(weChatIntent);
        } catch (Exception e) {
            ToastUtil.showToast(context.getApplicationContext(), "分享失败");
        }
    }
}
