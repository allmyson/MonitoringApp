package com.ys.zy.bean;

import java.util.List;

public class HdjjBean {

    /**
     * code : SUCCESS
     * msg : 查询成功
     * data : {"pageNum":1,"length":10,"start":1,"search":null,"order":null,"column":null,"recordsTotal":0,"recordsFiltered":0,"draw":0,"data":[],"subSQL":"","systemTime":1557769711475}
     * systemDate : 1557769711475
     */

    public String code;
    public String msg;
    public DataBeanX data;
    public long systemDate;

    public static class DataBeanX {
        /**
         * pageNum : 1
         * length : 10
         * start : 1
         * search : null
         * order : null
         * column : null
         * recordsTotal : 0
         * recordsFiltered : 0
         * draw : 0
         * data : []
         * subSQL :
         * systemTime : 1557769711475
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

        }
    }
}
