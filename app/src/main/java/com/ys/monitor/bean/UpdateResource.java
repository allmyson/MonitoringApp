package com.ys.monitor.bean;

import java.util.List;

/**
 * @author lh
 * @version 1.0.0
 * @filename UpdateResource
 * @description -------------------------------------------------------
 * @date 2020/11/5 14:38
 */
public class UpdateResource {

    /**
     * data : {"elementBasic":{"recNo":"620e65bc427c32702917fd8dfaeb0004","name":"林场水库",
     * "elementType":"40289fba74673c6b0174674e04870029","elementTypeName":"消防水池",
     * "resourceTypeName":"防火","createUserNo":"4028819075737bc001757395ce700023",
     * "createUserName":null,"createTime":-2209017600000,"description":null,"imgUrl":null,
     * "isDelete":"0"},"elementBasicEx":[{"name":"小地名","dataValue":null,
     * "dataName":"smallPlaceName"},{"name":"小班号","dataValue":null,"dataName":"smallClassNo"},{
     * "name":"乡镇","dataValue":"凤凰镇","dataName":"township"},{"name":"村","dataValue":"皂角树村",
     * "dataName":"village"},{"name":"行政区划","dataValue":"500106002007","dataName":"areaCode"},{
     * "name":"区县","dataValue":"沙坪坝区","dataName":"county"},{"name":"社","dataValue":"1",
     * "dataName":"agency"},{"name":"地点","dataValue":"沙坪坝区凤凰镇皂角树村1社",
     * "dataName":"investigationAddr"},{"name":"经度","dataValue":"106.30442521",
     * "dataName":"longitude"},{"name":"蓄水量","dataValue":"","dataName":"impoundage"},{"name
     * ":"纬度","dataValue":"29.69004853","dataName":"latitude"},{"name":"OBJECTID",
     * "dataValue":"5","dataName":"OBJECTID"}]}
     * code : 200
     * msg : 查询单个资源详情成功!
     */

    public DataBean data;
    public String code;
    public String msg;

    public static class DataBean {
        /**
         * elementBasic : {"recNo":"620e65bc427c32702917fd8dfaeb0004","name":"林场水库",
         * "elementType":"40289fba74673c6b0174674e04870029","elementTypeName":"消防水池",
         * "resourceTypeName":"防火","createUserNo":"4028819075737bc001757395ce700023",
         * "createUserName":null,"createTime":-2209017600000,"description":null,"imgUrl":null,
         * "isDelete":"0"}
         * elementBasicEx : [{"name":"小地名","dataValue":null,"dataName":"smallPlaceName"},{"name
         * ":"小班号","dataValue":null,"dataName":"smallClassNo"},{"name":"乡镇","dataValue":"凤凰镇",
         * "dataName":"township"},{"name":"村","dataValue":"皂角树村","dataName":"village"},{"name
         * ":"行政区划","dataValue":"500106002007","dataName":"areaCode"},{"name":"区县",
         * "dataValue":"沙坪坝区","dataName":"county"},{"name":"社","dataValue":"1",
         * "dataName":"agency"},{"name":"地点","dataValue":"沙坪坝区凤凰镇皂角树村1社",
         * "dataName":"investigationAddr"},{"name":"经度","dataValue":"106.30442521",
         * "dataName":"longitude"},{"name":"蓄水量","dataValue":"","dataName":"impoundage"},{"name
         * ":"纬度","dataValue":"29.69004853","dataName":"latitude"},{"name":"OBJECTID",
         * "dataValue":"5","dataName":"OBJECTID"}]
         */

        public ElementBasicBean elementBasic;
        public List<ElementBasicExBean> elementBasicEx;

        public static class ElementBasicBean {
            /**
             * recNo : 620e65bc427c32702917fd8dfaeb0004
             * name : 林场水库
             * elementType : 40289fba74673c6b0174674e04870029
             * elementTypeName : 消防水池
             * resourceTypeName : 防火
             * createUserNo : 4028819075737bc001757395ce700023
             * createUserName : null
             * createTime : -2209017600000
             * description : null
             * imgUrl : null
             * isDelete : 0
             */

            public String recNo;
            public String name;
            public String elementType;
            public String elementTypeName;
            public String resourceTypeName;
            public String createUserNo;
            public String createUserName;
            public long createTime;
            public String description;
            public String imgUrl;
            public String isDelete;
        }

        public static class ElementBasicExBean {
            /**
             * name : 小地名
             * dataValue : null
             * dataName : smallPlaceName
             */

            public String name;
            public String dataValue;
            public String dataName;
        }
    }
}
