package com.ys.monitor.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author lh
 * @version 1.0.0
 * @filename ZhUtil
 * @description -------------------------------------------------------
 * @date 2018/11/13 16:26
 */
public class ZhUtil {
    /**
     * 对字符串中元素进行重排序
     * 此外还可以在该方法对元素进行去重等
     * @param str  原字符串
     * @return  目标字符串
     */
    public static String stringFilter(String str){
        char[] c = str.toCharArray();
        Arrays.sort(c);
        return new String(c);
    }

    /**
     * 得到组合结果
     * @param num   从N个数中选取num个数
     * @param str   包含Ng个元素的字符串
     * @return  组合结果
     */
    public static List<String> getCombinationResult(int num, String str) {
        List<String> result = new ArrayList<String>();
        if (num == 1) {
            for (char c : str.toCharArray()) {
                result.add(String.valueOf(c));
            }
            return result;
        }
        if (num >= str.length()) {
            result.add(str);
            return result;
        }
        int strlen = str.length();
        for (int i = 0; i < (strlen - num + 1); i++) {
            List<String> cr = getCombinationResult(num - 1, str.substring(i + 1));//从i+1处直至字符串末尾
            char c = str.charAt(i);//得到上面被去掉的字符，进行组合
            for (String s : cr) {
                result.add(c + s);
            }
        }
        return result;
    }
}
