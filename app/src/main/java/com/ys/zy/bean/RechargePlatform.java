package com.ys.zy.bean;

import java.util.List;

public class RechargePlatform {

    /**
     * code : SUCCESS
     * msg : 查询成功！
     * data : [{"accountId":"04a39fe072b84445b46c7d4aa7e4d1fe","accountTypeCode":"UNION_WALLET","accountTypeName":"银联钱包(云闪付)","merchNo":"XF201808160001","key":"9416F3C0E62E167DA02DC4D91AB2B21E","reqUrl":"http://netway.xfzfpay.com:90/api/pay","queryUrl":"http://query.xfzfpay.com:90/api/queryPayResult","accountStatus":"1000","createId":"85e71457c01e41d18d4561cf32170878","createTime":"2019-04-02T01:11:34.000+0000"},{"accountId":"9c90a81b9a2744ffaea71b796c4a4757","accountTypeCode":"ZFB","accountTypeName":"支付宝","merchNo":"XF201808160003","key":"9416F3C0E62E167DA02DC4D91AB2B21E","reqUrl":"http://netway.xfzfpay.com:90/api/pay","queryUrl":"http://query.xfzfpay.com:90/api/queryPayResult","accountStatus":"1000","createId":"85e71457c01e41d18d4561cf32170878","createTime":"2019-04-02T01:10:53.000+0000"},{"accountId":"e5ddde9b2886495aae704c2f3f02fe83","accountTypeCode":"WX","accountTypeName":"微信","merchNo":"XF201808160004","key":"9416F3C0E62E167DA02DC4D91AB2B21E","reqUrl":"http://netway.xfzfpay.com:90/api/pay","queryUrl":"http://query.xfzfpay.com:90/api/queryPayResult","accountStatus":"1000","createId":"85e71457c01e41d18d4561cf32170878","createTime":"2019-04-02T01:11:57.000+0000"}]
     * systemDate : 1557394305437
     */

    public String code;
    public String msg;
    public long systemDate;
    public List<DataBean> data;

    public static class DataBean {
        /**
         * accountId : 04a39fe072b84445b46c7d4aa7e4d1fe
         * accountTypeCode : UNION_WALLET
         * accountTypeName : 银联钱包(云闪付)
         * merchNo : XF201808160001
         * key : 9416F3C0E62E167DA02DC4D91AB2B21E
         * reqUrl : http://netway.xfzfpay.com:90/api/pay
         * queryUrl : http://query.xfzfpay.com:90/api/queryPayResult
         * accountStatus : 1000
         * createId : 85e71457c01e41d18d4561cf32170878
         * createTime : 2019-04-02T01:11:34.000+0000
         */

        public String accountId;
        public String accountTypeCode;
        public String accountTypeName;
        public String merchNo;
        public String key;
        public String reqUrl;
        public String queryUrl;
        public String accountStatus;
        public String createId;
        public String createTime;
    }
}
