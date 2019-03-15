package com.ys.zy.bean;

public class AppInfo {


    /**
     * code : SUCCESS
     * msg : 查询成功
     * data : {"appId":"d36169a04c614c079b2840f7a6a2477f","apkTypeCode":"1000","apkTypeName":"安卓系统","downloadUrl":"http://103.65.180.104:8090/downloadFile?type=1000","versionName":"1.1","versionNum":"2","versionRemark":"app-release1.1.apk","versionTime":1548769004274,"sendTime":"2019-01-29T13:36:44.000+0000"}
     * systemDate : 1548772916447
     */

    public String code;
    public String msg;
    public DataBean data;
    public long systemDate;

    public static class DataBean {
        /**
         * appId : d36169a04c614c079b2840f7a6a2477f
         * apkTypeCode : 1000
         * apkTypeName : 安卓系统
         * downloadUrl : http://103.65.180.104:8090/downloadFile?type=1000
         * versionName : 1.1
         * versionNum : 2
         * versionRemark : app-release1.1.apk
         * versionTime : 1548769004274
         * sendTime : 2019-01-29T13:36:44.000+0000
         */

        public String appId;
        public String apkTypeCode;
        public String apkTypeName;
        public String downloadUrl;
        public String versionName;
        public String versionNum;
        public String versionRemark;
        public long versionTime;
        public String sendTime;
    }
}
