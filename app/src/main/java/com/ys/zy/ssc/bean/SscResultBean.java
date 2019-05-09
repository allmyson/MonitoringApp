package com.ys.zy.ssc.bean;

import java.util.List;

public class SscResultBean {

    /**
     * code : SUCCESS
     * msg : 查询成功
     * data : [{"recordId":"5fbf68d107fb4bd7932cfc2100e43d30","gameCode":"1000","gameName":"重庆时时彩","lotteryNum":"7,0,5,8,4","lotteryTime":"2019-01-28T17:20:24.000+0000","periodsNum":"20190129016"}]
     * systemDate : 1557406797672
     */

    public String code;
    public String msg;
    public long systemDate;
    public List<DataBean> data;

    public static class DataBean {
        /**
         * recordId : 5fbf68d107fb4bd7932cfc2100e43d30
         * gameCode : 1000
         * gameName : 重庆时时彩
         * lotteryNum : 7,0,5,8,4
         * lotteryTime : 2019-01-28T17:20:24.000+0000
         * periodsNum : 20190129016
         */

        public String recordId;
        public String gameCode;
        public String gameName;
        public String lotteryNum;
        public String lotteryTime;
        public String periodsNum;
    }
}
