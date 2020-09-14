package com.ys.monitor.bean;

import java.util.List;

public class YjtzBean {

    /**
     * data : {"total":1,"rows":[{"content":"111","createTime":1597799895000,"createUserNo":"402880905e31cb26015e31cb5d290000","description":"","isDelete":"0","recNo":"40289f3c74044ab70174044b90090000","source":"1","title":"111"}],"footer":null}
     * code : 200
     * msg : 预警通知信息获取成功!
     */

    public DataBean data;
    public String code;
    public String msg;

    public static class DataBean {
        /**
         * total : 1
         * rows : [{"content":"111","createTime":1597799895000,"createUserNo":"402880905e31cb26015e31cb5d290000","description":"","isDelete":"0","recNo":"40289f3c74044ab70174044b90090000","source":"1","title":"111"}]
         * footer : null
         */

        public int total;
        public Object footer;
        public List<RowsBean> rows;

        public static class RowsBean {
            /**
             * content : 111
             * createTime : 1597799895000
             * createUserNo : 402880905e31cb26015e31cb5d290000
             * description :
             * isDelete : 0
             * recNo : 40289f3c74044ab70174044b90090000
             * source : 1
             * title : 111
             */

            public String content;
            public long createTime;
            public String createUserNo;
            public String description;
            public String isDelete;
            public String recNo;
            public String source;
            public String title;
        }
    }
}
