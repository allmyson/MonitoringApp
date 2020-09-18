package com.ys.monitor.bean;

import com.huamai.poc.greendao.ChatMessage;
import com.huamai.poc.greendao.MessageDialogue;
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
    public long time;
    public MessageDialogue messageDialogue;
    public ChatMessage chatMessage;
    public Msg() {
    }

    public Msg(int drawableId, int num, String title, String content, long time) {
        this.drawableId = drawableId;
        this.num = num;
        this.title = title;
        this.content = content;
        this.time = time;
    }

    public static List<Msg> getDefaultMsgList() {
        List<Msg> list = new ArrayList<>();
        Msg msg1 = new Msg(R.mipmap.notice_fire_check, 0, "火情核查", "", 0);
        Msg msg2 = new Msg(R.mipmap.notice_warning, 0, "预警通知", "", 0);
        Msg msg3 = new Msg(R.mipmap.notice_task, 0, "任务通知", "", 0);
        Msg msg4 = new Msg(R.mipmap.notice_command_center, 0, "指挥中心", "", 0);
        list.add(msg1);
        list.add(msg2);
        list.add(msg3);
        list.add(msg4);
        return list;
    }

    @Override
    public String toString() {
        return "Msg{" +
                "drawableId=" + drawableId +
                ", num=" + num +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", time=" + time +
                '}';
    }
}
