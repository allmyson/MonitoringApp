package com.ys.zy.bean;

/**
 * @author lh
 * @version 1.0.0
 * @filename UserInfo
 * @description -------------------------------------------------------
 * @date 2018/12/14 10:05
 */
public class UserInfo {

    /**
     * code : SUCCESS
     * msg : 查询成功
     * data : {"consumerId":"be6cca47335946b9827ca5e09bea5f7c","backNum":"0.1","balance":97251,
     * "consumerImg":"/app/imgs/userapp/2018/12/14///1544749123680.png","consumerName":"cy2","levelCode":"1001",
     * "levelName":"代理会员","loginName":"cy","parentConsumerId":null,"pwd":"e10adc3949ba59abbe56e057f20f883e",
     * "todayYl":"0.0","todayCz":"0.0","todayXf":"0.0"}
     * systemDate : 1544751762205
     */

    public String code;
    public String msg;
    public DataBean data;
    public long systemDate;

    public static class DataBean {
        /**
         * consumerId : be6cca47335946b9827ca5e09bea5f7c
         * backNum : 0.1
         * balance : 97251
         * consumerImg : /app/imgs/userapp/2018/12/14///1544749123680.png
         * consumerName : cy2
         * levelCode : 1001
         * levelName : 代理会员
         * loginName : cy
         * parentConsumerId : null
         * pwd : e10adc3949ba59abbe56e057f20f883e
         * todayYl : 0.0
         * todayCz : 0.0
         * todayXf : 0.0
         */

        public String consumerId;
        public String backNum;
        public String balance;
        public String consumerImg;
        public String consumerName;
        public String levelCode;
        public String levelName;
        public String loginName;
        public String parentConsumerId;
        public String pwd;
        public String todayYl;
        public String todayCz;
        public String todayXf;
    }
}
