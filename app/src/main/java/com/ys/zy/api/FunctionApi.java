package com.ys.zy.api;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;

import com.yongchun.library.view.ImageSelectorActivity;
import com.ys.zy.util.SystemUtil;
import com.ys.zy.util.ToastUtil;

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
//        return UserSP.isLogin(mContext);
        return true;
    }


    /**
     * @param mContext
     * @param max           最大选择张数
     * @param mode          选择模式 1多选，2单
     * @param showCamera    显示拍照
     * @param enablePreview 预览
     * @param enableCrop    剪切
     */
    public static void takePicture(Context mContext, int max, int mode, boolean showCamera, boolean enablePreview,
                                   boolean enableCrop) {
        if ((ActivityCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) ||
                (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA) != PackageManager
                        .PERMISSION_GRANTED)) {
            ToastUtil.show(mContext, "请开启相关权限");
            ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission
                    .WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 1);
            return;
        }
        ImageSelectorActivity.AUTHORITY = SystemUtil.PackgeName(mContext) + FunctionApi.AUTHORITY;
        ImageSelectorActivity.start((Activity) mContext, max, mode, showCamera, enablePreview, enableCrop);
    }

    /**
     * @param mContext
     * @param max           最大选择张数
     * @param mode          选择模式 1多选，2单
     * @param showCamera    显示拍照
     * @param enablePreview 预览
     * @param enableCrop    剪切
     */
    public static void takePicture(Fragment mContext, int max, int mode, boolean showCamera, boolean enablePreview,
                                   boolean enableCrop) {
        if ((ActivityCompat.checkSelfPermission(mContext.getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) ||
                (ActivityCompat.checkSelfPermission(mContext.getActivity(), Manifest.permission.CAMERA) !=
                        PackageManager
                                .PERMISSION_GRANTED)) {
            ToastUtil.show(mContext.getActivity(), "请开启相关权限");
            ActivityCompat.requestPermissions(mContext.getActivity(), new String[]{Manifest.permission
                    .WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 1);
            return;
        }
        ImageSelectorActivity.AUTHORITY = SystemUtil.PackgeName(mContext.getActivity()) + FunctionApi.AUTHORITY;
        ImageSelectorActivity.start(mContext, max, mode, showCamera, enablePreview, enableCrop);
    }

    public static String getAuthority(Context context){
        return SystemUtil.PackgeName(context) + FunctionApi.AUTHORITY;
    }
}
