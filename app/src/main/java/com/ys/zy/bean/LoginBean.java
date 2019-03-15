package com.ys.zy.bean;

/**
 * @author lh
 * @version 1.0.0
 * @filename LoginBean
 * @description -------------------------------------------------------
 * @date 2018/12/3 11:11
 */
public class LoginBean {

    /**
     * code : SUCCESS
     * msg : 登陆成功。
     * data : {"consumerId":"be6cca47335946b9827ca5e09bea5f7c","backNum":"0.1","balance":0,"consumerImg":"",
     * "consumerName":"曹勇","createId":null,"createTime":"2018-12-03T02:44:01.000+0000",
     * "onlineTime":"2018-12-03T02:44:20.996+0000","levelCode":"1000","levelName":"普通用户","loginName":"cy",
     * "parentConsumerId":null,"pwd":"e10adc3949ba59abbe56e057f20f883e","remark":null,"status":"1001"}
     * systemDate : 1543805061021
     */

    public String code;
    public String msg;
    public DataBean data;
    public long systemDate;

    public static class DataBean {
        /**
         * consumerId : be6cca47335946b9827ca5e09bea5f7c
         * backNum : 0.1
         * balance : 0
         * consumerImg :
         * consumerName : 曹勇
         * createId : null
         * createTime : 2018-12-03T02:44:01.000+0000
         * onlineTime : 2018-12-03T02:44:20.996+0000
         * levelCode : 1000
         * levelName : 普通用户
         * loginName : cy
         * parentConsumerId : null
         * pwd : e10adc3949ba59abbe56e057f20f883e
         * remark : null
         * status : 1001
         */

        public String consumerId;
        public String backNum;
        public String balance;
        public String consumerImg;
        public String consumerName;
        public String createId;
        public String createTime;
        public String onlineTime;
        public String levelCode;
        public String levelName;
        public String loginName;
        public String parentConsumerId;
        public String pwd;
        public String remark;
        public String status;
    }
}
