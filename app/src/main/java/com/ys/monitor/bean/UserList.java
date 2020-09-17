package com.ys.monitor.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lh
 * @version 1.0.0
 * @filename UserList
 * @description -------------------------------------------------------
 * @date 2020/9/17 10:59
 */
public class UserList {

    /**
     * data : {"total":7,"rows":[{"s_recNo":"402880905e31cb26015e31cb5d290000",
     * "trueName":"admin","userName":"admin","password":null,
     * "userPassword":"H1AF2G39C90F59F00H5DHA574BA4EE3H","nation":null,"sex":null,"dutyNo":"1
     * ","dutyName":null,"positional":null,"mobilePhoneNumber":null,"signUrl":null,
     * "uniqueNo":null,"icon":null,"userDesc":"","orderNo":0,"checkMode":null,
     * "createTime":1504073964837,"createUserNo":null,"userStatus":1,
     * "lastLoginTime":1600309308723,"lastLoginIp":"0:0:0:0:0:0:0:1","deptNo":"1
     * ","deptName":"缙云山","email":null,"powerIP":null,"isSoftPhone":"0","softPhoneId":null,
     * "longitude":null,"latitude":null,"update_TIME":null,"update_USER":null,"description":null,
     * "imei":null},{"s_recNo":"4028819073b7fbe00173b811ba4c000b","trueName":"张云",
     * "userName":"zhangyun","password":"123456",
     * "userPassword":"H1AF2G39C90F59F00H5DHA574BA4EE3H","nation":null,"sex":"0",
     * "dutyNo":"402880905e325db3015e328079e1000b","dutyName":"副主任","positional":null,
     * "mobilePhoneNumber":"","signUrl":null,"uniqueNo":null,"icon":null,"userDesc":"",
     * "orderNo":0,"checkMode":null,"createTime":1596521036363,
     * "createUserNo":"402880905e31cb26015e31cb5d290000","userStatus":1,"lastLoginTime":null,
     * "lastLoginIp":"x.x.x.x","deptNo":"2                               ","deptName":"研发部",
     * "email":"","powerIP":null,"isSoftPhone":"1","softPhoneId":"1111031","longitude":"106
     * .552449","latitude":"29.605394","update_TIME":null,"update_USER":null,"description":null,
     * "imei":null},{"s_recNo":"40288ab865800bcf016580121fe10000","trueName":"李秋水",
     * "userName":"liqiushui","password":null,"userPassword":"5BGD9H3F57331AE1EB3331GCHD9234BH",
     * "nation":null,"sex":"0","dutyNo":"402880945e73f089015e73f35c230001","dutyName":"主任",
     * "positional":null,"mobilePhoneNumber":"","signUrl":null,"uniqueNo":null,"icon":null,
     * "userDesc":"","orderNo":1,"checkMode":null,"createTime":1541644134910,
     * "createUserNo":"402880905e31cb26015e31cb5d290000","userStatus":1,"lastLoginTime":null,
     * "lastLoginIp":"x.x.x.x","deptNo":"1                               ","deptName":"缙云山",
     * "email":"","powerIP":null,"isSoftPhone":"1","softPhoneId":"","longitude":"106.528242",
     * "latitude":"29.609965","update_TIME":null,"update_USER":null,"description":null,
     * "imei":null},{"s_recNo":"40288ab865800bcf016580121fe10006","trueName":"范博文",
     * "userName":"fanbowen","password":null,"userPassword":"1FDEF7E2GG75532HBGD3CE00C457453G",
     * "nation":null,"sex":null,"dutyNo":"                                ","dutyName":null,
     * "positional":null,"mobilePhoneNumber":"54545","signUrl":null,"uniqueNo":null,"icon":null,
     * "userDesc":"444","orderNo":6,"checkMode":null,"createTime":null,"createUserNo":null,
     * "userStatus":1,"lastLoginTime":null,"lastLoginIp":null,"deptNo":"1
     * ","deptName":"缙云山","email":"123","powerIP":null,"isSoftPhone":"0","softPhoneId":null,
     * "longitude":null,"latitude":null,"update_TIME":null,"update_USER":null,"description":null,
     * "imei":null},{"s_recNo":"40288ab865800bcf016580121fe10007","trueName":"江澈",
     * "userName":"jiangche","password":null,"userPassword":"H0D093E97C5B93H4HEC4504BBC94AG71",
     * "nation":null,"sex":null,"dutyNo":"                                ","dutyName":null,
     * "positional":null,"mobilePhoneNumber":"15223570680","signUrl":null,"uniqueNo":null,
     * "icon":null,"userDesc":"55555备注","orderNo":7,"checkMode":null,"createTime":null,
     * "createUserNo":null,"userStatus":1,"lastLoginTime":null,"lastLoginIp":null,"deptNo":"2
     * ","deptName":"研发部","email":"1148236628@qq.com","powerIP":null,"isSoftPhone":"0",
     * "softPhoneId":null,"longitude":null,"latitude":null,"update_TIME":null,"update_USER":null,
     * "description":null,"imei":null},{"s_recNo":"40288ab865800bcf016580121fe10008",
     * "trueName":"周文","userName":"zhouwen","password":null,
     * "userPassword":"H1AF2G39C90F59F00H5DHA574BA4EE3H","nation":null,"sex":null,
     * "dutyNo":"402880905e325db3015e328079e1000b","dutyName":"副主任","positional":null,
     * "mobilePhoneNumber":"15223570680","signUrl":null,"uniqueNo":null,"icon":null,
     * "userDesc":"6666备注","orderNo":8,"checkMode":null,"createTime":null,"createUserNo":null,
     * "userStatus":1,"lastLoginTime":1600307005237,"lastLoginIp":"125.82.187.153","deptNo":"1
     * ","deptName":"缙云山","email":"1148236628@qq.com","powerIP":null,"isSoftPhone":"0",
     * "softPhoneId":null,"longitude":null,"latitude":null,"update_TIME":null,"update_USER":null,
     * "description":null,"imei":"null"},{"s_recNo":"40289fa5745ce5aa01745ce871f20009",
     * "trueName":"APP测试用户","userName":"ceshi","password":"123456",
     * "userPassword":"H1AF2G39C90F59F00H5DHA574BA4EE3H","nation":null,"sex":"0",
     * "dutyNo":"402880945e73f089015e73f35c230001","dutyName":"主任","positional":null,
     * "mobilePhoneNumber":"","signUrl":null,"uniqueNo":null,"icon":null,"userDesc":"",
     * "orderNo":0,"checkMode":null,"createTime":1599286571507,
     * "createUserNo":"402880905e31cb26015e31cb5d290000","userStatus":1,
     * "lastLoginTime":1600255070037,"lastLoginIp":"0:0:0:0:0:0:0:1",
     * "deptNo":"4028819073c1d2ff0173c265b8e10005","deptName":"黄桷树","email":"","powerIP":null,
     * "isSoftPhone":null,"softPhoneId":"1111031","longitude":null,"latitude":null,
     * "update_TIME":null,"update_USER":null,"description":null,"imei":null}],"footer":null}
     * code : 200
     * msg : 通讯录获取成功!
     */

