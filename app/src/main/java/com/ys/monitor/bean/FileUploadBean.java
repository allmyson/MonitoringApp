package com.ys.monitor.bean;

/**
 * @author lh
 * @version 1.0.0
 * @filename FileUploadBean
 * @description -------------------------------------------------------
 * @date 2020/9/11 14:50
 */
public class FileUploadBean {

    /**
     * data : {"url":"/data/warn/73bf2749baff45d4923ef989fbab8988/Screenshot_2019-10-12-16-09-26
     * -430_com.upsoft.ep.app.png;/data/warn/fedb2a99cc2f409b9942e7ed4ad39fc9/Screenshot_2019-06
     * -21-17-41-43-093_com.upsoft.ckq.png;"}
     * code : 200
     * msg : 上传成功！
     */

    public DataBean data;
    public String code;
    public String msg;

    public static class DataBean {
        /**
         * url : /data/warn/73bf2749baff45d4923ef989fbab8988/Screenshot_2019-10-12-16-09-26
         * -430_com.upsoft.ep.app.png;/data/warn/fedb2a99cc2f409b9942e7ed4ad39fc9/Screenshot_2019-06-21-17-41-43-093_com.upsoft.ckq.png;
         */

        public String url;
    }
}
