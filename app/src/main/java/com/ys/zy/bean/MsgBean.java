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
     * data : {"pageNum":null,"length":null,"start":null,"search":null,"order":null,"column":null,"recordsTotal":3,
     * "recordsFiltered":3,"draw":null,"data":[{"is_read":"1001","version_time":null,
     * "send_time":"2018-12-02T21:17:01.000+0000","sender":"1","news_type_name":"系统公告","recipient":null,
     * "news_content
     * ":"由于下个月央行大调整，避免你的充值延期到账，充值的时候由于下个月央行大调整，避免你的充值延期到账，充值的时候由于下个月央行大调整，避免你的充值延期到账，充值的时候由于下个月央行大调整，避免你的充值延期到账，充值的时候。","news_type_code":"1002","news_id":"1"},{"is_read":"1001","version_time":null,"send_time":"2018-12-02T21:17:01.000+0000","sender":"1","news_type_name":"系统公告","recipient":null,"news_content":"用户龙辉分分彩中了100万。","news_type_code":"1002","news_id":"2"},{"is_read":"1001","version_time":null,"send_time":"2018-12-02T21:17:01.000+0000","sender":"1","news_type_name":"系统公告","recipient":null,"news_content":"用户龙辉时时彩中了100万。","news_type_code":"1002","news_id":"3"}],"subSQL":null,"systemTime":0}
     * systemDate : 1543829273570
     */

    public String code;
    public String msg;
    public DataBeanX data;
    public long systemDate;

    public static class DataBeanX {
        /**
         * pageNum : null
         * length : null
         * start : null
         * search : null
         * order : null
         * column : null
         * recordsTotal : 3
         * recordsFiltered : 3
         * draw : null
         * data : [{"is_read":"1001","version_time":null,"send_time":"2018-12-02T21:17:01.000+0000","sender":"1",
         * "news_type_name":"系统公告","recipient":null,
         * "news_content":"由于下个月央行大调整，避免你的充值延期到账，充值的时候由于下个月央行大调整，避免你的充值延期到账，充值的时候由于下个月央行大调整，避免你的充值延期到账，充值的时候由于下个月央行大调整，避免你的充值延期到账，充值的时候。","news_type_code":"1002","news_id":"1"},{"is_read":"1001","version_time":null,"send_time":"2018-12-02T21:17:01.000+0000","sender":"1","news_type_name":"系统公告","recipient":null,"news_content":"用户龙辉分分彩中了100万。","news_type_code":"1002","news_id":"2"},{"is_read":"1001","version_time":null,"send_time":"2018-12-02T21:17:01.000+0000","sender":"1","news_type_name":"系统公告","recipient":null,"news_content":"用户龙辉时时彩中了100万。","news_type_code":"1002","news_id":"3"}]
         * subSQL : null
         * systemTime : 0
         */

        public String pageNum;
        public int length;
        public int start;
        public String search;
        public String order;
        public String column;
        public int recordsTotal;
        public int recordsFiltered;
        public String draw;
        public String subSQL;
        public int systemTime;
        public List<DataBean> data;

        public static class DataBean implements Serializable {
            /**
             * is_read : 1001
             * version_time : null
             * send_time : 2018-12-02T21:17:01.000+0000
             * sender : 1
             * news_type_name : 系统公告
             * recipient : null
             * news_content : 由于下个月央行大调整，避免你的充值延期到账，充值的时候由于下个月央行大调整，避免你的充值延期到账，充值的时候由于下个月央行大调整，避免你的充值延期到账，充值的时候由于下个月央行大调整，避免你的充值延期到账，充值的时候。
             * news_type_code : 1002
             * news_id : 1
             */

            public String is_read;
            public String version_time;
            public String send_time;
            public String sender;
            public String news_type_name;
            public String recipient;
            public String news_content;
            public String news_type_code;
            public String news_id;
        }
    }
}