    public DataBean data;
    public String code;
    public String msg;

    public static class DataBean {
        /**
         * total : 7
         * rows : [{"s_recNo":"402880905e31cb26015e31cb5d290000","trueName":"admin",
         * "userName":"admin","password":null,"userPassword":"H1AF2G39C90F59F00H5DHA574BA4EE3H",
         * "nation":null,"sex":null,"dutyNo":"1                               ","dutyName":null,
         * "positional":null,"mobilePhoneNumber":null,"signUrl":null,"uniqueNo":null,"icon":null,
         * "userDesc":"","orderNo":0,"checkMode":null,"createTime":1504073964837,
         * "createUserNo":null,"userStatus":1,"lastLoginTime":1600309308723,
         * "lastLoginIp":"0:0:0:0:0:0:0:1","deptNo":"1                               ",
         * "deptName":"缙云山","email":null,"powerIP":null,"isSoftPhone":"0","softPhoneId":null,
         * "longitude":null,"latitude":null,"update_TIME":null,"update_USER":null,
         * "description":null,"imei":null},{"s_recNo":"4028819073b7fbe00173b811ba4c000b",
         * "trueName":"张云","userName":"zhangyun","password":"123456",
         * "userPassword":"H1AF2G39C90F59F00H5DHA574BA4EE3H","nation":null,"sex":"0",
         * "dutyNo":"402880905e325db3015e328079e1000b","dutyName":"副主任","positional":null,
         * "mobilePhoneNumber":"","signUrl":null,"uniqueNo":null,"icon":null,"userDesc":"",
         * "orderNo":0,"checkMode":null,"createTime":1596521036363,
         * "createUserNo":"402880905e31cb26015e31cb5d290000","userStatus":1,"lastLoginTime":null,
         * "lastLoginIp":"x.x.x.x","deptNo":"2                               ","deptName":"研发部",
         * "email":"","powerIP":null,"isSoftPhone":"1","softPhoneId":"1111031","longitude":"106
         * .552449","latitude":"29.605394","update_TIME":null,"update_USER":null,
         * "description":null,"imei":null},{"s_recNo":"40288ab865800bcf016580121fe10000",
         * "trueName":"李秋水","userName":"liqiushui","password":null,
         * "userPassword":"5BGD9H3F57331AE1EB3331GCHD9234BH","nation":null,"sex":"0",
         * "dutyNo":"402880945e73f089015e73f35c230001","dutyName":"主任","positional":null,
         * "mobilePhoneNumber":"","signUrl":null,"uniqueNo":null,"icon":null,"userDesc":"",
         * "orderNo":1,"checkMode":null,"createTime":1541644134910,
         * "createUserNo":"402880905e31cb26015e31cb5d290000","userStatus":1,"lastLoginTime":null,
         * "lastLoginIp":"x.x.x.x","deptNo":"1                               ","deptName":"缙云山",
         * "email":"","powerIP":null,"isSoftPhone":"1","softPhoneId":"","longitude":"106.528242",
         * "latitude":"29.609965","update_TIME":null,"update_USER":null,"description":null,
         * "imei":null},{"s_recNo":"40288ab865800bcf016580121fe10006","trueName":"范博文",
         * "userName":"fanbowen","password":null,
         * "userPassword":"1FDEF7E2GG75532HBGD3CE00C457453G","nation":null,"sex":null,"dutyNo":"
         * ","dutyName":null,"positional":null,"mobilePhoneNumber":"54545","signUrl":null,
         * "uniqueNo":null,"icon":null,"userDesc":"444","orderNo":6,"checkMode":null,
         * "createTime":null,"createUserNo":null,"userStatus":1,"lastLoginTime":null,
         * "lastLoginIp":null,"deptNo":"1                               ","deptName":"缙云山",
         * "email":"123","powerIP":null,"isSoftPhone":"0","softPhoneId":null,"longitude":null,
         * "latitude":null,"update_TIME":null,"update_USER":null,"description":null,"imei":null},
         * {"s_recNo":"40288ab865800bcf016580121fe10007","trueName":"江澈","userName":"jiangche",
         * "password":null,"userPassword":"H0D093E97C5B93H4HEC4504BBC94AG71","nation":null,
         * "sex":null,"dutyNo":"                                ","dutyName":null,
         * "positional":null,"mobilePhoneNumber":"15223570680","signUrl":null,"uniqueNo":null,
         * "icon":null,"userDesc":"55555备注","orderNo":7,"checkMode":null,"createTime":null,
         * "createUserNo":null,"userStatus":1,"lastLoginTime":null,"lastLoginIp":null,"deptNo":"2
         * ","deptName":"研发部","email":"1148236628@qq.com","powerIP":null,"isSoftPhone":"0",
         * "softPhoneId":null,"longitude":null,"latitude":null,"update_TIME":null,
         * "update_USER":null,"description":null,"imei":null},{"s_recNo
         * ":"40288ab865800bcf016580121fe10008","trueName":"周文","userName":"zhouwen",
         * "password":null,"userPassword":"H1AF2G39C90F59F00H5DHA574BA4EE3H","nation":null,
         * "sex":null,"dutyNo":"402880905e325db3015e328079e1000b","dutyName":"副主任",
         * "positional":null,"mobilePhoneNumber":"15223570680","signUrl":null,"uniqueNo":null,
         * "icon":null,"userDesc":"6666备注","orderNo":8,"checkMode":null,"createTime":null,
         * "createUserNo":null,"userStatus":1,"lastLoginTime":1600307005237,"lastLoginIp":"125.82
         * .187.153","deptNo":"1                               ","deptName":"缙云山",
         * "email":"1148236628@qq.com","powerIP":null,"isSoftPhone":"0","softPhoneId":null,
         * "longitude":null,"latitude":null,"update_TIME":null,"update_USER":null,
         * "description":null,"imei":"null"},{"s_recNo":"40289fa5745ce5aa01745ce871f20009",
         * "trueName":"APP测试用户","userName":"ceshi","password":"123456",
         * "userPassword":"H1AF2G39C90F59F00H5DHA574BA4EE3H","nation":null,"sex":"0",
         * "dutyNo":"402880945e73f089015e73f35c230001","dutyName":"主任","positional":null,
         * "mobilePhoneNumber":"","signUrl":null,"uniqueNo":null,"icon":null,"userDesc":"",
         * "orderNo":0,"checkMode":null,"createTime":1599286571507,
         * "createUserNo":"402880905e31cb26015e31cb5d290000","userStatus":1,
         * "lastLoginTime":1600255070037,"lastLoginIp":"0:0:0:0:0:0:0:1",
         * "deptNo":"4028819073c1d2ff0173c265b8e10005","deptName":"黄桷树","email":"",
         * "powerIP":null,"isSoftPhone":null,"softPhoneId":"1111031","longitude":null,
         * "latitude":null,"update_TIME":null,"update_USER":null,"description":null,"imei":null}]
         * footer : null
         */

