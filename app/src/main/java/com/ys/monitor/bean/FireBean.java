package com.ys.monitor.bean;

import java.util.List;

/**
 * @author lh
 * @version 1.0.0
 * @filename FireBean
 * @description -------------------------------------------------------
 * @date 2020/9/10 18:21
 */
public class FireBean {

    /**
     * data : {"total":1,"rows":[{"agency":"3","altitude":"300","areaCode":"500115001015",
     * "checkPerson":"","checkTime":"","createTime":"2020-09-19 13:49",
     * "createUserNo":"402880905e31cb26015e31cb5d290000","description":"","end_time":"",
     * "imgUrl":"/warn/92165665-de95-407b-9d3c-74aca6c9e46d/login.png;",
     * "investigationAddr":"长寿区八颗街道幸福村","isDelete":"","latitude":"20","longitude":"106",
     * "recNo":"4028819073e7a7630173e7a95e280001","siteSplicing":"长寿区八颗街道幸福村3社2小班狮子坪黄桷树信息",
     * "smallClassNo":"2","smallPlaceName":"狮子坪黄桷树信息",
     * "source":"40289fa573e690820173e6a6437a0004","sourceName":"","start_time":"","status":"",
     * "statusName":"核实中","videoUrl":"/warn/916923d9-3fc2-4084-9f9b-a297b9e85758
     * /EA2B0165CBCCCD5163BC462134713919中文.mp4;","warnAltitude":"","warnDesc":"火情添加",
     * "warnLatitude":"20","warnLongitude":"106","warnPerson":"","warnTime":"2020-09-19 13:49",
     * "warnerPhone":""}],"footer":null}
     * code : 200
     * msg : 火情数据获取成功!
     */

    public DataBean data;
    public String code;
    public String msg;

    public static class DataBean {
        /**
         * total : 1
         * rows : [{"agency":"3","altitude":"300","areaCode":"500115001015","checkPerson":"",
         * "checkTime":"","createTime":"2020-09-19 13:49",
         * "createUserNo":"402880905e31cb26015e31cb5d290000","description":"","end_time":"",
         * "imgUrl":"/warn/92165665-de95-407b-9d3c-74aca6c9e46d/login.png;",
         * "investigationAddr":"长寿区八颗街道幸福村","isDelete":"","latitude":"20","longitude":"106",
         * "recNo":"4028819073e7a7630173e7a95e280001","siteSplicing":"长寿区八颗街道幸福村3社2小班狮子坪黄桷树信息",
         * "smallClassNo":"2","smallPlaceName":"狮子坪黄桷树信息",
         * "source":"40289fa573e690820173e6a6437a0004","sourceName":"","start_time":"",
         * "status":"","statusName":"核实中","videoUrl":"/warn/916923d9-3fc2-4084-9f9b-a297b9e85758
         * /EA2B0165CBCCCD5163BC462134713919中文.mp4;","warnAltitude":"","warnDesc":"火情添加",
         * "warnLatitude":"20","warnLongitude":"106","warnPerson":"","warnTime":"2020-09-19
         * 13:49","warnerPhone":""}]
         * footer : null
         */

        public int total;
        public Object footer;
        public List<RowsBean> rows;

        public static class RowsBean {
            /**
             * agency : 3
             * altitude : 300
             * areaCode : 500115001015
             * checkPerson :
             * checkTime :
             * createTime : 2020-09-19 13:49
             * createUserNo : 402880905e31cb26015e31cb5d290000
             * description :
             * end_time :
             * imgUrl : /warn/92165665-de95-407b-9d3c-74aca6c9e46d/login.png;
             * investigationAddr : 长寿区八颗街道幸福村
             * isDelete :
             * latitude : 20
             * longitude : 106
             * recNo : 4028819073e7a7630173e7a95e280001
             * siteSplicing : 长寿区八颗街道幸福村3社2小班狮子坪黄桷树信息
             * smallClassNo : 2
             * smallPlaceName : 狮子坪黄桷树信息
             * source : 40289fa573e690820173e6a6437a0004
             * sourceName :
             * start_time :
             * status :
             * statusName : 核实中
             * videoUrl : /warn/916923d9-3fc2-4084-9f9b-a297b9e85758
             * /EA2B0165CBCCCD5163BC462134713919中文.mp4;
             * warnAltitude :
             * warnDesc : 火情添加
             * warnLatitude : 20
             * warnLongitude : 106
             * warnPerson :
             * warnTime : 2020-09-19 13:49
             * warnerPhone :
             */

            public String agency;
            public String altitude;
            public String areaCode;
            public String checkPerson;
            public String checkTime;
            public String createTime;
            public String createUserNo;
            public String description;
            public String end_time;
            public String imgUrl;
            public String investigationAddr;
            public String isDelete;
            public String latitude;
            public String longitude;
            public String recNo;
            public String siteSplicing;
            public String smallClassNo;
            public String smallPlaceName;
            public String source;
            public String sourceName;
            public String start_time;
            public String status;
            public String statusName;
            public String videoUrl;
            public String warnAltitude;
            public String warnDesc;
            public String warnLatitude;
            public String warnLongitude;
            public String warnPerson;
            public String warnTime;
            public String warnerPhone;
        }
    }
}
