package com.ys.monitor.bean;

import java.util.List;

/**
 * @author lh
 * @version 1.0.0
 * @filename VideoBean
 * @description -------------------------------------------------------
 * @date 2020/9/14 14:45
 */
public class VideoBean {


    /**
     * data : {"total":48,"rows":[{"imgUrl":"","modelNo":"DS-2CD268XYZUV-555","status":"-1   ",
     * "investigationAddr":"三角花园----高清点","devName":"气象采集设备3","factory":"海康威视","type":"气象采集设备",
     * "recNo":"40289fa574203e8301742052c0bc0050"}],"footer":null}
     * code : 200
     * msg : 设备信息获取成功!
     */

    public DataBean data;
    public String code;
    public String msg;

    public static class DataBean {
        /**
         * total : 48
         * rows : [{"imgUrl":"","modelNo":"DS-2CD268XYZUV-555","status":"-1   ",
         * "investigationAddr":"三角花园----高清点","devName":"气象采集设备3","factory":"海康威视",
         * "type":"气象采集设备","recNo":"40289fa574203e8301742052c0bc0050"}]
         * footer : null
         */

        public int total;
        public Object footer;
        public List<RowsBean> rows;

        public static class RowsBean {
            /**
             * imgUrl :
             * modelNo : DS-2CD268XYZUV-555
             * status : -1
             * investigationAddr : 三角花园----高清点
             * devName : 气象采集设备3
             * factory : 海康威视
             * type : 气象采集设备
             * recNo : 40289fa574203e8301742052c0bc0050
             */

            public String imgUrl;
            public String modelNo;
            public String status;
            public String investigationAddr;
            public String devName;
            public String factory;
            public String type;
            public String recNo;
        }
    }
}
