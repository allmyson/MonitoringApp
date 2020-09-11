package com.ys.monitor.bean;

import java.util.List;

/**
 * @author lh
 * @version 1.0.0
 * @filename FireBean
 * @description -------------------------------------------------------
 * @date 2020/9/10 18:21
 */
public class FireBean {


    /**
     * data : {"total":11,"rows":[{"recNo":"4028819073e7a7630173e7a95e280000","deviceNo":null,
     * "deviceName":null,"name":"宝塔峰附近疑似起火","areaCode":"500115001015","longitude":"106.382413",
     * "latitude":"29.829427","altitude":"300","warnDesc":"火情登记修改",
     * "source":"40289fa573e690820173e6a6437a0004","sourceName":"电话报警",
     * "warnerPhone":"13858624885","warnPerson":"周林","warnTime":1597319488000,"warnLongitude":"",
     * "warnLatitude":"","warnAltitude":"","status":"4028819073e0c45d0173e0c9c9170006",
     * "statusName":"已扑灭","checkPerson":null,"checkTime":null,"description":null,
     * "imgUrl":"/data/warn/15658bf45377429191ec1d28702ef484/22.jpg;",
     * "videoUrl":"/data/warn/916923d93fc240849f9ba297b9e85758/EA2B0165CBCCCD5163BC462134713919中文
     * .mp4;","agency":"3","smallClassNo":"2","smallPlaceName":"狮子坪黄桷树信息",
     * "investigationAddr":"长寿区八颗街道幸福村","siteSplicing":"长寿区八颗街道幸福村3社2小班狮子坪黄桷树信息",
     * "start_time":null,"end_time":null,"isDelete":"0",
     * "createUserNo":"402880905e31cb26015e31cb5d290000","createTime":1598931792000},{"recNo
     * ":"40289f3c742da2d301742deb548a000d","deviceNo":null,"deviceName":null,"name":"香炉峰附近发现明火",
     * "areaCode":"500109019003","longitude":"106.384674","latitude":"29.839717",
     * "altitude":"220","warnDesc":"树木着火，浓烟大，无被困人员","source":"40289fa573e690820173e6a6437a0004",
     * "sourceName":"电话报警","warnerPhone":"15023224151","warnPerson":"钟传碧",
     * "warnTime":1595606400000,"warnLongitude":"","warnLatitude":"","warnAltitude":"",
     * "status":"4028819073e0c45d0173e0c9ae070005","statusName":"发生火灾","checkPerson":null,
     * "checkTime":null,"description":null,"imgUrl":"","videoUrl":"","agency":"1",
     * "smallClassNo":"","smallPlaceName":"","investigationAddr":"北碚区缙云山管理局九峰保护站",
     * "siteSplicing":"北碚区缙云山管理局九峰保护站1社","start_time":null,"end_time":null,"isDelete":"0",
     * "createUserNo":"402880905e31cb26015e31cb5d290000","createTime":1598931679000},{"recNo
     * ":"40289f3c742da2d301742dec57ea000f","deviceNo":null,"deviceName":null,"name":"聚云峰附近发生火情",
     * "areaCode":"500227001008","longitude":"106.346446","latitude":"29.795275",
     * "altitude":"135","warnDesc":"树木着火，浓烟大，无被困人员","source":"40289fa573e690820173e6a6437a0004",
     * "sourceName":"电话报警","warnerPhone":"15215042103","warnPerson":"梁万芳",
     * "warnTime":1596988800000,"warnLongitude":"","warnLatitude":"","warnAltitude":"",
     * "status":"4028819073e0c45d0173e0c930910001","statusName":"待处理","checkPerson":null,
     * "checkTime":null,"description":null,"imgUrl":"","videoUrl":"","agency":"5",
     * "smallClassNo":"","smallPlaceName":"","investigationAddr":"璧山区八塘镇阳龙村",
     * "siteSplicing":"璧山区八塘镇阳龙村5社","start_time":null,"end_time":null,"isDelete":"0",
     * "createUserNo":"402880905e31cb26015e31cb5d290000","createTime":1598931645000},{"recNo
     * ":"40289f3c742da2d301742dee6a570013","deviceNo":null,"deviceName":null,"name":"莲花峰附近发现明火",
     * "areaCode":"500109012010","longitude":"106.387152","latitude":"29.850294","altitude":"96",
     * "warnDesc":"小范围树木燃烧，无风，蔓延速度小","source":"40289fa573e690820173e6a6437a0004",
     * "sourceName":"电话报警","warnerPhone":"13752840631","warnPerson":"刘志利",
     * "warnTime":1597471688000,"warnLongitude":"","warnLatitude":"","warnAltitude":"",
     * "status":"4028819073e0c45d0173e0c9ae070005","statusName":"发生火灾","checkPerson":null,
     * "checkTime":null,"description":null,"imgUrl":"","videoUrl":"","agency":"3",
     * "smallClassNo":"","smallPlaceName":"","investigationAddr":"北碚区澄江镇北泉村",
     * "siteSplicing":"北碚区澄江镇北泉村3社","start_time":null,"end_time":null,"isDelete":"0",
     * "createUserNo":"402880905e31cb26015e31cb5d290000","createTime":1598931581000},{"recNo
     * ":"40289f3c742da2d301742df001f60015","deviceNo":null,"deviceName":null,"name":"狮子峰附近疑似起火",
     * "areaCode":"500109012009","longitude":"106.37902","latitude":"29.853171","altitude":"",
     * "warnDesc":"浓烟范围大，有风，明火蔓延快","source":"40289fa573e690820173e6a6437a0004",
     * "sourceName":"电话报警","warnerPhone":"15825957508","warnPerson":"李中福",
     * "warnTime":1597025344000,"warnLongitude":"","warnLatitude":"","warnAltitude":"",
     * "status":"4028819073e0c45d0173e0c930910001","statusName":"待处理","checkPerson":null,
     * "checkTime":null,"description":null,"imgUrl":"","videoUrl":"","agency":"14",
     * "smallClassNo":"","smallPlaceName":"","investigationAddr":"北碚区澄江镇运河村",
     * "siteSplicing":"北碚区澄江镇运河村14社","start_time":null,"end_time":null,"isDelete":"0",
     * "createUserNo":"402880905e31cb26015e31cb5d290000","createTime":1598931549000},{"recNo
     * ":"40289f3c742da2d301742df3789d0019","deviceNo":null,"deviceName":null,"name":"夕照峰附近发现明火",
     * "areaCode":"500106007005","longitude":"106.294746","latitude":"29.708882",
     * "altitude":"135","warnDesc":"树木着火，浓烟大，无被困人员","source":"40289fa573e690820173e6a6437a0004",
     * "sourceName":"电话报警","warnerPhone":"13883799538","warnPerson":"张良富",
     * "warnTime":1592450748000,"warnLongitude":"","warnLatitude":"","warnAltitude":"",
     * "status":"4028819073e0c45d0173e0c930910001","statusName":"待处理","checkPerson":null,
     * "checkTime":null,"description":null,"imgUrl":"","videoUrl":"","agency":"1",
     * "smallClassNo":"","smallPlaceName":"","investigationAddr":"沙坪坝区青木关镇四楞碑村",
     * "siteSplicing":"沙坪坝区青木关镇四楞碑村1社","start_time":null,"end_time":null,"isDelete":"0",
     * "createUserNo":"402880905e31cb26015e31cb5d290000","createTime":1598931525000},{"recNo
     * ":"40289f3c742da2d301742df964c20021","deviceNo":null,"deviceName":null,"name":"夕照峰发现明火",
     * "areaCode":"500109019004","longitude":"106.381666999999","latitude":"29.837778",
     * "altitude":"98","warnDesc":"树木着火，浓烟大，无被困人员","source":"40289fa573e690820173e6a6437a0004",
     * "sourceName":"电话报警","warnerPhone":"13618284184","warnPerson":"李世强",
     * "warnTime":1592465464000,"warnLongitude":"","warnLatitude":"","warnAltitude":"",
     * "status":"4028819073e0c45d0173e0c930910001","statusName":"待处理","checkPerson":null,
     * "checkTime":null,"description":null,"imgUrl":"","videoUrl":"","agency":"",
     * "smallClassNo":"","smallPlaceName":"","investigationAddr":"北碚区缙云山管理局缙云保护站",
     * "siteSplicing":"北碚区缙云山管理局缙云保护站","start_time":null,"end_time":null,"isDelete":"0",
     * "createUserNo":"402880905e31cb26015e31cb5d290000","createTime":1598931297000},{"recNo
     * ":"40289f3c742da2d301742e00a9de002b","deviceNo":null,"deviceName":null,"name":"莲花峰发现明火",
     * "areaCode":"500106007001","longitude":"106.282992","latitude":"29.691148",
     * "altitude":"134","warnDesc":"范围树木燃烧，无风，蔓延速度小","source":"40289fa573e690820173e6a6437a0004",
     * "sourceName":"电话报警","warnerPhone":"18323430602","warnPerson":"伍成民",
     * "warnTime":1591405380000,"warnLongitude":"","warnLatitude":"","warnAltitude":"",
     * "status":"4028819073e0c45d0173e0c930910001","statusName":"待处理","checkPerson":null,
     * "checkTime":null,"description":null,"imgUrl":"","videoUrl":"","agency":"1",
     * "smallClassNo":"","smallPlaceName":"","investigationAddr":"沙坪坝区青木关镇关口村",
     * "siteSplicing":"沙坪坝区青木关镇关口村1社","start_time":null,"end_time":null,"isDelete":"0",
     * "createUserNo":"402880905e31cb26015e31cb5d290000","createTime":1598931027000},{"recNo
     * ":"40289f3c742da2d301742de8db020009","deviceNo":null,"deviceName":null,"name":"聚云峰发生火情",
     * "areaCode":"500109018001","longitude":"106.365303","latitude":"29.809426",
     * "altitude":"180","warnDesc":"小范围树木燃烧，无风，蔓延速度小",
     * "source":"40289fa573e690820173e6a6437a0004","sourceName":"电话报警",
     * "warnerPhone":"13983281567","warnPerson":"王志勇","warnTime":1598923642000,
     * "warnLongitude":"","warnLatitude":"","warnAltitude":"",
     * "status":"4028819073e0c45d0173e0c9ae070005","statusName":"发生火灾","checkPerson":null,
     * "checkTime":null,"description":null,"imgUrl":"","videoUrl":"","agency":"0",
     * "smallClassNo":"","smallPlaceName":"","investigationAddr":"北碚区歇马镇农荣村",
     * "siteSplicing":"北碚区歇马镇农荣村0社","start_time":null,"end_time":null,"isDelete":"0",
     * "createUserNo":"402880905e31cb26015e31cb5d290000","createTime":1598930925000},{"recNo
     * ":"40289f3c742da2d301742de269540003","deviceNo":null,"deviceName":null,"name":"香炉峰发现明火",
     * "areaCode":"500109018007","longitude":"106.304395","latitude":"29.745499",
     * "altitude":"156","warnDesc":"小范围树木燃烧，无风，蔓延速度小",
     * "source":"40289fa573e690820173e6a6437a0004","sourceName":"电话报警",
     * "warnerPhone":"13594657926","warnPerson":"王嘉良","warnTime":1598929643000,
     * "warnLongitude":"","warnLatitude":"","warnAltitude":"",
     * "status":"4028819073e0c45d0173e0c96f850003","statusName":"误报","checkPerson":null,
     * "checkTime":null,"description":null,"imgUrl":"","videoUrl":"","agency":"5",
     * "smallClassNo":"","smallPlaceName":"","investigationAddr":"北碚区歇马镇虎头村",
     * "siteSplicing":"北碚区歇马镇虎头村5社","start_time":null,"end_time":null,"isDelete":"0",
     * "createUserNo":"402880905e31cb26015e31cb5d290000","createTime":1598930849000},{"recNo
     * ":"40289f3c742da2d301742df10b3d0017","deviceNo":null,"deviceName":null,"name":"狮子峰疑似起火",
     * "areaCode":"500227003016","longitude":"106.287561","latitude":"29.715446",
     * "altitude":"186","warnDesc":"树木着火，浓烟大，无被困人员","source":"40289fa573e690820173e6a6437a0004",
     * "sourceName":"电话报警","warnerPhone":"15922940706","warnPerson":"李兴福",
     * "warnTime":1599016999000,"warnLongitude":"","warnLatitude":"","warnAltitude":"",
     * "status":"4028819073e0c45d0173e0c930910001","statusName":"待处理","checkPerson":null,
     * "checkTime":null,"description":null,"imgUrl":"","videoUrl":"","agency":"3",
     * "smallClassNo":"","smallPlaceName":"","investigationAddr":"璧山区大路街道办龙泉村",
     * "siteSplicing":"璧山区大路街道办龙泉村3社","start_time":null,"end_time":null,"isDelete":"0",
     * "createUserNo":"402880905e31cb26015e31cb5d290000","createTime":1598930645000}],"footer":null}
     * code : 200
     * msg : 火情数据获取成功!
     */

