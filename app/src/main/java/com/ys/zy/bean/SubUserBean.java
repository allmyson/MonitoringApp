package com.ys.zy.bean;

import java.util.List;

public class SubUserBean {

    /**
     * code : SUCCESS
     * msg : 查询成功
     * data : {"pageNum":1,"length":1000,"start":2,"search":null,"order":null,"column":null,"recordsTotal":3,"recordsFiltered":3,"draw":0,"data":[{"consumer_id":"4a213a8c729c46f3940e8407a5cb00fe","login_name":"liqiang3","online_time":"2019-05-09T02:52:32.000+0000","back_num":"0.03"},{"consumer_id":"b30f9b88c8424d0091955fe50800f052","login_name":"liqiang2","online_time":null,"back_num":"0.02"}],"subSQL":"","systemTime":1557370440515}
     * systemDate : 1557370440515
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
         * recordsTotal : 3
         * recordsFiltered : 3
         * draw : 0
         * data : [{"consumer_id":"4a213a8c729c46f3940e8407a5cb00fe","login_name":"liqiang3","online_time":"2019-05-09T02:52:32.000+0000","back_num":"0.03"},{"consumer_id":"b30f9b88c8424d0091955fe50800f052","login_name":"liqiang2","online_time":null,"back_num":"0.02"}]
         * subSQL :
         * systemTime : 1557370440515
         */

        public int pageNum;
        public int length;
        public int start;
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
             * consumer_id : 4a213a8c729c46f3940e8407a5cb00fe
             * login_name : liqiang3
             * online_time : 2019-05-09T02:52:32.000+0000
             * back_num : 0.03
             */

            public String consumer_id;
            public String login_name;
            public String online_time;
            public String back_num;
        }
    }
}
