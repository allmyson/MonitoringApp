package com.ys.zy.ttz.bean;

public class TtzTz {


    /**
     * code : SUCCESS
     * msg : 查询成功
     * data : {"gameNum":"05151025","zpx1t":null,"zpx2t":null,"zpx3t":null,"zsx1t":null,"zsx2t":null,"zsx3t":null,"zyx1t":"4.0","zyx2t":null,"zyx3t":null}
     * systemDate : 1557911056135
     */

    public String code;
    public String msg;
    public DataBean data;
    public long systemDate;

    public static class DataBean {
        /**
         * gameNum : 05151025
         * zpx1t : null
         * zpx2t : null
         * zpx3t : null
         * zsx1t : null
         * zsx2t : null
         * zsx3t : null
         * zyx1t : 4.0
         * zyx2t : null
         * zyx3t : null
         */

        public String gameNum;
        public String zpx1t;
        public String zpx2t;
        public String zpx3t;
        public String zsx1t;
        public String zsx2t;
        public String zsx3t;
        public String zyx1t;
        public String zyx2t;
        public String zyx3t;
    }
}