    public DataBean data;
    public String code;
    public String msg;

    public static class DataBean {
        /**
         * total : 11
         * rows : [{"recNo":"4028819073e7a7630173e7a95e280000","deviceNo":null,"deviceName":null,
         * "name":"宝塔峰附近疑似起火","areaCode":"500115001015","longitude":"106.382413","latitude":"29
         * .829427","altitude":"300","warnDesc":"火情登记修改",
         * "source":"40289fa573e690820173e6a6437a0004","sourceName":"电话报警",
         * "warnerPhone":"13858624885","warnPerson":"周林","warnTime":1597319488000,
         * "warnLongitude":"","warnLatitude":"","warnAltitude":"",
         * "status":"4028819073e0c45d0173e0c9c9170006","statusName":"已扑灭","checkPerson":null,
         * "checkTime":null,"description":null,
         * "imgUrl":"/data/warn/15658bf45377429191ec1d28702ef484/22.jpg;",
         * "videoUrl":"/data/warn/916923d93fc240849f9ba297b9e85758
         * /EA2B0165CBCCCD5163BC462134713919中文.mp4;","agency":"3","smallClassNo":"2",
         * "smallPlaceName":"狮子坪黄桷树信息","investigationAddr":"长寿区八颗街道幸福村",
         * "siteSplicing":"长寿区八颗街道幸福村3社2小班狮子坪黄桷树信息","start_time":null,"end_time":null,
         * "isDelete":"0","createUserNo":"402880905e31cb26015e31cb5d290000",
         * "createTime":1598931792000},{"recNo":"40289f3c742da2d301742deb548a000d",
         * "deviceNo":null,"deviceName":null,"name":"香炉峰附近发现明火","areaCode":"500109019003",
         * "longitude":"106.384674","latitude":"29.839717","altitude":"220",
         * "warnDesc":"树木着火，浓烟大，无被困人员","source":"40289fa573e690820173e6a6437a0004",
         * "sourceName":"电话报警","warnerPhone":"15023224151","warnPerson":"钟传碧",
         * "warnTime":1595606400000,"warnLongitude":"","warnLatitude":"","warnAltitude":"",
         * "status":"4028819073e0c45d0173e0c9ae070005","statusName":"发生火灾","checkPerson":null,
         * "checkTime":null,"description":null,"imgUrl":"","videoUrl":"","agency":"1",
         * "smallClassNo":"","smallPlaceName":"","investigationAddr":"北碚区缙云山管理局九峰保护站",
         * "siteSplicing":"北碚区缙云山管理局九峰保护站1社","start_time":null,"end_time":null,"isDelete":"0",
         * "createUserNo":"402880905e31cb26015e31cb5d290000","createTime":1598931679000},{"recNo
         * ":"40289f3c742da2d301742dec57ea000f","deviceNo":null,"deviceName":null,
         * "name":"聚云峰附近发生火情","areaCode":"500227001008","longitude":"106.346446","latitude":"29
         * .795275","altitude":"135","warnDesc":"树木着火，浓烟大，无被困人员",
         * "source":"40289fa573e690820173e6a6437a0004","sourceName":"电话报警",
         * "warnerPhone":"15215042103","warnPerson":"梁万芳","warnTime":1596988800000,
         * "warnLongitude":"","warnLatitude":"","warnAltitude":"",
         * "status":"4028819073e0c45d0173e0c930910001","statusName":"待处理","checkPerson":null,
         * "checkTime":null,"description":null,"imgUrl":"","videoUrl":"","agency":"5",
         * "smallClassNo":"","smallPlaceName":"","investigationAddr":"璧山区八塘镇阳龙村",
         * "siteSplicing":"璧山区八塘镇阳龙村5社","start_time":null,"end_time":null,"isDelete":"0",
         * "createUserNo":"402880905e31cb26015e31cb5d290000","createTime":1598931645000},{"recNo
         * ":"40289f3c742da2d301742dee6a570013","deviceNo":null,"deviceName":null,
         * "name":"莲花峰附近发现明火","areaCode":"500109012010","longitude":"106.387152","latitude":"29
         * .850294","altitude":"96","warnDesc":"小范围树木燃烧，无风，蔓延速度小",
         * "source":"40289fa573e690820173e6a6437a0004","sourceName":"电话报警",
         * "warnerPhone":"13752840631","warnPerson":"刘志利","warnTime":1597471688000,
         * "warnLongitude":"","warnLatitude":"","warnAltitude":"",
         * "status":"4028819073e0c45d0173e0c9ae070005","statusName":"发生火灾","checkPerson":null,
         * "checkTime":null,"description":null,"imgUrl":"","videoUrl":"","agency":"3",
         * "smallClassNo":"","smallPlaceName":"","investigationAddr":"北碚区澄江镇北泉村",
         * "siteSplicing":"北碚区澄江镇北泉村3社","start_time":null,"end_time":null,"isDelete":"0",
         * "createUserNo":"402880905e31cb26015e31cb5d290000","createTime":1598931581000},{"recNo
         * ":"40289f3c742da2d301742df001f60015","deviceNo":null,"deviceName":null,
         * "name":"狮子峰附近疑似起火","areaCode":"500109012009","longitude":"106.37902","latitude":"29
         * .853171","altitude":"","warnDesc":"浓烟范围大，有风，明火蔓延快",
         * "source":"40289fa573e690820173e6a6437a0004","sourceName":"电话报警",
         * "warnerPhone":"15825957508","warnPerson":"李中福","warnTime":1597025344000,
         * "warnLongitude":"","warnLatitude":"","warnAltitude":"",
         * "status":"4028819073e0c45d0173e0c930910001","statusName":"待处理","checkPerson":null,
         * "checkTime":null,"description":null,"imgUrl":"","videoUrl":"","agency":"14",
         * "smallClassNo":"","smallPlaceName":"","investigationAddr":"北碚区澄江镇运河村",
         * "siteSplicing":"北碚区澄江镇运河村14社","start_time":null,"end_time":null,"isDelete":"0",
         * "createUserNo":"402880905e31cb26015e31cb5d290000","createTime":1598931549000},{"recNo
         * ":"40289f3c742da2d301742df3789d0019","deviceNo":null,"deviceName":null,
         * "name":"夕照峰附近发现明火","areaCode":"500106007005","longitude":"106.294746","latitude":"29
         * .708882","altitude":"135","warnDesc":"树木着火，浓烟大，无被困人员",
         * "source":"40289fa573e690820173e6a6437a0004","sourceName":"电话报警",
         * "warnerPhone":"13883799538","warnPerson":"张良富","warnTime":1592450748000,
         * "warnLongitude":"","warnLatitude":"","warnAltitude":"",
         * "status":"4028819073e0c45d0173e0c930910001","statusName":"待处理","checkPerson":null,
         * "checkTime":null,"description":null,"imgUrl":"","videoUrl":"","agency":"1",
         * "smallClassNo":"","smallPlaceName":"","investigationAddr":"沙坪坝区青木关镇四楞碑村",
         * "siteSplicing":"沙坪坝区青木关镇四楞碑村1社","start_time":null,"end_time":null,"isDelete":"0",
         * "createUserNo":"402880905e31cb26015e31cb5d290000","createTime":1598931525000},{"recNo
         * ":"40289f3c742da2d301742df964c20021","deviceNo":null,"deviceName":null,
         * "name":"夕照峰发现明火","areaCode":"500109019004","longitude":"106.381666999999",
         * "latitude":"29.837778","altitude":"98","warnDesc":"树木着火，浓烟大，无被困人员",
         * "source":"40289fa573e690820173e6a6437a0004","sourceName":"电话报警",
         * "warnerPhone":"13618284184","warnPerson":"李世强","warnTime":1592465464000,
         * "warnLongitude":"","warnLatitude":"","warnAltitude":"",
         * "status":"4028819073e0c45d0173e0c930910001","statusName":"待处理","checkPerson":null,
         * "checkTime":null,"description":null,"imgUrl":"","videoUrl":"","agency":"",
         * "smallClassNo":"","smallPlaceName":"","investigationAddr":"北碚区缙云山管理局缙云保护站",
         * "siteSplicing":"北碚区缙云山管理局缙云保护站","start_time":null,"end_time":null,"isDelete":"0",
         * "createUserNo":"402880905e31cb26015e31cb5d290000","createTime":1598931297000},{"recNo
         * ":"40289f3c742da2d301742e00a9de002b","deviceNo":null,"deviceName":null,
         * "name":"莲花峰发现明火","areaCode":"500106007001","longitude":"106.282992","latitude":"29
         * .691148","altitude":"134","warnDesc":"范围树木燃烧，无风，蔓延速度小",
         * "source":"40289fa573e690820173e6a6437a0004","sourceName":"电话报警",
         * "warnerPhone":"18323430602","warnPerson":"伍成民","warnTime":1591405380000,
         * "warnLongitude":"","warnLatitude":"","warnAltitude":"",
         * "status":"4028819073e0c45d0173e0c930910001","statusName":"待处理","checkPerson":null,
         * "checkTime":null,"description":null,"imgUrl":"","videoUrl":"","agency":"1",
         * "smallClassNo":"","smallPlaceName":"","investigationAddr":"沙坪坝区青木关镇关口村",
         * "siteSplicing":"沙坪坝区青木关镇关口村1社","start_time":null,"end_time":null,"isDelete":"0",
         * "createUserNo":"402880905e31cb26015e31cb5d290000","createTime":1598931027000},{"recNo
         * ":"40289f3c742da2d301742de8db020009","deviceNo":null,"deviceName":null,
         * "name":"聚云峰发生火情","areaCode":"500109018001","longitude":"106.365303","latitude":"29
         * .809426","altitude":"180","warnDesc":"小范围树木燃烧，无风，蔓延速度小",
         * "source":"40289fa573e690820173e6a6437a0004","sourceName":"电话报警",
         * "warnerPhone":"13983281567","warnPerson":"王志勇","warnTime":1598923642000,
         * "warnLongitude":"","warnLatitude":"","warnAltitude":"",
         * "status":"4028819073e0c45d0173e0c9ae070005","statusName":"发生火灾","checkPerson":null,
         * "checkTime":null,"description":null,"imgUrl":"","videoUrl":"","agency":"0",
         * "smallClassNo":"","smallPlaceName":"","investigationAddr":"北碚区歇马镇农荣村",
         * "siteSplicing":"北碚区歇马镇农荣村0社","start_time":null,"end_time":null,"isDelete":"0",
         * "createUserNo":"402880905e31cb26015e31cb5d290000","createTime":1598930925000},{"recNo
         * ":"40289f3c742da2d301742de269540003","deviceNo":null,"deviceName":null,
         * "name":"香炉峰发现明火","areaCode":"500109018007","longitude":"106.304395","latitude":"29
         * .745499","altitude":"156","warnDesc":"小范围树木燃烧，无风，蔓延速度小",
         * "source":"40289fa573e690820173e6a6437a0004","sourceName":"电话报警",
         * "warnerPhone":"13594657926","warnPerson":"王嘉良","warnTime":1598929643000,
         * "warnLongitude":"","warnLatitude":"","warnAltitude":"",
         * "status":"4028819073e0c45d0173e0c96f850003","statusName":"误报","checkPerson":null,
         * "checkTime":null,"description":null,"imgUrl":"","videoUrl":"","agency":"5",
         * "smallClassNo":"","smallPlaceName":"","investigationAddr":"北碚区歇马镇虎头村",
         * "siteSplicing":"北碚区歇马镇虎头村5社","start_time":null,"end_time":null,"isDelete":"0",
         * "createUserNo":"402880905e31cb26015e31cb5d290000","createTime":1598930849000},{"recNo
         * ":"40289f3c742da2d301742df10b3d0017","deviceNo":null,"deviceName":null,
         * "name":"狮子峰疑似起火","areaCode":"500227003016","longitude":"106.287561","latitude":"29
         * .715446","altitude":"186","warnDesc":"树木着火，浓烟大，无被困人员",
         * "source":"40289fa573e690820173e6a6437a0004","sourceName":"电话报警",
         * "warnerPhone":"15922940706","warnPerson":"李兴福","warnTime":1599016999000,
         * "warnLongitude":"","warnLatitude":"","warnAltitude":"",
         * "status":"4028819073e0c45d0173e0c930910001","statusName":"待处理","checkPerson":null,
         * "checkTime":null,"description":null,"imgUrl":"","videoUrl":"","agency":"3",
         * "smallClassNo":"","smallPlaceName":"","investigationAddr":"璧山区大路街道办龙泉村",
         * "siteSplicing":"璧山区大路街道办龙泉村3社","start_time":null,"end_time":null,"isDelete":"0",
         * "createUserNo":"402880905e31cb26015e31cb5d290000","createTime":1598930645000}]
         * footer : null
         */

