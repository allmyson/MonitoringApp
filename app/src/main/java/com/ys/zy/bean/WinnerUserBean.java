package com.ys.zy.bean;

import java.util.List;

public class WinnerUserBean {

    /**
     * code : SUCCESS
     * msg : 查询成功
     * data : {"pageNum":1,"length":20,"start":2,"search":null,"order":null,"column":null,"recordsTotal":1,"recordsFiltered":1,"draw":0,"data":[{"consumer_id":"85e71457c01e41d18d4561cf32170878","login_name":"admin","win_money":0,"consumer_img":"/userapp/2019/05/04///1556964374704.png"}],"subSQL":"","systemTime":1557304948554}
     * systemDate : 1557304948554
     */

    public String code;
    public String msg;
    public DataBeanX data;
    public long systemDate;

    public static class DataBeanX {
        /**
         * pageNum : 1
         * length : 20
         * start : 2
         * search : null
         * order : null
         * column : null
         * recordsTotal : 1
         * recordsFiltered : 1
         * draw : 0
         * data : [{"consumer_id":"85e71457c01e41d18d4561cf32170878","login_name":"admin","win_money":0,"consumer_img":"/userapp/2019/05/04///1556964374704.png"}]
         * subSQL :
         * systemTime : 1557304948554
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
             * consumer_id : 85e71457c01e41d18d4561cf32170878
             * login_name : admin
             * win_money : 0
             * consumer_img : /userapp/2019/05/04///1556964374704.png
             */

            public String consumer_id;
            public String login_name;
            public String win_money;
            public String consumer_img;
        }
    }
}
