package com.ys.monitor.chat.adapter;

import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.huamai.poc.chat.ChatMessageStatus;
import com.huamai.poc.greendao.ChatMessage;
import com.ys.monitor.chat.adapter.holder.BaseViewHolder;
import com.ys.monitor.chat.adapter.holder.ChatAcceptViewHolder;
import com.ys.monitor.chat.adapter.holder.ChatSendViewHolder;
import com.ys.monitor.chat.entity.MessageInfo;
import com.ys.monitor.chat.util.Constants;
import com.ys.monitor.util.L;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：Rance on 2016/11/29 10:46
 * 邮箱：rance935@163.com
 */
public class ChatAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private onItemClickListener onItemClickListener;
    public Handler handler;
    private List<MessageInfo> messageInfoList;
    private RecyclerView mRecyclerView;

    public RecyclerView getmRecyclerView() {
        return mRecyclerView;
    }

    public void setmRecyclerView(RecyclerView mRecyclerView) {
        this.mRecyclerView = mRecyclerView;
    }

    public ChatAdapter(List<MessageInfo> messageInfoList) {
        handler = new Handler();
        this.messageInfoList = messageInfoList;
    }

//    @Override
//    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
//        BaseViewHolder viewHolder = null;
//        switch (viewType) {
//            case Constants.CHAT_ITEM_TYPE_LEFT:
//                viewHolder = new ChatAcceptViewHolder(parent, onItemClickListener, handler);
//                break;
//            case Constants.CHAT_ITEM_TYPE_RIGHT:
//                viewHolder = new ChatSendViewHolder(parent, onItemClickListener, handler);
//                break;
//        }
//        return viewHolder;
//    }
//
//    @Override
//    public int getViewType(int position) {
//        return getAllData().get(position).getType();
//    }

    public void addItemClickListener(onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder viewHolder = null;
        switch (viewType) {
            case Constants.CHAT_ITEM_TYPE_LEFT:
                viewHolder = new ChatAcceptViewHolder(parent, onItemClickListener, handler);
                break;
            case Constants.CHAT_ITEM_TYPE_RIGHT:
                viewHolder = new ChatSendViewHolder(parent, onItemClickListener, handler);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.itemView.setTag(position);
        holder.setData(messageInfoList.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        return messageInfoList.get(position).getType();
    }

    @Override
    public int getItemCount() {
        if (messageInfoList == null) {
            return 0;
        } else {
            return messageInfoList.size();
        }
    }

    public void addAll(List<MessageInfo> messageInfos) {
        if (messageInfoList == null) {
            messageInfoList = messageInfos;
        } else {
            messageInfoList.addAll(messageInfos);
        }
        notifyDataSetChanged();
    }

    public void add(MessageInfo messageInfo) {
        if (messageInfoList == null) {
            messageInfoList = new ArrayList<>();
        }

        messageInfoList.add(messageInfo);

        notifyDataSetChanged();
    }

    public interface onItemClickListener {
        void onHeaderClick(int position);

        void onImageClick(View view, int position);

        void onVideoClick(View view, int position);

        void onVoiceClick(ImageView imageView, int position);

        void onFileClick(View view, int position);

        void onLinkClick(View view, int position);

        void onLongClickImage(View view, int position);

        void onLongClickText(View view, int position);

        void onLongClickItem(View view, int position);

        void onLongClickFile(View view, int position);

        void onLongClickLink(View view, int position);

        void reSend(View view, int position);
    }


    public void updateItem(ChatMessage chatMessage) {
        boolean hasUpdated = false;
        int count = mRecyclerView.getChildCount();
        for (int i = 0; i < count; i++) {
            BaseViewHolder holder =
                    (BaseViewHolder) mRecyclerView.getChildViewHolder(mRecyclerView.getChildAt(i));
            int position = (int) holder.itemView.getTag();
            if (position > messageInfoList.size() - 1) {
                continue;
            }
            MessageInfo hitMessage = messageInfoList.get(position);
            if (chatMessage.getId().equals(hitMessage.getMsgId())) {
                int sip_status = chatMessage.getSip_status() == null ?
                        ChatMessageStatus.Sip.UNKNOW : chatMessage.getSip_status();
                switch (sip_status) {
                    case ChatMessageStatus.Sip.UNKNOW:
                    case ChatMessageStatus.Sip.FAILED:
                        hitMessage.setSendState(Constants.CHAT_ITEM_SEND_ERROR);
                        break;
                    case ChatMessageStatus.Sip.SENDING:
                        hitMessage.setSendState(Constants.CHAT_ITEM_SENDING);
                        break;
                    case ChatMessageStatus.Sip.SENDED:
                        hitMessage.setSendState(Constants.CHAT_ITEM_SEND_SUCCESS);
                        break;
                }
                notifyItemChanged(position);
                hasUpdated = true;
                L.d("notifyItemChanged " + position);
                break;
            }
        }
        if (!hasUpdated) {
            for (MessageInfo message : messageInfoList) {
                if (message.getMsgId().equals(chatMessage.getId())) {
                    int sip_status = chatMessage.getSip_status() == null ?
                            ChatMessageStatus.Sip.UNKNOW : chatMessage.getSip_status();
                    switch (sip_status) {
                        case ChatMessageStatus.Sip.UNKNOW:
                        case ChatMessageStatus.Sip.FAILED:
                            message.setSendState(Constants.CHAT_ITEM_SEND_ERROR);
                            break;
                        case ChatMessageStatus.Sip.SENDING:
                            message.setSendState(Constants.CHAT_ITEM_SENDING);
                            break;
                        case ChatMessageStatus.Sip.SENDED:
                            message.setSendState(Constants.CHAT_ITEM_SEND_SUCCESS);
                            break;
                    }
                    break;
                }
            }
        }
    }

    public int getLastPosition() {
        if (getItemCount() > 0) {
            return getItemCount() - 1;
        } else {
            return 0;
        }
    }

    public void updateAll(ArrayList<ChatMessage> messages) {
        messageInfoList.clear();
        List<MessageInfo> list = new ArrayList<>();
        for (ChatMessage chatMessage : messages) {
            MessageInfo messageInfo = MessageInfo.getMessageInfo(chatMessage);
            list.add(messageInfo);
        }
        messageInfoList.addAll(list);
        notifyDataSetChanged();
        mRecyclerView.scrollToPosition(getLastPosition());
    }

    public void addChatMessage(ChatMessage message) {
        // 查找插入位置
        int insertIndex = hitInsertIndex(messageInfoList, message.getTime_stamp());
        MessageInfo messageInfo = MessageInfo.getMessageInfo(message);
        messageInfoList.add(insertIndex, messageInfo);
        notifyItemInserted(insertIndex);
        if (insertIndex == getLastPosition()) {
            mRecyclerView.smoothScrollToPosition(getLastPosition());
        }
    }

    public void addChatMessage(List<ChatMessage> messages) {
        /*for (ChatMessage message : mMessageList) {
            mLogger.d("message " + message.getText() + " " + message.getTime_stamp());
        }
        mLogger.d("add ===> " + messages.get(0).getText() + " " + messages.get(0).getTime_stamp()
        );*/
        if (messages.size() > 0) {
            // 查找插入位置
            int insertIndex = hitInsertIndex(messageInfoList, messages.get(0).getTime_stamp());
            List<MessageInfo> list = new ArrayList<>();
            for (ChatMessage chatMessage : messages) {
                MessageInfo messageInfo = MessageInfo.getMessageInfo(chatMessage);
                list.add(messageInfo);
            }
            messageInfoList.addAll(insertIndex, list);
            notifyItemInserted(insertIndex);
            if (insertIndex + messages.size() - 1 == getLastPosition()) {
                mRecyclerView.smoothScrollToPosition(getLastPosition());
            }
        }
    }

    private int hitInsertIndex(List<MessageInfo> list, long target) {
        if (list.size() == 0) {
            return 0;
        }
        int left = 0;
        int right = list.size() - 1;
        int index = list.size();
        if (target < list.get(left).getTime()) {
            index = left;
        } else if (target > list.get(right).getTime()) {
            index = right + 1;
        } else if (list.size() == 2) {
            index = right;
        } else {
            // 二分查找消息插入点
            while (left < right) {
                int mid = (left + right) / 2;
                long midValue = list.get(mid).getTime();
                long lSideValue = list.get(mid - 1).getTime();
                long rSideValue = list.get(mid + 1).getTime();
                //System.out.println("target=" + target + "====== mid.index=" + mid + "
                // lSideValue=" + lSideValue + " midValue=" + midValue + " rSideValue=" +
                // rSideValue + " ======");
                if (midValue == target) { //命中
                    index = mid + 1;
                    //System.out.println("hit mid");
                    break;
                } else if (target >= lSideValue && target < midValue) {//命中
                    index = mid;
                    //System.out.println("hit lMid");
                    break;
                } else if (target > midValue && target <= rSideValue) {//命中
                    index = mid + 1;
                    //System.out.println("hit rMid");
                    break;
                } else if (target < lSideValue) { // 取左侧继续二分查找
                    left = 0;
                    right = mid;
                    //System.out.println("check left [" + left + "," + right + "]");
                } else if (target > rSideValue) {// 取右侧继续二分查找
                    left = mid;
                    right = list.size() - 1;
                    //System.out.println("check right [" + left + "," + right + "]");
                } else {
                    //System.out.println("not match");
                    break;
                }
            }
        }
        return index;
    }

    public void removeChatMessage(int position) {
        messageInfoList.remove(position);
        notifyItemRemoved(position);
    }

    public List<MessageInfo> getMessageInfoList() {
        return messageInfoList;
    }

    public void setMessageInfoList(List<MessageInfo> messageInfoList) {
        this.messageInfoList = messageInfoList;
    }
}
