package com.ys.zy.bean;

import java.util.List;

public class OddBean {

    /**
     * pageNum : 2
     * length : 1
     * start : 2
     * search : null
     * order : null
     * column : null
     * recordsTotal : 0
     * recordsFiltered : 0
     * draw : 0
     * data : [{"ks_hz_3_18":"192.24","sc_lhd":"1.98","ks_hz_6_15":"19.22","ks_hz_4_17":"64.08","ks_hz_7_14":"12.81","ssc_wxzx":"99,000","ks_hz_5_16":"32.04","ssc_hexzx":"49.5","ttz_zx":"1.98","ttz_ping":"59.74","ks_hz_9_12":"7.68","ssc_dxds":"1.98","ks_hz_8_13":"9.15","sc_dxds":"1.98","odds":"9","lunpan":"11.88","ks_hz_10_11":"7.12","ks_dxds":"1.98","odds_id":"ca6131bf29d244559558aaab7cdf147a","ssc_dwd":"9.9","sc_dwd":"9.9"}]
     * subSQL :
     * systemTime : 1557316618864
     */

    public int pageNum;
    public int length;
    public int start;
    public String search;
    public String order;
    public String column;
    public String recordsTotal;
    public String recordsFiltered;
    public String draw;
    public String subSQL;
    public long systemTime;
    public List<DataBean> data;

    public static class DataBean {
        /**
         * ks_hz_3_18 : 192.24
         * sc_lhd : 1.98
         * ks_hz_6_15 : 19.22
         * ks_hz_4_17 : 64.08
         * ks_hz_7_14 : 12.81
         * ssc_wxzx : 99,000
         * ks_hz_5_16 : 32.04
         * ssc_hexzx : 49.5
         * ttz_zx : 1.98
         * ttz_ping : 59.74
         * ks_hz_9_12 : 7.68
         * ssc_dxds : 1.98
         * ks_hz_8_13 : 9.15
         * sc_dxds : 1.98
         * odds : 9
         * lunpan : 11.88
         * ks_hz_10_11 : 7.12
         * ks_dxds : 1.98
         * odds_id : ca6131bf29d244559558aaab7cdf147a
         * ssc_dwd : 9.9
         * sc_dwd : 9.9
         */

        public String ks_hz_3_18;
        public String sc_lhd;
        public String ks_hz_6_15;
        public String ks_hz_4_17;
        public String ks_hz_7_14;
        public String ssc_wxzx;
        public String ks_hz_5_16;
        public String ssc_hexzx;
        public String ttz_zx;
        public String ttz_ping;
        public String ks_hz_9_12;
        public String ssc_dxds;
        public String ks_hz_8_13;
        public String sc_dxds;
        public String odds;
        public String lunpan;
        public String ks_hz_10_11;
        public String ks_dxds;
        public String odds_id;
        public String ssc_dwd;
        public String sc_dwd;
    }
}
