package com.ys.zy.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author lh
 * @version 1.0.0
 * @filename MsgBean
 * @description -------------------------------------------------------
 * @date 2018/10/23 17:46
 */
public class MsgBean {

    /**
     * code : SUCCESS
     * msg : 查询成功
     * data : {"pageNum":1,"length":1000,"start":2,"search":null,"order":null,"column":null,"recordsTotal":2,"recordsFiltered":2,"draw":0,"data":[{"notice_title":"123","del_flag":"0","notice_content":"123","create_id":"85e71457c01e41d18d4561cf32170878","publish_time":"2019-03-31T10:09:40.000+0000","notice_status":"1","notice_id":"a93c95afad1f47b2b9aedcbad884ee95"}],"subSQL":"","systemTime":1557305732678}
     * systemDate : 1557305732678
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
         * data : [{"notice_title":"123","del_flag":"0","notice_content":"123","create_id":"85e71457c01e41d18d4561cf32170878","publish_time":"2019-03-31T10:09:40.000+0000","notice_status":"1","notice_id":"a93c95afad1f47b2b9aedcbad884ee95"}]
         * subSQL :
         * systemTime : 1557305732678
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

        public static class DataBean implements Serializable {
            /**
             * notice_title : 123
             * del_flag : 0
             * notice_content : 123
             * create_id : 85e71457c01e41d18d4561cf32170878
             * publish_time : 2019-03-31T10:09:40.000+0000
             * notice_status : 1
             * notice_id : a93c95afad1f47b2b9aedcbad884ee95
             */

            public String notice_title;
            public String del_flag;
            public String notice_content;
            public String create_id;
            public String publish_time;
            public String notice_status;
            public String notice_id;
        }
    }
}
