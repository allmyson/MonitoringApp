package com.ys.monitor.api;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.telephony.PhoneNumberUtils;

import com.lcw.library.imagepicker.ImagePicker;
import com.yongchun.library.view.ImageSelectorActivity;
import com.ys.monitor.activity.ShowPicDetailActivity;
import com.ys.monitor.activity.VideoActivity;
import com.ys.monitor.base.GlideLoader;
import com.ys.monitor.sp.UserSP;
import com.ys.monitor.util.AppUtil;
import com.ys.monitor.util.Constant;
import com.ys.monitor.util.SystemUtil;
import com.ys.monitor.util.ToastUtil;
import com.ys.monitor.util.YS;
import com.ys.monitor.web.CommonWebviewActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lh
 * @version 1.0.0
 * @filename FunctionApi
 * @description -------------------------------------------------------
 * @date 2018/10/23 16:12
 */
public class FunctionApi {
    public static final String AUTHORITY = ".fileprovider";

    public static boolean isFirst(Context mContext) {
        return true;
    }

    public static boolean isLogin(Context mContext) {
        return UserSP.isLogin(mContext);
    }


    /**
     * @param mContext
     * @param max           最大选择张数
     * @param mode          选择模式 1多选，2单
     * @param showCamera    显示拍照
     * @param enablePreview 预览
     * @param enableCrop    剪切
     */
    public static void takePicture(Context mContext, int max, int mode, boolean showCamera,
                                   boolean enablePreview,
                                   boolean enableCrop) {
        if ((ActivityCompat.checkSelfPermission(mContext,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) ||
                (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA) != PackageManager
                        .PERMISSION_GRANTED)) {
            ToastUtil.show(mContext, "请开启相关权限");
            ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission
                    .WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 1);
            return;
        }
        ImageSelectorActivity.AUTHORITY = SystemUtil.PackgeName(mContext) + FunctionApi.AUTHORITY;
//        ImageSelectorActivity.start((Activity) mContext, max, mode, showCamera, enablePreview,
//                enableCrop);



