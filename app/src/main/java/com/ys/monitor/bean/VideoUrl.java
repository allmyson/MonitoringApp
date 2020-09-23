package com.ys.monitor.bean;

import java.util.List;

/**
 * @author lh
 * @version 1.0.0
 * @filename VideoUrl
 * @description -------------------------------------------------------
 * @date 2020/9/23 10:15
 */
public class VideoUrl {

    /**
     * data : [{"code":"0","msg":"success","data":{"url":"rtsp://192.168.31
     * .199:554/openUrl/I5ERAIg"}},{"code":"0","msg":"success","data":{"url":"rtsp://192.168.31
     * .199:554/openUrl/I6YC4aQ"}}]
     * code : 200
     * msg : 设备预览取流URL获取成功!
     */

    public String code;
    public String msg;
    public List<DataBeanX> data;

    public static class DataBeanX {
        /**
         * code : 0
         * msg : success
         * data : {"url":"rtsp://192.168.31.199:554/openUrl/I5ERAIg"}
         */

        public String code;
        public String msg;
        public DataBean data;

        public static class DataBean {
            /**
             * url : rtsp://192.168.31.199:554/openUrl/I5ERAIg
             */

            public String url;
        }
    }
}
