package com.ys.zy.bean;

import java.util.List;

public class MyXfjlBean {

    /**
     * code : SUCCESS
     * msg : 查询成功
     * data : {"pageNum":2,"length":1,"start":1,"search":null,"order":null,"column":null,"recordsTotal":54,"recordsFiltered":54,"draw":0,"data":[{"bets_money":11,"win_money":3.3,"periods_num":"20190513001"}],"subSQL":"","systemTime":1557770591562}
     * systemDate : 1557770591562
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
         * recordsTotal : 54
         * recordsFiltered : 54
         * draw : 0
         * data : [{"bets_money":11,"win_money":3.3,"periods_num":"20190513001"}]
         * subSQL :
         * systemTime : 1557770591562
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
             * bets_money : 11
             * win_money : 3.3
             * periods_num : 20190513001
             */

            public String bets_money;
            public String win_money;
            public String periods_num;
        }
    }
}
