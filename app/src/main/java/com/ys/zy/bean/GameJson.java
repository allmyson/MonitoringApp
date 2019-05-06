package com.ys.zy.bean;

import java.util.List;

public class GameJson {

    /**
     * code : SUCCESS
     * msg : 查询成功
     * data : [{"gameId":"7ec83eea41ab40018074edcf2386fccc","gameName":"重庆时时彩","gameCode":"1000","gameStatus":"1001"},{"gameId":"19b7ae766d8440f8ba5f2bb07e0c98ea","gameName":"1 分彩","gameCode":"1001","gameStatus":"1000"},{"gameId":"a3ce4d442eab45e4a7bb6cbc19d54e9c","gameName":"最后的胜利者","gameCode":"1002","gameStatus":"1000"},{"gameId":"138a6e15197b404ba7959cb737fa8eac","gameName":"轮盘","gameCode":"1003","gameStatus":"1000"},{"gameId":"ed1aa7a1356047c59ad8c05d1d75bb99","gameName":"推筒子","gameCode":"1004","gameStatus":"1000"},{"gameId":"737e89324155461db107249ebe8290a8","gameName":"江苏快 3","gameCode":"1005","gameStatus":"1000"},{"gameId":"df1a3daf01da4022b92732aba6ebb893","gameName":"幸运快 3","gameCode":"1006","gameStatus":"1000"},{"gameId":"7af76f2d3abb48988d0fae60d904bf00","gameName":"好运快 3","gameCode":"1007","gameStatus":"1000"},{"gameId":"3516c4fe49124361b1781d08945ee7bc","gameName":"北京赛车","gameCode":"1008","gameStatus":"1000"},{"gameId":"7e9219cd41c34da79739a59c5f5ad84d","gameName":"幸运赛车","gameCode":"1009","gameStatus":"1000"},{"gameId":"4b0033b3ab7a4e79a7216f07008eff8f","gameName":"5 分赛车","gameCode":"1010","gameStatus":"1000"}]
     * systemDate : 1557125111723
     */

    public String code;
    public String msg;
    public long systemDate;
    public List<DataBean> data;

    public static class DataBean {
        /**
         * gameId : 7ec83eea41ab40018074edcf2386fccc
         * gameName : 重庆时时彩
         * gameCode : 1000
         * gameStatus : 1001
         */

        public String gameId;
        public String gameName;
        public String gameCode;
        public String gameStatus;
    }
}