        ImagePicker.getInstance()
                .setTitle("照片")//设置标题
                .showCamera(true)//设置是否显示拍照按钮
                .showImage(true)//设置是否展示图片
                .showVideo(true)//设置是否展示视频
                .filterGif(true)//设置是否过滤gif图片
                .setMaxCount(max)//设置最大选择图片数目(默认为1，单选)
                .setSingleType(true)//设置图片视频不能同时选择
                .setImagePaths(new ArrayList<>())//设置历史选择记录
                .setImageLoader(new GlideLoader())//设置自定义图片加载器
                .start((Activity) mContext, YS.REQUEST_SELECT_IMAGES_CODE);//REQEST_
    }

    /**
     * @param mContext
     * @param max           最大选择张数
     * @param mode          选择模式 1多选，2单
     * @param showCamera    显示拍照
     * @param enablePreview 预览
     * @param enableCrop    剪切
     */
    public static void takePicture(Fragment mContext, int max, int mode, boolean showCamera,
                                   boolean enablePreview,
                                   boolean enableCrop) {
        if ((ActivityCompat.checkSelfPermission(mContext.getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) ||
                (ActivityCompat.checkSelfPermission(mContext.getActivity(),
                        Manifest.permission.CAMERA) !=
                        PackageManager
                                .PERMISSION_GRANTED)) {
            ToastUtil.show(mContext.getActivity(), "请开启相关权限");
            ActivityCompat.requestPermissions(mContext.getActivity(),
                    new String[]{Manifest.permission
                    .WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 1);
            return;
        }
        ImageSelectorActivity.AUTHORITY =
                SystemUtil.PackgeName(mContext.getActivity()) + FunctionApi.AUTHORITY;
        ImageSelectorActivity.start(mContext, max, mode, showCamera, enablePreview, enableCrop);
    }

    public static String getAuthority(Context context) {
        return SystemUtil.PackgeName(context) + FunctionApi.AUTHORITY;
    }

    //联系客服
    public static void contactKF(Context context) {
//        ToastUtil.show(context, "联系客服");
        CommonWebviewActivity.openWebUrl(context, YS.KF_URL);
    }

    //获取完整图片路径
    public static String getImagePath(String url) {
        return YS.IP + url;
    }


    /**
     * 拨打电话（跳转到拨号界面，用户手动点击拨打）
     *
     * @param phoneNum 电话号码
     */
    public static void diallPhone(Context context, String phoneNum) {
        if ((ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) !=
                PackageManager.PERMISSION_GRANTED)) {
            ToastUtil.show(context, "请开启相关权限");
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission
                    .CALL_PHONE}, 1);
            return;
        }
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        context.startActivity(intent);
    }


    /**
     * 拨打电话（直接拨打电话）
     *
     * @param phoneNum 电话号码
     */
    public static void callPhone(Context context, String phoneNum) {
        if ((ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) !=
                PackageManager.PERMISSION_GRANTED)) {
            ToastUtil.show(context, "请开启相关权限");
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission
                    .CALL_PHONE}, 1);
            return;
        }
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        context.startActivity(intent);
    }

    /**
     * 发短信
     *
     * @param context
     * @param phoneNumber
     * @param message
     */
    public static void sendSMS(Context context, String phoneNumber, String message) {
        if (PhoneNumberUtils.isGlobalPhoneNumber(phoneNumber)) {
            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + phoneNumber));
            intent.putExtra("sms_body", message);
            context.startActivity(intent);
        }
    }

    /**
     * 查看大图
     *
     * @param mContext
     * @param path
     * @param position
     */
    public static void LargerImage(Context mContext, List<String> path, int position) {
        if (path != null && path.size() > 0) {
            String[] strs1 = path.toArray(new String[path.size()]);
            LargerImage(mContext, strs1, position);
        }
    }


    /**
     * 查看大图
     *
     * @param mContext
     * @param path
     * @param position
     */
    public static void LargerImage(Context mContext, String[] path, int position) {
        if (path != null && path.length > 0) {
            Intent intent = new Intent();
            intent.putExtra("imageUrl", path);
            if (position < path.length) {
                intent.putExtra("position", position);
            } else {
                intent.putExtra("position", 0);
            }
            intent.setClass(mContext, ShowPicDetailActivity.class);
            mContext.startActivity(intent);
        } else {
            ToastUtil.show(mContext, "暂无图片");
        }
    }


    /**
     * @return void
     * @author 董杰科
     * @version 1.0
     * @Description: 开始录像
     * @time： 2016/11/7
     */
    public static void startVideo(Activity activity, int i,String filaName) {
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            try {
                File dir = new File(Constant.VIDEO_PATH);
                if (!dir.exists())
                    dir.mkdirs();
//                File f = new File(dir, StringUtil.getNowTimeStr(3));
                File f = new File(dir, filaName);
                Uri u = null;
                String authority = AppUtil.getPackageName(activity) + FunctionApi.AUTHORITY;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//android-7.0
                    u = FileProvider.getUriForFile(activity, authority, f);
                } else {
                    u = Uri.fromFile(f);
                }
                Intent mIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                mIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);//相机辨识度
                mIntent.putExtra(MediaStore.EXTRA_OUTPUT, u);
                activity.startActivityForResult(mIntent, i);
            } catch (ActivityNotFoundException e) {
                ToastUtil.show(activity, "没有找到储存目录");
            }
        } else {
            ToastUtil.show(activity, "没有储存卡");
        }
    }
    public static void startVideo(Fragment fragment, int i,String filaName) {
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            try {
                File dir = new File(Constant.VIDEO_PATH);
                if (!dir.exists())
                    dir.mkdirs();
                File f = new File(dir, filaName);
//                File f = new File(dir, StringUtil.getNowTimeStr(3));
                Uri u = null;
                String authority = AppUtil.getPackageName(fragment.getActivity()) + FunctionApi.AUTHORITY;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//android-7.0
                    u = FileProvider.getUriForFile(fragment.getActivity(), authority, f);
                } else {
                    u = Uri.fromFile(f);
                }
                Intent mIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                mIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);//相机辨识度
                mIntent.putExtra(MediaStore.EXTRA_OUTPUT, u);
                fragment.startActivityForResult(mIntent, i);
            } catch (ActivityNotFoundException e) {
                ToastUtil.show(fragment.getActivity(), "没有找到储存目录");
            }
        } else {
            ToastUtil.show(fragment.getActivity(), "没有储存卡");
        }
    }

    /**
     * @return void
     * @author 董杰科
     * @version 1.0
     * @Description: 播放本地视频
     * @time： 2016/11/7
     */
    public static void playVideo(Context mContext, String path) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            String authority = AppUtil.getPackageName(mContext) + FunctionApi.AUTHORITY;
            Uri u = FileProvider.getUriForFile(mContext, authority, new File(path));
            intent.setDataAndType(u, "video/*");
        } else {
            intent.setDataAndType(Uri.parse("file://" + path), "video/*");
        }
        intent.putExtra("videoUrl", "file://" + path);
        mContext.startActivity(intent);
    }

    //播放在线和本地视频
    public static void playVideo2(Context mContext, String path){
        VideoActivity.playVideo(mContext,path);
    }

}
