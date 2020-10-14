package com.ys.monitor.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author lh
 * @version 1.0.0
 * @filename GjBean
 * @description -------------------------------------------------------
 * @date 2020/10/14 10:20
 */
public class GjBean {

    /**
     * 0 : {"gis_jd":"106.498301","gis_wd":"29.626857","gis_gd":"","gis_sd":"",
     * "extension":"1111031","create_stamp":"2020-10-14 10:15:50"}
     * header : {"code":"1","msg":"处理成功"}
     * track : [{"exten":"1111031","gis_jd":"106.498301","gis_wd":"29.626857","gis_gd":"",
     * "gis_sd":"","date":"2020-10-14 10:15:50"}]
     */

    @SerializedName("0")
    public _$0Bean _$0;
    public HeaderBean header;
    public List<TrackBean> track;

    public static class _$0Bean {
        /**
         * gis_jd : 106.498301
         * gis_wd : 29.626857
         * gis_gd :
         * gis_sd :
         * extension : 1111031
         * create_stamp : 2020-10-14 10:15:50
         */

        public String gis_jd;
        public String gis_wd;
        public String gis_gd;
        public String gis_sd;
        public String extension;
        public String create_stamp;
    }

    public static class HeaderBean {
        /**
         * code : 1
         * msg : 处理成功
         */

        public String code;
        public String msg;
    }

    public static class TrackBean {
        /**
         * exten : 1111031
         * gis_jd : 106.498301
         * gis_wd : 29.626857
         * gis_gd :
         * gis_sd :
         * date : 2020-10-14 10:15:50
         */

        public String exten;
        public String gis_jd;
        public String gis_wd;
        public String gis_gd;
        public String gis_sd;
        public String date;
    }
}
