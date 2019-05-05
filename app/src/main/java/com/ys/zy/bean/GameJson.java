package com.ys.zy.bean;

import java.util.List;

public class GameJson {

    /**
     * pageNum : 1
     * length : 100
     * start : 1
     * search : null
     * order : null
     * column : null
     * recordsTotal : 11
     * recordsFiltered : 11
     * draw : 2
     * data : [{"game_status":"1001","game_name":"重庆时时彩","game_code":"1000","game_id":"7ec83eea41ab40018074edcf2386fccc"},{"game_status":"1000","game_name":"1 分彩","game_code":"1001","game_id":"19b7ae766d8440f8ba5f2bb07e0c98ea"},{"game_status":"1000","game_name":"最后的胜利者","game_code":"1002","game_id":"a3ce4d442eab45e4a7bb6cbc19d54e9c"},{"game_status":"1000","game_name":"轮盘","game_code":"1003","game_id":"138a6e15197b404ba7959cb737fa8eac"},{"game_status":"1000","game_name":"推筒子","game_code":"1004","game_id":"ed1aa7a1356047c59ad8c05d1d75bb99"},{"game_status":"1000","game_name":"江苏快 3","game_code":"1005","game_id":"737e89324155461db107249ebe8290a8"},{"game_status":"1000","game_name":"幸运快 3","game_code":"1006","game_id":"df1a3daf01da4022b92732aba6ebb893"},{"game_status":"1000","game_name":"好运快 3","game_code":"1007","game_id":"7af76f2d3abb48988d0fae60d904bf00"},{"game_status":"1000","game_name":"北京赛车","game_code":"1008","game_id":"3516c4fe49124361b1781d08945ee7bc"},{"game_status":"1000","game_name":"幸运赛车","game_code":"1009","game_id":"7e9219cd41c34da79739a59c5f5ad84d"},{"game_status":"1000","game_name":"5 分赛车","game_code":"1010","game_id":"4b0033b3ab7a4e79a7216f07008eff8f"}]
     * subSQL :
     * systemTime : 1557047482069
     */

    public int pageNum;
    public int length;
    public int start;
    public String search;
    public String order;
    public String column;
    public int recordsTotal;
    public int recordsFiltered;
    public int draw;
    public String subSQL;
    public long systemTime;
    public List<DataBean> data;

    public static class DataBean {
        /**
         * game_status : 1001
         * game_name : 重庆时时彩
         * game_code : 1000
         * game_id : 7ec83eea41ab40018074edcf2386fccc
         */

        public String game_status;
        public String game_name;
        public String game_code;
        public String game_id;
    }
}
