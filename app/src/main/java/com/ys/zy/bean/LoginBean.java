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
     * data : {"consumerId":"dc258de6f71848c6b3278350d609537e","backNum":"0.09","balance":0,"consumerImg":"","consumerName":"88b629eefc954a8fa461865a73824506","createId":null,"createTime":"2019-05-05T08:09:00.000+0000","levelCode":"1001","levelName":"代理会员","loginName":"longhui0","moneyPwd":null,"onlineTime":"2019-05-05T08:09:26.981+0000","parentConsumerId":"be6cca47335946b9827ca5e09bea5f7c","parentConsumerName":null,"payUrl":null,"pwd":"dd4b21e9ef71e1291183a46b913ae6f2","qqNum":null,"remark":null,"status":"1001","weixinNum":null,"tel":null,"regCode":null,"delFlag":"0"}
     * systemDate : 1557043766995
     */

    public String code;
    public String msg;
    public DataBean data;
    public long systemDate;

    public static class DataBean {
        /**
         * consumerId : dc258de6f71848c6b3278350d609537e
         * backNum : 0.09
         * balance : 0.0
         * consumerImg :
         * consumerName : 88b629eefc954a8fa461865a73824506
         * createId : null
         * createTime : 2019-05-05T08:09:00.000+0000
         * levelCode : 1001
         * levelName : 代理会员
         * loginName : longhui0
         * moneyPwd : null
         * onlineTime : 2019-05-05T08:09:26.981+0000
         * parentConsumerId : be6cca47335946b9827ca5e09bea5f7c
         * parentConsumerName : null
         * payUrl : null
         * pwd : dd4b21e9ef71e1291183a46b913ae6f2
         * qqNum : null
         * remark : null
         * status : 1001
         * weixinNum : null
         * tel : null
         * regCode : null
         * delFlag : 0
         */

        public String consumerId;
        public String backNum;
        public String balance;
        public String consumerImg;
        public String consumerName;
        public String createId;
        public String createTime;
        public String levelCode;
        public String levelName;
        public String loginName;
        public String moneyPwd;
        public String onlineTime;
        public String parentConsumerId;
        public String parentConsumerName;
        public String payUrl;
        public String pwd;
        public String qqNum;
        public String remark;
        public String status;
        public String weixinNum;
        public String tel;
        public String regCode;
        public String delFlag;
    }
}
