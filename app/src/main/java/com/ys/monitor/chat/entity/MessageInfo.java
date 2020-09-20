package com.ys.monitor.chat.entity;

import com.huamai.poc.chat.ChatMessageCategory;
import com.huamai.poc.chat.ChatMessageStatus;
import com.huamai.poc.greendao.ChatMessage;
import com.ys.monitor.chat.util.Constants;

/**
 * 作者：Rance on 2016/12/14 14:13
 * 邮箱：rance935@163.com
 */
public class MessageInfo {
    private long chatId;
    private int type;
    private String content;
    private String filepath;
    private int sendState;
    private long time;
    private String header;
    private long voiceTime;
    private String msgId;
    private String fileType;
    private Object object;
    private String mimeType;

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public int getSendState() {
        return sendState;
    }

    public void setSendState(int sendState) {
        this.sendState = sendState;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public long getVoiceTime() {
        return voiceTime;
    }

    public void setVoiceTime(long voiceTime) {
        this.voiceTime = voiceTime;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    @Override
    public String toString() {
        return "MessageInfo{" +
                "type=" + type +
                ", content='" + content + '\'' +
                ", filepath='" + filepath + '\'' +
                ", sendState=" + sendState +
                ", time='" + time + '\'' +
                ", header='" + header + '\'' +
                ", mimeType='" + mimeType + '\'' +
                ", voiceTime=" + voiceTime +
                ", msgId='" + msgId + '\'' +
                ", fileType='" + fileType + '\'' +
                ", object=" + object +
                '}';
    }

    public static MessageInfo getMessageInfo(ChatMessage chatMessage){
        MessageInfo messageInfo = new MessageInfo();
        messageInfo.setTime(chatMessage.getTime_stamp());
        messageInfo.setObject(chatMessage);
        messageInfo.setMsgId(chatMessage.getId());
        messageInfo.setChatId(chatMessage.getChat_id());
        if(chatMessage.getIs_out()){
            messageInfo.setType(Constants.CHAT_ITEM_TYPE_RIGHT);
        }else{
            messageInfo.setType(Constants.CHAT_ITEM_TYPE_LEFT);
        }
        int sip_status = chatMessage.getSip_status() == null ? ChatMessageStatus.Sip.UNKNOW : chatMessage.getSip_status();
        switch (sip_status) {
            case ChatMessageStatus.Sip.UNKNOW:
            case ChatMessageStatus.Sip.FAILED:
                messageInfo.setSendState(Constants.CHAT_ITEM_SEND_ERROR);
                break;
            case ChatMessageStatus.Sip.SENDING:
                messageInfo.setSendState(Constants.CHAT_ITEM_SENDING);
                break;
            case ChatMessageStatus.Sip.SENDED:
                messageInfo.setSendState(Constants.CHAT_ITEM_SEND_SUCCESS);
                break;
        }
        if (chatMessage.getCategory() == ChatMessageCategory.AUDIO_FILE) {
            messageInfo.setFileType(Constants.CHAT_FILE_TYPE_VOICE);
            messageInfo.setFilepath(chatMessage.getHttpFile());
            messageInfo.setVoiceTime(chatMessage.getDuration());
        }else if(chatMessage.getCategory() == ChatMessageCategory.TEXT){
            messageInfo.setContent(chatMessage.getText());
            messageInfo.setFileType(Constants.CHAT_FILE_TYPE_TEXT);
        }else if(chatMessage.getCategory() == ChatMessageCategory.IMAGE){
            messageInfo.setFileType(Constants.CHAT_FILE_TYPE_IMAGE);
            messageInfo.setFilepath(chatMessage.getHttpFile());
        }else if(chatMessage.getCategory() == ChatMessageCategory.VIDEO_FILE){
            messageInfo.setFileType(Constants.CHAT_FILE_TYPE_VIDEO);
            messageInfo.setFilepath(chatMessage.getHttpFile());
        }else if(chatMessage.getCategory() == ChatMessageCategory.AUDIO){
            messageInfo.setFileType(Constants.CHAT_FILE_TYPE_TEXT);
            messageInfo.setContent("[语音通话]");
        }else if(chatMessage.getCategory() == ChatMessageCategory.VIDEO){
            messageInfo.setFileType(Constants.CHAT_FILE_TYPE_TEXT);
            messageInfo.setContent("[视频通话]");
        }else {
            messageInfo.setFileType(Constants.CHAT_FILE_TYPE_TEXT);
            messageInfo.setContent("[其他消息]");
        }
        return messageInfo;
    }
}
