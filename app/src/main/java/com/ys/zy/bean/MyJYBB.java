package com.ys.zy.bean;

import java.util.List;

public class MyJYBB {

    /**
     * code : SUCCESS
     * msg : 查询成功
     * data : {"pageNum":2,"length":1,"start":1,"search":null,"order":null,"column":null,"recordsTotal":23,"recordsFiltered":23,"draw":0,"data":[{"type_name":"提现","status_code":"1000","money":"1","date_time":"2019-05-20T06:44:25.000+0000","method_name":"银行卡","status_name":"提现中","method_code":"BANK","type_code":"1000"}],"subSQL":"","systemTime":1558338111099}
     * systemDate : 1558338111099
     */

    public String code;
    public String msg;
    public DataBeanX data;
    public long systemDate;

    public static class DataBeanX {
        /**
         * pageNum : 2
         * length : 1
         * start : 1
         * search : null
         * order : null
         * column : null
         * recordsTotal : 23
         * recordsFiltered : 23
         * draw : 0
         * data : [{"type_name":"提现","status_code":"1000","money":"1","date_time":"2019-05-20T06:44:25.000+0000","method_name":"银行卡","status_name":"提现中","method_code":"BANK","type_code":"1000"}]
         * subSQL :
         * systemTime : 1558338111099
         */

        public int pageNum;
        public int length;
        public int start;
        public Object search;
        public Object order;
        public Object column;
        public int recordsTotal;
        public int recordsFiltered;
        public int draw;
        public String subSQL;
        public long systemTime;
        public List<DataBean> data;

        public static class DataBean {
            /**
             * type_name : 提现
             * status_code : 1000
             * money : 1
             * date_time : 2019-05-20T06:44:25.000+0000
             * method_name : 银行卡
             * status_name : 提现中
             * method_code : BANK
             * type_code : 1000
             */

            public String type_name;
            public String status_code;
            public String money;
            public String date_time;
            public String method_name;
            public String status_name;
            public String method_code;
            public String type_code;
        }
    }
}
