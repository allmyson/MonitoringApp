package com.ys.zy.bean;

import java.util.List;

public class BankData {

    /**
     * code : SUCCESS
     * msg : 查询成功
     * data : [{"bankName":"建行","bindTime":1557321791889,"cardNumber":"********3321","openerName":"龙辉","position":1},{"openerName":"龙辉","cardNumber":"*****6789","bankName":"中国建设银行","position":3}]
     * systemDate : 1557366987819
     */

    public String code;
    public String msg;
    public long systemDate;
    public List<DataBean> data;

    public static class DataBean {
        /**
         * bankName : 建行
         * bindTime : 1557321791889
         * cardNumber : ********3321
         * openerName : 龙辉
         * position : 1
         */

        public String bankName;
        public long bindTime;
        public String cardNumber;
        public String openerName;
        public int position;
    }
}
