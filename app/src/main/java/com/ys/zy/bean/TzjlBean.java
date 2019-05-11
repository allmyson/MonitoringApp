package com.ys.zy.bean;

import android.support.annotation.NonNull;

import com.ys.zy.util.StringUtil;

import java.util.List;

public class TzjlBean {

    /**
     * code : SUCCESS
     * msg : 查询成功
     * data : {"pageNum":null,"length":null,"start":null,"search":null,"order":null,"column":null,"recordsTotal":50,"recordsFiltered":50,"draw":null,"data":[{"bets_time":"2019-05-11T13:26:30.000+0000","bets_money":"2.0","bets_bit_code":null,"create_time":"2019-05-11T13:26:30.000+0000","SN_money":null,"is_win_code":"1002","is_win_name":"待开奖","periods_num":"05211291","complant_type_name":"手动下注","catalog_id":"1557581189626","consumer_id":"6542c3007f824566838217e3292664ab","game_name":"1 分彩","wallet_id":"07f3870a9ebf4884b6d95f9b4f251fea","times":"1","consumer_name":"折欲","lottery_type_code":null,"bets_num":"0","win_money":null,"lottery_type_name":null,"complant_type_code":"1000","reamrk":null,"game_code":"1001","bets_bit_name":null}],"subSQL":null,"systemTime":0}
     * systemDate : 1557581685343
     */

    public String code;
    public String msg;
    public DataBeanX data;
    public long systemDate;

    public static class DataBeanX {
        /**
         * pageNum : null
         * length : null
         * start : null
         * search : null
         * order : null
         * column : null
         * recordsTotal : 50
         * recordsFiltered : 50
         * draw : null
         * data : [{"bets_time":"2019-05-11T13:26:30.000+0000","bets_money":"2.0","bets_bit_code":null,"create_time":"2019-05-11T13:26:30.000+0000","SN_money":null,"is_win_code":"1002","is_win_name":"待开奖","periods_num":"05211291","complant_type_name":"手动下注","catalog_id":"1557581189626","consumer_id":"6542c3007f824566838217e3292664ab","game_name":"1 分彩","wallet_id":"07f3870a9ebf4884b6d95f9b4f251fea","times":"1","consumer_name":"折欲","lottery_type_code":null,"bets_num":"0","win_money":null,"lottery_type_name":null,"complant_type_code":"1000","reamrk":null,"game_code":"1001","bets_bit_name":null}]
         * subSQL : null
         * systemTime : 0
         */

        public String pageNum;
        public String length;
        public String start;
        public String search;
        public String order;
        public String column;
        public int recordsTotal;
        public int recordsFiltered;
        public String draw;
        public String subSQL;
        public int systemTime;
        public List<DataBean> data;

        public static class DataBean implements Comparable<DataBean> {
            /**
             * bets_time : 2019-05-11T13:26:30.000+0000
             * bets_money : 2.0
             * bets_bit_code : null
             * create_time : 2019-05-11T13:26:30.000+0000
             * SN_money : null
             * is_win_code : 1002
             * is_win_name : 待开奖
             * periods_num : 05211291
             * complant_type_name : 手动下注
             * catalog_id : 1557581189626
             * consumer_id : 6542c3007f824566838217e3292664ab
             * game_name : 1 分彩
             * wallet_id : 07f3870a9ebf4884b6d95f9b4f251fea
             * times : 1
             * consumer_name : 折欲
             * lottery_type_code : null
             * bets_num : 0
             * win_money : null
             * lottery_type_name : null
             * complant_type_code : 1000
             * reamrk : null
             * game_code : 1001
             * bets_bit_name : null
             */

            public String bets_time;
            public String bets_money;
            public String bets_bit_code;
            public String create_time;
            public String SN_money;
            public String is_win_code;
            public String is_win_name;
            public String periods_num;
            public String complant_type_name;
            public String catalog_id;
            public String consumer_id;
            public String game_name;
            public String wallet_id;
            public String times;
            public String consumer_name;
            public String lottery_type_code;
            public String bets_num;
            public String win_money;
            public String lottery_type_name;
            public String complant_type_code;
            public String reamrk;
            public String game_code;
            public String bets_bit_name;
            @Override
            public int compareTo(@NonNull DataBean o) {
                return (int) (StringUtil.StringToLong(o.periods_num) - StringUtil.StringToLong(this.periods_num));//降序
            }
        }
    }
}
