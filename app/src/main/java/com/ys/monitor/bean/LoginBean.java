package com.ys.monitor.bean;

/**
 * @author lh
 * @version 1.0.0
 * @filename LoginBean
 * @description -------------------------------------------------------
 * @date 2018/12/3 11:11
 */
public class LoginBean {


    /**
     * data : {"s_recNo":"40289fa5745ce5aa01745ce871f20009","trueName":"APP测试用户",
     * "userName":"ceshi","password":"123456","userPassword":"H1AF2G39C90F59F00H5DHA574BA4EE3H",
     * "nation":null,"sex":null,"dutyNo":"402880945e73f089015e73f35c230001","dutyName":null,
     * "positional":null,"mobilePhoneNumber":"","signUrl":null,"uniqueNo":null,"icon":null,
     * "userDesc":"","orderNo":0,"checkMode":null,"createTime":1599286571507,
     * "createUserNo":"402880905e31cb26015e31cb5d290000","userStatus":1,
     * "lastLoginTime":1599468423251,"lastLoginIp":"222.180.198.138",
     * "deptNo":"4028819073c1d2ff0173c265b8e10005","deptName":null,"email":"","powerIP":null,
     * "isSoftPhone":null,"softPhoneId":null,"longitude":null,"latitude":null,"update_TIME":null,
     * "update_USER":null,"description":null,"imei":"864141033499622"}
     * code : 200
     * msg : 登录成功！
     */

    public DataBean data;
    public String code;
    public String msg;

    public static class DataBean {
        /**
         * s_recNo : 40289fa5745ce5aa01745ce871f20009
         * trueName : APP测试用户
         * userName : ceshi
         * password : 123456
         * userPassword : H1AF2G39C90F59F00H5DHA574BA4EE3H
         * nation : null
         * sex : null
         * dutyNo : 402880945e73f089015e73f35c230001
         * dutyName : null
         * positional : null
         * mobilePhoneNumber :
         * signUrl : null
         * uniqueNo : null
         * icon : null
         * userDesc :
         * orderNo : 0
         * checkMode : null
         * createTime : 1599286571507
         * createUserNo : 402880905e31cb26015e31cb5d290000
         * userStatus : 1
         * lastLoginTime : 1599468423251
         * lastLoginIp : 222.180.198.138
         * deptNo : 4028819073c1d2ff0173c265b8e10005
         * deptName : null
         * email :
         * powerIP : null
         * isSoftPhone : null
         * softPhoneId : null
         * longitude : null
         * latitude : null
         * update_TIME : null
         * update_USER : null
         * description : null
         * imei : 864141033499622
         */

        public String s_recNo;
        public String trueName;
        public String userName;
        public String password;
        public String userPassword;
        public String nation;
        public String sex;
        public String dutyNo;
        public String dutyName;
        public String positional;
        public String mobilePhoneNumber;
        public String signUrl;
        public String uniqueNo;
        public String icon;
        public String userDesc;
        public int orderNo;
        public String checkMode;
        public long createTime;
        public String createUserNo;
        public int userStatus;
        public long lastLoginTime;
        public String lastLoginIp;
        public String deptNo;
        public String deptName;
        public String email;
        public String powerIP;
        public String isSoftPhone;
        public String softPhoneId;
        public String longitude;
        public String latitude;
        public String update_TIME;
        public String update_USER;
        public String description;
        public String imei;
    }
}
