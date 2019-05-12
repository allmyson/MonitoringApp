package com.ys.zy.winner.bean;

public class WinnerResult {

    /**
     * code : SUCCESS
     * msg : 查询成功
     * data : {"periodNum":"20190512001","lastUserName":null,"lastMoney":null,"incentiveUserName1":null,"incentiveSnNum1":null,"incentive_money1":null,"incentiveUserName2":null,"incentiveSnNum2":null,"incentive_money2":null,"incentiveUserName3":null,"incentiveSnNum3":null,"incentive_money3":null,"snNum":null}
     * systemDate : 1557658325509
     */

    public String code;
    public String msg;
    public DataBean data;
    public long systemDate;

    public static class DataBean {
        /**
         * periodNum : 20190512001
         * lastUserName : null
         * lastMoney : null
         * incentiveUserName1 : null
         * incentiveSnNum1 : null
         * incentive_money1 : null
         * incentiveUserName2 : null
         * incentiveSnNum2 : null
         * incentive_money2 : null
         * incentiveUserName3 : null
         * incentiveSnNum3 : null
         * incentive_money3 : null
         * snNum : null
         */

        public String periodNum;
        public String lastUserName;
        public String lastMoney;
        public String incentiveUserName1;
        public String incentiveSnNum1;
        public String incentive_money1;
        public String incentiveUserName2;
        public String incentiveSnNum2;
        public String incentive_money2;
        public String incentiveUserName3;
        public String incentiveSnNum3;
        public String incentive_money3;
        public String snNum;
    }
}
