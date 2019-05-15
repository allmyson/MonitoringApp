package com.ys.zy.roulette.bean;

public class LpTz {


    /**
     * code : SUCCESS
     * msg : 查询成功
     * data : {"cattlePayNum":0,"chickenPayNum":0,"dogPayNum":0,"gameNum":"05151031","horsePayNum":0,"loongPayNum":0,"monkeyPayNum":0,"mousePayNum":0,"pigPayNum":0,"rabbitPayNum":0,"sheepPayNum":0,"snakePayNum":10,"tigerPayNum":0}
     * systemDate : 1557911430218
     */

    public String code;
    public String msg;
    public DataBean data;
    public long systemDate;

    public static class DataBean {
        /**
         * cattlePayNum : 0
         * chickenPayNum : 0
         * dogPayNum : 0
         * gameNum : 05151031
         * horsePayNum : 0
         * loongPayNum : 0
         * monkeyPayNum : 0
         * mousePayNum : 0
         * pigPayNum : 0
         * rabbitPayNum : 0
         * sheepPayNum : 0
         * snakePayNum : 10
         * tigerPayNum : 0
         */

        public String cattlePayNum;
        public String chickenPayNum;
        public String dogPayNum;
        public String gameNum;
        public String horsePayNum;
        public String loongPayNum;
        public String monkeyPayNum;
        public String mousePayNum;
        public String pigPayNum;
        public String rabbitPayNum;
        public String sheepPayNum;
        public String snakePayNum;
        public String tigerPayNum;
    }
}
