package com.ys.zy.bean;

import java.util.List;

public class RechargePlatform {

    /**
     * code : SUCCESS
     * msg : 查询成功！
     * data : [{"accountId":"072fc5bb75254eeeae7b6ce447db52ca","accountTypeCode":"ZFB","accountTypeName":"支付宝","merchNo":"XF201904202853","key":"28F253A74D808BD24496C5E7CCE88C6A","reqUrl":"http://netway.xfzfpay.com:90/api/pay","queryUrl":"http://query.xfzfpay.com:90/api/queryPayResult","accountStatus":"1000","createId":"85e71457c01e41d18d4561cf32170878","createTime":"2019-05-10T04:27:29.000+0000"},{"accountId":"14de86d3df0d46b0974f801b156474d9","accountTypeCode":"BANK","accountTypeName":"银行卡","merchNo":null,"key":null,"reqUrl":null,"queryUrl":null,"accountStatus":"1000","createId":"85e71457c01e41d18d4561cf32170878","createTime":"2019-05-15T13:25:17.000+0000"},{"accountId":"3a28a53c9cf9426e90d15ab06ec8c27f","accountTypeCode":"WX_WAP","accountTypeName":"微信WAP(H5)","merchNo":"XF201904202853","key":"28F253A74D808BD24496C5E7CCE88C6A","reqUrl":"http://netway.xfzfpay.com:90/api/pay","queryUrl":"http://query.xfzfpay.com:90/api/queryPayResult","accountStatus":"1000","createId":"85e71457c01e41d18d4561cf32170878","createTime":"2019-05-14T09:27:34.000+0000"},{"accountId":"9770e48381714d2aa71338cc455ff202","accountTypeCode":"ZFB","accountTypeName":"支付宝","merchNo":"123123","key":"123123","reqUrl":"123123","queryUrl":"123123","accountStatus":"1000","createId":"85e71457c01e41d18d4561cf32170878","createTime":"2019-05-16T02:53:05.000+0000"},{"accountId":"a985c377dbc24396be9228f7a1cf5cc0","accountTypeCode":"WX","accountTypeName":"微信","merchNo":"XF201904202853","key":"28F253A74D808BD24496C5E7CCE88C6A","reqUrl":"http://netway.xfzfpay.com:90/api/pay","queryUrl":"http://netway.xfzfpay.com:90/api/pay","accountStatus":"1000","createId":"85e71457c01e41d18d4561cf32170878","createTime":"2019-05-14T09:22:13.000+0000"},{"accountId":"c7a015ca98d14237a13c556db3ace717","accountTypeCode":"BANK","accountTypeName":"银行卡","merchNo":null,"key":null,"reqUrl":null,"queryUrl":null,"accountStatus":"1000","createId":"85e71457c01e41d18d4561cf32170878","createTime":"2019-05-16T02:51:07.000+0000"}]
     * systemDate : 1557975846964
     */

    public String code;
    public String msg;
    public long systemDate;
    public List<DataBean> data;

    public static class DataBean {
        /**
         * accountId : 072fc5bb75254eeeae7b6ce447db52ca
         * accountTypeCode : ZFB
         * accountTypeName : 支付宝
         * merchNo : XF201904202853
         * key : 28F253A74D808BD24496C5E7CCE88C6A
         * reqUrl : http://netway.xfzfpay.com:90/api/pay
         * queryUrl : http://query.xfzfpay.com:90/api/queryPayResult
         * accountStatus : 1000
         * createId : 85e71457c01e41d18d4561cf32170878
         * createTime : 2019-05-10T04:27:29.000+0000
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
        public String remark;
    }
}
