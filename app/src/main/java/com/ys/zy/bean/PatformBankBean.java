package com.ys.zy.bean;

import java.util.List;

public class PatformBankBean {

    /**
     * code : SUCCESS
     * msg : 查询成功！
     * data : [{"accountId":"13c9ab146fb146c3b0545bf48c209ee7","accountTypeCode":"BANK","accountTypeName":"银行卡","merchNo":null,"key":null,"reqUrl":null,"queryUrl":null,"bankName":"工商银行","bankCode":"ICBC","bankNumber":"53333339999222222","bankUser":"测试","accountStatus":"1000","categoryType":"2","remark":"实施","createId":"85e71457c01e41d18d4561cf32170878","createTime":"2019-05-16T08:33:33.000+0000"}]
     * systemDate : 1558002065902
     */

    public String code;
    public String msg;
    public long systemDate;
    public List<DataBean> data;

    public static class DataBean {
        /**
         * accountId : 13c9ab146fb146c3b0545bf48c209ee7
         * accountTypeCode : BANK
         * accountTypeName : 银行卡
         * merchNo : null
         * key : null
         * reqUrl : null
         * queryUrl : null
         * bankName : 工商银行
         * bankCode : ICBC
         * bankNumber : 53333339999222222
         * bankUser : 测试
         * accountStatus : 1000
         * categoryType : 2
         * remark : 实施
         * createId : 85e71457c01e41d18d4561cf32170878
         * createTime : 2019-05-16T08:33:33.000+0000
         */

        public String accountId;
        public String accountTypeCode;
        public String accountTypeName;
        public String merchNo;
        public String key;
        public String reqUrl;
        public String queryUrl;
        public String bankName;
        public String bankCode;
        public String bankNumber;
        public String bankUser;
        public String accountStatus;
        public String categoryType;
        public String remark;
        public String createId;
        public String createTime;
    }
}
