package com.ys.monitor.bean;

/**
 * @author lh
 * @version 1.0.0
 * @filename RecordBean
 * @description -------------------------------------------------------
 * @date 2020/10/26 17:13
 */
public class RecordBean {
    public static final String TYPE_FIRE= "火情上报";
    public static final String TYPE_XUHU= "日常巡护";
    public static final String TYPE_ZIYUAN= "资源采集";
    public static final String DO_ADD= "新增";
    public static final String DO_UPDATE= "更新";
    public static final String DO_DELETE= "删除";
    public String id;
    public String name;//火情上报、日常巡护、资源采集
    public String handleType;//新增、更新、删除
    public long createTime;
    public String address;
    public boolean isSucc;//是否上报成功
}
