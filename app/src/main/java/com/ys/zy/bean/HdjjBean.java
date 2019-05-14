package com.ys.zy.bean;

import java.util.List;

public class HdjjBean {

    /**
     * code : SUCCESS
     * msg : 查询成功
     * data : {"pageNum":1,"length":1000,"start":1,"search":null,"order":null,"column":null,"recordsTotal":1,"recordsFiltered":1,"draw":0,"data":[{"activity_name":"5月充值活动","bonus_num":"1000000.0","bonus_time":"2019-05-14T02:27:18.000+0000"}],"subSQL":"","systemTime":1557803615743}
     * systemDate : 1557803615743
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
         * recordsTotal : 1
         * recordsFiltered : 1
         * draw : 0
         * data : [{"activity_name":"5月充值活动","bonus_num":"1000000.0","bonus_time":"2019-05-14T02:27:18.000+0000"}]
         * subSQL :
         * systemTime : 1557803615743
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
             * activity_name : 5月充值活动
             * bonus_num : 1000000.0
             * bonus_time : 2019-05-14T02:27:18.000+0000
             */

            public String activity_name;
            public String bonus_num;
            public String bonus_time;
        }
    }
}