        public int total;
        public Object footer;
        public List<RowsBean> rows;

        public static class RowsBean {
            /**
             * s_recNo : 402880905e31cb26015e31cb5d290000
             * trueName : admin
             * userName : admin
             * password : null
             * userPassword : H1AF2G39C90F59F00H5DHA574BA4EE3H
             * nation : null
             * sex : null
             * dutyNo : 1
             * dutyName : null
             * positional : null
             * mobilePhoneNumber : null
             * signUrl : null
             * uniqueNo : null
             * icon : null
             * userDesc :
             * orderNo : 0
             * checkMode : null
             * createTime : 1504073964837
             * createUserNo : null
             * userStatus : 1
             * lastLoginTime : 1600309308723
             * lastLoginIp : 0:0:0:0:0:0:0:1
             * deptNo : 1
             * deptName : 缙云山
             * email : null
             * powerIP : null
             * isSoftPhone : 0
             * softPhoneId : null
             * longitude : null
             * latitude : null
             * update_TIME : null
             * update_USER : null
             * description : null
             * imei : null
             */

            public String s_recNo;
            public String trueName;
            public String userName;
            public String password;
            public String userPassword;
            public String nation;
            public Object sex;
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
            public Object update_TIME;
            public Object update_USER;
            public String description;
            public String imei;
        }
    }

    public static Map<String, List<UserList.DataBean.RowsBean>> groupList(List<UserList.DataBean.RowsBean> students) {
        Map<String, List<UserList.DataBean.RowsBean>> map = new HashMap<>();
        for (UserList.DataBean.RowsBean student : students) {
            List<UserList.DataBean.RowsBean> tmpList = map.get(student.deptNo);
            if (tmpList == null) {
                tmpList = new ArrayList<>();
                tmpList.add(student);
                map.put(student.deptNo, tmpList);
            } else {
                tmpList.add(student);
            }
        }
        return map;
    }
}
