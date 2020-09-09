package com.ys.monitor.bean;

import com.ys.monitor.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lh
 * @version 1.0.0
 * @filename Msg
 * @description -------------------------------------------------------
 * @date 2020/9/9 15:35
 */
public class Msg {
    public int drawableId;
    public int num;
    public String title;
    public String content;
    public String time;

    public Msg() {
    }

    public Msg(int drawableId, int num, String title, String content, String time) {
        this.drawableId = drawableId;
        this.num = num;
        this.title = title;
        this.content = content;
        this.time = time;
    }

    public static List<Msg> getDefaultMsgList() {
        List<Msg> list = new ArrayList<>();
        Msg msg1 = new Msg(R.mipmap.notice_fire_check, 1, "火情核查", "[8条] 大渡口区建胜镇四民村四社附近发生火灾", "16" +
                ":30");
        Msg msg2 = new Msg(R.mipmap.notice_warning, 0, "预警通知", "[124条] 森林火险气象等级预报（20219）", "16:30");
        Msg msg3 = new Msg(R.mipmap.notice_task, 1, "任务通知", "[36条] 待处理", "16:30");
        Msg msg4 = new Msg(R.mipmap.notice_command_center, 1, "指挥中心", "", "16:30");
        list.add(msg1);
        list.add(msg2);
        list.add(msg3);
        list.add(msg4);
        return list;
    }
}
