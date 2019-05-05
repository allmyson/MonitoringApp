package com.ys.zy.bean;

import java.util.List;

public class HotGameBean {

    /**
     * code : SUCCESS
     * msg : 验证成功
     * data : [{"bets_money":960,"game_name":"江苏快 3","game_code":"1005"},{"bets_money":240,"game_name":"5 分赛车","game_code":"1010"},{"bets_money":170,"game_name":"1 分彩","game_code":"1001"},{"bets_money":130,"game_name":"轮盘","game_code":"1003"},{"bets_money":90,"game_name":"推筒子","game_code":"1004"},{"bets_money":30,"game_name":"幸运快 3","game_code":"1006"},{"bets_money":20,"game_name":"幸运赛车","game_code":"1009"},{"bets_money":20,"game_name":"重庆时时彩","game_code":"1000"}]
     * systemDate : 1557069263995
     */

    public String code;
    public String msg;
    public long systemDate;
    public List<DataBean> data;

    public static class DataBean {
        /**
         * bets_money : 960
         * game_name : 江苏快 3
         * game_code : 1005
         */

        public String bets_money;
        public String game_name;
        public String game_code;
    }
}
