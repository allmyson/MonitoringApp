package com.ys.monitor.bean;

import java.util.List;

public class TaskBean {

    /**
     * data : {"total":1,"rows":[{"assignPerson":"admin","content":"任务描述","createTime":"2020-08-14 14:22:20","createUserNo":"402880905e31cb26015e31cb5d290000","description":"","finishTime":null,"isDelete":"0","isFinish":0,"name":"任务管理","planTime":"2020-08-20 14:22:20","recNo":"4028819073eb9d0f0173eba25a290003","type":"4028819073d76f4d0173d7aaa57a0009","typeName":"火情核查","warnNo":"4028819073e7a7630173e7a95e280000"}],"footer":null}
     * code : 200
     * msg : 火情核查任务获取成功!
     */

    public DataBean data;
    public String code;
    public String msg;

    public static class DataBean {
        /**
         * total : 1
         * rows : [{"assignPerson":"admin","content":"任务描述","createTime":"2020-08-14 14:22:20","createUserNo":"402880905e31cb26015e31cb5d290000","description":"","finishTime":null,"isDelete":"0","isFinish":0,"name":"任务管理","planTime":"2020-08-20 14:22:20","recNo":"4028819073eb9d0f0173eba25a290003","type":"4028819073d76f4d0173d7aaa57a0009","typeName":"火情核查","warnNo":"4028819073e7a7630173e7a95e280000"}]
         * footer : null
         */

        public int total;
        public Object footer;
        public List<RowsBean> rows;

        public static class RowsBean {
            /**
             * assignPerson : admin
             * content : 任务描述
             * createTime : 2020-08-14 14:22:20
             * createUserNo : 402880905e31cb26015e31cb5d290000
             * description :
             * finishTime : null
             * isDelete : 0
             * isFinish : 0
             * name : 任务管理
             * planTime : 2020-08-20 14:22:20
             * recNo : 4028819073eb9d0f0173eba25a290003
             * type : 4028819073d76f4d0173d7aaa57a0009
             * typeName : 火情核查
             * warnNo : 4028819073e7a7630173e7a95e280000
             */

            public String assignPerson;
            public String content;
            public long createTime;
            public String createUserNo;
            public String description;
            public String finishTime;
            public String isDelete;
            public int isFinish;
            public String name;
            public String planTime;
            public String recNo;
            public String type;
            public String typeName;
            public String warnNo;
        }
    }
}
