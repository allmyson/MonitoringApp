package com.ys.zy.bean;

public class OddsBean {

    /**
     * code : SUCCESS
     * msg : 查询成功
     * data : {"oddsId":"ad89fffe960e4022af1eec1da357f016","odds":"0.1","ksHz318":"173.01","ksHz417":"57.67","ksHz516":"28.83","ksHz615":"17.3","ksHz714":"11.53","ksHz813":"8.23","ksHz912":"6.92","ksHz1011":"6.4","ksDxds":"1.81","scDxds":"1.81","scLhd":"1.81","scDwd":"9.01","sscDwd":"9.01","sscDxds":"1.81","sscHexzx":"45.05","sscWxzx":"90,100","lunpan":"10.82","ttzZx":"1.81","ttzPing":"59.21"}
     * systemDate : 1557747863027
     */

    public String code;
    public String msg;
    public DataBean data;
    public long systemDate;

    public static class DataBean {
        /**
         * oddsId : ad89fffe960e4022af1eec1da357f016
         * odds : 0.1
         * ksHz318 : 173.01
         * ksHz417 : 57.67
         * ksHz516 : 28.83
         * ksHz615 : 17.3
         * ksHz714 : 11.53
         * ksHz813 : 8.23
         * ksHz912 : 6.92
         * ksHz1011 : 6.4
         * ksDxds : 1.81
         * scDxds : 1.81
         * scLhd : 1.81
         * scDwd : 9.01
         * sscDwd : 9.01
         * sscDxds : 1.81
         * sscHexzx : 45.05
         * sscWxzx : 90,100
         * lunpan : 10.82
         * ttzZx : 1.81
         * ttzPing : 59.21
         */

        public String oddsId;
        public String odds;
        public String ksHz318;
        public String ksHz417;
        public String ksHz516;
        public String ksHz615;
        public String ksHz714;
        public String ksHz813;
        public String ksHz912;
        public String ksHz1011;
        public String ksDxds;
        public String scDxds;
        public String scLhd;
        public String scDwd;
        public String sscDwd;
        public String sscDxds;
        public String sscHexzx;
        public String sscWxzx;
        public String lunpan;
        public String ttzZx;
        public String ttzPing;
    }
}
