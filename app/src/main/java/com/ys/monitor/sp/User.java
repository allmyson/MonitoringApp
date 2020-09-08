package com.ys.monitor.sp;

public class User {


    /**
     * code : SUCCESS
     * msg : 查询成功
     * data : {"consumerId":"6542c3007f824566838217e3292664ab","backNum":"3.0","balance":1.002756095E7,"consumerImg":"/userapp/2019/05/15///1557922618940.jpg","consumerName":"折欲","createId":null,"createTime":"2019-05-10T06:12:36.000+0000","levelCode":"1001","levelName":"代理会员","loginName":"longhui0","moneyPwd":"dd4b21e9ef71e1291183a46b913ae6f2","onlineTime":"2019-05-16T06:27:25.000+0000","parentConsumerId":"85e71457c01e41d18d4561cf32170878","parentConsumerName":null,"payUrl":null,"pwd":"dd4b21e9ef71e1291183a46b913ae6f2","qqNum":null,"remark":null,"status":"1001","weixinNum":null,"tel":"13640507192","regCode":"RBUS7F4X","delFlag":"0","withdrawMoney":231.42}
     * systemDate : 1558083500946
     */

    public String code;
    public String msg;
    public DataBean data;
    public long systemDate;

    public static class DataBean {
        /**
         * consumerId : 6542c3007f824566838217e3292664ab
         * backNum : 3.0
         * balance : 1.002756095E7
         * consumerImg : /userapp/2019/05/15///1557922618940.jpg
         * consumerName : 折欲
         * createId : null
         * createTime : 2019-05-10T06:12:36.000+0000
         * levelCode : 1001
         * levelName : 代理会员
         * loginName : longhui0
         * moneyPwd : dd4b21e9ef71e1291183a46b913ae6f2
         * onlineTime : 2019-05-16T06:27:25.000+0000
         * parentConsumerId : 85e71457c01e41d18d4561cf32170878
         * parentConsumerName : null
         * payUrl : null
         * pwd : dd4b21e9ef71e1291183a46b913ae6f2
         * qqNum : null
         * remark : null
         * status : 1001
         * weixinNum : null
         * tel : 13640507192
         * regCode : RBUS7F4X
         * delFlag : 0
         * withdrawMoney : 231.42
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
        public String withdrawMoney;
    }
}
