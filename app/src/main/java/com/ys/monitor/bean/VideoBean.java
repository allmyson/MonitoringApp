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
     * data : {"total":1,"rows":[{"accessUrl":"","agency":"","altitude":"","createTime":null,
     * "createUserNo":"","description":"","devName":"红外探测器16","devNo":"1111111","end_time":"",
     * "factory":"","imgUrl":"","installationTime":null,"investigationAddr":"","isDelete":"0",
     * "latitude":"","longitude":"","modelNo":"","recNo":"40288ae96705bda4016705bf07d00007",
     * "responseDeptNo":"","smallClassNo":"","smallPlaceName":"","start_time":"","status":"0",
     * "t_InstallationTime":"","type":"40289fa573e141640173e14ea2da0005","typeName":"",
     * "warranty":""}],"footer":null}
     * code : 200
     * msg : 设备信息成功!
     */

    public DataBean data;
    public String code;
    public String msg;

    public static class DataBean {
        /**
         * total : 1
         * rows : [{"accessUrl":"","agency":"","altitude":"","createTime":null,"createUserNo":"",
         * "description":"","devName":"红外探测器16","devNo":"1111111","end_time":"","factory":"",
         * "imgUrl":"","installationTime":null,"investigationAddr":"","isDelete":"0",
         * "latitude":"","longitude":"","modelNo":"","recNo":"40288ae96705bda4016705bf07d00007",
         * "responseDeptNo":"","smallClassNo":"","smallPlaceName":"","start_time":"",
         * "status":"0","t_InstallationTime":"","type":"40289fa573e141640173e14ea2da0005",
         * "typeName":"","warranty":""}]
         * footer : null
         */

        public int total;
        public Object footer;
        public List<RowsBean> rows;

        public static class RowsBean {
            /**
             * accessUrl :
             * agency :
             * altitude :
             * createTime : null
             * createUserNo :
             * description :
             * devName : 红外探测器16
             * devNo : 1111111
             * end_time :
             * factory :
             * imgUrl :
             * installationTime : null
             * investigationAddr :
             * isDelete : 0
             * latitude :
             * longitude :
             * modelNo :
             * recNo : 40288ae96705bda4016705bf07d00007
             * responseDeptNo :
             * smallClassNo :
             * smallPlaceName :
             * start_time :
             * status : 0
             * t_InstallationTime :
             * type : 40289fa573e141640173e14ea2da0005
             * typeName :
             * warranty :
             */

            public String accessUrl;
            public String agency;
            public String altitude;
            public Object createTime;
            public String createUserNo;
            public String description;
            public String devName;
            public String devNo;
            public String end_time;
            public String factory;
            public String imgUrl;
            public Object installationTime;
            public String investigationAddr;
            public String isDelete;
            public String latitude;
            public String longitude;
            public String modelNo;
            public String recNo;
            public String responseDeptNo;
            public String smallClassNo;
            public String smallPlaceName;
            public String start_time;
            public String status;
            public String t_InstallationTime;
            public String type;
            public String typeName;
            public String warranty;
        }
    }
}
