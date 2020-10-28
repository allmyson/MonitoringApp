package com.ys.monitor.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.SurfaceTexture;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hikvision.open.hikvideoplayer.CustomRect;
import com.hikvision.open.hikvideoplayer.HikVideoPlayer;
import com.hikvision.open.hikvideoplayer.HikVideoPlayerCallback;
import com.hikvision.open.hikvideoplayer.HikVideoPlayerFactory;
import com.ys.monitor.R;
import com.ys.monitor.base.BaseActivity;
import com.ys.monitor.rtsp.AutoHideView;
import com.ys.monitor.rtsp.PlayWindowContainer;
import com.ys.monitor.rtsp.PlayerStatus;
import com.ys.monitor.util.L;
import com.ys.monitor.util.StringUtil;

import java.text.DecimalFormat;
import java.text.MessageFormat;

public class PreviewActivity extends BaseActivity implements HikVideoPlayerCallback, TextureView.SurfaceTextureListener{
    /**
     * 播放区域
     */
    protected PlayWindowContainer frameLayout;
    protected TextureView textureView;
    protected ProgressBar progressBar;
    protected TextView playHintText;
    protected TextView digitalScaleText;
    protected AutoHideView autoHideView;

    private String mUri;
    private HikVideoPlayer mPlayer;
    private boolean mDigitalZooming = false;
    private PlayerStatus mPlayerStatus = PlayerStatus.IDLE;//默认闲置

    /**
     * 电子放大倍数格式化,显示小数点后一位
     */
    private DecimalFormat decimalFormat;
    @Override
    public int getLayoutId() {
        return R.layout.activity_preview;
    }

    @Override
    public void initView() {
        setBarColor("#ffffff");
        mUri = getIntent().getStringExtra("path");
        L.e("mUri=" + mUri);
        if (StringUtil.isBlank(mUri)) {
            show("播放地址不能为空！");
            finish();
            return;
        }
        textureView = findViewById(R.id.texture_view);
        progressBar = findViewById(R.id.progress_bar);
        playHintText = findViewById(R.id.result_hint_text);
        digitalScaleText = findViewById(R.id.digital_scale_text);
        autoHideView = findViewById(R.id.auto_hide_view);
        textureView.setSurfaceTextureListener(this);
        initPlayWindowContainer();
        mPlayer = HikVideoPlayerFactory.provideHikVideoPlayer();
        //设置默认值
        mPlayer.setHardDecodePlay(false);
        mPlayer.setSmartDetect(true);
        mPlayer.enableSound(true);

    }

    @Override
    public void getData() {

    }

    @Override
    public void onClick(View v) {
    }

