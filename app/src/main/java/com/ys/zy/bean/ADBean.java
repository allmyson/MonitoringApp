package com.ys.zy.bean;

import java.util.List;

public class ADBean {


    /**
     * code : SUCCESS
     * msg : 查询成功
     * data : {"pageNum":2,"length":1,"start":1,"search":null,"order":null,"column":null,"recordsTotal":16,"recordsFiltered":16,"draw":0,"data":[{"activity_name":"正式2","stop_time":null,"create_time":"2019-05-06T08:45:03.000+0000","activity_status_name":"进行中","end_time":"2019-06-06T08:48:19.000+0000","stop_reason":null,"start_time":"2019-05-06T08:48:18.000+0000","create_id":"85e71457c01e41d18d4561cf32170878","activity_image":"/file/2019/05/06///1557132296010.jpg","stop_id":null,"activity_id":"aa18732fad9346899730c96346c1cbfc","activity_content":"正式2","activity_number":"H2019050604","activity_status":"1001"}],"subSQL":"","systemTime":1557314099735}
     * systemDate : 1557314099735
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
         * recordsTotal : 16
         * recordsFiltered : 16
         * draw : 0
         * data : [{"activity_name":"正式2","stop_time":null,"create_time":"2019-05-06T08:45:03.000+0000","activity_status_name":"进行中","end_time":"2019-06-06T08:48:19.000+0000","stop_reason":null,"start_time":"2019-05-06T08:48:18.000+0000","create_id":"85e71457c01e41d18d4561cf32170878","activity_image":"/file/2019/05/06///1557132296010.jpg","stop_id":null,"activity_id":"aa18732fad9346899730c96346c1cbfc","activity_content":"正式2","activity_number":"H2019050604","activity_status":"1001"}]
         * subSQL :
         * systemTime : 1557314099735
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
             * activity_name : 正式2
             * stop_time : null
             * create_time : 2019-05-06T08:45:03.000+0000
             * activity_status_name : 进行中
             * end_time : 2019-06-06T08:48:19.000+0000
             * stop_reason : null
             * start_time : 2019-05-06T08:48:18.000+0000
             * create_id : 85e71457c01e41d18d4561cf32170878
             * activity_image : /file/2019/05/06///1557132296010.jpg
             * stop_id : null
             * activity_id : aa18732fad9346899730c96346c1cbfc
             * activity_content : 正式2
             * activity_number : H2019050604
             * activity_status : 1001
             */

            public String activity_name;
            public String stop_time;
            public String create_time;
            public String activity_status_name;
            public String end_time;
            public String stop_reason;
            public String start_time;
            public String create_id;
            public String activity_image;
            public String stop_id;
            public String activity_id;
            public String activity_content;
            public String activity_number;
            public String activity_status;
        }
    }
}
