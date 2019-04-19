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
        list.add(new GameBean(R.mipmap.game_k3_icon, "江苏快3", "全天41期"));
        list.add(new GameBean(R.mipmap.game_k3_icon, "1分快3", "1分钟1期"));
        list.add(new GameBean(R.mipmap.game_k3_icon, "5分快3", "5分钟1期"));
        list.add(new GameBean(R.mipmap.game_lp_icon, "轮盘", "1分钟1期"));
        list.add(new GameBean(R.mipmap.game_ttz_icon, "推筒子", "1分钟1期"));
        list.add(new GameBean(R.mipmap.game_lwin_icon, "最后的胜利者", ""));
        list.add(new GameBean(R.mipmap.game_pk_icon, "北京赛车", "全天44期"));
        list.add(new GameBean(R.mipmap.game_pk_icon, "1分赛车", "1分钟1期"));
        list.add(new GameBean(R.mipmap.game_pk_icon, "5分赛车", "5分钟1期"));
        list.add(new GameBean(R.mipmap.game_ssc_icon, "1分彩", "1分钟1期"));
        list.add(new GameBean(R.mipmap.game_ssc_icon, "时时彩", "10分钟一期"));
        list.add(new GameBean(R.mipmap.home_game_icon_more, "更多", ""));
        return list;
    }
}
