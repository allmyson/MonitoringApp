package com.ys.monitor.bean;

import java.util.List;

public class YjtzBean {


    /**
     * data : {"total":10,"rows":[{"recNo":"4028b88174d91dd70174d932849b0000","title":"森林火险气象等级预报（20273）","content":"据重庆市气象台09月29日8时预报，未来24小时全市森林火险气象等级为低度危险（一级）。","source":"2","createUserNo":"402880905e31cb26015e31cb5d290000","createUserName":"admin","createTime":1601371800000,"description":null,"isDelete":"0","imgUrl":"http://lyj.cq.gov.cn/lyzs/slhxtqyb/202009/W020200929627493877688.jpg;http://lyj.cq.gov.cn/lyzs/slhxtqyb/202009/W020200929627493958369.jpg;"},{"recNo":"5b198d6174d3ddf70174d40c27b20002","title":"森林火险气象等级预报（20272）","content":"据重庆市气象台08月28日8时预报，未来24小时全市森林火险气象等级为低度危险（一级）。","source":"2","createUserNo":"402880905e31cb26015e31cb5d290000","createUserName":"admin","createTime":1601285400000,"description":null,"isDelete":"0","imgUrl":"http://lyj.cq.gov.cn/lyzs/slhxtqyb/202009/W020200928609926509657.jpg;http://lyj.cq.gov.cn/lyzs/slhxtqyb/202009/W020200928609926604744.jpg;"},{"recNo":"40289f3c74babf5f0174babf7d320000","title":"森林火险气象等级预报（20265）","content":"据重庆市气象台09月21日8时预报，未来24小时全市森林火险气象等级为低度危险（一级）。","source":"2","createUserNo":"402880905e31cb26015e31cb5d290000","createUserName":"admin","createTime":1600860945000,"description":null,"isDelete":"0","imgUrl":"http://lyj.cq.gov.cn/lyzs/slhxtqyb/202009/W020200921523193551107.jpg;http://lyj.cq.gov.cn/lyzs/slhxtqyb/202009/W020200921523193649354.jpg;"},{"recNo":"40289f3c74044ab70174044b90090000","title":"森林火灾预警信息发布","content":"森林防火期内，当森林火险等级持续四级以上，极易引发森林火灾的关键时期，由县森林防火指挥机构对外发布橙色预警或红色预警。","source":"2","createUserNo":"402880905e31cb26015e31cb5d290000","createUserName":"admin","createTime":1597799895000,"description":"","isDelete":"0","imgUrl":"/data/warining/cafb9b0d5dc34b08ba09e7df8b9e5879/huoqing1.jpg;"},{"recNo":"40289f3c73fb94920173fb96c1a50000","title":"全市发布森林火险预警","content":"当前火险风险高，森林防火形势严峻。重庆市气象台提醒，广大基层森林防火部门应切实做好火源管控、监测工作，加大巡山护林力度；市民外出游山时，要注意用火安全。","source":"1","createUserNo":"402880905e31cb26015e31cb5d290000","createUserName":"admin","createTime":1597454289000,"description":"","isDelete":"0","imgUrl":null},{"recNo":"40289f3c73fb94920173fb984ced0002","title":"森林防火预警信号","content":"天气晴间多云，无明显降雨，近日有一次弱冷空气影响。风力较大，湿度小，森林火险气象等级高，请区自然资源局和各涉农镇街加强森林防火工作。","source":"1","createUserNo":"402880905e31cb26015e31cb5d290000","createUserName":"admin","createTime":1596770823000,"description":"","isDelete":"0","imgUrl":null},{"recNo":"40289f3c73fba0640173fba109ed0000","title":"火险气象预警","content":"当森林火险气象等级达高度危险或高森林火险达橙色警戒及以上时，在每日电视天气预报中发布高森林火险气象等级预报图，向高森林火险区域发布森林火险预警信息，提醒公众注意森林防火安全，遇火情及时拨打\u201c12119\u201d森林火险报警电话等内容。","source":"1","createUserNo":"402880905e31cb26015e31cb5d290000","createUserName":"admin","createTime":1595520000000,"description":"","isDelete":"0","imgUrl":null},{"recNo":"40289f3c73fa38e20173fa3aa5070001","title":"森林防火安全提示","content":"1.南方地区进入春季森林火灾高发期，请注意森林消防安全，做到进山不带火，入林不吸烟。2.近期森林火险等级居高不下，一点星星火，可毁万顷林，请注意野外用火安全。3.进山旅游、野外宿营，请注意观察森林防火标识和疏散路线，不违规用火，不乱丢垃圾。","source":"1","createUserNo":"402880905e31cb26015e31cb5d290000","createUserName":"admin","createTime":1592469765000,"description":"","isDelete":"0","imgUrl":null},{"recNo":"40289f3c742da2d301742f0f5ab50067","title":"森林防火","content":"最近全县气温持续升高，火险等级较高，极易引发森林火灾，请各乡镇务必高度重视，加强巡查管护，杜绝火源进入林区，防止森林火灾","source":"1","createUserNo":"402880905e31cb26015e31cb5d290000","createUserName":"admin","createTime":1583378116000,"description":"","isDelete":"0","imgUrl":null},{"recNo":"40289f3c742da2d301742f011042005d","title":"春节期间森林防火","content":"现已临近春节，尤其是除夕、元宵节两个重要时间节点，村民普遍有上坟烧纸、点香、烧荒等习俗，稍有不慎即有可能引发森林火灾。请各位注意防火。","source":"1","createUserNo":"402880905e31cb26015e31cb5d290000","createUserName":"admin","createTime":1580356982000,"description":"","isDelete":"0","imgUrl":null}],"footer":null}
     * code : 200
     * msg : 预警通知信息获取成功!
     */

