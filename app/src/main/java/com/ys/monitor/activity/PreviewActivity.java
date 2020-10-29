package com.ys.monitor.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.SurfaceTexture;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;
import android.view.TextureView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.hikvision.open.hikvideoplayer.CustomRect;
import com.hikvision.open.hikvideoplayer.HikVideoPlayer;
import com.hikvision.open.hikvideoplayer.HikVideoPlayerCallback;
import com.hikvision.open.hikvideoplayer.HikVideoPlayerFactory;
import com.ys.monitor.R;
import com.ys.monitor.base.BaseActivity;
import com.ys.monitor.rtsp.PlayWindowContainer;
import com.ys.monitor.rtsp.PlayerStatus;
import com.ys.monitor.util.L;
import com.ys.monitor.util.StringUtil;

import java.text.DecimalFormat;
import java.text.MessageFormat;

public class PreviewActivity extends BaseActivity implements HikVideoPlayerCallback,
        TextureView.SurfaceTextureListener {
    /**
     * 播放区域
     */
    protected PlayWindowContainer frameLayout;
    protected TextureView textureView;
    protected ProgressBar progressBar;
    protected TextView playHintText;
    protected TextView digitalScaleText;

    private String mUri;
    private HikVideoPlayer mPlayer;
    private boolean mDigitalZooming = false;
    private PlayerStatus mPlayerStatus = PlayerStatus.IDLE;//默认闲置
    private boolean isFirst = true;
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
//        setBarColor("#ffffff");
        setTransparent();
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
//        if (mPlayerStatus != PlayerStatus.SUCCESS) {
//            startRealPlay(textureView.getSurfaceTexture());
//        }
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
        frameLayout.setOnDigitalListener(new PlayWindowContainer.OnDigitalZoomListener() {
            @Override
            public void onDigitalZoomOpen() {
                executeDigitalZoom();
            }
        });
    }


    /**
     * 执行电子放大操作
     */
    private void executeDigitalZoom() {
        if (mPlayerStatus != PlayerStatus.SUCCESS) {
            ToastUtils.showShort("没有视频在播放");
        }
        if (decimalFormat == null) {
            decimalFormat = new DecimalFormat("0.0");
        }
        if (!mDigitalZooming) {
            frameLayout.setOnScaleChangeListener(new PlayWindowContainer.OnDigitalScaleChangeListener() {
                @Override
                public void onDigitalScaleChange(float scale) {
                    L.e("onDigitalScaleChange scale = " + scale);
                    if (scale < 1.0f && mDigitalZooming) {
                        //如果已经开启了电子放大且倍率小于1就关闭电子放大
                        executeDigitalZoom();
                    }
                    if (scale >= 1.0f) {
                        digitalScaleText.setText(MessageFormat.format("{0}X",
                                decimalFormat.format(scale)));
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
            digitalScaleText.setText(MessageFormat.format("{0}X", decimalFormat.format(1.0f)));
        } else {
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
    }


    @Override
    protected void onResume() {
        super.onResume();
        //TODO 注意:APP前后台切换时 SurfaceTextureListener可能在有某些 华为手机 上不会回调，例如：华为P20，所以我们在这里手动调用
        if (textureView.isAvailable()) {
            L.e("onResume: onSurfaceTextureAvailable");
            onSurfaceTextureAvailable(textureView.getSurfaceTexture(), textureView.getWidth(),
                    textureView.getHeight());
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
            //TODO 注意: 不要通过判断 startRealPlay() 方法返回 true
            // 来确定播放成功，播放成功会通过HikVideoPlayerCallback回调，startRealPlay() 方法返回 false 即代表 播放失败;
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
                        playHintText.setText(MessageFormat.format("预览失败，错误码：{0}",
                                Integer.toHexString(errorCode)));
                        break;
                    case EXCEPTION:
                        //取流异常
                        mPlayerStatus = PlayerStatus.EXCEPTION;
                        mPlayer.stopPlay();//TODO 注意:异常时关闭取流
                        playHintText.setVisibility(View.VISIBLE);
                        playHintText.setText(MessageFormat.format("取流发生异常，错误码：{0}",
                                Integer.toHexString(errorCode)));
                        break;
                }
            }
        });
    }

    /*************************TextureView.SurfaceTextureListener 接口的回调方法********************/
    //TODO 注意:APP前后台切换时 SurfaceTextureListener可能在有某些华为手机上不会回调，例如：华为P20，因此我们需要在Activity生命周期中手动调用回调方法
    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        L.e("onSurfaceTextureAvailable执行---width=" + width + "---height=" + height);
        if (isFirst) {
            L.e("onSurfaceTextureAvailable执行---进行播放操作");
            isFirst = false;
            if (mPlayerStatus != PlayerStatus.SUCCESS) {
                startRealPlay(textureView.getSurfaceTexture());
            }
        }
        if (mPlayerStatus == PlayerStatus.STOPPING) {
            //恢复处于暂停播放状态的窗口
            startRealPlay(textureView.getSurfaceTexture());
            L.e("onSurfaceTextureAvailable: startRealPlay");
        }
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        L.e("onSurfaceTextureSizeChanged执行---width=" + width + "---height=" + height);
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        if (mPlayerStatus == PlayerStatus.SUCCESS) {
            mPlayerStatus = PlayerStatus.STOPPING;//暂停播放，再次进入时恢复播放
            mPlayer.stopPlay();
            L.e("onSurfaceTextureDestroyed: stopPlay");
        }
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        L.e("onSurfaceTextureUpdated执行");
    }
}
