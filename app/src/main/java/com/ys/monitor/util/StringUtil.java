package com.ys.monitor.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.ys.monitor.R;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StringUtil {

    public static String valueOf(Object obj) {
        String result = obj == null ? "" : String.valueOf(obj);
        if ("null".equalsIgnoreCase(result)) {
            result = "";
        }
        return result;
    }

    public static String valueOf2(Object address) {
        String s = valueOf(address);
        if ("null".equals(s)) {
            s = "";
        }
        return s;
    }

    public static boolean isBlank(String str) {
        return str == null || "".equals(str);
    }

    public static boolean checkNull(Object checkable) {
        return checkable != null;
    }

    /**
     * @return params type 1.图片 2.录音，3视频
     * @描述: 得到当前时间的字符串
     * @retrun String
     */
    public static String getNowTimeStr(int type) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddhhmmss");
        String result = "";
        if (type == 1) {
            result = sf.format(new Date()).toString() + ".jpg";
        } else if (type == 2) {
            result = sf.format(new Date()).toString() + ".mp3";
        } else if (type == 3) {
            result = sf.format(new Date()).toString() + ".mp4";
        } else if (type == 4) {
            result = sf.format(new Date()).toString();
        }
        return result;
    }


    /* 将List<V>按照V的methodName方法返回值（返回值必须为K类型）分组，合入到Map<K, List<V>>中<br>
     * 要保证入参的method必须为V的某一个有返回值的方法，并且该返回值必须为K类型
     *
     * @param list 待分组的列表
     * @param map 存放分组后的map
     * @param clazz 泛型V的类型
     * @param methodName 方法名
     */
    public static <K, V> void listGroup2Map(List<V> list, Map<K, List<V>> map, Class<V> clazz,
                                            String methodName) {
        // 入参非法行校验
        if (null == list || null == map || null == clazz || StringUtil.isBlank(methodName)) {
            L.e("CommonUtils.listGroup2Map 入参错误，list：" + list + " ；map：" + map
                    + " ；clazz：" + clazz + " ；methodName：" + methodName);
            return;
        }

        // 获取方法
        Method method = getMethodByName(clazz, methodName);
        // 非空判断
        if (null == method) {
            return;
        }
        // 正式分组
        listGroup2Map(list, map, method);
    }

    /**
     * 根据类和方法名，获取方法对象
     *
     * @param clazz
     * @param methodName
     * @return
     */
    public static Method getMethodByName(Class<?> clazz, String methodName) {
        Method method = null;
        // 入参不能为空
        if (null == clazz || StringUtil.isBlank(methodName)) {
            L.e("CommonUtils.getMethodByName 入参错误，clazz：" + clazz + " ；methodName："
                    + methodName);
            return method;
        }

        try {
            method = clazz.getDeclaredMethod(methodName);
        } catch (Exception e) {
            L.e("类获取方法失败！", e.getMessage());
        }

        return method;
    }

    /**
     * 将List<V>按照V的某个方法返回值（返回值必须为K类型）分组，合入到Map<K, List<V>>中<br>
     * 要保证入参的method必须为V的某一个有返回值的方法，并且该返回值必须为K类型
     *
     * @param list   待分组的列表
     * @param map    存放分组后的map
     * @param method 方法
     */
    @SuppressWarnings("unchecked")
    public static <K, V> void listGroup2Map(List<V> list, Map<K, List<V>> map, Method method) {
        // 入参非法行校验
        if (null == list || null == map || null == method) {
            L.e("CommonUtils.listGroup2Map 入参错误，list：" + list + " ；map：" + map
                    + " ；method：" + method);
            return;
        }
        try {
            // 开始分组
            Object key;
            List<V> listTmp;
            for (V val : list) {
                key = method.invoke(val);
                listTmp = map.get(key);
                if (null == listTmp) {
                    listTmp = new ArrayList<V>();
                    map.put((K) key, listTmp);
                }
                listTmp.add(val);
            }
        } catch (Exception e) {
            L.e("分组失败！", e.getMessage());
        }
    }

    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static int StringToInt(String data) {
        return (int) StringToDefaultDouble(data);
    }

    /**
     * 保留一位小数，四舍五入
     *
     * @param data
     * @return
     */
    public static double StringToDoubleOne(String data) {
        double result = StringToDefaultDouble(data);
        BigDecimal b = new BigDecimal(result);
        double f1 = b.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
        return f1;
    }

    /**
     * 四舍五入保留两位小数
     *
     * @param data
     * @return
     */
    public static double StringToDoubleTwo(String data) {
        double result = StringToDefaultDouble(data);
        BigDecimal b = new BigDecimal(result);
        double f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return f1;
    }

    public static double StringToDouble(String data) {
        return StringToDoubleTwo(data);
    }

    public static String StringToDoubleStr(String data) {
        double result = StringToDefaultDouble(data);
        return String.format("%.2f", result);
    }

    public static String StringToDoubleStr(double data) {
        return String.format("%.2f", data);
    }

    public static double StringToDefaultDouble(String data) {
        try {
            if (data == null || "".equals(data)) {
                return 0.0;
            } else {
                return Double.valueOf(data);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            L.e(e.getMessage());
            return 0;
        }
    }

    public static long StringToLong(String data) {
        try {
            if (data == null || "".equals(data)) {
                return 0;
            } else {
                return Long.valueOf(data);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            L.e(e.getMessage());
            return 0;
        }
    }

    public static boolean isGoodJson(String json) {
        if (TextUtils.isEmpty(json)) {
            return false;
        }
        try {
            new JsonParser().parse(json);
            return true;
        } catch (JsonSyntaxException e) {
            return false;
        } catch (JsonParseException e) {
            return false;
        }
    }

    /**
     * 调用第三方浏览器打开
     *
     * @param context
     * @param url     要浏览的资源地址
     */
    public static void openBrowser(Context context, String url) {
        final Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
// 注意此处的判断intent.resolveActivity()可以返回显示该Intent的Activity对应的组件名
// 官方解释 : Name of the component implementing an activity that can display the intent
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            final ComponentName componentName =
                    intent.resolveActivity(context.getPackageManager()); // 打印Log
            // ComponentName到底是什么 L.d("componentName = " + componentName.getClassName());
            context.startActivity(Intent.createChooser(intent, "请选择浏览器"));
        } else {
            Toast.makeText(context.getApplicationContext(), "请下载浏览器", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 对字符串处理:将指定位置到指定位置的字符以星号代替
     *
     * @param content 传入的字符串
     * @param begin   开始位置
     * @param end     结束位置
     * @return
     */
    public static String getStarString(String content, int begin, int end) {

        if (begin >= content.length() || begin < 0) {
            return content;
        }
        if (end >= content.length() || end < 0) {
            return content;
        }
        if (begin >= end) {
            return content;
        }
        String starStr = "";
        for (int i = begin; i < end; i++) {
            starStr = starStr + "*";
        }
        return content.substring(0, begin) + starStr + content.substring(end, content.length());

    }


    public static String changeToX(String text) {
        String str = "";
        if (!isBlank(text)) {
            for (int i = 0; i < text.length(); i++) {
                str += "*";
            }
        }
        return str;
    }

    public static boolean isLetterDigit(String str) {
        String regex = "^[a-z0-9A-Z]+$";
        return str.matches(regex);
    }


    public static boolean isPhone(String phone) {
        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|" +
                "(18[0-9])|(19[8|9]))\\d{8}$";
        if (phone.length() != 11) {
            return false;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            boolean isMatch = m.matches();
            return isMatch;
        }
    }

    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    //取视频第一帧
    public static Bitmap getVideoThumb(String path) {
        MediaMetadataRetriever media = new MediaMetadataRetriever();
        media.setDataSource(path);
        return media.getFrameAtTime();

    }

    //获取网络图片第一帧
    public static Bitmap getNetVideoBitmap(String videoUrl) {
        L.e("videoUrl");
        Bitmap bitmap = null;

        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            //根据url获取缩略图
            retriever.setDataSource(videoUrl, new HashMap());
            //获得第一帧图片
            bitmap = retriever.getFrameAtTime();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } finally {
            retriever.release();
        }
        return bitmap;
    }

    public static String getLevelByAqi(int aqi) {
        String level = "优";
        if (aqi <= 50) {
            level = "优";
        } else if (aqi <= 100) {
            level = "良";
        } else if (aqi <= 150) {
            level = "轻度污染";
        } else if (aqi <= 200) {
            level = "中度污染";
        } else if (aqi <= 300) {
            level = "重度污染";
        } else {
            level = "严重污染";
        }
        return level;
    }

    public static String getColorByAqi(int aqi) {
        String level = "#00bb00";
        if (aqi <= 50) {
            level = "#00bb00";
        } else if (aqi <= 100) {
            level = "#ffff00";
        } else if (aqi <= 150) {
            level = "#ff9900";
        } else if (aqi <= 200) {
            level = "#ff0000";
        } else if (aqi <= 300) {
            level = "#800080";
        } else {
            level = "#800000";
        }
        return level;
    }


    public static int getDrawableIdByAqi(int aqi) {
        int level = R.drawable.rect_cornor_aqi1;
        if (aqi <= 50) {
            level = R.drawable.rect_cornor_aqi1;
        } else if (aqi <= 100) {
            level = R.drawable.rect_cornor_aqi2;
        } else if (aqi <= 150) {
            level = R.drawable.rect_cornor_aqi3;
        } else if (aqi <= 200) {
            level = R.drawable.rect_cornor_aqi4;
        } else if (aqi <= 300) {
            level = R.drawable.rect_cornor_aqi5;
        } else {
            level = R.drawable.rect_cornor_aqi6;
        }
        return level;
    }

    //getImageFromAssetsFile("weather/100.png")
    public static Bitmap getImageFromAssetsFile(Context context, String fileName) {
        Bitmap image = null;
        AssetManager am = context.getResources().getAssets();
        try {
            InputStream is = am.open(fileName);
            image = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }


    //定位一次
    public static LocationClient getDefaultLocationClient(Context context,
                                                          BDAbstractLocationListener myLocationListener) {
        //定位服务的客户端。宿主程序在客户端声明此类，并调用，目前只支持在主线程中启动
        LocationClient locationClient = new LocationClient(context);
//声明LocationClient类实例并配置定位参数
        LocationClientOption locationOption = new LocationClientOption();
//注册监听函数
        locationClient.registerLocationListener(myLocationListener);
//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        locationOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
//可选，默认gcj02，设置返回的定位结果坐标系，如果配合百度地图使用，建议设置为bd09ll;
        locationOption.setCoorType("bd09ll");
//可选，默认0，即仅定位一次，设置发起连续定位请求的间隔需要大于等于1000ms才是有效的
        locationOption.setScanSpan(0);
//可选，设置是否需要地址信息，默认不需要
        locationOption.setIsNeedAddress(true);
//可选，设置是否需要地址描述
        locationOption.setIsNeedLocationDescribe(true);
//可选，设置是否需要设备方向结果
        locationOption.setNeedDeviceDirect(false);
//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        locationOption.setLocationNotify(true);
//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        locationOption.setIgnoreKillProcess(true);
//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        locationOption.setIsNeedLocationDescribe(true);
//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        locationOption.setIsNeedLocationPoiList(true);
//可选，默认false，设置是否收集CRASH信息，默认收集
        locationOption.SetIgnoreCacheException(false);
//可选，默认false，设置是否开启Gps定位
        locationOption.setOpenGps(true);
//可选，默认false，设置定位时是否需要海拔信息，默认不需要，除基础定位版本都可用
        locationOption.setIsNeedAltitude(false);
//设置打开自动回调位置模式，该开关打开后，期间只要定位SDK检测到位置变化就会主动回调给开发者，该模式下开发者无需再关心定位间隔是多少，定位SDK本身发现位置变化就会及时回调给开发者
        locationOption.setOpenAutoNotifyMode();
//设置打开自动回调位置模式，该开关打开后，期间只要定位SDK检测到位置变化就会主动回调给开发者
        locationOption.setOpenAutoNotifyMode(3000, 1, LocationClientOption.LOC_SENSITIVITY_HIGHT);
//需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
        locationClient.setLocOption(locationOption);
//开始定位
        return locationClient;
    }
}
