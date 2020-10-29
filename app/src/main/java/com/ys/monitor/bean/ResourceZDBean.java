package com.ys.monitor.bean;

import java.util.List;

public class ResourceZDBean {


    /**
     * data : {"BaseElementExt":[{"recNo":"5b198d6174812e3401748550b16b0002","dataName":"age",
     * "datatype":"int","datatypeName":null,"dataLen":null,"dataExt":null,"name":"树龄",
     * "elementNo":"40289fba74673c6b01746753f720003b",
     * "createUserNo":"402880905e31cb26015e31cb5d290000","createTime":1599964492000,
     * "updateUserNo":"402880905e31cb26015e31cb5d290000","updateTime":1599964492000,
     * "description":""},{"recNo":"5b198d6174ae96b90174af4d743b000a","dataName":"number",
     * "datatype":"varchar","datatypeName":null,"dataLen":50,"dataExt":null,"name":"编号",
     * "elementNo":"40289fba74673c6b01746753f720003b",
     * "createUserNo":"402880905e31cb26015e31cb5d290000","createTime":1600668922000,
     * "updateUserNo":"402880905e31cb26015e31cb5d290000","updateTime":1600668922000,
     * "description":""},{"recNo":"5b198d6174ae96b90174af50d4ff000c","dataName":"OBJECTID",
     * "datatype":"varchar","datatypeName":null,"dataLen":50,"dataExt":null,"name":"OBJECTID",
     * "elementNo":"40289fba74673c6b01746753f720003b",
     * "createUserNo":"402880905e31cb26015e31cb5d290000","createTime":1600669144000,
     * "updateUserNo":"402880905e31cb26015e31cb5d290000","updateTime":1600669144000,
     * "description":""}],"ElementBasic":{"imgUrl":"图片","createUserNo":"创建人","createTime":"创建时间",
     * "smallPlaceName":"小地名","smallClassNo":"小班号","areaCode":"行政区域代码","description":"描述",
     * "resourcetypeName":"资源类型名称","name":"名称","investigationAddr":"地址","agency":"社",
     * "resourcetype":"资源类型","recNo":"编号"}}
     * code : 200
     * msg : 查询类型资源属性值成功!
     */

    public DataBean data;
    public String code;
    public String msg;

    public static class DataBean {
        /**
         * BaseElementExt : [{"recNo":"5b198d6174812e3401748550b16b0002","dataName":"age",
         * "datatype":"int","datatypeName":null,"dataLen":null,"dataExt":null,"name":"树龄",
         * "elementNo":"40289fba74673c6b01746753f720003b",
         * "createUserNo":"402880905e31cb26015e31cb5d290000","createTime":1599964492000,
         * "updateUserNo":"402880905e31cb26015e31cb5d290000","updateTime":1599964492000,
         * "description":""},{"recNo":"5b198d6174ae96b90174af4d743b000a","dataName":"number",
         * "datatype":"varchar","datatypeName":null,"dataLen":50,"dataExt":null,"name":"编号",
         * "elementNo":"40289fba74673c6b01746753f720003b",
         * "createUserNo":"402880905e31cb26015e31cb5d290000","createTime":1600668922000,
         * "updateUserNo":"402880905e31cb26015e31cb5d290000","updateTime":1600668922000,
         * "description":""},{"recNo":"5b198d6174ae96b90174af50d4ff000c","dataName":"OBJECTID",
         * "datatype":"varchar","datatypeName":null,"dataLen":50,"dataExt":null,
         * "name":"OBJECTID","elementNo":"40289fba74673c6b01746753f720003b",
         * "createUserNo":"402880905e31cb26015e31cb5d290000","createTime":1600669144000,
         * "updateUserNo":"402880905e31cb26015e31cb5d290000","updateTime":1600669144000,
         * "description":""}]
         * ElementBasic : {"imgUrl":"图片","createUserNo":"创建人","createTime":"创建时间",
         * "smallPlaceName":"小地名","smallClassNo":"小班号","areaCode":"行政区域代码","description":"描述",
         * "resourcetypeName":"资源类型名称","name":"名称","investigationAddr":"地址","agency":"社",
         * "resourcetype":"资源类型","recNo":"编号"}
         */

        public ElementBasicBean ElementBasic;
        public List<BaseElementExtBean> BaseElementExt;

        public static class ElementBasicBean {
            /**
             * imgUrl : 图片
             * createUserNo : 创建人
             * createTime : 创建时间
             * smallPlaceName : 小地名
             * smallClassNo : 小班号
             * areaCode : 行政区域代码
             * description : 描述
             * resourcetypeName : 资源类型名称
             * name : 名称
             * investigationAddr : 地址
             * agency : 社
             * resourcetype : 资源类型
             * recNo : 编号
             */

            public String imgUrl;
            public String createUserNo;
            public String createTime;
//            public String smallPlaceName;
//            public String smallClassNo;
//            public String areaCode;
            public String description;
            public String resourcetypeName;
            public String name;
//            public String investigationAddr;
//            public String agency;
            public String resourcetype;
            public String recNo;
        }

        public static class BaseElementExtBean {
            /**
             * recNo : 5b198d6174812e3401748550b16b0002
             * dataName : age
             * datatype : int
             * datatypeName : null
             * dataLen : null
             * dataExt : null
             * name : 树龄
             * elementNo : 40289fba74673c6b01746753f720003b
             * createUserNo : 402880905e31cb26015e31cb5d290000
             * createTime : 1599964492000
             * updateUserNo : 402880905e31cb26015e31cb5d290000
             * updateTime : 1599964492000
             * description :
             */

            public String recNo;
            public String dataName;
            public String datatype;
            public Object datatypeName;
            public Object dataLen;
            public Object dataExt;
            public String name;
            public String elementNo;
            public String createUserNo;
            public long createTime;
            public String updateUserNo;
            public long updateTime;
            public String description;
        }
    }
}
