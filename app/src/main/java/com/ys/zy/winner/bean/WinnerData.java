package com.ys.zy.winner.bean;

import java.util.List;

public class WinnerData {


    /**
     * code : SUCCESS
     * msg : 查询成功
     * data : {"lastWinnerBaseVo":{"totleMoney":"3.0","gameStartTime":"2019-05-10T10:15:00.000+0000","periodNum":"20190510002","gameStatusName":"已开奖","earnMoney":"0.20","freeMoney":"9999668.72","payMoney":"3","endTime":"2019-05-10T12:15:20.000+0000","snprice":"3"},"listSn":["SN002","SN001"],"listRecord":["折欲于17:14:58成功购买了SN002","折欲于17:14:10成功购买了SN001"]}
     * systemDate : 1557652504052
     */

    public String code;
    public String msg;
    public DataBean data;
    public long systemDate;

    public static class DataBean {
        /**
         * lastWinnerBaseVo : {"totleMoney":"3.0","gameStartTime":"2019-05-10T10:15:00.000+0000","periodNum":"20190510002","gameStatusName":"已开奖","earnMoney":"0.20","freeMoney":"9999668.72","payMoney":"3","endTime":"2019-05-10T12:15:20.000+0000","snprice":"3"}
         * listSn : ["SN002","SN001"]
         * listRecord : ["折欲于17:14:58成功购买了SN002","折欲于17:14:10成功购买了SN001"]
         */

        public LastWinnerBaseVoBean lastWinnerBaseVo;
        public List<String> listSn;
        public List<String> listRecord;

        public static class LastWinnerBaseVoBean {
            /**
             * totleMoney : 3.0
             * gameStartTime : 2019-05-10T10:15:00.000+0000
             * periodNum : 20190510002
             * gameStatusName : 已开奖
             * earnMoney : 0.20
             * freeMoney : 9999668.72
             * payMoney : 3
             * endTime : 2019-05-10T12:15:20.000+0000
             * snprice : 3
             */

            public String totleMoney;
            public String gameStartTime;
            public String periodNum;
            public String gameStatusName;
            public String earnMoney;
            public String freeMoney;
            public String payMoney;
            public String endTime;
            public String snprice;
        }
    }
}
