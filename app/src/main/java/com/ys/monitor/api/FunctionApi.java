package com.ys.monitor.api;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.telephony.PhoneNumberUtils;

import com.yongchun.library.view.ImageSelectorActivity;
import com.ys.monitor.activity.ShowPicDetailActivity;
import com.ys.monitor.sp.UserSP;
import com.ys.monitor.util.SystemUtil;
import com.ys.monitor.util.ToastUtil;
import com.ys.monitor.util.YS;
import com.ys.monitor.web.CommonWebviewActivity;

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
        ImageSelectorActivity.start((Activity) mContext, max, mode, showCamera, enablePreview,
                enableCrop);
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
}
