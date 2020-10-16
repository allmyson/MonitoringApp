package com.ys.monitor.base;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.DisplayMetrics;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.huamai.poc.IPocEngineEventHandler;
import com.huamai.poc.PocEngine;
import com.huamai.poc.PocEngineFactory;
import com.huamai.poc.chat.ChatMessageCategory;
import com.huamai.poc.chat.ChatMessageStatus;
import com.huamai.poc.greendao.ChatMessage;
import com.tencent.bugly.crashreport.CrashReport;
import com.unionbroad.app.PocApplication;
import com.unionbroad.app.eventbus.ChannelChangedEvent;
import com.unionbroad.app.util.FileDownloadManager;
import com.unionbroad.app.util.UnionUtils;
import com.yanzhenjie.nohttp.InitializationConfig;
import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.cookie.DBCookieStore;
import com.ys.monitor.activity.AvActivity;
import com.ys.monitor.http.CookieListener;
import com.ys.monitor.sp.LocationSP;
import com.ys.monitor.util.Constant;
import com.ys.monitor.util.L;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author lh
 * @version 1.0.0
 * @filename App
 * @description -------------------------------------------------------
 * @date 2018/10/23 16:01
 */
public class App extends PocApplication {
    /**
     * 屏幕宽度
     */
    public static int screenWidth;
    /**
     * 屏幕高度
     */
    public static int screenHeight;
    /**
     * 屏幕密度
     */
    public static float screenDensity;

    @Override
    public void onCreate() {
        super.onCreate();
        Constant.buildFile();
        NoHttp.initialize(InitializationConfig.newBuilder(this).cookieStore(new DBCookieStore(this).setCookieStoreListener(new CookieListener(this))).build());
        Logger.setDebug(true);
        initBugly();
        initScreenSize();
        /** 防止反复初始化 */
        if (getPackageName() != null && getPackageName().equals(getProcessName(android.os.Process.myPid()))) {
            initBroadcast();
            PocEngineFactory.initialize(this, getConfig());
            //可以在Application中监听，也可以在Service中监听，主要希望全局监听来电和新消息
            PocEngineFactory.get().addEventHandler(pocEventHandler);
        }
    }

    private void initBugly() {
        CrashReport.initCrashReport(getApplicationContext(), "4fe346068c", true);
    }

    /**
     * 初始化当前设备屏幕宽高
     */
    private void initScreenSize() {
        DisplayMetrics curMetrics = getApplicationContext().getResources().getDisplayMetrics();
        screenWidth = curMetrics.widthPixels;
        screenHeight = curMetrics.heightPixels;
        screenDensity = curMetrics.density;
    }

    private PocEngine.Configure getConfig() {
        PocEngine.Configure configure = PocEngineConfigureProducer.getConfig();
        /** 可选配置 */
        //定位模式
        configure.gpsMode = IPocEngineEventHandler.GpsMode.NET_AND_GPS;
        //位置上报时间间隔，单位秒
        configure.gpsReportInterval = 300;
        //联系人状态刷新时间间隔，单位秒
        configure.statusUpdateInterval = 60;
        //使用音乐通道
        configure.useMusicStream = false;
        //对讲录音
        configure.pttPlayback = true;
        //防杀服务
        configure.keepLiveService = true;
        //视频通话分辨率，需要和调度台中设置的值一样才能生效
        configure.videoResolution = IPocEngineEventHandler.VideoResolution.RESOLUTION_640X480;
        //缓存提供器，没特殊要求用不到
        //configure.cacheFileSupplier = cacheFileSupplier;
        //侧边功能键注册，一般都用不到
        //configure.broadcastHotKeyActionSupplier = hotKeyActionSupplier;
        //其它...
        return configure;
    }

    /**
     * 目前配置项较多，很多配置没有单独的设置接口，建议全局保持一个Configure，
     * 确保每次 PocEngineFactory.get().config()时，设置进去的配置项都是有效值
     */
    public static class PocEngineConfigureProducer {

        static PocEngine.Configure configure;

        public static PocEngine.Configure getConfig() {
            if (configure == null) {
                configure = new PocEngine.Configure();
                /** 必须设置 */
//                configure.ip = "123.57.6.84";
//                configure.port = "5060";
//                configure.httpPort = "80";
                configure.ip = "123.57.92.107";
                configure.port = "6050";
                configure.httpPort = "80";
                configure.gpsMode = IPocEngineEventHandler.GpsMode.NET_AND_GPS;
            }
            return configure;
        }
    }

