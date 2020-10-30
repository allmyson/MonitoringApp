package com.ys.monitor.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.huamai.poc.IPocEngineEventHandler;
import com.huamai.poc.PocEngine;
import com.huamai.poc.PocEngineFactory;
import com.huamai.poc.chat.ChatMessageCategory;
import com.huamai.poc.greendao.ChatMessage;
import com.huamai.poc.greendao.MessageDialogue;
import com.yanzhenjie.nohttp.rest.Response;
import com.ys.monitor.R;
import com.ys.monitor.activity.AddHelpActivity;
import com.ys.monitor.activity.ChatActivity;
import com.ys.monitor.activity.FireListActivity;
import com.ys.monitor.activity.TaskListActivity;
import com.ys.monitor.activity.YjtzActivity;
import com.ys.monitor.adapter.NoticeAdapter;
import com.ys.monitor.api.FunctionApi;
import com.ys.monitor.base.BaseFragment;
import com.ys.monitor.bean.FireBean;
import com.ys.monitor.bean.Msg;
import com.ys.monitor.bean.TaskBean;
import com.ys.monitor.bean.YjtzBean;
import com.ys.monitor.http.HttpListener;
import com.ys.monitor.sp.MsgSP;
import com.ys.monitor.sp.UserSP;
import com.ys.monitor.ui.NoNetView;
import com.ys.monitor.util.HttpUtil;
import com.ys.monitor.util.L;
import com.ys.monitor.util.StringUtil;
import com.ys.monitor.util.YS;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lh
 * @version 1.0.0
 * @filename OneFragment
 * @description -------------------------------------------------------
 * @date 2018/10/23 17:09
 */
public class ThreeFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,
        NoNetView.ClickListener, View.OnClickListener {
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView listView;
    private List<Msg> list;
    private NoticeAdapter adapter;
    private String userId;
    PocEngine pocEngine = PocEngineFactory.get();
    ArrayList<MessageDialogue> messageDialogueArray = new ArrayList<>(20);
    private List<Msg> topList;
    private List<Msg> chatList;
    private List<FireBean.DataBean.RowsBean> fireList;//发生火灾的火情数据

    public static ThreeFragment newInstance() {
        return new ThreeFragment();
    }

    @Override
    public void onRefresh() {
        getData();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapter.getCount() > 0) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void init() {
        fireList = new ArrayList<>();
        topList = new ArrayList<>();
        chatList = new ArrayList<>();
        swipeRefreshLayout = getView(R.id.srl_);
        listView = getView(R.id.lv_);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.main_color));
        list = new ArrayList<>();
//        topList.addAll(Msg.getDefaultMsgList());
        topList.addAll(MsgSP.getTopList(mContext));
        list.addAll(topList);
        adapter = new NoticeAdapter(mContext, list, R.layout.item_notice);
        listView.setAdapter(adapter);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                                 int totalItemCount) {
                View firstView = view.getChildAt(firstVisibleItem);
                if (firstVisibleItem == 0 && (firstView == null || firstView.getTop() == 0)) {
                    /*上滑到listView的顶部时，下拉刷新组件可见*/
                    swipeRefreshLayout.setEnabled(true);
                } else {
                    /*不是listView的顶部时，下拉刷新组件不可见*/
                    swipeRefreshLayout.setEnabled(false);
                }
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(mContext, FireListActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(mContext, YjtzActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(mContext, TaskListActivity.class));
                        break;
                    case 3:
                        if (fireList.size() == 0) {
                            show("暂无火情,无需上报扑救信息");
                            return;
                        }
                        String json = new Gson().toJson(fireList);
                        AddHelpActivity.intentToHelp(mContext, json);
//                        User user = PocEngineFactory.get().getUser("1111031");
//                        MessageDialogue md = PocEngineFactory.get().createMessageDialogueIfNeed
//                        (user);
//                        ChatActivity.intentToChat(mContext, md.getChat_id());
                        break;
                    default:
                        if (PocEngineFactory.get().hasServiceConnected()) {
                            MessageDialogue dialogue = adapter.getItem(position).messageDialogue;
                            dialogue.setUnread(0);
                            try {
                                PocEngineFactory.get().markConversationAsRead(dialogue.getChat_id());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            list.get(position).num = 0;
                            adapter.refresh(list);
                            ChatActivity.intentToChat(mContext, dialogue.getChat_id());
                        }
                        break;
                }
            }
        });
