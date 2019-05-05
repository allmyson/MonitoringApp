package com.ys.zy.bean;

import java.util.List;

public class ADBean {

    /**
     * pageNum : 1
     * length : 10
     * start : 1
     * search : null
     * order : null
     * column : null
     * recordsTotal : 12
     * recordsFiltered : 12
     * draw : 0
     * data : [{"activity_name":"test","stop_time":null,"create_time":"2019-05-05T15:26:20.000+0000","activity_status_name":"进行中","end_time":"2019-05-10T15:26:49.000+0000","stop_reason":null,"start_time":"2019-05-05T15:26:45.000+0000","create_id":"85e71457c01e41d18d4561cf32170878","activity_image":"/file/2019/05/05///1557069947896.jpg","stop_id":null,"activity_id":"7bef23b5bcee4a3699452eb91e7cc4e9","activity_content":"123","activity_number":"H2019050501","activity_status":"1001"},{"activity_name":"test","stop_time":"2019-04-30T12:03:30.000+0000","create_time":"2019-04-24T12:05:35.000+0000","activity_status_name":"到期停止","end_time":"2019-04-30T12:05:16.000+0000","stop_reason":"到期停止","start_time":"2019-04-24T12:05:15.000+0000","create_id":"85e71457c01e41d18d4561cf32170878","activity_image":"/file\\2019\\04\\24\\/\\1556107522513.png","stop_id":null,"activity_id":"194345c21924410f8ecb93d3ef4e9961","activity_content":"test","activity_number":"H2019042401","activity_status":"1002"},{"activity_name":"10","stop_time":"2019-04-23T06:31:00.000+0000","create_time":"2019-04-23T06:33:15.000+0000","activity_status_name":"到期停止","end_time":"2019-04-30T06:32:48.000+0000","stop_reason":"到期停止","start_time":"2019-04-23T06:32:46.000+0000","create_id":"85e71457c01e41d18d4561cf32170878","activity_image":"/file\\2019\\04\\23\\/\\1556001173731.png","stop_id":null,"activity_id":"4bfca1dfa55746eba6f0aa77e7937f8d","activity_content":"10","activity_number":"H2019042308","activity_status":"1002"},{"activity_name":"9","stop_time":"2019-04-23T06:28:00.000+0000","create_time":"2019-04-23T06:30:08.000+0000","activity_status_name":"到期停止","end_time":"2019-04-30T06:29:58.000+0000","stop_reason":"到期停止","start_time":"2019-04-23T06:29:57.000+0000","create_id":"85e71457c01e41d18d4561cf32170878","activity_image":"/file\\2019\\04\\23\\/\\1556001002130.png","stop_id":null,"activity_id":"1f75381da408401bb51854e8e994a485","activity_content":"9","activity_number":"H2019042307","activity_status":"1002"},{"activity_name":"8","stop_time":"2019-04-23T06:16:30.000+0000","create_time":"2019-04-23T06:18:27.000+0000","activity_status_name":"到期停止","end_time":"2019-04-30T06:18:13.000+0000","stop_reason":"到期停止","start_time":"2019-04-23T06:18:12.000+0000","create_id":"85e71457c01e41d18d4561cf32170878","activity_image":"/file\\2019\\04\\23\\/\\1556000297402.png","stop_id":null,"activity_id":"d39b15a8c38f455cbf7b13bcd780f1fb","activity_content":"8","activity_number":"H2019042306","activity_status":"1002"},{"activity_name":"7","stop_time":"2019-04-23T06:14:00.000+0000","create_time":"2019-04-23T06:16:12.000+0000","activity_status_name":"到期停止","end_time":"2019-04-30T06:15:52.000+0000","stop_reason":"到期停止","start_time":"2019-04-23T06:15:51.000+0000","create_id":"85e71457c01e41d18d4561cf32170878","activity_image":"/file\\2019\\04\\23\\/\\1556000156827.png","stop_id":null,"activity_id":"836f6b97ae084bc98e2d41236a276f8a","activity_content":"7","activity_number":"H2019042305","activity_status":"1002"},{"activity_name":"6","stop_time":"2019-04-23T06:07:00.000+0000","create_time":"2019-04-23T06:09:12.000+0000","activity_status_name":"到期停止","end_time":"2019-04-27T06:08:56.000+0000","stop_reason":"到期停止","start_time":"2019-04-23T06:08:55.000+0000","create_id":"85e71457c01e41d18d4561cf32170878","activity_image":"/file\\2019\\04\\23\\/\\1555999743574.png","stop_id":null,"activity_id":"7ad4141a8a07475881246a5d6eb15348","activity_content":"6","activity_number":"H2019042304","activity_status":"1002"},{"activity_name":"5","stop_time":"2019-04-23T05:58:00.000+0000","create_time":"2019-04-23T05:59:54.000+0000","activity_status_name":"到期停止","end_time":"2019-04-29T05:59:41.000+0000","stop_reason":"到期停止","start_time":"2019-04-23T05:59:40.000+0000","create_id":"85e71457c01e41d18d4561cf32170878","activity_image":"/file\\2019\\04\\23\\/\\1555999185711.png","stop_id":null,"activity_id":"92fcf0abd2204688beb3216f58222217","activity_content":"5","activity_number":"H2019042303","activity_status":"1002"},{"activity_name":"4","stop_time":"2019-04-23T05:53:30.000+0000","create_time":"2019-04-23T05:53:08.000+0000","activity_status_name":"到期停止","end_time":"2019-04-29T05:52:52.000+0000","stop_reason":"到期停止","start_time":"2019-04-23T05:52:50.000+0000","create_id":"85e71457c01e41d18d4561cf32170878","activity_image":"/file\\2019\\04\\23\\/\\1555998776995.png","stop_id":null,"activity_id":"24f576d8ace84a0dbfbaf396f129e5c5","activity_content":"4","activity_number":"H2019042302","activity_status":"1002"},{"activity_name":"3","stop_time":"2019-04-23T06:27:00.000+0000","create_time":"2019-04-23T05:52:38.000+0000","activity_status_name":"到期停止","end_time":"2019-04-27T05:48:19.000+0000","stop_reason":"到期停止","start_time":"2019-04-23T05:48:18.000+0000","create_id":"85e71457c01e41d18d4561cf32170878","activity_image":"/file\\2019\\04\\23\\/\\1555998513355.png","stop_id":null,"activity_id":"59a172bc410f4970927fce1ce2e410b9","activity_content":"3","activity_number":"H2019042301","activity_status":"1002"}]
     * subSQL :
     * systemTime : 1557069993125
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

    public static class DataBean {
        /**
         * activity_name : test
         * stop_time : null
         * create_time : 2019-05-05T15:26:20.000+0000
         * activity_status_name : 进行中
         * end_time : 2019-05-10T15:26:49.000+0000
         * stop_reason : null
         * start_time : 2019-05-05T15:26:45.000+0000
         * create_id : 85e71457c01e41d18d4561cf32170878
         * activity_image : /file/2019/05/05///1557069947896.jpg
         * stop_id : null
         * activity_id : 7bef23b5bcee4a3699452eb91e7cc4e9
         * activity_content : 123
         * activity_number : H2019050501
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
