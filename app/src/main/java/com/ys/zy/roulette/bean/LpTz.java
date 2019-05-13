package com.ys.zy.roulette.bean;

public class LpTz {

    /**
     * code : SUCCESS
     * msg : 查询成功
     * data : {"cattleWinNum":0,"chickenWinNum":0,"dogWinNum":0,"gameNum":"05131233","horseWinNum":1082,"loongWinNum":0,"monkeyWinNum":0,"mouseWinNum":0,"pigWinNum":0,"rabbitWinNum":0,"sheepWinNum":541,"snakeWinNum":0,"tigerWinNum":0}
     * systemDate : 1557750742549
     */

    public String code;
    public String msg;
    public DataBean data;
    public long systemDate;

    public static class DataBean {
        /**
         * cattleWinNum : 0
         * chickenWinNum : 0
         * dogWinNum : 0
         * gameNum : 05131233
         * horseWinNum : 1082
         * loongWinNum : 0
         * monkeyWinNum : 0
         * mouseWinNum : 0
         * pigWinNum : 0
         * rabbitWinNum : 0
         * sheepWinNum : 541
         * snakeWinNum : 0
         * tigerWinNum : 0
         */

        public String cattleWinNum;
        public String chickenWinNum;
        public String dogWinNum;
        public String gameNum;
        public String horseWinNum;
        public String loongWinNum;
        public String monkeyWinNum;
        public String mouseWinNum;
        public String pigWinNum;
        public String rabbitWinNum;
        public String sheepWinNum;
        public String snakeWinNum;
        public String tigerWinNum;
    }
}
