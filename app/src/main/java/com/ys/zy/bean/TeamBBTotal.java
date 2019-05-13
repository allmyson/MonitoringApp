package com.ys.zy.bean;

public class TeamBBTotal {

    /**
     * code : SUCCESS
     * msg : 查询成功！
     * data : {"bets_money":0,"user_count":"2","back_moeny":0}
     * systemDate : 1557766128292
     */

    public String code;
    public String msg;
    public DataBean data;
    public long systemDate;

    public static class DataBean {
        /**
         * bets_money : 0
         * user_count : 2
         * back_moeny : 0
         */

        public String bets_money;
        public String user_count;
        public String back_moeny;
    }
}
