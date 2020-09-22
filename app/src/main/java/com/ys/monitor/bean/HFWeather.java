package com.ys.monitor.bean;

import java.util.List;

/**
 * @author lh
 * @version 1.0.0
 * @filename HFWeather
 * @description -------------------------------------------------------
 * @date 2020/9/22 17:45
 */
public class HFWeather {

    public List<HeWeather6Bean> HeWeather6;

    public static class HeWeather6Bean {
        /**
         * basic : {"cid":"CN101040800","location":"北碚","parent_city":"重庆","admin_area":"重庆",
         * "cnty":"中国","lat":"29.82542992","lon":"106.43786621","tz":"+8.00"}
         * update : {"loc":"2020-09-22 17:26","utc":"2020-09-22 09:26"}
         * status : ok
         * now : {"cloud":"100","cond_code":"104","cond_txt":"阴","fl":"20","hum":"93","pcpn":"0
         * .0","pres":"984","tmp":"19","vis":"13","wind_deg":"97","wind_dir":"东风","wind_sc":"1",
         * "wind_spd":"5"}
         */

        public BasicBean basic;
        public UpdateBean update;
        public String status;
        public NowBean now;

        public static class BasicBean {
            /**
             * cid : CN101040800
             * location : 北碚
             * parent_city : 重庆
             * admin_area : 重庆
             * cnty : 中国
             * lat : 29.82542992
             * lon : 106.43786621
             * tz : +8.00
             */

            public String cid;
            public String location;
            public String parent_city;
            public String admin_area;
            public String cnty;
            public String lat;
            public String lon;
            public String tz;
        }

        public static class UpdateBean {
            /**
             * loc : 2020-09-22 17:26
             * utc : 2020-09-22 09:26
             */

            public String loc;
            public String utc;
        }

        public static class NowBean {
            /**
             * cloud : 100
             * cond_code : 104
             * cond_txt : 阴
             * fl : 20
             * hum : 93
             * pcpn : 0.0
             * pres : 984
             * tmp : 19
             * vis : 13
             * wind_deg : 97
             * wind_dir : 东风
             * wind_sc : 1
             * wind_spd : 5
             */

            public String cloud;
            public String cond_code;
            public String cond_txt;
            public String fl;
            public String hum;
            public String pcpn;
            public String pres;
            public String tmp;
            public String vis;
            public String wind_deg;
            public String wind_dir;
            public String wind_sc;
            public String wind_spd;
        }
    }
}
