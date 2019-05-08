package com.ys.zy.util;

import com.ys.zy.bean.KeyValue;
import com.ys.zy.bean.OddBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OddUtil {
    public static List<String> getGameList() {
        List<String> list = new ArrayList<>();
        list.add("快3");
        list.add("赛车");
        list.add("时时彩");
        list.add("轮盘");
        list.add("推筒子");
        return list;
    }

    public static List<String> getPlayList(String game) {
        List<String> list = new ArrayList<>();
        switch (game) {
            case "快3":
                list.add("和值3/18");
                list.add("和值4/17");
                list.add("和值5/16");
                list.add("和值6/15");
                list.add("和值7/14");
                list.add("和值8/13");
                list.add("和值9/12");
                list.add("和值10/11");
                list.add("大小单双");
                break;
            case "赛车":
                list.add("大小单双");
                list.add("龙虎斗");
                list.add("定位胆");
                break;
            case "时时彩":
                list.add("定位胆");
                list.add("大小单双");
                list.add("后二星组选");
                list.add("五星直选");
                break;
            case "轮盘":
                list.add("轮盘");
                break;
            case "推筒子":
                list.add("庄闲");
                list.add("平");
                break;
        }
        return list;
    }

    public static List<KeyValue> getList(List<OddBean.DataBean> dataBeanList, String game, String play) {
        List<KeyValue> list = new ArrayList<>();
        if (dataBeanList != null && dataBeanList.size() > 0) {
            Map<String, OddBean.DataBean> map = new HashMap<>();
            for (OddBean.DataBean dataBean : dataBeanList) {
                map.put(dataBean.odds, dataBean);
            }
            String filedName = "";
            switch (game) {
                case "快3":
                    switch (play) {
                        case "和值3/18":
                            filedName = "ks_hz_3_18";
                            break;
                        case "和值4/17":
                            filedName = "ks_hz_4_17";
                            break;
                        case "和值5/16":
                            filedName = "ks_hz_5_16";
                            break;
                        case "和值6/15":
                            filedName = "ks_hz_6_15";
                            break;
                        case "和值7/14":
                            filedName = "ks_hz_7_14";
                            break;
                        case "和值8/13":
                            filedName = "ks_hz_8_13";
                            break;
                        case "和值9/12":
                            filedName = "ks_hz_9_12";
                            break;
                        case "和值10/11":
                            filedName = "ks_hz_10_11";
                            break;
                        case "大小单双":
                            filedName = "ks_dxds";
                            break;
                    }
                    break;
                case "赛车":
                    switch (play) {
                        case "大小单双":
                            filedName = "sc_dxds";
                            break;
                        case "龙虎斗":
                            filedName = "sc_lhd";
                            break;
                        case "定位胆":
                            filedName = "sc_dwd";
                            break;
                    }
                    break;
                case "时时彩":
                    switch (play) {
                        case "定位胆":
                            filedName = "ssc_dwd";
                            break;
                        case "大小单双":
                            filedName = "ssc_dxds";
                            break;
                        case "后二星组选":
                            filedName = "ssc_hexzx";
                            break;
                        case "五星直选":
                            filedName = "ssc_wxzx";
                            break;
                    }
                    break;
                case "轮盘":
                    switch (play) {
                        case "轮盘":
                            filedName = "lunpan";
                            break;
                    }
                    break;
                case "推筒子":
                    switch (play) {
                        case "庄闲":
                            filedName = "ttz_zx";
                            break;
                        case "平":
                            filedName = "ttz_ping";
                            break;
                    }
                    break;
            }
            try {
                for (Map.Entry<String, OddBean.DataBean> entry : map.entrySet()) {
                    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
//                    KeyValue keyValue = new KeyValue(entry.getKey(), entry.getValue().ks_hz_3_18);
                    OddBean.DataBean bean = entry.getValue();
                    String value = (String) bean.getClass().getField(filedName).get(bean);
                    KeyValue keyValue = new KeyValue(entry.getKey(), value);
                    list.add(keyValue);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}
