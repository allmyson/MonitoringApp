package com.ys.monitor.bean;

import com.ys.monitor.util.YS;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lh
 * @version 1.0.0
 * @filename KVBean
 * @description -------------------------------------------------------
 * @date 2020/9/11 17:54
 */
public class KVBean {
    public String id;
    public String name;

    public KVBean() {

    }

    public KVBean(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public static List<KVBean> getStatusTypeList() {
        List<KVBean> list = new ArrayList<>();
//        list.add(new KVBean(YS.FireStatus.Status_DCL, "待处理"));
//        list.add(new KVBean(YS.FireStatus.Status_HSZ, "核实中"));
        list.add(new KVBean(YS.FireStatus.Status_WB, "误报"));
        list.add(new KVBean(YS.FireStatus.Status_YWYH, "野外用火"));
        list.add(new KVBean(YS.FireStatus.Status_FSHZ, "发生火灾"));
        list.add(new KVBean(YS.FireStatus.Status_YPM, "已扑灭"));
        return list;
    }

    //巡护状态
    public static List<KVBean> getXHStatusList() {
        List<KVBean> list = new ArrayList<>();
        list.add(new KVBean( "0", "存在隐患"));
        list.add(new KVBean( "1", "正常"));
        list.add(new KVBean( "2", "预警"));
        return list;
    }
}
