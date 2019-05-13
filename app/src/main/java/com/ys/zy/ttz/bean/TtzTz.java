package com.ys.zy.ttz.bean;

public class TtzTz {

    /**
     * code : SUCCESS
     * msg : 查询成功
     * data : {"gameNum":"05131253","zpx1":null,"zpx2":"177.63","zpx3":null,"zsx1":null,"zsx2":"3.62","zsx3":"3.62","zyx1":"5.43","zyx2":null,"zyx3":null}
     * systemDate : 1557751931391
     */

    public String code;
    public String msg;
    public DataBean data;
    public long systemDate;

    public static class DataBean {
        /**
         * gameNum : 05131253
         * zpx1 : null
         * zpx2 : 177.63
         * zpx3 : null
         * zsx1 : null
         * zsx2 : 3.62
         * zsx3 : 3.62
         * zyx1 : 5.43
         * zyx2 : null
         * zyx3 : null
         */

        public String gameNum;
        public String zpx1;
        public String zpx2;
        public String zpx3;
        public String zsx1;
        public String zsx2;
        public String zsx3;
        public String zyx1;
        public String zyx2;
        public String zyx3;
    }
}