    public DataBean data;
    public String code;
    public String msg;

    public static class DataBean {
        /**
         * total : 10
         * rows : [{"recNo":"4028b88174d91dd70174d932849b0000","title":"森林火险气象等级预报（20273）","content":"据重庆市气象台09月29日8时预报，未来24小时全市森林火险气象等级为低度危险（一级）。","source":"2","createUserNo":"402880905e31cb26015e31cb5d290000","createUserName":"admin","createTime":1601371800000,"description":null,"isDelete":"0","imgUrl":"http://lyj.cq.gov.cn/lyzs/slhxtqyb/202009/W020200929627493877688.jpg;http://lyj.cq.gov.cn/lyzs/slhxtqyb/202009/W020200929627493958369.jpg;"},{"recNo":"5b198d6174d3ddf70174d40c27b20002","title":"森林火险气象等级预报（20272）","content":"据重庆市气象台08月28日8时预报，未来24小时全市森林火险气象等级为低度危险（一级）。","source":"2","createUserNo":"402880905e31cb26015e31cb5d290000","createUserName":"admin","createTime":1601285400000,"description":null,"isDelete":"0","imgUrl":"http://lyj.cq.gov.cn/lyzs/slhxtqyb/202009/W020200928609926509657.jpg;http://lyj.cq.gov.cn/lyzs/slhxtqyb/202009/W020200928609926604744.jpg;"},{"recNo":"40289f3c74babf5f0174babf7d320000","title":"森林火险气象等级预报（20265）","content":"据重庆市气象台09月21日8时预报，未来24小时全市森林火险气象等级为低度危险（一级）。","source":"2","createUserNo":"402880905e31cb26015e31cb5d290000","createUserName":"admin","createTime":1600860945000,"description":null,"isDelete":"0","imgUrl":"http://lyj.cq.gov.cn/lyzs/slhxtqyb/202009/W020200921523193551107.jpg;http://lyj.cq.gov.cn/lyzs/slhxtqyb/202009/W020200921523193649354.jpg;"},{"recNo":"40289f3c74044ab70174044b90090000","title":"森林火灾预警信息发布","content":"森林防火期内，当森林火险等级持续四级以上，极易引发森林火灾的关键时期，由县森林防火指挥机构对外发布橙色预警或红色预警。","source":"2","createUserNo":"402880905e31cb26015e31cb5d290000","createUserName":"admin","createTime":1597799895000,"description":"","isDelete":"0","imgUrl":"/data/warining/cafb9b0d5dc34b08ba09e7df8b9e5879/huoqing1.jpg;"},{"recNo":"40289f3c73fb94920173fb96c1a50000","title":"全市发布森林火险预警","content":"当前火险风险高，森林防火形势严峻。重庆市气象台提醒，广大基层森林防火部门应切实做好火源管控、监测工作，加大巡山护林力度；市民外出游山时，要注意用火安全。","source":"1","createUserNo":"402880905e31cb26015e31cb5d290000","createUserName":"admin","createTime":1597454289000,"description":"","isDelete":"0","imgUrl":null},{"recNo":"40289f3c73fb94920173fb984ced0002","title":"森林防火预警信号","content":"天气晴间多云，无明显降雨，近日有一次弱冷空气影响。风力较大，湿度小，森林火险气象等级高，请区自然资源局和各涉农镇街加强森林防火工作。","source":"1","createUserNo":"402880905e31cb26015e31cb5d290000","createUserName":"admin","createTime":1596770823000,"description":"","isDelete":"0","imgUrl":null},{"recNo":"40289f3c73fba0640173fba109ed0000","title":"火险气象预警","content":"当森林火险气象等级达高度危险或高森林火险达橙色警戒及以上时，在每日电视天气预报中发布高森林火险气象等级预报图，向高森林火险区域发布森林火险预警信息，提醒公众注意森林防火安全，遇火情及时拨打\u201c12119\u201d森林火险报警电话等内容。","source":"1","createUserNo":"402880905e31cb26015e31cb5d290000","createUserName":"admin","createTime":1595520000000,"description":"","isDelete":"0","imgUrl":null},{"recNo":"40289f3c73fa38e20173fa3aa5070001","title":"森林防火安全提示","content":"1.南方地区进入春季森林火灾高发期，请注意森林消防安全，做到进山不带火，入林不吸烟。2.近期森林火险等级居高不下，一点星星火，可毁万顷林，请注意野外用火安全。3.进山旅游、野外宿营，请注意观察森林防火标识和疏散路线，不违规用火，不乱丢垃圾。","source":"1","createUserNo":"402880905e31cb26015e31cb5d290000","createUserName":"admin","createTime":1592469765000,"description":"","isDelete":"0","imgUrl":null},{"recNo":"40289f3c742da2d301742f0f5ab50067","title":"森林防火","content":"最近全县气温持续升高，火险等级较高，极易引发森林火灾，请各乡镇务必高度重视，加强巡查管护，杜绝火源进入林区，防止森林火灾","source":"1","createUserNo":"402880905e31cb26015e31cb5d290000","createUserName":"admin","createTime":1583378116000,"description":"","isDelete":"0","imgUrl":null},{"recNo":"40289f3c742da2d301742f011042005d","title":"春节期间森林防火","content":"现已临近春节，尤其是除夕、元宵节两个重要时间节点，村民普遍有上坟烧纸、点香、烧荒等习俗，稍有不慎即有可能引发森林火灾。请各位注意防火。","source":"1","createUserNo":"402880905e31cb26015e31cb5d290000","createUserName":"admin","createTime":1580356982000,"description":"","isDelete":"0","imgUrl":null}]
         * footer : null
         */

        public int total;
        public Object footer;
        public List<RowsBean> rows;

        public static class RowsBean {
            /**
             * recNo : 4028b88174d91dd70174d932849b0000
             * title : 森林火险气象等级预报（20273）
             * content : 据重庆市气象台09月29日8时预报，未来24小时全市森林火险气象等级为低度危险（一级）。
             * source : 2
             * createUserNo : 402880905e31cb26015e31cb5d290000
             * createUserName : admin
             * createTime : 1601371800000
             * description : null
             * isDelete : 0
             * imgUrl : http://lyj.cq.gov.cn/lyzs/slhxtqyb/202009/W020200929627493877688.jpg;http://lyj.cq.gov.cn/lyzs/slhxtqyb/202009/W020200929627493958369.jpg;
             */

            public String recNo;
            public String title;
            public String content;
            public String source;
            public String createUserNo;
            public String createUserName;
            public long createTime;
            public String description;
            public String isDelete;
            public String imgUrl;
        }
    }
}
