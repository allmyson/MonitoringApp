package com.ys.zy.bean;

import java.util.List;

public class SubJYJL {

    /**
     * code : SUCCESS
     * msg : 查询成功
     * data : {"pageNum":1,"length":1000,"start":1,"search":null,"order":null,"column":null,"recordsTotal":3,"recordsFiltered":3,"draw":0,"data":[{"type_name":"充值","login_name":"putong01","money":"10000000","date_time":"2019-05-12T11:25:00.000+0000","type_code":"1001"},{"type_name":"充值","login_name":"pt000001","money":"10000000","date_time":"2019-05-12T10:47:35.000+0000","type_code":"1001"},{"type_name":"充值","login_name":"putong01","money":"6000","date_time":"2019-05-10T14:04:40.000+0000","type_code":"1001"}],"subSQL":"","systemTime":1557753704814}
     * systemDate : 1557753704814
     */

    public String code;
    public String msg;
    public DataBeanX data;
    public long systemDate;

    public static class DataBeanX {
        /**
         * pageNum : 1
         * length : 1000
         * start : 1
         * search : null
         * order : null
         * column : null
         * recordsTotal : 3
         * recordsFiltered : 3
         * draw : 0
         * data : [{"type_name":"充值","login_name":"putong01","money":"10000000","date_time":"2019-05-12T11:25:00.000+0000","type_code":"1001"},{"type_name":"充值","login_name":"pt000001","money":"10000000","date_time":"2019-05-12T10:47:35.000+0000","type_code":"1001"},{"type_name":"充值","login_name":"putong01","money":"6000","date_time":"2019-05-10T14:04:40.000+0000","type_code":"1001"}]
         * subSQL :
         * systemTime : 1557753704814
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
             * type_name : 充值
             * login_name : putong01
             * money : 10000000
             * date_time : 2019-05-12T11:25:00.000+0000
             * type_code : 1001
             */

            public String type_name;
            public String login_name;
            public String money;
            public String date_time;
            public String type_code;
        }
    }
}
