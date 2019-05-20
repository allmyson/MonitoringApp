package com.ys.zy.bean;

import android.support.annotation.NonNull;

import com.ys.zy.util.StringUtil;

import java.util.List;

public class MyXfjlBean {


    /**
     * code : SUCCESS
     * msg : 查询成功
     * data : {"pageNum":2,"length":1,"start":1,"search":null,"order":null,"column":null,"recordsTotal":57,"recordsFiltered":57,"draw":0,"data":[{"bets_money":3,"game_name":"最后的胜利者","win_money":0.9,"game_code":"1002","periods_num":"20190514001"}],"subSQL":"","systemTime":1557811744293}
     * systemDate : 1557811744293
     */

    public String code;
    public String msg;
    public DataBeanX data;
    public long systemDate;

    public static class DataBeanX {
        /**
         * pageNum : 2
         * length : 1
         * start : 1
         * search : null
         * order : null
         * column : null
         * recordsTotal : 57
         * recordsFiltered : 57
         * draw : 0
         * data : [{"bets_money":3,"game_name":"最后的胜利者","win_money":0.9,"game_code":"1002","periods_num":"20190514001"}]
         * subSQL :
         * systemTime : 1557811744293
         */

        public int pageNum;
        public int length;
        public int start;
        public Object search;
        public Object order;
        public Object column;
        public int recordsTotal;
        public int recordsFiltered;
        public int draw;
        public String subSQL;
        public long systemTime;
        public List<DataBean> data;

        public static class DataBean implements Comparable<DataBean> {
            /**
             * bets_money : 3
             * game_name : 最后的胜利者
             * win_money : 0.9
             * game_code : 1002
             * periods_num : 20190514001
             */

            public String bets_money;
            public String game_name;
            public String win_money;
            public String game_code;
            public String periods_num;
            public String bets_time;
            public String game_status;

            @Override
            public int compareTo(@NonNull DataBean o) {
                return (int) (StringUtil.StringToLong(o.periods_num) - StringUtil.StringToLong(this.periods_num));//降序
            }
        }
    }
}
