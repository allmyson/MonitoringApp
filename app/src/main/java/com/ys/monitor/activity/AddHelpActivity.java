package com.ys.monitor.activity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
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

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.huamai.poc.IPocEngineEventHandler;
import com.huamai.poc.PocEngine;
import com.huamai.poc.PocEngineFactory;
import com.huamai.poc.greendao.User;
import com.labo.kaji.relativepopupwindow.RelativePopupWindow;
import com.yanzhenjie.nohttp.rest.Response;
import com.ys.monitor.R;
import com.ys.monitor.api.FunctionApi;
import com.ys.monitor.base.BaseActivity;
import com.ys.monitor.bean.BaseBean;
import com.ys.monitor.bean.FileUploadBean;
import com.ys.monitor.bean.FireBean;
import com.ys.monitor.bean.GPSBean;
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
import com.ys.monitor.http.HttpListener;
import com.ys.monitor.sp.UserSP;
import com.ys.monitor.util.GPSUtil;
import com.ys.monitor.util.HttpUtil;
import com.ys.monitor.util.L;
import com.ys.monitor.util.StringUtil;
import com.ys.monitor.util.YS;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddHelpActivity extends BaseActivity {
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
    private PocEngine pocEngine;
    private String number;
    private double gis_jd = 0;
    private double gis_wd = 0;

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_help;
    }

    @Override
    public void initView() {
        fireList = new ArrayList<>();
        messageInfos = new ArrayList<>();
        setBarColor2("#ffffff", true);
//        setBarColor("#ffffff");
        titleView.setText("指挥中心");
        findViewByIds();
        EventBus.getDefault().register(this);
        initWidget();
        userId = UserSP.getUserId(mContext);
        pocEngine = PocEngineFactory.get();
        getAddress();
        getFire();
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
        fragments = new ArrayList<>();
        chatEmotionFragment = new ChatEmotionFragment();
        fragments.add(chatEmotionFragment);
        chatFunctionFragment = new ChatFunctionFragment();
        fragments.add(chatFunctionFragment);
        adapter = new CommonFragmentPagerAdapter(getSupportFragmentManager(), fragments);
        viewpager.setAdapter(adapter);
        viewpager.setCurrentItem(0);

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
                    FunctionApi.playVideo(mContext, messageInfos.get(position).getFilepath());
                }

                @Override
                public void onVoiceClick(final ImageView imageView, final int position) {
                    show("点了声音");
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

    private Map<String, Object> map;
    FireBean.DataBean.RowsBean rowsBean;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void MessageEventBus(final MessageInfo messageInfo) {
        messageInfo.setHeader("http://img.dongqiudi" +
                ".com/uploads/avatar/2014/10/20/8MCTb0WBFG_thumb_1413805282863.jpg");
        messageInfo.setType(Constants.CHAT_ITEM_TYPE_RIGHT);
        messageInfo.setSendState(Constants.CHAT_ITEM_SENDING);
        messageInfos.add(messageInfo);
        chatAdapter.notifyItemInserted(messageInfos.size() - 1);
//        chatAdapter.add(messageInfo);
        chatList.scrollToPosition(chatAdapter.getItemCount() - 1);
        map = new HashMap<>();
        FireBean.DataBean.RowsBean rowsBean = getNearFireBean();
        if (rowsBean == null) {
            rowsBean = new FireBean.DataBean.RowsBean();
        }
        map.put("warnInfoNo", StringUtil.valueOf(rowsBean.recNo));
        map.put("fireTitle", StringUtil.valueOf(rowsBean.name));
        int reportingType = 0;
        String content = "";
        if (Constants.CHAT_FILE_TYPE_TEXT.equals(messageInfo.getFileType())) {
            reportingType = 0;
            map.put("reportingType", "" + reportingType);
            map.put("content", StringUtil.valueOf(messageInfo.getContent()));
            String json = new Gson().toJson(map);
            L.e("json=" + json);
            HttpUtil.addHelpMsgWithNoDialog(mContext, userId, json, new HttpListener<String>() {
                @Override
                public void onSucceed(int what, Response<String> response) {
                    try {
                        BaseBean baseBean = new Gson().fromJson(response.get(), BaseBean.class);
                        if (baseBean != null && YS.SUCCESE.equals(baseBean.code)) {
                            messageInfo.setSendState(Constants.CHAT_ITEM_SEND_SUCCESS);
                            chatAdapter.notifyDataSetChanged();
                        } else {
                            messageInfo.setSendState(Constants.CHAT_ITEM_SEND_ERROR);
                            chatAdapter.notifyDataSetChanged();
                        }
                    } catch (JsonSyntaxException e) {
                        e.printStackTrace();
                        messageInfo.setSendState(Constants.CHAT_ITEM_SEND_ERROR);
                        chatAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailed(int what, Response<String> response) {
                    messageInfo.setSendState(Constants.CHAT_ITEM_SEND_ERROR);
                    chatAdapter.notifyDataSetChanged();
                }
            });
        } else if (Constants.CHAT_FILE_TYPE_IMAGE.equals(messageInfo.getFileType()) || Constants.CHAT_FILE_TYPE_VIDEO.equals(messageInfo.getFileType())) {
            if (Constants.CHAT_FILE_TYPE_IMAGE.equals(messageInfo.getFileType())) {
                reportingType = 1;
            } else if (Constants.CHAT_FILE_TYPE_VIDEO.equals(messageInfo.getFileType())) {
                reportingType = 2;
            }
            map.put("reportingType", "" + reportingType);
            List<String> fileList = new ArrayList<>();
            fileList.add(messageInfo.getFilepath());
            HttpUtil.uploadFileWithNoDialog(mContext, userId, YS.FileType.FILE_PJ, fileList,
                    new HttpListener<String>() {
                        @Override
                        public void onSucceed(int what, Response<String> response) {
                            try {
                                FileUploadBean fileUploadBean = new Gson().fromJson(response.get(),
                                        FileUploadBean.class);
                                if (fileUploadBean != null && YS.SUCCESE.equals(fileUploadBean.code) && fileUploadBean.data != null) {
                                    map.put("content", fileUploadBean.data.url);
                                    String json = new Gson().toJson(map);
                                    L.e("json=" + json);
                                    HttpUtil.addHelpMsgWithNoDialog(mContext, userId, json,
                                            new HttpListener<String>() {
                                                @Override
                                                public void onSucceed(int what,
                                                                      Response<String> response) {
                                                    BaseBean baseBean =
                                                            new Gson().fromJson(response.get(),
                                                                    BaseBean.class);
                                                    if (baseBean != null && YS.SUCCESE.equals(baseBean.code)) {
                                                        messageInfo.setSendState(Constants.CHAT_ITEM_SEND_SUCCESS);
                                                        chatAdapter.notifyDataSetChanged();
                                                    } else {
                                                        messageInfo.setSendState(Constants.CHAT_ITEM_SEND_ERROR);
                                                        chatAdapter.notifyDataSetChanged();
                                                    }
                                                }

                                                @Override
                                                public void onFailed(int what,
                                                                     Response<String> response) {
                                                    messageInfo.setSendState(Constants.CHAT_ITEM_SEND_ERROR);
                                                    chatAdapter.notifyDataSetChanged();
                                                }
                                            });

                                } else {
                                    L.e("附件上传失败!");
                                    messageInfo.setSendState(Constants.CHAT_ITEM_SEND_ERROR);
                                    chatAdapter.notifyDataSetChanged();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                messageInfo.setSendState(Constants.CHAT_ITEM_SEND_ERROR);
                                chatAdapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onFailed(int what, Response<String> response) {
                            messageInfo.setSendState(Constants.CHAT_ITEM_SEND_ERROR);
                            chatAdapter.notifyDataSetChanged();
                        }
                    });
        }
//        new Handler().postDelayed(new Runnable() {
//            public void run() {
//                messageInfo.setSendState(Constants.CHAT_ITEM_SEND_SUCCESS);
//                chatAdapter.notifyDataSetChanged();
//            }
//        }, 2000);
//        new Handler().postDelayed(new Runnable() {
//            public void run() {
//                MessageInfo message = new MessageInfo();
//                message.setContent("这是模拟消息回复");
//                message.setType(Constants.CHAT_ITEM_TYPE_LEFT);
//                message.setFileType(Constants.CHAT_FILE_TYPE_TEXT);
//                message.setHeader("http://img0.imgtn.bdimg.com/it/u=401967138," +
//                        "750679164&fm=26&gp=0.jpg");
//                messageInfos.add(message);
//                chatAdapter.notifyItemInserted(messageInfos.size() - 1);
//                chatList.scrollToPosition(chatAdapter.getItemCount() - 1);
//            }
//        }, 3000);
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
        EventBus.getDefault().removeStickyEvent(this);
        EventBus.getDefault().unregister(this);
    }

    private void getAddress() {
        if (pocEngine.hasServiceConnected()) {
            if (!pocEngine.isDisableInternalGpsFunc()) {
                User user = pocEngine.getCurrentUser();
                number = "" + user.getNumber();
                L.e("user=" + user.toString());
                List<User> list = new ArrayList<>();
                list.add(user);
                pocEngine.getUserGPS(list, new IPocEngineEventHandler.Callback<String>() {
                    @Override
                    public void onResponse(final String json) {
                        //回调在子线程
                        new Handler().post(new Runnable() {
                            @Override
                            public void run() {
                                L.e("json=" + json);
                                if (StringUtil.isGoodJson(json)) {
                                    List<GPSBean> ll = new Gson().fromJson(json,
                                            new TypeToken<List<GPSBean>>() {
                                            }.getType());
                                    if (ll != null && ll.size() > 0) {
                                        for (GPSBean gpsBean : ll) {
                                            if (number.equals(gpsBean.exten)) {
                                                double[] gps =
                                                        GPSUtil.bd09_To_gps84(StringUtil.StringToDouble(gpsBean.gis_wd),
                                                                StringUtil.StringToDouble(gpsBean.gis_jd));
                                                gis_wd = gps[0];
                                                gis_jd = gps[1];
                                                break;
                                            }
                                        }
                                    }

                                }
                            }
                        });
                    }
                });
            }
        }
    }

    private List<FireBean.DataBean.RowsBean> fireList;

    private void getFire() {
        HttpUtil.getFireListWithNoDialog(mContext, userId, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                fireList.clear();
                try {
                    FireBean fireBean = new Gson().fromJson(response.get(), FireBean.class);
                    if (fireBean != null) {
                        if (YS.SUCCESE.equals(fireBean.code) && fireBean.data != null && fireBean
                                .data.rows != null && fireBean.data.rows.size() > 0) {
                            for (FireBean.DataBean.RowsBean bean : fireBean.data.rows) {
                                if (YS.FireStatus.Status_DCL.equals(bean.status) || YS.FireStatus.Status_HSZ.equals(bean.status)) {
                                    fireList.add(bean);
                                }
                            }
                        }
                    } else {
                        show(YS.HTTP_TIP);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
//                rowsBean = getNearFireBean();
            }

            @Override
            public void onFailed(int what, Response<String> response) {
            }
        });
    }

    private FireBean.DataBean.RowsBean getNearFireBean() {
        List<Double> doubleList = new ArrayList<>();
        if (fireList != null && fireList.size() > 0 && gis_wd != 0 && gis_jd != 0) {
            for (FireBean.DataBean.RowsBean rowsBean : fireList) {
                double distance = GPSUtil.getDistance(gis_wd, gis_jd,
                        StringUtil.StringToDouble(rowsBean.latitude),
                        StringUtil.StringToDouble(rowsBean.longitude));
                L.e("id=" + rowsBean.recNo + "-name=" + rowsBean.name + "-distance=" + distance);
                doubleList.add(distance);
            }
            double min = doubleList.get(0);
            int min_index = 0;
            for (int i = 0; i < doubleList.size(); i++) {
                if (doubleList.get(i) < min) {//比较后赋值
                    min = doubleList.get(i);
                    min_index = i;
                }
            }
            FireBean.DataBean.RowsBean bean = fireList.get(min_index);
            L.e("最短距离=" + min + "-下标" + min_index + "--fireId=" + bean.recNo + "--fireTitle=" + bean.name);
            return bean;
        }
        return null;
    }

}
