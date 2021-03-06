package com.ys.monitor.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.huamai.poc.IPocEngineEventHandler;
import com.huamai.poc.PocEngineFactory;
import com.huamai.poc.chat.ChatUtil;
import com.huamai.poc.greendao.ChatMessage;
import com.huamai.poc.greendao.MessageDialogue;
import com.huamai.poc.greendao.User;
import com.huamai.poc.media.MediaRecordFunc;
import com.labo.kaji.relativepopupwindow.RelativePopupWindow;
import com.ys.monitor.R;
import com.ys.monitor.api.FunctionApi;
import com.ys.monitor.base.BaseActivity;
import com.ys.monitor.chat.activity.FullImageActivity;
import com.ys.monitor.chat.adapter.ChatAdapter;
import com.ys.monitor.chat.adapter.CommonFragmentPagerAdapter;
import com.ys.monitor.chat.entity.FullImageInfo;
import com.ys.monitor.chat.entity.Link;
import com.ys.monitor.chat.entity.MessageInfo;
import com.ys.monitor.chat.fragment.ChatEmotionFragment;
import com.ys.monitor.chat.fragment.ChatFunctionFragment;
import com.ys.monitor.chat.util.Constants;
import com.ys.monitor.chat.util.GlobalOnItemClickManagerUtils;
import com.ys.monitor.chat.util.MediaManager;
import com.ys.monitor.chat.widget.ChatContextMenu;
import com.ys.monitor.chat.widget.EmotionInputDetector;
import com.ys.monitor.chat.widget.NoScrollViewPager;
import com.ys.monitor.chat.widget.StateButton;
import com.ys.monitor.sp.UserSP;
import com.ys.monitor.util.L;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends BaseActivity implements ChatFunctionFragment.ClickListener {
    public static final String EXTRAS_CHAT_ID = "extras_chat_id";
    private long chatId;
    RecyclerView chatList;
    ImageView emotionVoice;
    EditText editText;
    TextView voiceText;
    ImageView emotionButton;
    ImageView emotionAdd;
    StateButton emotionSend;
    NoScrollViewPager viewpager;
    RelativeLayout emotionLayout;

    private EmotionInputDetector mDetector;
    private ArrayList<Fragment> fragments;
    private ChatEmotionFragment chatEmotionFragment;
    private ChatFunctionFragment chatFunctionFragment;
    private CommonFragmentPagerAdapter adapter;


    private ChatAdapter chatAdapter;
    private LinearLayoutManager layoutManager;
    private List<MessageInfo> messageInfos;


    //录音相关
    int animationRes = 0;
    int res = 0;
    AnimationDrawable animationDrawable = null;
    private ImageView animView;
    private String userId;
    private User user;

    @Override
    public int getLayoutId() {
        return R.layout.activity_chat;
    }

    @Override
    public void initView() {
        initColor();
        chatId = getIntent().getLongExtra(EXTRAS_CHAT_ID, -1);
        messageInfos = new ArrayList<>();
//        setBarColor2("#ffffff", true);
//        setBarColor("#ffffff");
        PocEngineFactory.get().addEventHandler(pocEngineEventHandler);
        MessageDialogue md = null;
        try {
            md = PocEngineFactory.get().getConversation(chatId);
        } catch (Exception e) {
            e.printStackTrace();
            show("获取消息数据失败！");
            finish();
            return;
        }
        if (md == null) {
            show("获取消息数据失败！");
            finish();
            return;
        }
        titleView.setText(md.getName());
        findViewByIds();
        EventBus.getDefault().register(this);
        initWidget();
        userId = UserSP.getUserId(mContext);
        List<ChatMessage> list = PocEngineFactory.get().getConversationMessages(chatId);
        if (list != null) {
            chatAdapter.updateAll((ArrayList<ChatMessage>) list);
        }
        // mark all as read
        PocEngineFactory.get().markMessagesAsRead(list);
    }

    @Override
    public void getData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_update:
                startActivity(new Intent(mContext, UpdateInfoActivity.class));
                break;
        }
    }

    private void findViewByIds() {
        chatList = (RecyclerView) findViewById(R.id.chat_list);
        emotionVoice = (ImageView) findViewById(R.id.emotion_voice);
        editText = (EditText) findViewById(R.id.edit_text);
        voiceText = (TextView) findViewById(R.id.voice_text);
        emotionButton = (ImageView) findViewById(R.id.emotion_button);
        emotionAdd = (ImageView) findViewById(R.id.emotion_add);
        emotionSend = (StateButton) findViewById(R.id.emotion_send);
        emotionLayout = (RelativeLayout) findViewById(R.id.emotion_layout);
        viewpager = (NoScrollViewPager) findViewById(R.id.viewpager);
    }

    private void initWidget() {
//        fragments = new ArrayList<>();
//        chatEmotionFragment = new ChatEmotionFragment();
//        fragments.add(chatEmotionFragment);
        chatFunctionFragment = new ChatFunctionFragment();
        chatFunctionFragment.setClickListener(this);
//        fragments.add(chatFunctionFragment);
//        adapter = new CommonFragmentPagerAdapter(getSupportFragmentManager(), fragments);
//        viewpager.setAdapter(adapter);
//        viewpager.setCurrentItem(0);
        addFragment(chatFunctionFragment, R.id.emotion_layout);
        mDetector = EmotionInputDetector.with(this)
                .setEmotionView(emotionLayout)
                .setViewPager(viewpager)
                .bindToContent(chatList)
                .bindToEditText(editText)
                .bindToEmotionButton(emotionButton)
                .bindToAddButton(emotionAdd)
                .bindToSendButton(emotionSend)
                .bindToVoiceButton(emotionVoice)
                .bindToVoiceText(voiceText)
                .build();

        GlobalOnItemClickManagerUtils globalOnItemClickListener =
                GlobalOnItemClickManagerUtils.getInstance(this);
        globalOnItemClickListener.attachToEditText(editText);

        chatAdapter = new ChatAdapter(messageInfos);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        chatList.setLayoutManager(layoutManager);
        chatList.setAdapter(chatAdapter);
        chatAdapter.setmRecyclerView(chatList);
        chatList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        chatAdapter.handler.removeCallbacksAndMessages(null);
                        chatAdapter.notifyDataSetChanged();
                        break;
                    case RecyclerView.SCROLL_STATE_DRAGGING:
                        chatAdapter.handler.removeCallbacksAndMessages(null);
                        mDetector.hideEmotionLayout(false);
                        mDetector.hideSoftInput();
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        chatAdapter.addItemClickListener(itemClickListener);
//        LoadData();
    }

    /**
     * item点击事件
     */
    private ChatAdapter.onItemClickListener itemClickListener =
            new ChatAdapter.onItemClickListener() {
                @Override
                public void onHeaderClick(int position) {
                    Toast.makeText(mContext, "onHeaderClick", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onImageClick(View view, int position) {
                    int location[] = new int[2];
                    view.getLocationOnScreen(location);
                    FullImageInfo fullImageInfo = new FullImageInfo();
                    fullImageInfo.setLocationX(location[0]);
                    fullImageInfo.setLocationY(location[1]);
                    fullImageInfo.setWidth(view.getWidth());
                    fullImageInfo.setHeight(view.getHeight());
                    fullImageInfo.setImageUrl(messageInfos.get(position).getFilepath());
                    EventBus.getDefault().postSticky(fullImageInfo);
                    startActivity(new Intent(mContext, FullImageActivity.class));
                    overridePendingTransition(0, 0);
                }

                @Override
                public void onVideoClick(View view, int position) {
                    L.e(messageInfos.get(position).getFilepath());
//            ToastUtil.show(mContext,messageInfos.get(position).getFilepath());
                    FunctionApi.playVideo2(mContext, messageInfos.get(position).getFilepath());
                }

                @Override
                public void onVoiceClick(final ImageView imageView, final int position) {
                    if (animView != null) {
                        animView.setImageResource(res);
                        animView = null;
                    }
                    switch (messageInfos.get(position).getType()) {
                        case 1:
                            animationRes = R.drawable.voice_left;
                            res = R.mipmap.icon_voice_left3;
                            break;
                        case 2:
                            animationRes = R.drawable.voice_right;
                            res = R.mipmap.icon_voice_right3;
                            break;
                    }
                    animView = imageView;
                    animView.setImageResource(animationRes);
                    animationDrawable = (AnimationDrawable) imageView.getDrawable();
                    animationDrawable.start();
                    MediaManager.playSound(messageInfos.get(position).getFilepath(),
                            new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(MediaPlayer mp) {
                                    animView.setImageResource(res);
                                }
                            });
                }

                @Override
                public void onFileClick(View view, int position) {

                    MessageInfo messageInfo = messageInfos.get(position);
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_DEFAULT);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    File file = new File(messageInfo.getFilepath());
                    Uri fileUri = FileProvider.getUriForFile(mContext,
                            FunctionApi.getAuthority(mContext)
                            , file);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    }
                    intent.setDataAndType(fileUri, messageInfo.getMimeType());
                    startActivity(intent);
                }

                @Override
                public void onLinkClick(View view, int position) {
                    MessageInfo messageInfo = messageInfos.get(position);
                    Link link = (Link) messageInfo.getObject();
                    Uri uri = Uri.parse(link.getUrl());
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }

                @Override
                public void onLongClickImage(View view, int position) {

                    ChatContextMenu chatContextMenu = new ChatContextMenu(view.getContext(),
                            messageInfos.get(position));
//            chatContextMenu.setAnimationStyle();
                    chatContextMenu.showOnAnchor(view, RelativePopupWindow.VerticalPosition.ABOVE,
                            RelativePopupWindow.HorizontalPosition.CENTER);

                }

                @Override
                public void onLongClickText(View view, int position) {
                    ChatContextMenu chatContextMenu = new ChatContextMenu(view.getContext(),
                            messageInfos.get(position));
                    chatContextMenu.showOnAnchor(view, RelativePopupWindow.VerticalPosition.ABOVE,
                            RelativePopupWindow.HorizontalPosition.CENTER);
                }

                @Override
                public void onLongClickItem(View view, int position) {
                    ChatContextMenu chatContextMenu = new ChatContextMenu(view.getContext(),
                            messageInfos.get(position));
                    chatContextMenu.showOnAnchor(view, RelativePopupWindow.VerticalPosition.ABOVE,
                            RelativePopupWindow.HorizontalPosition.CENTER);
                }

                @Override
                public void onLongClickFile(View view, int position) {
                    ChatContextMenu chatContextMenu = new ChatContextMenu(view.getContext(),
                            messageInfos.get(position));
                    chatContextMenu.showOnAnchor(view, RelativePopupWindow.VerticalPosition.ABOVE,
                            RelativePopupWindow.HorizontalPosition.CENTER);
                }

                @Override
                public void onLongClickLink(View view, int position) {
                    ChatContextMenu chatContextMenu = new ChatContextMenu(view.getContext(),
                            messageInfos.get(position));
                    chatContextMenu.showOnAnchor(view, RelativePopupWindow.VerticalPosition.ABOVE,
                            RelativePopupWindow.HorizontalPosition.CENTER);
                }

                @Override
                public void reSend(View view, int position) {
                    show("重发" + position);
                }
            };


    /**
     * 构造聊天数据
     */
    private void LoadData() {
//        messageInfos = new ArrayList<>();

        MessageInfo messageInfo = new MessageInfo();
        messageInfo.setContent("你好，欢迎使用Rance的聊天界面框架");
        messageInfo.setFileType(Constants.CHAT_FILE_TYPE_TEXT);
        messageInfo.setType(Constants.CHAT_ITEM_TYPE_LEFT);
        messageInfo.setHeader("");
        messageInfos.add(messageInfo);

        MessageInfo messageInfo1 = new MessageInfo();
        messageInfo1.setFilepath("http://www.trueme.net/bb_midi/welcome.wav");
        messageInfo1.setVoiceTime(3000);
        messageInfo1.setFileType(Constants.CHAT_FILE_TYPE_VOICE);
        messageInfo1.setType(Constants.CHAT_ITEM_TYPE_RIGHT);
        messageInfo1.setSendState(Constants.CHAT_ITEM_SEND_SUCCESS);
        messageInfo1.setHeader("http://img.dongqiudi" +
                ".com/uploads/avatar/2014/10/20/8MCTb0WBFG_thumb_1413805282863.jpg");
        messageInfos.add(messageInfo1);

        MessageInfo messageInfo2 = new MessageInfo();
        messageInfo2.setFilepath("http://img4.imgtn.bdimg.com/it/u=1800788429," +
                "176707229&fm=21&gp=0.jpg");
        messageInfo2.setFileType(Constants.CHAT_FILE_TYPE_IMAGE);
        messageInfo2.setType(Constants.CHAT_ITEM_TYPE_LEFT);
        messageInfo2.setHeader("http://img0.imgtn.bdimg.com/it/u=401967138,750679164&fm=26&gp=0" +
                ".jpg");
        messageInfos.add(messageInfo2);

        MessageInfo messageInfo3 = new MessageInfo();
        messageInfo3.setContent("[微笑][色][色][色]");
        messageInfo3.setFileType(Constants.CHAT_FILE_TYPE_TEXT);
        messageInfo3.setType(Constants.CHAT_ITEM_TYPE_RIGHT);
        messageInfo3.setSendState(Constants.CHAT_ITEM_SEND_ERROR);
        messageInfo3.setHeader("http://img.dongqiudi" +
                ".com/uploads/avatar/2014/10/20/8MCTb0WBFG_thumb_1413805282863.jpg");
        messageInfos.add(messageInfo3);

        chatAdapter.addAll(messageInfos);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void MessageEventBus(final MessageInfo messageInfo) {
        ChatMessage message = null;
        String type = messageInfo.getFileType();
        switch (type) {
            case Constants.CHAT_FILE_TYPE_TEXT:
                message = ChatUtil.createOutTextMessage(messageInfo.getContent(), chatId);
                break;
            case Constants.CHAT_FILE_TYPE_IMAGE:
                message = ChatUtil.createOutImgMessage(messageInfo.getFilepath(), chatId);
                break;
            case Constants.CHAT_FILE_TYPE_VOICE:
                message = ChatUtil.createOutAudioMessage(messageInfo.getFilepath(), chatId);
                break;
            case Constants.CHAT_FILE_TYPE_VIDEO:
                message = ChatUtil.createOutVideoMessage(messageInfo.getFilepath(), chatId);
                break;

        }
        PocEngineFactory.get().sendMessage(message);
        messageInfo.setHeader("http://img.dongqiudi" +
                ".com/uploads/avatar/2014/10/20/8MCTb0WBFG_thumb_1413805282863.jpg");
        messageInfo.setType(Constants.CHAT_ITEM_TYPE_RIGHT);
        messageInfo.setSendState(Constants.CHAT_ITEM_SENDING);
        messageInfo.setMsgId(message.getId());
        messageInfo.setChatId(message.getChat_id());
        messageInfo.setTime(message.getTime_stamp());
        messageInfo.setObject(message);
        messageInfos.add(messageInfo);
        chatAdapter.notifyItemInserted(messageInfos.size() - 1);
//        chatAdapter.add(messageInfo);
        chatList.scrollToPosition(chatAdapter.getItemCount() - 1);
    }

    @Override
    public void onBackPressed() {
        if (!mDetector.interceptBackPress()) {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MediaRecordFunc.getInstance().destroy();
        PocEngineFactory.get().removeEventHandler(pocEngineEventHandler);
        EventBus.getDefault().removeStickyEvent(this);
        EventBus.getDefault().unregister(this);
    }

    public static void intentToChat(Context context, long chatId) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra(EXTRAS_CHAT_ID, chatId);
        context.startActivity(intent);
    }

    private final IPocEngineEventHandler pocEngineEventHandler = new IPocEngineEventHandler() {

        @Override
        public void onMessageArrived(ChatMessage message) {
            chatAdapter.updateItem(message);
        }

        @Override
        public void onMessageSendFailed(ChatMessage message) {
            chatAdapter.updateItem(message);
        }

        @Override
        public void onMessageFileSendProgressChanged(ChatMessage message) {
            chatAdapter.updateItem(message);
        }

        @Override
        public void onMessageReceived(long chatId, List<ChatMessage> messages) {
            chatAdapter.addChatMessage(messages);
        }
    };

    @Override
    public void clickVoice() {
        Intent intent = new Intent(mContext.getApplicationContext(), AvActivity.class);
        intent.putExtra("callerId", chatId + "");
        intent.putExtra("type", IPocEngineEventHandler.SessionType.TYPE_AUDIO_CALL);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.getApplicationContext().startActivity(intent);
    }

    @Override
    public void clickVideo() {
        Intent intent = new Intent(mContext.getApplicationContext(), AvActivity.class);
        intent.putExtra("callerId", chatId + "");
        intent.putExtra("type", IPocEngineEventHandler.SessionType.TYPE_VIDEO_CALL);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.getApplicationContext().startActivity(intent);
    }

    private void initColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decor = getWindow().getDecorView();
            int ui = decor.getSystemUiVisibility();
            ui |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR; //设置状态栏中字体的颜色为黑色
            //ui &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR; //设置状态栏中字体颜色为白色
            decor.setSystemUiVisibility(ui);
        }
    }
}
