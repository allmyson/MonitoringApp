package com.ys.monitor.bean;

public class WeatherBean {


    /**
     * success : 1
     * result : {"weaid":"105","days":"2020-09-19","week":"星期六","cityno":"chongqing","citynm":"重庆","cityid":"101040100","temperature":"27℃/21℃","temperature_curr":"29℃","humidity":"47%","aqi":"49","weather":"晴转阴","weather_curr":"晴","weather_icon":"http://api.k780.com/upload/weather/d/0.gif","weather_icon1":"","wind":"东风","winp":"1级","temp_high":"27","temp_low":"21","temp_curr":"29","humi_high":"0","humi_low":"0","weatid":"1","weatid1":"","windid":"2","winpid":"1","weather_iconid":"0"}
     */

    public String success;
    public ResultBean result;

    public static class ResultBean {
        /**
         * weaid : 105
         * days : 2020-09-19
         * week : 星期六
         * cityno : chongqing
         * citynm : 重庆
         * cityid : 101040100
         * temperature : 27℃/21℃
         * temperature_curr : 29℃
         * humidity : 47%
         * aqi : 49
         * weather : 晴转阴
         * weather_curr : 晴
         * weather_icon : http://api.k780.com/upload/weather/d/0.gif
         * weather_icon1 :
         * wind : 东风
         * winp : 1级
         * temp_high : 27
         * temp_low : 21
         * temp_curr : 29
         * humi_high : 0
         * humi_low : 0
         * weatid : 1
         * weatid1 :
         * windid : 2
         * winpid : 1
         * weather_iconid : 0
         */

        public String weaid;
        public String days;
        public String week;
        public String cityno;
        public String citynm;
        public String cityid;
        public String temperature;
        public String temperature_curr;
        public String humidity;
        public String aqi;
        public String weather;
        public String weather_curr;
        public String weather_icon;
        public String weather_icon1;
        public String wind;
        public String winp;
        public String temp_high;
        public String temp_low;
        public String temp_curr;
        public String humi_high;
        public String humi_low;
        public String weatid;
        public String weatid1;
        public String windid;
        public String winpid;
        public String weather_iconid;
    }
}
