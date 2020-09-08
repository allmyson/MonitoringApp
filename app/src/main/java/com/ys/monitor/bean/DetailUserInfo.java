package com.ys.monitor.bean;

public class DetailUserInfo {

    /**
     * code : SUCCESS
     * msg : 查询成功
     * data : {"consumerId":"be6cca47335946b9827ca5e09bea5f7c","backNum":"0.1","balance":9.099956524E7,"consumerImg":"http://103.65.180.104:8090/userapp/2019/01/03///1546528192850.jpg","consumerName":"李白不白","createId":null,"createTime":"2018-12-03T02:44:01.000+0000","onlineTime":"2019-01-15T02:41:03.000+0000","levelCode":"1001","levelName":"代理会员","loginName":"cy","parentConsumerId":"85e71457c01e41d18d4561cf32170878","pwd":"e10adc3949ba59abbe56e057f20f883e","remark":null,"status":"1001","payUrl":"http://103.65.180.104:8090/bindAccountapp/2019/01/15///1547514622923.jpg","moneyPwd":"000000"}
     * systemDate : 1547520074375
     */

    public String code;
    public String msg;
    public DataBean data;
    public long systemDate;

    public static class DataBean {
        /**
         * consumerId : be6cca47335946b9827ca5e09bea5f7c
         * backNum : 0.1
         * balance : 9.099956524E7
         * consumerImg : http://103.65.180.104:8090/userapp/2019/01/03///1546528192850.jpg
         * consumerName : 李白不白
         * createId : null
         * createTime : 2018-12-03T02:44:01.000+0000
         * onlineTime : 2019-01-15T02:41:03.000+0000
         * levelCode : 1001
         * levelName : 代理会员
         * loginName : cy
         * parentConsumerId : 85e71457c01e41d18d4561cf32170878
         * pwd : e10adc3949ba59abbe56e057f20f883e
         * remark : null
         * status : 1001
         * payUrl : http://103.65.180.104:8090/bindAccountapp/2019/01/15///1547514622923.jpg
         * moneyPwd : 000000
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
        public String payUrl;
        public String moneyPwd;
    }
}
