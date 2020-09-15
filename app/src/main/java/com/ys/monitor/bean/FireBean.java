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
     * data : {"total":3,"rows":[{"recNo":"4028819073e7a7630173e7a95e280000","deviceNo":null,
     * "deviceName":null,"name":"宝塔峰附近疑似起火","areaCode":"500115001015","longitude":"106.382413",
     * "latitude":"29.829427","altitude":"300","warnDesc":"火情登记修改",
     * "source":"40289fa573e690820173e6a6437a0004","sourceName":"电话报警",
     * "warnerPhone":"13858624885","warnPerson":"周林","warnTime":1597319488000,"warnLongitude":"",
     * "warnLatitude":"","warnAltitude":"","status":"4028819073e0c45d0173e0c9c9170006",
     * "statusName":"已扑灭","checkPerson":null,"checkTime":null,"description":null,
     * "imgUrl":"/data/warn/15658bf45377429191ec1d28702ef484/22.jpg;",
     * "videoUrl":"/data/warn/916923d93fc240849f9ba297b9e85758/EA2B0165CBCCCD5163BC462134713919中文
     * .mp4;","agency":"3","smallClassNo":"2","smallPlaceName":"狮子坪黄桷树信息",
     * "investigationAddr":"长寿区八颗街道幸福村","siteSplicing":"长寿区八颗街道幸福村3社2小班狮子坪黄桷树信息",
     * "start_time":null,"end_time":null,"isDelete":"0",
     * "createUserNo":"402880905e31cb26015e31cb5d290000","createTime":1598931792000},{"recNo
     * ":"5b198d61747b504f01747cda63b20006","deviceNo":null,"deviceName":null,"name":"app火情测试2",
     * "areaCode":"500115001015","longitude":"106.53063501","latitude":"29.54460611",
     * "altitude":null,"warnDesc":"着火了","source":"40289fa573e690820173e6a6ceaa0006",
     * "sourceName":"APP报警","warnerPhone":null,"warnPerson":null,"warnTime":1599813126000,
     * "warnLongitude":null,"warnLatitude":null,"warnAltitude":null,
     * "status":"4028819073e0c45d0173e0c9529f0002","statusName":"核实中","checkPerson":null,
     * "checkTime":null,"description":null,
     * "imgUrl":"/data/warn/d9aa86a9a8084dfa8848e1d971d672d1/20200410025925.jpg;
     * /data/warn/7219322cddff47188b6dccb0505bcb76/20200410034854.jpg;","videoUrl":"",
     * "agency":"4","smallClassNo":"5","smallPlaceName":null,"investigationAddr":"长寿区八颗街道幸福村",
     * "siteSplicing":"长寿区八颗街道幸福村4社5小班","start_time":null,"end_time":null,"isDelete":"0",
     * "createUserNo":"40289fa5745ce5aa01745ce871f20009","createTime":1599822521000},{"recNo
     * ":"5b198d61747b504f01747c4aa9b10005","deviceNo":null,"deviceName":null,"name":"app火情测试1",
     * "areaCode":"500115001015","longitude":"106.53063501","latitude":"29.54460611",
     * "altitude":null,"warnDesc":"火情","source":"40289fa573e690820173e6a6ceaa0006",
     * "sourceName":"APP报警","warnerPhone":null,"warnPerson":null,"warnTime":1599814063000,
     * "warnLongitude":null,"warnLatitude":null,"warnAltitude":null,
     * "status":"4028819073e0c45d0173e0c9529f0002","statusName":"核实中","checkPerson":null,
     * "checkTime":null,"description":null,
     * "imgUrl":"/data/warn/a04122022fd047b59d554f15d5eb9445/Screenshot_2019-09-03-13-18-25
     * -824_com.upsoft.ckq.png;","videoUrl":"","agency":"4","smallClassNo":"5",
     * "smallPlaceName":null,"investigationAddr":"长寿区八颗街道幸福村","siteSplicing":"长寿区八颗街道幸福村4社5小班",
     * "start_time":null,"end_time":null,"isDelete":"0",
     * "createUserNo":"40289fa5745ce5aa01745ce871f20009","createTime":1599813102000}],"footer":null}
     * code : 200
     * msg : 火情数据获取成功!
     */

    public DataBean data;
    public String code;
    public String msg;

    public static class DataBean {
        /**
         * total : 3
         * rows : [{"recNo":"4028819073e7a7630173e7a95e280000","deviceNo":null,"deviceName":null,
         * "name":"宝塔峰附近疑似起火","areaCode":"500115001015","longitude":"106.382413","latitude":"29
         * .829427","altitude":"300","warnDesc":"火情登记修改",
         * "source":"40289fa573e690820173e6a6437a0004","sourceName":"电话报警",
         * "warnerPhone":"13858624885","warnPerson":"周林","warnTime":1597319488000,
         * "warnLongitude":"","warnLatitude":"","warnAltitude":"",
         * "status":"4028819073e0c45d0173e0c9c9170006","statusName":"已扑灭","checkPerson":null,
         * "checkTime":null,"description":null,
         * "imgUrl":"/data/warn/15658bf45377429191ec1d28702ef484/22.jpg;",
         * "videoUrl":"/data/warn/916923d93fc240849f9ba297b9e85758
         * /EA2B0165CBCCCD5163BC462134713919中文.mp4;","agency":"3","smallClassNo":"2",
         * "smallPlaceName":"狮子坪黄桷树信息","investigationAddr":"长寿区八颗街道幸福村",
         * "siteSplicing":"长寿区八颗街道幸福村3社2小班狮子坪黄桷树信息","start_time":null,"end_time":null,
         * "isDelete":"0","createUserNo":"402880905e31cb26015e31cb5d290000",
         * "createTime":1598931792000},{"recNo":"5b198d61747b504f01747cda63b20006",
         * "deviceNo":null,"deviceName":null,"name":"app火情测试2","areaCode":"500115001015",
         * "longitude":"106.53063501","latitude":"29.54460611","altitude":null,"warnDesc":"着火了",
         * "source":"40289fa573e690820173e6a6ceaa0006","sourceName":"APP报警","warnerPhone":null,
         * "warnPerson":null,"warnTime":1599813126000,"warnLongitude":null,"warnLatitude":null,
         * "warnAltitude":null,"status":"4028819073e0c45d0173e0c9529f0002","statusName":"核实中",
         * "checkPerson":null,"checkTime":null,"description":null,
         * "imgUrl":"/data/warn/d9aa86a9a8084dfa8848e1d971d672d1/20200410025925.jpg;
         * /data/warn/7219322cddff47188b6dccb0505bcb76/20200410034854.jpg;","videoUrl":"",
         * "agency":"4","smallClassNo":"5","smallPlaceName":null,
         * "investigationAddr":"长寿区八颗街道幸福村","siteSplicing":"长寿区八颗街道幸福村4社5小班","start_time":null,
         * "end_time":null,"isDelete":"0","createUserNo":"40289fa5745ce5aa01745ce871f20009",
         * "createTime":1599822521000},{"recNo":"5b198d61747b504f01747c4aa9b10005",
         * "deviceNo":null,"deviceName":null,"name":"app火情测试1","areaCode":"500115001015",
         * "longitude":"106.53063501","latitude":"29.54460611","altitude":null,"warnDesc":"火情",
         * "source":"40289fa573e690820173e6a6ceaa0006","sourceName":"APP报警","warnerPhone":null,
         * "warnPerson":null,"warnTime":1599814063000,"warnLongitude":null,"warnLatitude":null,
         * "warnAltitude":null,"status":"4028819073e0c45d0173e0c9529f0002","statusName":"核实中",
         * "checkPerson":null,"checkTime":null,"description":null,
         * "imgUrl":"/data/warn/a04122022fd047b59d554f15d5eb9445/Screenshot_2019-09-03-13-18-25
         * -824_com.upsoft.ckq.png;","videoUrl":"","agency":"4","smallClassNo":"5",
         * "smallPlaceName":null,"investigationAddr":"长寿区八颗街道幸福村",
         * "siteSplicing":"长寿区八颗街道幸福村4社5小班","start_time":null,"end_time":null,"isDelete":"0",
         * "createUserNo":"40289fa5745ce5aa01745ce871f20009","createTime":1599813102000}]
         * footer : null
         */

        public int total;
        public Object footer;
        public List<RowsBean> rows;

        public static class RowsBean {
            /**
             * recNo : 4028819073e7a7630173e7a95e280000
             * deviceNo : null
             * deviceName : null
             * name : 宝塔峰附近疑似起火
             * areaCode : 500115001015
             * longitude : 106.382413
             * latitude : 29.829427
             * altitude : 300
             * warnDesc : 火情登记修改
             * source : 40289fa573e690820173e6a6437a0004
             * sourceName : 电话报警
             * warnerPhone : 13858624885
             * warnPerson : 周林
             * warnTime : 1597319488000
             * warnLongitude :
             * warnLatitude :
             * warnAltitude :
             * status : 4028819073e0c45d0173e0c9c9170006
             * statusName : 已扑灭
             * checkPerson : null
             * checkTime : null
             * description : null
             * imgUrl : /data/warn/15658bf45377429191ec1d28702ef484/22.jpg;
             * videoUrl : /data/warn/916923d93fc240849f9ba297b9e85758
             * /EA2B0165CBCCCD5163BC462134713919中文.mp4;
             * agency : 3
             * smallClassNo : 2
             * smallPlaceName : 狮子坪黄桷树信息
             * investigationAddr : 长寿区八颗街道幸福村
             * siteSplicing : 长寿区八颗街道幸福村3社2小班狮子坪黄桷树信息
             * start_time : null
             * end_time : null
             * isDelete : 0
             * createUserNo : 402880905e31cb26015e31cb5d290000
             * createTime : 1598931792000
             */

            public String recNo;
            public Object deviceNo;
            public Object deviceName;
            public String name;
            public String areaCode;
            public String longitude;
            public String latitude;
            public String altitude;
            public String warnDesc;
            public String source;
            public String sourceName;
            public String warnerPhone;
            public String warnPerson;
            public long warnTime;
            public String warnLongitude;
            public String warnLatitude;
            public String warnAltitude;
            public String status;
            public String statusName;
            public Object checkPerson;
            public Object checkTime;
            public Object description;
            public String imgUrl;
            public String videoUrl;
            public String agency;
            public String smallClassNo;
            public String smallPlaceName;
            public String investigationAddr;
            public String siteSplicing;
            public Object start_time;
            public Object end_time;
            public String isDelete;
            public String createUserNo;
            public long createTime;
        }
    }
}