        public int total;
        public Object footer;
        public List<RowsBean> rows;

        public static class RowsBean {
            /**
             * recNo : 4028819073e7a7630173e7a95e280000
             * deviceNo : null
             * deviceName : null
             * name : 宝塔峰附近疑似起火
             * areaCode : 500115001015
             * longitude : 106.382413
             * latitude : 29.829427
             * altitude : 300
             * warnDesc : 火情登记修改
             * source : 40289fa573e690820173e6a6437a0004
             * sourceName : 电话报警
             * warnerPhone : 13858624885
             * warnPerson : 周林
             * warnTime : 1597319488000
             * warnLongitude :
             * warnLatitude :
             * warnAltitude :
             * status : 4028819073e0c45d0173e0c9c9170006
             * statusName : 已扑灭
             * checkPerson : null
             * checkTime : null
             * description : null
             * imgUrl : /data/warn/15658bf45377429191ec1d28702ef484/22.jpg;
             * videoUrl : /data/warn/916923d93fc240849f9ba297b9e85758/EA2B0165CBCCCD5163BC462134713919中文.mp4;
             * agency : 3
             * smallClassNo : 2
             * smallPlaceName : 狮子坪黄桷树信息
             * investigationAddr : 长寿区八颗街道幸福村
             * siteSplicing : 长寿区八颗街道幸福村3社2小班狮子坪黄桷树信息
             * start_time : null
             * end_time : null
             * isDelete : 0
             * createUserNo : 402880905e31cb26015e31cb5d290000
             * createTime : 1598931792000
             */

            public String recNo;
            public String deviceNo;
            public String deviceName;
            public String name;
            public String areaCode;
            public String longitude;
            public String latitude;
            public String altitude;
            public String warnDesc;
            public String source;
            public String sourceName;
            public String warnerPhone;
            public String warnPerson;
            public long warnTime;
            public String warnLongitude;
            public String warnLatitude;
            public String warnAltitude;
            public String status;
            public String statusName;
            public String checkPerson;
            public Object checkTime;
            public String description;
            public String imgUrl;
            public String videoUrl;
            public String agency;
            public String smallClassNo;
            public String smallPlaceName;
            public String investigationAddr;
            public String siteSplicing;
            public Object start_time;
            public Object end_time;
            public String isDelete;
            public String createUserNo;
            public long createTime;
        }
    }
}