//        messageDialogueArray.clear();
//        messageDialogueArray.addAll(PocEngineFactory.get().getAllConversation());
//        refreshChat();
    }

    @Override
    protected void getData() {
        messageDialogueArray.clear();
        messageDialogueArray.addAll(PocEngineFactory.get().getAllConversation());
        refreshChat();
        userId = UserSP.getUserId(mContext);
        HttpUtil.getFireListWithNoDialog(mContext, userId, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                fireList.clear();
                try {
                    FireBean fireBean = new Gson().fromJson(response.get(), FireBean.class);
                    List<FireBean.DataBean.RowsBean> rowsBeanList = new ArrayList<>();
                    if (fireBean != null && fireBean.data != null && fireBean.data.rows != null && fireBean.data.rows.size() > 0) {
                        for (FireBean.DataBean.RowsBean rowsBean1 : fireBean.data.rows) {
                            if (YS.FireStatus.Status_DCL.equals(rowsBean1.status) || YS.FireStatus.Status_HSZ.equals(rowsBean1.status)) {
                                rowsBeanList.add(rowsBean1);
                            }
                            if (YS.FireStatus.Status_FSHZ.equals(rowsBean1.status)) {
                                fireList.add(rowsBean1);
                            }
                        }
                    }
                    if (rowsBeanList.size() > 0) {
                        if (rowsBeanList.get(0) != null) {
                            String data =
                                    "[" + rowsBeanList.size() + "条]" + StringUtil.valueOf(rowsBeanList.get(0).name);
                            Msg msg = new Msg(R.mipmap.notice_fire_check,
                                    rowsBeanList.size()
                                    , "火情核查", data, rowsBeanList.get(0).createTime);
                            topList.set(0, msg);
                            list.set(0, msg);
                            L.e(msg.toString());
                            adapter.refresh(list);
                            MsgSP.saveMsg(mContext,topList);
                        }
                    }else{
                        Msg msg = new Msg(R.mipmap.notice_fire_check,
                                rowsBeanList.size()
                                , "火情核查", "", 0);
                        topList.set(0, msg);
                        list.set(0, msg);
                        L.e(msg.toString());
                        adapter.refresh(list);
                        MsgSP.saveMsg(mContext,topList);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        HttpUtil.getYjtzListWithNoDialog(mContext, userId, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                try {
                    YjtzBean yjtzBean = new Gson().fromJson(response.get(), YjtzBean.class);
                    List<YjtzBean.DataBean.RowsBean> rowsBeanList = new ArrayList<>();
                    if (yjtzBean != null && yjtzBean.data != null && yjtzBean.data.rows != null && yjtzBean.data.rows.size() > 0) {
                        rowsBeanList.addAll(yjtzBean.data.rows);
                    }
                    if (rowsBeanList.size() > 0) {
                        if (rowsBeanList.get(0) != null) {
                            String data =
                                    "[" + rowsBeanList.size() + "条]" + StringUtil.valueOf(rowsBeanList.get(0).title);
                            Msg msg = new Msg(R.mipmap.notice_warning,
                                    rowsBeanList.size(),
                                    "预警通知", data, rowsBeanList.get(0).createTime);
                            topList.set(1, msg);
                            list.set(1, msg);
                            L.e(msg.toString());
                            adapter.refresh(list);
                            MsgSP.saveMsg(mContext,topList);
                        }
                    }else{
                        Msg msg = new Msg(R.mipmap.notice_warning,
                                rowsBeanList.size(),
                                "预警通知", "", 0);
                        topList.set(1, msg);
                        list.set(1, msg);
                        L.e(msg.toString());
                        adapter.refresh(list);
                        MsgSP.saveMsg(mContext,topList);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                swipeRefreshLayout.setRefreshing(false);

            }
        });

        HttpUtil.getTaskListWithNoDialog(mContext, userId, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                try {
                    TaskBean taskBean = new Gson().fromJson(response.get(), TaskBean.class);
                    List<TaskBean.DataBean.RowsBean> rowsBeanList = new ArrayList<>();
                    if (taskBean != null && taskBean.data != null && taskBean.data.rows != null && taskBean.data.rows.size() > 0) {
                        for (TaskBean.DataBean.RowsBean rowsBean : taskBean.data.rows) {
                            if (rowsBean != null && rowsBean.isFinish == 0) {
                                rowsBeanList.add(rowsBean);
                            }
                        }
                    }
                    if (rowsBeanList.size() > 0) {
                        String data =
                                "[" + rowsBeanList.size() + "条]" + StringUtil.valueOf(rowsBeanList.get(0).name);
                        Msg msg = new Msg(R.mipmap.notice_task, rowsBeanList.size(), "任务通知",
                                data, rowsBeanList.get(0).createTime);
                        topList.set(2, msg);
                        list.set(2, msg);
                        L.e(msg.toString());
                        adapter.refresh(list);
                        MsgSP.saveMsg(mContext,topList);
                    }else {
                        Msg msg = new Msg(R.mipmap.notice_task, rowsBeanList.size(), "任务通知",
                                "", 0);
                        topList.set(2, msg);
                        list.set(2, msg);
                        L.e(msg.toString());
                        adapter.refresh(list);
                        MsgSP.saveMsg(mContext,topList);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_three;
    }

    @Override
    public void reload() {
        swipeRefreshLayout.setRefreshing(true);
        onRefresh();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_kf) {
            FunctionApi.contactKF(mContext);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        PocEngineFactory.get().addEventHandler(mPocEventHandler);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        PocEngineFactory.get().removeEventHandler(mPocEventHandler);
    }

    private final IPocEngineEventHandler mPocEventHandler = new IPocEngineEventHandler() {
        @Override
        public void onNewConversationCreated(MessageDialogue messageDialogue) {
            messageDialogueArray.clear();
            messageDialogueArray.addAll(PocEngineFactory.get().getAllConversation());
            refreshChat();
        }
    };

    public void addNewConversation(MessageDialogue messageDialogue) {
        for (MessageDialogue dialogue : messageDialogueArray) {
            if (dialogue.getChat_id() == messageDialogue.getChat_id()) {
                messageDialogueArray.remove(dialogue);
                break;
            }
        }
        messageDialogueArray.add(0, messageDialogue);
        refreshChat();
    }

    private void refreshChat() {
        chatList.clear();
        L.e("chat", "messageDialogueArray.size() = " + messageDialogueArray.size());
        if (messageDialogueArray.size() > 0) {
            for (MessageDialogue messageDialogue : messageDialogueArray) {
                ChatMessage lastMessage =
                        pocEngine.getLastConversationMessage(messageDialogue.getChat_id());
                int drawableId = R.mipmap.ic_users_offline;
                if (!messageDialogue.getIsGroup()) {
                    drawableId = R.mipmap.ic_users_offline;
                } else {
                    drawableId = R.mipmap.re_default_custom_group;
                }
                String content = "";
                if (lastMessage != null) {
                    switch (lastMessage.getCategory()) {
                        case ChatMessageCategory.TEXT:
                            content = lastMessage.getText();
                            break;
                        case ChatMessageCategory.AUDIO:
                            content = "[语音呼叫]";
                            break;
                        case ChatMessageCategory.VIDEO_FILE:
                            content = "[视频]";
                            break;
                        case ChatMessageCategory.VIDEO:
                            content = "[视频呼叫]";
                            break;
                        case ChatMessageCategory.IMAGE:
                            content = "[图片]";
                            break;
                        case ChatMessageCategory.AUDIO_FILE:
                        case ChatMessageCategory.PTT_AUDIO_FILE:
                            content = "[语音]";
                            break;
                        default:
                            content = "[未知消息]";
                            break;
                    }
                }
                Msg msg = new Msg(drawableId, messageDialogue.getUnread(),
                        messageDialogue.getName(), content, messageDialogue.getUpdate_time());
                msg.messageDialogue = messageDialogue;
                msg.chatMessage = lastMessage;
                chatList.add(msg);
            }
        }
        list.clear();
        list.addAll(topList);
        list.addAll(chatList);
        adapter.refresh(list);
    }
}