    /**
     * 缓存文件路径及格式提供器
     */
    private final IPocEngineEventHandler.CacheFileSupplier cacheFileSupplier =
            new IPocEngineEventHandler.CacheFileSupplier() {
                @Override
                public String getBaseDir() {
                    //TODO 返回一个缓存根目录路径，需要创建
                    return Constant.DOWNLOAD_PATH;
                }

                @Override
                public String getFileNameExpansion() {
                    //TODO 返回扩展文件名，即 用户id + ????? + .格式 中问号这一段，缺省时，会按照日期的格式
                    return null;
                }

                /**
                 * 每需要缓存一个文件时，会调用一次这个接口，根据后缀返回相应的全路径，路径要创建好
                 * @param fileFormat 文件后缀
                 * @return
                 */
                @Override
                public String getFullCacheFilePath(String fileFormat) {
                    String baseCache;
                    if (fileFormat.contains("3pg") || fileFormat.contains("mp4")) {
                        baseCache = UnionUtils.getVideoCachePath();
                    } else if (fileFormat.contains("jpg") || fileFormat.contains("png")) {
                        baseCache = UnionUtils.getPicCachePath();
                    } else if (fileFormat.contains("raw") || fileFormat.contains("wav")
                            || fileFormat.contains("amr") || fileFormat.contains("mp3")) {
                        baseCache = UnionUtils.getAudioFileCachePath();
                    } else {
                        baseCache = UnionUtils.getBaseCachePath();
                    }


                    Date date = new Date();
                    String ymd = new SimpleDateFormat("yyyyMMdd").format(date);
                    String saveDir = baseCache + File.separator + ymd;
                    File dir = new File(saveDir);
                    if (!dir.exists()) {
                        dir.mkdir();
                    }
                    StringBuilder builder = new StringBuilder();
                    builder.append(PocApplication.sUserNumber);
                    builder.append("_");
                    builder.append(ymd);
                    builder.append("_");
                    builder.append("test");
                    builder.append(fileFormat);
                    String fileName = builder.toString();

                    String path = new File(saveDir, fileName).getPath();
                    return path;
                }
            };


    /**
     * 机器PTT热键适配：
     * 如果集成了SDK的应用，想要支持多款机器的热键(侧边功能键)，可以在这里进行配置
     */
    private final IPocEngineEventHandler.BroadcastHotKeyActionSupplier hotKeyActionSupplier =
            new IPocEngineEventHandler.BroadcastHotKeyActionSupplier() {
                @Override
                public String[] getPttDownActions() {
                    return new String[]{
                            "phone1.test.ptt.down",
                            "phone2.test.ptt.down"
                    };
                }

                @Override
                public String[] getPttUpActions() {
                    return new String[]{
                            "phone1.test.ptt.up",
                            "phone2.test.ptt.up"
                    };
                }
            };

