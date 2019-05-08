package com.ys.zy.bean;

import java.util.List;

public class YqmBean {


    /**
     * code : SUCCESS
     * msg : 查询成功
     * data : {"pageNum":1,"length":1000,"start":2,"search":null,"order":null,"column":null,"recordsTotal":2,"recordsFiltered":2,"draw":0,"data":[{"consumer_id":"dc258de6f71848c6b3278350d609537e","reg_count":0,"generate_time":"2019-05-08T06:20:37.000+0000","login_name":"longhui0","consumer_name":"折欲","reg_id":"86ce3c94bb4d4ae99ce58a0eb0441c55","back_num":"0.08","reg_code":"RBUS7F45"}],"subSQL":"","systemTime":1557316235152}
     * systemDate : 1557316235152
     */

    public String code;
    public String msg;
    public DataBeanX data;
    public long systemDate;

    public static class DataBeanX {
        /**
         * pageNum : 1
         * length : 1000
         * start : 2
         * search : null
         * order : null
         * column : null
         * recordsTotal : 2
         * recordsFiltered : 2
         * draw : 0
         * data : [{"consumer_id":"dc258de6f71848c6b3278350d609537e","reg_count":0,"generate_time":"2019-05-08T06:20:37.000+0000","login_name":"longhui0","consumer_name":"折欲","reg_id":"86ce3c94bb4d4ae99ce58a0eb0441c55","back_num":"0.08","reg_code":"RBUS7F45"}]
         * subSQL :
         * systemTime : 1557316235152
         */

        public String pageNum;
        public String length;
        public String start;
        public String search;
        public String order;
        public String column;
        public String recordsTotal;
        public String recordsFiltered;
        public String draw;
        public String subSQL;
        public long systemTime;
        public List<DataBean> data;

        public static class DataBean {
            /**
             * consumer_id : dc258de6f71848c6b3278350d609537e
             * reg_count : 0
             * generate_time : 2019-05-08T06:20:37.000+0000
             * login_name : longhui0
             * consumer_name : 折欲
             * reg_id : 86ce3c94bb4d4ae99ce58a0eb0441c55
             * back_num : 0.08
             * reg_code : RBUS7F45
             */

            public String consumer_id;
            public String reg_count;
            public String generate_time;
            public String login_name;
            public String consumer_name;
            public String reg_id;
            public String back_num;
            public String reg_code;
        }
    }
}
