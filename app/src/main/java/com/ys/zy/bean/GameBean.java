package com.ys.zy.bean;

import com.ys.zy.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lh
 * @version 1.0.0
 * @filename GameBean
 * @description -------------------------------------------------------
 * @date 2018/11/26 18:11
 */
public class GameBean {
    public int drawableId;
    public String status;
    public String code;
    public String name;
    public String des;

    public GameBean() {
    }

    public GameBean(int drawableId, String name, String des) {
        this.drawableId = drawableId;
        this.name = name;
        this.des = des;
    }

    public static List<GameBean> getDefaultList() {
        List<GameBean> list = new ArrayList<>();
        list.add(new GameBean(R.mipmap.game_k3_icon, "10分快3", "10分钟1期"));
        list.add(new GameBean(R.mipmap.game_k3_icon, "1分快3", "1分钟1期"));
        list.add(new GameBean(R.mipmap.game_k3_icon, "5分快3", "5分钟1期"));
        list.add(new GameBean(R.mipmap.game_lp_icon, "轮盘", "1分钟1期"));
        list.add(new GameBean(R.mipmap.game_ttz_icon, "推筒子", "1分钟1期"));
        list.add(new GameBean(R.mipmap.game_lwin_icon, "最后胜利者", "10:00-22:00"));
        list.add(new GameBean(R.mipmap.game_pk_icon, "20分赛车", "20分钟1期"));
        list.add(new GameBean(R.mipmap.game_pk_icon, "1分赛车", "1分钟1期"));
        list.add(new GameBean(R.mipmap.game_pk_icon, "5分赛车", "5分钟1期"));
        list.add(new GameBean(R.mipmap.game_ssc_icon, "1分彩", "1分钟1期"));
        list.add(new GameBean(R.mipmap.game_ssc_icon, "时时彩", "10分钟一期"));
        list.add(new GameBean(R.mipmap.home_game_icon_more, "更多", ""));
        return list;
    }
    public static GameBean getMoreBean(){
        GameBean gameBean = new GameBean(R.mipmap.home_game_icon_more, "更多", "");
        gameBean.status="1000";
        gameBean.code="800";
        return gameBean;
    }
    public static String getNameByCode(String game_code) {
        String name = "";
        switch (game_code) {
            case "1000":
                name = "时时彩";
                break;
            case "1001":
                name = "1分彩";
                break;
            case "1002":
                name = "最后胜利者";
                break;
            case "1003":
                name = "轮盘";
                break;
            case "1004":
                name = "推筒子";
                break;
            case "1005":
                name = "10分快3";
                break;
            case "1006":
                name = "1分快3";
                break;
            case "1007":
                name = "5分快3";
                break;
            case "1008":
                name = "20分赛车";
                break;
            case "1009":
                name = "1分赛车";
                break;
            case "1010":
                name = "5分赛车";
                break;
        }
        return name;
    }

    public static String getDesByCode(String game_code) {
        String dec = "";
        switch (game_code) {
            case "1000":
                dec = "10分钟1期";
                break;
            case "1001":
                dec = "1分钟1期";
                break;
            case "1002":
                dec = "10:00-22:00";
                break;
            case "1003":
                dec = "1分钟1期";
                break;
            case "1004":
                dec = "1分钟1期";
                break;
            case "1005":
                dec = "10分钟1期";
                break;
            case "1006":
                dec = "1分钟1期";
                break;
            case "1007":
                dec = "5分钟1期";
                break;
            case "1008":
                dec = "20分钟1期";
                break;
            case "1009":
                dec = "1分钟1期";
                break;
            case "1010":
                dec = "5分钟1期";
                break;
        }
        return dec;
    }

    public static int getImageByCode (String game_code){
        int drawableId = R.mipmap.game_ssc_icon;
        switch (game_code) {
            case "1000":
                drawableId = R.mipmap.game_ssc_icon;
                break;
            case "1001":
                drawableId = R.mipmap.game_ssc_icon;
                break;
            case "1002":
                drawableId = R.mipmap.game_lwin_icon;
                break;
            case "1003":
                drawableId = R.mipmap.game_lp_icon;
                break;
            case "1004":
                drawableId = R.mipmap.game_ttz_icon;
                break;
            case "1005":
                drawableId = R.mipmap.game_k3_icon;
                break;
            case "1006":
                drawableId = R.mipmap.game_k3_icon;
                break;
            case "1007":
                drawableId = R.mipmap.game_k3_icon;
                break;
            case "1008":
                drawableId = R.mipmap.game_pk_icon;
                break;
            case "1009":
                drawableId = R.mipmap.game_pk_icon;
                break;
            case "1010":
                drawableId = R.mipmap.game_pk_icon;
                break;
        }
        return drawableId;
    }
}
