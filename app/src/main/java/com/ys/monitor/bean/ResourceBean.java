package com.ys.monitor.bean;

import java.util.List;

public class ResourceBean {

    /**
     * data : {"total":1,"rows":[{"createTime":"2020-09-19 13:49","createUserName":"admin","createUserNo":"402880905e31cb26015e31cb5d290000","description":"","name":"通信线","recNo":"4028819073e5990c0173e5a07b9b0033","resourcetype":"4028819073c3b2580173c3bbab4d0003","resourcetypeName":"基础","updateTime":"2020-09-19 13:49","updateUserNo":"402880905e31cb26015e31cb5d290000"}],"footer":null}
     * code : 200
     * msg : 资源类型成功!
     */

    public DataBean data;
    public String code;
    public String msg;

    public static class DataBean {
        /**
         * total : 1
         * rows : [{"createTime":"2020-09-19 13:49","createUserName":"admin","createUserNo":"402880905e31cb26015e31cb5d290000","description":"","name":"通信线","recNo":"4028819073e5990c0173e5a07b9b0033","resourcetype":"4028819073c3b2580173c3bbab4d0003","resourcetypeName":"基础","updateTime":"2020-09-19 13:49","updateUserNo":"402880905e31cb26015e31cb5d290000"}]
         * footer : null
         */

        public int total;
        public Object footer;
        public List<RowsBean> rows;

        public static class RowsBean {
            /**
             * createTime : 2020-09-19 13:49
             * createUserName : admin
             * createUserNo : 402880905e31cb26015e31cb5d290000
             * description :
             * name : 通信线
             * recNo : 4028819073e5990c0173e5a07b9b0033
             * resourcetype : 4028819073c3b2580173c3bbab4d0003
             * resourcetypeName : 基础
             * updateTime : 2020-09-19 13:49
             * updateUserNo : 402880905e31cb26015e31cb5d290000
             */

            public String createTime;
            public String createUserName;
            public String createUserNo;
            public String description;
            public String name;
            public String recNo;
            public String resourcetype;
            public String resourcetypeName;
            public String updateTime;
            public String updateUserNo;
        }
    }
}