    private final IPocEngineEventHandler pocEventHandler = new IPocEngineEventHandler() {

        @Override
        public void onMessageReceived(long chatId, List<ChatMessage> messages) {
            L.d("onMessageReceived-> " + chatId + " size=" + messages.size());
            //收到新的消息
            if (messages != null && messages.size() > 0) {
                //仅为调试，只取出其中一条
                ChatMessage message = messages.get(0);
                //语音通话记录
                if (message.getCategory() == ChatMessageCategory.AUDIO) {
                    L.i("==> 语音通话记录");
                }
                //视频通话记录
                else if (message.getCategory() == ChatMessageCategory.VIDEO) {
                    L.i("==> 视频通话记录");
                }
                //语音广播
                else if (message.getCategory() == ChatMessageCategory.AUDIO_BROADCAST) {
                    L.i("==> 语音广播");
                }
                //语音文件，类似微信的语音消息
                else if (message.getCategory() == ChatMessageCategory.AUDIO_FILE) {
                    L.i("==> 语音消息");
                }
                //对讲录音，只有开启对讲回放功能，每次有人讲话时，才会把声音录制下来
                else if (message.getCategory() == ChatMessageCategory.PTT_AUDIO_FILE) {
                    L.i("==> 对讲录音消息");
                }
                //视频消息，类似微信的视频消息
                else if (message.getCategory() == ChatMessageCategory.VIDEO_FILE) {
                    L.i("==> 视频消息: " + message.getDownload_status());
                    switch (message.getDownload_status()) {
                        case ChatMessageStatus.File.DOWN_SUCCESSED:
                            message.setDownload_status(ChatMessageStatus.File.DOWN_SUCCESSED);
                            //play
                            break;
                        case ChatMessageStatus.File.DOWN_UNINIT:
                            message.setDownload_status(ChatMessageStatus.File.DOWN_STARTING);
                            FileDownloadManager.getInstance().startDownTask(message.getId(),
                                    message.getHttpFile(), message.getLocalFile());
                            //使用EventBus监听ChatFileDownloadCompletedEvent，获取文件下载结果
                            break;
                    }
                }
                //图片消息
                else if (message.getCategory() == ChatMessageCategory.IMAGE) {
                    L.i("==> 图片消息");
                }
                //位置消息
                else if (message.getCategory() == ChatMessageCategory.LOCATION) {
                    L.i("==> 位置消息");
                }
                //文字消息
                else if (message.getCategory() == ChatMessageCategory.TEXT) {
                    L.i("==> 文字消息: " + message.getText());
                }

                //================以下两种消息，如果业务需求，可以放到动态页中去=========================
                //拍传@消息
                else if (message.getCategory() == ChatMessageCategory.NOTIFICATION_REPORT) {
                    L.i("==> 拍传@消息");
                }
                //报警消息
                else if (message.getCategory() == ChatMessageCategory.ALERT) {
                    L.i("==> 报警消息: " + message.getText());
                }
                //任务消息
                else if (message.getCategory() == ChatMessageCategory.NOTIFICATION_TASK) {
                    L.i("==> 任务消息: " + message.getText());
                }
                //==================================================================================

                //其它消息一般用不到
                else {
                    L.i("==> 其它消息: " + message.getText());
                }
            }
        }

        @Override
        public void onIncoming(IncomingInfo incomingInfo) {
            L.d("onIncoming-> sessionId=" + incomingInfo.sessionId + " type=" + incomingInfo
                    .sessionType + " uid=" +
                    incomingInfo.callerId + " name=" + incomingInfo.callerName + " level=" +
                    incomingInfo.level +
                    " extra=" + incomingInfo.extra);
            Intent intent = new Intent(App.this, AvActivity.class);
            intent.putExtra("sessionId", incomingInfo.sessionId);
            intent.putExtra("callerId", incomingInfo.callerId);
            intent.putExtra("callerName", incomingInfo.callerName);
            intent.putExtra("type", incomingInfo.sessionType);
            intent.putExtra("extra", incomingInfo.extra);
            intent.putExtra("isIncomingCall", true);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            App.this.startActivity(intent);
        }

        @Override
        public void onIgnoreIncoming(int sessionType, long callerId) {
            super.onIgnoreIncoming(sessionType, callerId);
        }

        @Override
        public void onChannelChangedEvent(ChannelChangedEvent event) {
            Toast.makeText(getApplicationContext(), "onChannelChangedEvent", Toast.LENGTH_SHORT).show();
        }

        @Override
        public boolean onForcedOffline() {
            //True: 响应了下线事件，False：没有响应，sdk内部会弹出对话框，禁止再操作
            Toast.makeText(getApplicationContext(), "onForcedOffline", Toast.LENGTH_SHORT).show();
            return false;
        }

        @Override
        public void onBackstageMonitorAction(int action, long sessionId, String monitorUid) {
            L.i("onBackstageMonitorAction: action=" + action);
        }

        @Override
        public void onReceiveLocation(double latitude, double longitude, String address) {
            L.e("latitude=" + latitude + "--lon=" + longitude + "--addr=" + address);
//            Toast.makeText(getApplicationContext(),
//                    "坐标变化(" + latitude + "," + longitude + "," + address + ")",
//                    Toast.LENGTH_SHORT).show();
            LocationSP.saveLocation(App.getContext(), latitude, longitude, address);
        }


        @Override
        public void onRequestCamera() {
            Toast.makeText(getApplicationContext(), "请求Camera", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onReleaseCamera() {
            Toast.makeText(getApplicationContext(), "释放Camera", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRequestMic() {
            Toast.makeText(getApplicationContext(), "请求Mic", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onReleaseMic() {
            Toast.makeText(getApplicationContext(), "释放Mic", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onVideoStreamTakePictureFinish(String filePath) {
            Toast.makeText(getApplicationContext(), "已保存到: " + filePath, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCallConnected(long sessionId, String remoteId, int sessionType) {
        }

    };

    private String getProcessName(int pid) {
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo processInfo : runningApps) {
            if (processInfo.pid == pid) {
                return processInfo.processName;
            }
        }
        return null;
    }

    private SDKReceiver mReceiver;

    private void initBroadcast() {
        IntentFilter iFilter = new IntentFilter();
        iFilter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_OK);
        iFilter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
        iFilter.addAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);
        mReceiver = new SDKReceiver();
        registerReceiver(mReceiver, iFilter);
    }

    //定位模块状态通知
    public class SDKReceiver extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
            String s = intent.getAction();
            L.d("SDKReceiver: " + s);
            if (s.equals(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR)) {
                L.d("key 验证出错! 错误码");
            } else if (s.equals(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_OK)) {
                L.d("key 验证成功! 功能可以正常使用");
            } else if (s.equals(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR)) {
                L.d("网络出错");
            }
        }
    }

}