    public static void playRtspVideo(Context context, String path) {
        Intent intent = new Intent(context, PreviewActivity.class);
        intent.putExtra("path", path);
        context.startActivity(intent);
    }
    private void initPlayWindowContainer() {
        frameLayout = findViewById(R.id.frame_layout);
        frameLayout.setOnClickListener(new PlayWindowContainer.OnClickListener() {
            @Override
            public void onSingleClick() {
                if (autoHideView.isVisible()) {
                    autoHideView.hide();
                } else {
                    autoHideView.show();
                }
            }
        });
        frameLayout.setOnDigitalListener(new PlayWindowContainer.OnDigitalZoomListener() {
            @Override
            public void onDigitalZoomOpen() {
                executeDigitalZoom();
            }
        });
    }
    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1) {

    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

    }

    /**
     * 执行电子放大操作
     */
    private void executeDigitalZoom(){
        if (mPlayerStatus != PlayerStatus.SUCCESS) {
            ToastUtils.showShort("没有视频在播放");
        }
        if (decimalFormat == null){
            decimalFormat = new DecimalFormat("0.0");
        }
        if (!mDigitalZooming){
            frameLayout.setOnScaleChangeListener(new PlayWindowContainer.OnDigitalScaleChangeListener() {
                @Override
                public void onDigitalScaleChange(float scale) {
                    L.e("onDigitalScaleChange scale = "+scale);
                    if (scale < 1.0f && mDigitalZooming){
                        //如果已经开启了电子放大且倍率小于1就关闭电子放大
                        executeDigitalZoom();
                    }
                    if (scale>= 1.0f){
                        digitalScaleText.setText(MessageFormat.format("{0}X",decimalFormat.format(scale)));
                    }
                }

                @Override
                public void onDigitalRectChange(CustomRect oRect, CustomRect curRect) {
                    mPlayer.openDigitalZoom(oRect, curRect);
                }
            });
            ToastUtils.showShort("电子放大开启");
            mDigitalZooming = true;
            digitalScaleText.setVisibility(View.VISIBLE);
            digitalScaleText.setText(MessageFormat.format("{0}X",decimalFormat.format(1.0f)));
        }else {
            ToastUtils.showShort("电子放大关闭");
            mDigitalZooming = false;
            digitalScaleText.setVisibility(View.GONE);
            frameLayout.setOnScaleChangeListener(null);
            mPlayer.closeDigitalZoom();
        }
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        layoutViews();
    }

    /**
     * 屏幕方向变化后重新布局View
     */
    private void layoutViews() {
        ViewGroup.LayoutParams layoutParams = frameLayout.getLayoutParams();
        if (ScreenUtils.isPortrait()) {
            //先显示系统状态栏
            showSystemUI();
            //再显示控制按钮区域
            layoutParams.height = SizeUtils.dp2px(250f);
            showOrHideControlArea(true);
        } else if (ScreenUtils.isLandscape()) {
            //隐藏系统UI
            hideSystemUI();
            showOrHideControlArea(false);
            layoutParams.height = ScreenUtils.getScreenHeight();
        }
    }

    /**
     * 显示或隐藏控制区域
     *
     * @param isShow true-显示，false-隐藏
     */
    private void showOrHideControlArea(boolean isShow) {
        int visibility = isShow ? View.VISIBLE : View.GONE;
    }

    /**
     * 隐藏系统ui
     */
    private void hideSystemUI() {
        //隐藏ActionBar 如果使用了NoActionBar的Theme则不需要隐藏actionBar

        //TODO：View.setSystemUiVisibility(int visibility)中，visibility是Mode与Layout任意取值的组合，可传入的实参为：

        // Mode属性
        //View.SYSTEM_UI_FLAG_LOW_PROFILE：状态栏显示处于低能显示状态(low profile模式)，状态栏上一些图标显示会被隐藏。
        //View.SYSTEM_UI_FLAG_FULLSCREEN：Activity全屏显示，且状态栏被隐藏覆盖掉。等同于（WindowManager.LayoutParams.FLAG_FULLSCREEN）
        //View.SYSTEM_UI_FLAG_HIDE_NAVIGATION：隐藏虚拟按键(导航栏)。有些手机会用虚拟按键来代替物理按键。
        //View.SYSTEM_UI_FLAG_IMMERSIVE：这个flag只有当设置了SYSTEM_UI_FLAG_HIDE_NAVIGATION才起作用。
        // 如果没有设置这个flag，任意的View相互动作都退出SYSTEM_UI_FLAG_HIDE_NAVIGATION模式。如果设置就不会退出。
        //View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY：这个flag只有当设置了SYSTEM_UI_FLAG_FULLSCREEN | SYSTEM_UI_FLAG_HIDE_NAVIGATION 时才起作用。
        // 如果没有设置这个flag，任意的View相互动作都坏退出SYSTEM_UI_FLAG_FULLSCREEN | SYSTEM_UI_FLAG_HIDE_NAVIGATION模式。如果设置就不受影响。

        // Layout属性
        //View.SYSTEM_UI_FLAG_LAYOUT_STABLE： 保持View Layout不变，隐藏状态栏或者导航栏后，View不会拉伸。
        //View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN：让View全屏显示，Layout会被拉伸到StatusBar下面，不包含NavigationBar。
        //View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION：让View全屏显示，Layout会被拉伸到StatusBar和NavigationBar下面。
//        View decorView = getWindow().getDecorView();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            decorView.setSystemUiVisibility(
//                    View.SYSTEM_UI_FLAG_LOW_PROFILE
//                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
//                            | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
//                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//            );
//        }
//        //解决在华为手机上横屏时，状态栏不消失的问题
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //隐藏状态栏

    }

    /**
     * 显示系统UI
     */
    private void showSystemUI() {
        //显示ActionBar 如果使用了NoActionBar的Theme则不需要显示actionBar
//        View decorView = getWindow().getDecorView();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//            // This snippet shows the system bars. It does this by removing all the flags
//            // except for the ones that make the content appear under the system bars.
//            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
//        }
//        //解决在华为手机上横屏时，状态栏不消失的问题
//        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //显示状态栏
    }
    @Override
    protected void onResume() {
        super.onResume();
        //TODO 注意:APP前后台切换时 SurfaceTextureListener可能在有某些 华为手机 上不会回调，例如：华为P20，所以我们在这里手动调用
        if (textureView.isAvailable()) {
            L.e("onResume: onSurfaceTextureAvailable");
            onSurfaceTextureAvailable(textureView.getSurfaceTexture(), textureView.getWidth(), textureView.getHeight());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        //TODO 注意:APP前后台切换时 SurfaceTextureListener可能在有某些 华为手机 上不会回调，例如：华为P20，所以我们在这里手动调用
        if (textureView.isAvailable()) {
            L.e("onPause: onSurfaceTextureDestroyed");
            onSurfaceTextureDestroyed(textureView.getSurfaceTexture());
        }
    }


    /**
     * 开始播放
     *
     * @param surface 渲染画面
     */
    private void startRealPlay(SurfaceTexture surface) {
        mPlayerStatus = PlayerStatus.LOADING;
        progressBar.setVisibility(View.VISIBLE);
        playHintText.setVisibility(View.GONE);
        mPlayer.setSurfaceTexture(surface);
        //TODO 注意: startRealPlay() 方法会阻塞当前线程，需要在子线程中执行,建议使用RxJava
        new Thread(() -> {
            //TODO 注意: 不要通过判断 startRealPlay() 方法返回 true 来确定播放成功，播放成功会通过HikVideoPlayerCallback回调，startRealPlay() 方法返回 false 即代表 播放失败;
            if (!mPlayer.startRealPlay(mUri, PreviewActivity.this)) {
                onPlayerStatus(Status.FAILED, mPlayer.getLastError());
            }
        }).start();
    }


    /**
     * 播放结果回调
     *
     * @param status    共四种状态：SUCCESS（播放成功）、FAILED（播放失败）、EXCEPTION（取流异常）、FINISH（回放结束）
     * @param errorCode 错误码，只有 FAILED 和 EXCEPTION 才有值
     */
    @Override
    @WorkerThread
    public void onPlayerStatus(@NonNull Status status, int errorCode) {
        //TODO 注意: 由于 HikVideoPlayerCallback 是在子线程中进行回调的，所以一定要切换到主线程处理UI
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
                //只有播放成功时，才允许开启电子放大
                frameLayout.setAllowOpenDigitalZoom(status == Status.SUCCESS);
                switch (status) {
                    case SUCCESS:
                        //播放成功
                        mPlayerStatus = PlayerStatus.SUCCESS;
                        playHintText.setVisibility(View.GONE);
                        textureView.setKeepScreenOn(true);//保持亮屏
                        break;
                    case FAILED:
                        //播放失败
                        mPlayerStatus = PlayerStatus.FAILED;
                        playHintText.setVisibility(View.VISIBLE);
                        playHintText.setText(MessageFormat.format("预览失败，错误码：{0}", Integer.toHexString(errorCode)));
                        break;
                    case EXCEPTION:
                        //取流异常
                        mPlayerStatus = PlayerStatus.EXCEPTION;
                        mPlayer.stopPlay();//TODO 注意:异常时关闭取流
                        playHintText.setVisibility(View.VISIBLE);
                        playHintText.setText(MessageFormat.format("取流发生异常，错误码：{0}", Integer.toHexString(errorCode)));
                        break;
                }
            }
        });
    }
}
