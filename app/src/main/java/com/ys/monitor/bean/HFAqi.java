package com.ys.monitor.bean;

import java.util.List;

/**
 * @author lh
 * @version 1.0.0
 * @filename HFAqi
 * @description -------------------------------------------------------
 * @date 2020/9/22 19:00
 */
public class HFAqi {

    public List<HeWeather6Bean> HeWeather6;

    public static class HeWeather6Bean {
        /**
         * basic : {"cid":"CN101040100","location":"重庆","parent_city":"重庆","admin_area":"重庆",
         * "cnty":"中国","lat":"29.56376076","lon":"106.55046082","tz":"+8.00"}
         * update : {"loc":"2020-09-22 18:42","utc":"2020-09-22 10:42"}
         * status : ok
         * air_now_city : {"aqi":"18","qlty":"优","main":"-","pm25":"12","pm10":"18","no2":"30",
         * "so2":"6","co":"0.7","o3":"19","pub_time":"2020-09-22 18:00"}
         * air_now_station : [{"air_sta":"上清寺","aqi":"18","asid":"CNA3133","co":"0.7","lat":"29
         * .565","lon":"106.5469","main":"-","no2":"34","o3":"8","pm10":"18","pm25":"10",
         * "pub_time":"2020-09-22 18:00","qlty":"优","so2":"6"},{"air_sta":"龙洲湾","aqi":"16",
         * "asid":"CNA3016","co":"0.8","lat":"29.41569","lon":"106.5506","main":"-","no2":"32",
         * "o3":"18","pm10":"11","pm25":"5","pub_time":"2020-09-22 18:00","qlty":"优","so2":"10"},
         * {"air_sta":"龙井湾","aqi":"16","asid":"CNA3015","co":"0.6","lat":"29.57278","lon":"106
         * .4042","main":"-","no2":"18","o3":"33","pm10":"10","pm25":"11","pub_time":"2020-09-22
         * 18:00","qlty":"优","so2":"6"},{"air_sta":"歇台子","aqi":"18","asid":"CNA3014","co":"0.8",
         * "lat":"29.53675","lon":"106.4959","main":"-","no2":"32","o3":"12","pm10":"12",
         * "pm25":"12","pub_time":"2020-09-22 18:00","qlty":"优","so2":"2"},{"air_sta":"鱼新街",
         * "aqi":"33","asid":"CNA1429","co":"0.5","lat":"29.389","lon":"106.513","main":"-",
         * "no2":"34","o3":"18","pm10":"33","pm25":"7","pub_time":"2020-09-22 18:00","qlty":"优",
         * "so2":"4"},{"air_sta":"蔡家","aqi":"19","asid":"CNA1428","co":"0.8","lat":"29.709",
         * "lon":"106.452","main":"-","no2":"23","o3":"23","pm10":"15","pm25":"13",
         * "pub_time":"2020-09-22 18:00","qlty":"优","so2":"6"},{"air_sta":"礼嘉","aqi":"22",
         * "asid":"CNA1427","co":"0.5","lat":"29.6453","lon":"106.562","main":"-","no2":"34",
         * "o3":"19","pm10":"17","pm25":"15","pub_time":"2020-09-22 18:00","qlty":"优","so2":"8"},
         * {"air_sta":"新山村","aqi":"19","asid":"CNA1426","co":"0.6","lat":"29.4892","lon":"106
         * .468","main":"-","no2":"38","o3":"10","pm10":"15","pm25":"9","pub_time":"2020-09-22
         * 18:00","qlty":"优","so2":"9"},{"air_sta":"空港","aqi":"25","asid":"CNA1425","co":"1.0",
         * "lat":"29.7125","lon":"106.617","main":"-","no2":"26","o3":"29","pm10":"13",
         * "pm25":"17","pub_time":"2020-09-22 18:00","qlty":"优","so2":"6"},{"air_sta":"白市驿",
         * "aqi":"23","asid":"CNA1422","co":"0.5","lat":"29.4822","lon":"106.364","main":"-",
         * "no2":"17","o3":"28","pm10":"19","pm25":"16","pub_time":"2020-09-22 18:00","qlty":"优",
         * "so2":"0"},{"air_sta":"茶园","aqi":"21","asid":"CNA1421","co":"0.7","lat":"29.4892",
         * "lon":"106.634","main":"-","no2":"29","o3":"23","pm10":"21","pm25":"12",
         * "pub_time":"2020-09-22 18:00","qlty":"优","so2":"6"},{"air_sta":"唐家沱","aqi":"26",
         * "asid":"CNA1420","co":"0.5","lat":"29.6219","lon":"106.65","main":"-","no2":"40",
         * "o3":"11","pm10":"26","pm25":"18","pub_time":"2020-09-22 18:00","qlty":"优","so2":"7"},
         * {"air_sta":"南坪","aqi":"19","asid":"CNA1419","co":"0.7","lat":"29.5186","lon":"106.54",
         * "main":"-","no2":"38","o3":"7","pm10":"12","pm25":"9","pub_time":"2020-09-22 18:00",
         * "qlty":"优","so2":"3"},{"air_sta":"虎溪","aqi":"16","asid":"CNA1418","co":"","lat":"29
         * .5983","lon":"106.296","main":"-","no2":"28","o3":"0","pm10":"16","pm25":"8",
         * "pub_time":"2020-09-22 18:00","qlty":"优","so2":"6"},{"air_sta":"两路","aqi":"13",
         * "asid":"CNA1417","co":"0.5","lat":"29.7228","lon":"106.626","main":"-","no2":"26",
         * "o3":"22","pm10":"13","pm25":"7","pub_time":"2020-09-22 18:00","qlty":"优","so2":"3"},{
         * "air_sta":"天生","aqi":"32","asid":"CNA1416","co":"0.8","lat":"29.8264","lon":"106.424",
         * "main":"-","no2":"23","o3":"22","pm10":"32","pm25":"16","pub_time":"2020-09-22 18:00",
         * "qlty":"优","so2":"3"},{"air_sta":"缙云山","aqi":"15","asid":"CNA1414","co":"0.4",
         * "lat":"29.8272","lon":"106.379","main":"-","no2":"5","o3":"47","pm10":"6","pm25":"10",
         * "pub_time":"2020-09-22 18:00","qlty":"优","so2":"5"}]
         */

