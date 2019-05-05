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
     * pageNum : 1
     * length : 10
     * start : 1
     * search : null
     * order : null
     * column : null
     * recordsTotal : 2
     * recordsFiltered : 2
     * draw : 0
     * data : [{"notice_title":"44","del_flag":"0","notice_content":"444","create_id":"85e71457c01e41d18d4561cf32170878","publish_time":"2019-03-31T10:10:08.000+0000","notice_status":"1","notice_id":"9fff93b8aa9742b4ae72958651f1a146"},{"notice_title":"123","del_flag":"0","notice_content":"123","create_id":"85e71457c01e41d18d4561cf32170878","publish_time":"2019-03-31T10:09:40.000+0000","notice_status":"1","notice_id":"a93c95afad1f47b2b9aedcbad884ee95"}]
     * subSQL :
     * systemTime : 1557069338229
     */

    public int pageNum;
    public int length;
    public int start;
    public String search;
    public String order;
    public String column;
    public int recordsTotal;
    public int recordsFiltered;
    public int draw;
    public String subSQL;
    public long systemTime;
    public List<DataBean> data;

    public static class DataBean implements Serializable {
        /**
         * notice_title : 44
         * del_flag : 0
         * notice_content : 444
         * create_id : 85e71457c01e41d18d4561cf32170878
         * publish_time : 2019-03-31T10:10:08.000+0000
         * notice_status : 1
         * notice_id : 9fff93b8aa9742b4ae72958651f1a146
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
