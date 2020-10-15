package com.ys.monitor.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

import java.net.URISyntaxException;


/**
 * @author lh
 * @version 1.0.0
 * @filename NavigationUtil
 * @description -------------------------------------------------------
 * @date 2020/6/23 17:36
 */
public class NavigationUtil {

    public enum TYPE {
        BAIDU, GAODE
    }

    public static void goAddress(Context context, TYPE type, double startLon, double startLat,
                                 String startAddress, double endLon, double endLat,
                                 String endAddress) {
        if (type == TYPE.BAIDU) {
            if (checkApkExist(context, "com.baidu.BaiduMap")) {
//                double[] start = GPSUtil.gps84_To_bd09(startLat, startLon);
                double[] data = GPSUtil.gps84_To_bd09(endLat, endLon);
                try {
                    Intent intent = Intent.parseUri("intent://map/direction?" +
                            "origin=latlng:" + startLat + "," + startLon +
                            "|name:" + startAddress +
                            "&destination=latlng:" + data[0] + "," + data[1] +
                            "|name:" + endAddress +
                            "&mode=driving" +
                            "&src=Name|AppName" +
                            "#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end", 0);
                    context.startActivity(intent);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            } else {
                ToastUtil.show(context, "请安装百度地图再使用导航功能！");
            }
        } else if (type == TYPE.GAODE) {
            if (checkApkExist(context, "com.autonavi.minimap")) {
//                double[] start = GPSUtil.bd09_To_Gcj02(startLat, startLon);
                double[] data = GPSUtil.gps84_To_Gcj02(endLat, endLon);
                StringBuilder builder = new StringBuilder("amapuri://route/plan?sourceApplication" +
                        "=maxuslife");
                builder.append("&dlat=").append(data[0])
                        .append("&dlon=").append(data[1])
                        .append("&dname=").append(endAddress)
                        .append("&dev=0")
                        .append("&t=0");
                L.e(builder.toString());
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setPackage("com.autonavi.minimap");
                intent.setData(Uri.parse(builder.toString()));
                context.startActivity(intent);
            } else {
                ToastUtil.show(context, "请安装高德地图再使用导航功能！");
            }
        }
    }

    /**
     * 包是否存在
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean checkApkExist(Context context, String packageName) {
        if (packageName == null || "".equals(packageName))
            return false;
        try {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo(packageName,
                    PackageManager
                    .GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