        public BasicBean basic;
        public UpdateBean update;
        public String status;
        public AirNowCityBean air_now_city;
        public List<AirNowStationBean> air_now_station;

        public static class BasicBean {
            /**
             * cid : CN101040100
             * location : 重庆
             * parent_city : 重庆
             * admin_area : 重庆
             * cnty : 中国
             * lat : 29.56376076
             * lon : 106.55046082
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
             * loc : 2020-09-22 18:42
             * utc : 2020-09-22 10:42
             */

            public String loc;
            public String utc;
        }

        public static class AirNowCityBean {
            /**
             * aqi : 18
             * qlty : 优
             * main : -
             * pm25 : 12
             * pm10 : 18
             * no2 : 30
             * so2 : 6
             * co : 0.7
             * o3 : 19
             * pub_time : 2020-09-22 18:00
             */

            public String aqi;
            public String qlty;
            public String main;
            public String pm25;
            public String pm10;
            public String no2;
            public String so2;
            public String co;
            public String o3;
            public String pub_time;
        }

        public static class AirNowStationBean {
            /**
             * air_sta : 上清寺
             * aqi : 18
             * asid : CNA3133
             * co : 0.7
             * lat : 29.565
             * lon : 106.5469
             * main : -
             * no2 : 34
             * o3 : 8
             * pm10 : 18
             * pm25 : 10
             * pub_time : 2020-09-22 18:00
             * qlty : 优
             * so2 : 6
             */

            public String air_sta;
            public String aqi;
            public String asid;
            public String co;
            public String lat;
            public String lon;
            public String main;
            public String no2;
            public String o3;
            public String pm10;
            public String pm25;
            public String pub_time;
            public String qlty;
            public String so2;
        }
    }
}
