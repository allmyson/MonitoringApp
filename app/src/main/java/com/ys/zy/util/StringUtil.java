package com.ys.zy.util;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;


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
    public static <K, V> void listGroup2Map(List<V> list, Map<K, List<V>> map, Class<V> clazz, String methodName) {
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
            final ComponentName componentName = intent.resolveActivity(context.getPackageManager()); // 打印Log   ComponentName到底是什么 L.d("componentName = " + componentName.getClassName());
            context.startActivity(Intent.createChooser(intent, "请选择浏览器"));
        } else {
            Toast.makeText(context.getApplicationContext(), "请下载浏览器", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 对字符串处理:将指定位置到指定位置的字符以星号代替
     *
     * @param content
     *            传入的字符串
     * @param begin
     *            开始位置
     * @param end
     *            结束位置
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


    public static String changeToX(String text){
        String str = "";
        if(!isBlank(text)){
            for(int i = 0;i<text.length();i++){
                str += "*";
            }
        }
        return str;
    }
}
